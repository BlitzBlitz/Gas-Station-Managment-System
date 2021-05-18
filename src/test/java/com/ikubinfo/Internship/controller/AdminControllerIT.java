package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.InternshipApplication;
import com.ikubinfo.Internship.dto.AdminDto;
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

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternshipApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class AdminControllerIT {
    @LocalServerPort
    private int port;

    @Test
    @DirtiesContext
    public void getAdminsTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/admins", null, HttpMethod.GET);
        JSONAssert.assertEquals("[{name : miri}, {name : cimi}]", response.getBody(), false);
    }
    @Test
    @DirtiesContext
    public void getAdminTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/admins/miri", null, HttpMethod.GET);
        JSONAssert.assertEquals("{name : miri}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void updateAdminTest() throws JSONException {
        ResponseEntity<String> response = sendRequest("/admins/miri", new AdminDto("miri","qwerty!2A"),
                HttpMethod.PUT);
        JSONAssert.assertEquals("{name : miri}", response.getBody(), false);
    }

    @Test
    @DirtiesContext
    public void deleteAdminTest() throws JSONException {
        sendRequest("/admins/miri", null, HttpMethod.DELETE);
        ResponseEntity<String> responseGet = sendRequest("/admins/miri", null,
                HttpMethod.GET);
        assertEquals(HttpStatus.NOT_FOUND, responseGet.getStatusCode());
    }


    public ResponseEntity<String>  sendRequest(String url,
                                           Object body, HttpMethod method){
        url = "http://localhost:" + port + url;
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(body, headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return testRestTemplate.exchange(url, method, entity, String.class);
    }

}
