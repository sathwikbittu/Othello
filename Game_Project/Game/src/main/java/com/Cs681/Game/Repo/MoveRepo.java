package com.Cs681.Game.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cs681.Game.Model.Move;

public interface MoveRepo  extends JpaRepository<Move, Long> {
	List<Move> findByGameId(String gameId);

    // Additional methods if needed
}


