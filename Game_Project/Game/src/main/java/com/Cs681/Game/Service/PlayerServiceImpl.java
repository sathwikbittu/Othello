package com.Cs681.Game.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Cs681.Game.Model.Player;
import com.Cs681.Game.Repo.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {
    
    private final PlayerRepository playerRepository;
    
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    
    @Override
    public List<Player> getPlayers(String gameId) {
        return playerRepository.findByGameId(gameId);
    }
}
