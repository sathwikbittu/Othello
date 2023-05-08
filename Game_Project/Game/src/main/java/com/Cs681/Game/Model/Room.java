package com.Cs681.Game.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Room {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roomId;
	private String userName;
	private String roomName;
	private String joinedPlayerName;
	

	public long getRoomId() {
		return roomId;
	}


	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public String getJoinedPlayerName() {
		return joinedPlayerName;
	}


	public void setJoinedPlayerName(String joinedPlayerName) {
		this.joinedPlayerName = joinedPlayerName;
	}

}
