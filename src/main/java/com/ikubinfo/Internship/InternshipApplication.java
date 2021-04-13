package com.ikubinfo.Internship;

import com.ikubinfo.Internship.service.AdminService;
import com.ikubinfo.Internship.service.FinancierService;
import com.ikubinfo.Internship.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//Roles:
//		1-Employees
//		2-Financier
//		3-Administrators
//
//		Employees: Has the right to supply the cars, to see the status of his shift as well as to view the status of the previous days through a search.
//
//		Financier: Looks at the condition of all employees, annual statistics with the order of months by turnover, monthly turnover, daily, daily peak, how many cars are supplied per day, etc.
//
//		Administrator: Manages the creation of employees, financiers, setting fuel prices.
//		Fuel prices should be kept in the form of a history so that we do not lose the price reference on a certain day in case the price changes.



//my biggest question for now is what`s the best way of converting from dto to entity, especially when i have entities
// that have relation to other entities, should i include these relation in dto and who should i handle the conversion

@SpringBootApplication
public class InternshipApplication implements CommandLineRunner {
	@Autowired
	AdminService adminService;
	@Autowired
	WorkerService workerService;
	@Autowired
	FinancierService financierService;


	public static void main(String[] args) {
		SpringApplication.run(InternshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
