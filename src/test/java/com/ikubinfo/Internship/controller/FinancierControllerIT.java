package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.InternshipApplication;
import com.ikubinfo.Internship.dto.FinancierDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternshipApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class FinancierControllerIT {
    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext
    public void getFinancierTest() throws JSONException {
        String url = "http://localhost:" + port + "/financiers/beni";
        ResponseEntity<String> response = sendRequest(url, null, HttpMethod.GET);
        JSONAssert.assertEquals("{username : beni}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void registerFinancierTest() throws JSONException {
        String url = "http://localhost:" + port + "/financiers";
        ResponseEntity<String> response = sendRequest(url,
                new FinancierDto("genti", "Password!1", 3000.0),
                HttpMethod.POST);
        JSONAssert.assertEquals("{username : genti}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void updateFinancierTest() throws JSONException {
        String url = "http://localhost:" + port + "/financiers/beni";
        ResponseEntity<String> response = sendRequest(url,
                new FinancierDto("beni", "Password!1", 2500.0),
                HttpMethod.PUT);
        JSONAssert.assertEquals("{username : beni, salary: 2500.0}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void deleteFinancierTest() throws JSONException {
        String url = "http://localhost:" + port + "/financiers/beni";
        sendRequest(url, null, HttpMethod.DELETE);
        ResponseEntity<String> responseGet = sendRequest(url, null, HttpMethod.GET);
        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    @DirtiesContext
    public void investTest() throws JSONException {
        String url = "http://localhost:" + port + "/financiers/beni/invest";
        ResponseEntity<String> response = sendRequest(url,2000.0, HttpMethod.POST);
        assertEquals(102000.0, Double.parseDouble(response.getBody()));
    }

    @Test
    @DirtiesContext
    public void collectDailyTotalTest() throws JSONException {
        String url = "http://localhost:" + port + "/financiers/beni/collectDailyTotal";
        ResponseEntity<String> response = sendRequest(url, null, HttpMethod.POST);
        assertEquals(106000.0, Double.parseDouble(response.getBody()));
    }

    public ResponseEntity<String> sendRequest(String url, Object body, HttpMethod method) {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(body, headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return testRestTemplate.exchange(url, method, entity, String.class);
    }
}
