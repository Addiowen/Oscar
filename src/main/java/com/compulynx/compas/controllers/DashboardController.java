package com.compulynx.compas.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.DashboardStats;
import com.compulynx.compas.handlers.LineChart;
import com.compulynx.compas.models.extras.CustomerStats;
import com.compulynx.compas.repositories.BranchRepository;
import com.compulynx.compas.repositories.CustomerRepository;
import com.compulynx.compas.repositories.UserRepository;

@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class DashboardController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/dashboard/stats")
	public ResponseEntity<?> starts() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH);
		int custs = customerRepository.countIssuedCard();
		int bra = branchRepository.getBranchCount();
		int users = userRepository.getUserCount();
		System.out.println("Year###"+year +"month##"+month);
		int menroll = customerRepository.getMonthlyCount(year,month+1);
		List<CustomerStats> monthlystats = customerRepository.getYearlyCustomerStats();
		ArrayList<Integer> series = new ArrayList<Integer>();
		ArrayList<String> usernames =new ArrayList<>();
		
		for(CustomerStats stat: monthlystats) {
			
			series.add(stat.getCount());
			
		}
		
		ArrayList<ArrayList<Integer>> outer = new ArrayList<ArrayList<Integer>>();
		
		outer.add(series);
		
		return new ResponseEntity<>(new DashboardStats(custs,
				new LineChart(LineChart.YEAR,outer),bra,users,
				menroll,usernames), HttpStatus.OK);
	}
}
