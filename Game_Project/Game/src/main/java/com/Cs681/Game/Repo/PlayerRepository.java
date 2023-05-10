package com.Cs681.Game.Repo;

import java.util.List;

import com.Cs681.Game.Model.Player;

public interface PlayerRepository {
    List<Player> findByGameId(String gameId);
}
