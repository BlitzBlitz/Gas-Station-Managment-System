package com.ikubinfo.Internship.controller;


import com.ikubinfo.Internship.InternshipApplication;
import com.ikubinfo.Internship.dto.WorkerDto;
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
public class WorkerControllerIT {
    @LocalServerPort
    private int port;


    @Test
    @DirtiesContext
    public void getWorkersTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/workers", null, HttpMethod.GET);
        System.out.println(response.getBody());
        JSONAssert.assertEquals("[{name : agimi}]", response.getBody(), false);
    }
    @Test
    @DirtiesContext
    public void getWorkerTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/workers/agimi", null, HttpMethod.GET);
        System.out.println(response.getBody());
        JSONAssert.assertEquals("{name : agimi}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void registerWorkerTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/workers",
                new WorkerDto("genci", "Password!1", 200.0, 2000.0), HttpMethod.POST);
        JSONAssert.assertEquals("{name : genci}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void updateWorkerTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/workers/agimi",
                new WorkerDto("agimi", "Password!1", 2020.0, 3000.0), HttpMethod.PUT);
        JSONAssert.assertEquals("{name : agimi, salary: 3000}", response.getBody(), false);
    }
    @Test
    @DirtiesContext
    public void deleteWorkerTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/workers/agimi",
                null, HttpMethod.DELETE);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    public void getShiftBalanceTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/workers/agimi/shiftBalance",
                null, HttpMethod.GET);
        assertEquals(1000.0, response.getBody());
    }
    @Test
    @DirtiesContext
    public void getShiftsHistoryTest() throws JSONException {

        ResponseEntity<String> response = sendRequest("/workers/agimi/shiftHistory",
                null, HttpMethod.GET);
        System.out.println(response.getBody());
        assertEquals(1000.0, response.getBody());
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
