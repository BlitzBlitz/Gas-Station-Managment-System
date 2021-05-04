package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.InternshipApplication;
import com.ikubinfo.Internship.dto.FuelDto;
import com.ikubinfo.Internship.dto.FuelSupplyDataDto;
import com.ikubinfo.Internship.dto.PriceDataDto;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternshipApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class FuelControllerIT {
    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext
    public void getAllFuelsTest() throws JSONException {
        String url = "http://localhost:" + port + "/fuels";
        ResponseEntity<String> response = sendRequest(url, null, HttpMethod.GET);
        JSONAssert.assertEquals("[{type : gas}]", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void getFuelTest() throws JSONException {
        String url = "http://localhost:" + port + "/fuels/gas";
        ResponseEntity<String> response = sendRequest(url, null, HttpMethod.GET);
        JSONAssert.assertEquals("{type : gas}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void getFuelHistoryTest() throws JSONException {
        String url = "http://localhost:" + port + "/fuels/gas/history";
        ResponseEntity<String> response = sendRequest(url, null, HttpMethod.GET);
        JSONAssert.assertEquals("[{price:120.0}, {price:122.0}]",
                response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void addFuelTest() throws JSONException {
        String url = "http://localhost:" + port + "/fuels";
        ResponseEntity<String> response = sendRequest(url,
                new FuelDto("gasoline", 120., 3000.0),
                HttpMethod.POST);
        JSONAssert.assertEquals("{type : gasoline}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void changePriceTest() throws JSONException {
        String url = "http://localhost:" + port + "/fuels/gas";
        ResponseEntity<String> response = sendRequest(url,
                new PriceDataDto(130.0, "gas", "miri", new Date()),
                HttpMethod.PUT);
        JSONAssert.assertEquals("{type : gas, currentPrice:130}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void deleteFuelTest() throws JSONException {
        String url = "http://localhost:" + port + "/fuels/gas";
        sendRequest(url, null, HttpMethod.DELETE);
        ResponseEntity<String> responseGet = sendRequest(url, null, HttpMethod.GET);
        System.out.println(responseGet.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    @DirtiesContext
    public void addFuelSupplyTest() throws JSONException {
        String url = "http://localhost:" + port + "/fuels/gas";
        ResponseEntity<String> response = sendRequest(url,
                new FuelSupplyDataDto("gas", 120.0, 10.0, "beni"),
                HttpMethod.POST);
        System.out.println(response.getBody());
        JSONAssert.assertEquals("{fuelType : gas, price:120, amount: 10, boughtBy: beni}", response.getBody(), false);
    }

    public ResponseEntity<String> sendRequest(String url,
                                              Object body, HttpMethod method) {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(body, headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return testRestTemplate.exchange(url, method, entity, String.class);
    }
}
