package com.Cs681.Game.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cs681.Game.Model.Room;
import com.Cs681.Game.Model.User;

public interface RoomRepo extends JpaRepository<Room, Long> {
	Room findByUserName(String userName);


}
