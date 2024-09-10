package com.karthik.axonsaga.projection;

import java.util.List;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.karthik.axonsaga.entity.User;
import com.karthik.axonsaga.repository.UserRepository;
import com.karthik.axonsaga.repository.WalletRepository;
import com.karthik.userservice.queries.GetAllUsersQuery;
import com.karthik.userservice.queries.GetUserByIdQuery;

@Component
public class UserProjection {

	@Autowired
	UserRepository userRepository;

	@Autowired
	WalletRepository walletRepository;

	@QueryHandler
	public List<User> getAllUsersQuery(GetAllUsersQuery query) {

		return userRepository.findAll();
	}

	@QueryHandler
	public User getUserByIdQuery(GetUserByIdQuery query) {

		return userRepository.findById(Integer.valueOf(query.getId())).orElse(null);
	}
}
