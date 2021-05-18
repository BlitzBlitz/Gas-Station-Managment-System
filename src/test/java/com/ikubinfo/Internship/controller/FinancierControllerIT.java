package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.InternshipApplication;
import com.ikubinfo.Internship.dto.FinancierDto;
import org.json.JSONException;
import org.junit.Test;
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
        ResponseEntity<String> response = sendRequest("/financiers/beni", null, HttpMethod.GET);
        JSONAssert.assertEquals("{username : beni}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void registerFinancierTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/financiers",
                new FinancierDto("genti", "Password!1", 3000.0),
                HttpMethod.POST);
        JSONAssert.assertEquals("{username : genti}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void updateFinancierTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/financiers/beni",
                new FinancierDto("beni", "Password!1", 2500.0),
                HttpMethod.PUT);
        JSONAssert.assertEquals("{username : beni, salary: 2500.0}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void deleteFinancierTest() throws JSONException {
        sendRequest("/financiers/beni", null, HttpMethod.DELETE);
        ResponseEntity<String> responseGet = sendRequest("/financiers/beni", null, HttpMethod.GET);
        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }

    @Test
    @DirtiesContext
    public void investTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/financiers/beni/invest",2000.0, HttpMethod.POST);
        assertEquals(102000.0, Double.parseDouble(response.getBody()));
    }

    @Test
    @DirtiesContext
    public void collectDailyTotalTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/financiers/beni/collectDailyTotal", null, HttpMethod.POST);
        assertEquals(106000.0, Double.parseDouble(response.getBody()));
    }

    public ResponseEntity<String> sendRequest(String url, Object body, HttpMethod method) {
        url = "http://localhost:" + port + url;
        System.out.println(url);
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(body, headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return testRestTemplate.exchange(url, method, entity, String.class);
    }
}
