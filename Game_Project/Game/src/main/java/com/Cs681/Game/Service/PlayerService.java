package com.Cs681.Game.Service;

import java.util.List;

import com.Cs681.Game.Model.Player;

public interface PlayerService {
    List<Player> getPlayers(String gameId);
}
