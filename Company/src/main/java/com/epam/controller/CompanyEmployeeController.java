package com.epam.controller;

import java.nio.charset.Charset;
import com.epam.dto.EmployeeDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/company")
public class CompanyEmployeeController {

	@Autowired
	RestTemplate restTemplate;

	public static final String EMPLOYEE_SERVICE = "employeeService";

	@Value("${employee.url}")
	private String url;

	HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

	@CircuitBreaker(name=EMPLOYEE_SERVICE, fallbackMethod = "fallbackMethod")
	@GetMapping("/employee/employeelist")
	public ResponseEntity<String> getAllEmployee() {

		HttpEntity<String> entity = new HttpEntity<String>(createHeaders("admin", "password"));
		return restTemplate.exchange(url+"/employeelist", HttpMethod.GET, entity, String.class);
	}

	public ResponseEntity<String> fallbackMethod(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CompanyService Down");
	}

	@GetMapping("/employee/{firstname}")
	public ResponseEntity<String> getEmployeeByName(@PathVariable String firstname){
		HttpEntity<String> entity = new HttpEntity<String>(createHeaders("admin", "password"));
		return restTemplate.exchange(url+"/"+firstname, HttpMethod.GET, entity, String.class);
	}

	@PostMapping("/employee/addEmployee")
	public ResponseEntity<String> addEmployee(@RequestBody EmployeeDto employeeDto){
		HttpEntity<EmployeeDto> entity = new HttpEntity<>(employeeDto,createHeaders("admin", "password"));
		return restTemplate.exchange(url+"/addEmployee", HttpMethod.POST, entity, String.class);
	}

	@DeleteMapping("/employee/{firstname}")
	public ResponseEntity<String> removeBook(@PathVariable String firstname) {

		HttpEntity<String> entity = new HttpEntity<String>(createHeaders("admin", "password"));
		return restTemplate.exchange(url+"/"+firstname, HttpMethod.DELETE, entity, String.class);
	}
}