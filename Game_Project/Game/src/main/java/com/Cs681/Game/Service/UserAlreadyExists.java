package com.Cs681.Game.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cs681.Game.Model.User;
import com.Cs681.Game.Repo.UserRepo;

@Service
public class UserAlreadyExists {
	@Autowired
	UserRepo userRepo;
	public String checkUser(User user) {
		if(!userRepo.findByUserName(user.getUserName()).isEmpty()) {
			return "UserName is already taken";
			
		}
		
		if(!userRepo.findByUserName(user.getEmailAddress()).isEmpty()) {
			return "EmailAddress is already registered";
			
		}
		if(!userRepo.findByUserName(user.getPhoneNumber()).isEmpty()) {
			return "Phone number is already registered";
			
		}
		
		
		return null;
	}

}
