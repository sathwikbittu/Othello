package project.game.Othello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import project.game.Othello.model.Movesinfo;
import project.game.Othello.model.Othelloroom;
import project.game.Othello.service.Playothello;

@Controller
public class OthelloRoomController {

	    private final Playothello playRoomService;

	    @Autowired
	    public OthelloRoomController(Playothello playRoomService) {
	        this.playRoomService = playRoomService;
	    }

	    @MessageMapping("/register")
	    @SendTo("/topic/start")
	    public Othelloroom register(String username) {
	        playRoomService.startGame();
	        return playRoomService.playerInfo(username);
	    }

	    @MessageMapping("/move")
	    @SendTo("/topic/move")
	    public Othelloroom move(Movesinfo details) throws Exception {
	        return playRoomService.move(details);
	    }
	}
