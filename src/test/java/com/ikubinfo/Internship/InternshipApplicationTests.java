package com.ikubinfo.Internship;

import com.ikubinfo.Internship.repository.OrderRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InternshipApplicationTests {

	@Autowired
	OrderRepo orderRepo;

	@Test
	void contextLoads() {
	}

}
