package com.ikubinfo.Internship.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminService.class)
@ActiveProfiles(value = "test")
public class WorkerServiceTest {


}
