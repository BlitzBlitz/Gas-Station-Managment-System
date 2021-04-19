package com.ikubinfo.Internship;

import com.ikubinfo.Internship.repository.OrderRepo;
import com.ikubinfo.Internship.service.AdminService;
import com.ikubinfo.Internship.service.FinancierService;
import com.ikubinfo.Internship.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class InternshipApplication implements CommandLineRunner {
	@Autowired
	AdminService adminService;
	@Autowired
	WorkerService workerService;
	@Autowired
	FinancierService financierService;
	@Autowired
	OrderRepo orderRepo;


	public static void main(String[] args) {
		SpringApplication.run(InternshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
