package com.Cs681.Game.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cs681.Game.Model.GameMoves;

public interface GameRepo extends JpaRepository<GameMoves, Long>{
	

}
