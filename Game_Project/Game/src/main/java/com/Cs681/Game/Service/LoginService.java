package com.Cs681.Game.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Cs681.Game.Model.User;
import com.Cs681.Game.Repo.UserRepo;

@Service
public class LoginService{
	@Autowired
	UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Boolean loginCheck(String userName, String userPassword) {
		List<User> users = userRepo.findByUserName(userName);
		User user = null;
		if(!users.isEmpty()) {
			user = users.get(0);
		}
		if(user!=null && passwordEncoder.matches(userPassword,user.getUserPassword())) {
			return true;
		}
		return false;
	

}
}
