package com.karthik.axonsaga.controller;

import java.util.List;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.karthik.axonsaga.entity.User;
import com.karthik.userservice.queries.GetAllUsersQuery;
import com.karthik.userservice.queries.GetUserByIdQuery;

@RestController
@RequestMapping("/userservice")
public class UserController {

	@Autowired
	private transient QueryGateway queryGateway;

	@GetMapping("/getAllUsers")
	public List<User> getAllUsersQuery() {

		GetAllUsersQuery getAllUsersQuery = new GetAllUsersQuery();
		List<User> users = queryGateway.query(getAllUsersQuery, ResponseTypes.multipleInstancesOf(User.class))
				.join();
		return users;
	}

	@GetMapping("/getUserById/{userId}")
	public User getUserByIdQuery(@PathVariable int userId) {

		GetUserByIdQuery getUserByIdQuery = new GetUserByIdQuery(userId);
		User user = queryGateway.query(getUserByIdQuery, ResponseTypes.instanceOf(User.class)).join();
		return user;
	}
}
