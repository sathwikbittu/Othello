package com.Cs681.Game.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cs681.Game.Model.User;

public interface UserRepo extends JpaRepository<User, Long>{
	List<User> findByUserName(String userName);
	List<User> findByEmailAddress(String emailAddress);
	List<User> findByPhoneNumber(String phoneNumber);

}
