package com.Cs681.Game.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Cs681.Game.Model.Player;

@Repository
public interface PlayerRepositoryJpaImpl extends PlayerRepository, JpaRepository<Player, Long> {
    List<Player> findByGameId(String gameId);
    
}

