package com.ikubinfo.Internship.controller;


import com.ikubinfo.Internship.InternshipApplication;
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

import java.time.LocalDate;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternshipApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class StatisticControllerIT {
    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext
    public void getYearlyStatisticsTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/statistics/yearly?year=2021", null, HttpMethod.GET);
        System.out.println(response.getBody());
        JSONAssert.assertEquals("[{name : agimi}]", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void getTotalTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/statistics/total?getBy=year",
                LocalDate.of(2021,5,4), HttpMethod.POST);
        System.out.println(response.getBody());
        JSONAssert.assertEquals("[{name : agimi}]", response.getBody(), false);
    }



    public ResponseEntity<String> sendRequest(String urlAdd, Object body, HttpMethod method) {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String url = "http://localhost:" + port + urlAdd;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(body, headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return testRestTemplate.exchange(url, method, entity, String.class);
    }
}
