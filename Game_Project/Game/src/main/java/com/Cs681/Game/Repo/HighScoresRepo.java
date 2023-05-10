package com.Cs681.Game.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cs681.Game.Model.HighScores;


public interface HighScoresRepo extends JpaRepository<HighScores, Long>{
	

}
