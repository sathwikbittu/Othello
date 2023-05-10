package com.Cs681.Game.Controller;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Cs681.Game.Model.HighScores;
import com.Cs681.Game.Model.Player;
import com.Cs681.Game.Model.Room;
import com.Cs681.Game.Model.User;
import com.Cs681.Game.OthelloGame.OthelloGameService;
import com.Cs681.Game.Repo.HighScoresRepo;
import com.Cs681.Game.Repo.PlayerRepository;
import com.Cs681.Game.Repo.PlayerRepositoryJpaImpl;
import com.Cs681.Game.Repo.RoomRepo;
import com.Cs681.Game.Repo.UserRepo;
import com.Cs681.Game.Service.LoginService;
import com.Cs681.Game.Service.OtpService;
import com.Cs681.Game.Service.PlayerService;
import com.Cs681.Game.Service.UserAlreadyExists;
import com.Cs681.Game.Util.JwtUtil;

import jakarta.mail.MessagingException;


@RestController
@CrossOrigin(origins = "*")
public class GameController {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private OtpService otpService;
	@Autowired
	private UserAlreadyExists userCheck;
	@Autowired
	private LoginService loginService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private OthelloGameService othelloGame;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private PlayerRepositoryJpaImpl playerRepo;
	@Autowired
	private RoomRepo roomRepo;
	@Autowired
	private HighScoresRepo highScoresRepo;
	
	 private List<Player> players = new ArrayList<>();
	    private OthelloGameService gameService = new OthelloGameService();
	    private String gameId = "";
	    private boolean moved = false;
	@PostMapping("/registration")
	public String userRegistration(@RequestBody User user) {
		System.out.println("TESTING"+user.getFirstName());
		userRepo.save(user);
		String token = jwtUtil.generateToken(user.getUserName());
		return "hi";

	}
	@PostMapping("/otpVerification")
	public String otpVerification(@RequestBody User user) throws MessagingException {
		String check = userCheck.checkUser(user);
		if(check==null) {
		System.out.println("UserEmail: "+user.getEmailAddress());
		try {
			
			return otpService.sendOTP(user.getEmailAddress());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		}else {
			return check;
		}
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		System.out.println("userName: "+user.getUserName());
		boolean check = loginService.loginCheck(user.getUserName(), user.getUserPassword());
		System.out.println("CHECK: "+check);
		if(check==true) {
			Player player = new Player();
	        player.setName(user.getUserName());
	        player.setReady(true);
	        //gameId = UUID.randomUUID().toString();
	        gameId = "test";
	        System.out.println("GAMEID: "+gameId);
	        playerRepo.save(player);
			//players.add(new Player(user.getUserName()));
	        String token = jwtUtil.generateToken(user.getUserName());
	        System.out.println(token);
	        return token;
			
		}
		return null;
		
	}
	
	@GetMapping("/highScores")
	public List<HighScores> getHighScores(){
		return highScoresRepo.findAll();
	}
	
	@PostMapping("/createRoom")
	public String createRoom(@RequestBody Room room) {
		roomRepo.save(room);
		return "roomCreated";
		
	}
	@MessageMapping("/join")
    @SendTo("/topic/joinRoom")
	public ResponseEntity<Room> joinRoom(@Payload Room room) {
		System.out.println("HERE JOINED ROOM");
		String joinedPlayerName = room.getJoinedPlayerName();
		Room roomReady = roomRepo.findByUserName(room.getUserName());
		roomReady.setJoinedPlayerName(joinedPlayerName);
		roomRepo.save(roomReady);
		
		return ResponseEntity.ok(roomReady);
		
	}
	@MessageMapping("/currentPlayer")
    @SendTo("/topic/currentPlayer")	
	public ResponseEntity<String> currentPlayer(@RequestBody Room room) {
		return ResponseEntity.ok(String.valueOf(othelloGame.getCurrentPlayer()));
		
	}

//	@PostMapping("/start")
//    public ResponseEntity<String> startGame() {
//		System.out.println("Start");
//        othelloGame = new OthelloGameService();
//        return ResponseEntity.ok("New game started.");
//    }
	
//	@Autowired
//	private SocketIOServer server;
//
//	@PostConstruct
//	private void init() {
//	    server.addConnectListener(client -> {
//	        System.out.println("Client connected: " + client.getSessionId());
//	    });
//
//	    server.addDisconnectListener(client -> {
//	        System.out.println("Client disconnected: " + client.getSessionId());
//	    });
//
//	    server.addEventListener("boardUpdate", Object.class, (client, data, ackSender) -> {
//	        System.out.println("Board updated: " + data);
//	        // TODO: Handle board update
//	    });
//	    
//	}
	@MessageMapping("/hello")
    @SendTo("/topic/initial")
    public String chat(String msg) {
		msg = "Hi Nishith";
        System.out.println(msg);
        return msg;
    }
	@MessageMapping("/rooms")
    @SendTo("/topic/rooms")
	public ResponseEntity<List<Room>> rooms(@Payload String message) {
		return ResponseEntity.ok(roomRepo.findAll());
		
	}
	@MessageMapping("/movedOrNot")
    @SendTo("/topic/moved")
	public ResponseEntity<String> movedOrNot(@Payload String message){
		System.out.println("HERE AT MOVEDDDDDDDD");
		boolean movedOrNot = moved;
		if(movedOrNot) {
			moved = false;
			System.out.println("HERE MOVED OR NOT");
			return ResponseEntity.ok("MOVED");
		}
		return ResponseEntity.ok("NotMoved");
		
	}


	@MessageMapping("/kai")
    @SendTo("/topic/board")
	public ResponseEntity<char[][]> getBoard1(@Payload String message) {
    	System.out.println("HERE IN MESSAGE MAPPING");
    	
        if (othelloGame == null) {
        	System.out.println("Inside");
            return ResponseEntity.badRequest().body(null);
        }
        char[][] board = othelloGame.getBoard();
        System.out.println("Board: "+board[3][3]);
        //server.getBroadcastOperations().sendEvent("boardUpdate", (Object[]) board);

        
        return ResponseEntity.ok(board);
    }
	@MessageMapping("/winner")
    @SendTo("/topic/gameWinner")
	public ResponseEntity<String> getWinner(@Payload String message) {
		Long roomId =  (long) Integer.parseInt(message);
//		System.out.println("roomId: "+roomId);
//		Room room = roomRepo.getById(roomId);
//		User blackUser = userRepo.findByUserName(room.getUserName()).get(0);
//		User whiteUser = userRepo.findByUserName(room.getJoinedPlayerName()).get(0);
//		HighScores highScoreOfWhite = highScoresRepo.getById(whiteUser.getUserId());
//		HighScores highScoreOfBlack = highScoresRepo.getById(blackUser.getUserId());

//		if( highScoreOfWhite!= null) {
//			highScoreOfWhite.setNumberOfMatches(highScoreOfWhite.getNumberOfMatches()+1);
//		}
//		else {
//			highScoreOfWhite.setUserId(whiteUser.getUserId());
//			highScoreOfWhite.setUserName(whiteUser.getUserName());
//			highScoreOfWhite.setNumberOfMatches(1);
//		}
//
//		if( highScoreOfBlack!= null) {
//			highScoreOfBlack.setNumberOfMatches(highScoreOfBlack.getNumberOfMatches()+1);
//		}
//		else {
//			highScoreOfBlack.setUserId(blackUser.getUserId());
//			highScoreOfBlack.setUserName(blackUser.getUserName());
//			highScoreOfBlack.setNumberOfMatches(1);
//		}
    	char win = othelloGame.getWinner();
    	String winner = "";
    	if(win=='W') {
    		winner = "white";
//    		if( highScoreOfWhite!= null) {
//    			highScoreOfWhite.setNumberOfWins(highScoreOfWhite.getNumberOfWins()+1);
//    			if(highScoreOfWhite.getHighestScore()<othelloGame.getWhiteCount()) {
//    				highScoreOfWhite.setHighestScore(othelloGame.getWhiteCount());
//    			}
//    			
//    		}
//    		else {
//    			highScoreOfWhite.setHighestScore(othelloGame.getWhiteCount());
//    			}
    	}
    	else if(win=='B'){
    		winner = "black";
//    		if( highScoreOfBlack!= null) {
//    			highScoreOfBlack.setNumberOfWins(highScoreOfBlack.getNumberOfWins()+1);
//    			if(highScoreOfBlack.getHighestScore()<othelloGame.getBlackCount()) {
//    				highScoreOfBlack.setHighestScore(othelloGame.getBlackCount());
//    			}
//    			
//    		}
//    		else {
//    			highScoreOfBlack.setHighestScore(othelloGame.getBlackCount());
//    			}
    	}
    	else {
    		winner="tie";
    	}
    	//highScoresRepo.save(highScoreOfWhite);
    	//highScoresRepo.save(highScoreOfBlack);
		roomRepo.deleteById(roomId);

        return ResponseEntity.ok(winner);
    }
	
	
    @GetMapping("/board")
    public ResponseEntity<char[][]> getBoard() {
    	System.out.println("here");
    	
        if (othelloGame == null) {
        	System.out.println("Inside");
            return ResponseEntity.badRequest().body(null);
        }
        char[][] board = othelloGame.getBoard();
        System.out.println("Board: "+board[3][3]);
        //server.getBroadcastOperations().sendEvent("boardUpdate", (Object[]) board);

        
        return ResponseEntity.ok(board);
    }
  

    @PostMapping("/move")
    public ResponseEntity<String> makeMove(@RequestParam int row, @RequestParam int col) {
    	System.out.println("ROW AND COLUMN: "+row+" "+col);
        if (othelloGame == null) {
            return ResponseEntity.badRequest().body("Game not started.");
        }
        boolean validMove = othelloGame.makeMove(row, col);
        if (!validMove) {
            return ResponseEntity.badRequest().body("Invalid move.");
        }
        moved = true;
        System.out.println("HERE AT MOVED");
        othelloGame.switchPlayer();
        char[][] board = othelloGame.getBoard();
        //server.getBroadcastOperations().sendEvent("boardUpdate", (Object[]) board);

        return ResponseEntity.ok("Moved");

    }
    
//    private void updateBoard() {
//  	  char[][] board = othelloGame.getBoard();
//  	  // Update the board state
//      server.getBroadcastOperations().sendEvent("boardUpdate", (Object[]) board);
//
//  	}

    
    @PostMapping("/join-game")
    public ResponseEntity<String> joinGame(@RequestParam String playerName) {
        if (players.size() == 2) {
            return new ResponseEntity<>("Game is full", HttpStatus.BAD_REQUEST);
        }
        
//        Player player = new Player();
//        player.setName(playerName);
        gameId = UUID.randomUUID().toString();
//        player.setGameId(gameId);
//        System.out.println("PlayerName: "+playerName);
//        System.out.println("GAMEID: "+gameId);
//        playerRepo.save(player);
        return ResponseEntity.ok(gameId);
    }
    @PostMapping("/set-ready")
    public ResponseEntity<List<Player>> setReady(@RequestParam  String playerName, @RequestParam boolean isReady) {
    	players = playerRepo.findAll();
    	System.out.println("INSIDE SET READY");
        for (Player p : players) {
        	System.out.println("INSIDE FOR: "+p.getName());
            if (p.getName().equals(playerName)) {
                p.setReady(isReady);
                break;
            }
        }
        return ResponseEntity.ok(players);
    }
    @PostMapping("/start-game")
    public ResponseEntity<char[][]> startGame(@RequestBody String gameId) {
        if (!gameId.equals(this.gameId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (players.size() != 2 || players.stream().anyMatch(p -> !p.isReady())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        gameService = new OthelloGameService();
        System.out.println("GETBOARD: "+gameService.getBoard());
        return ResponseEntity.ok(gameService.getBoard());
    }
    @GetMapping("/players")
    public List<Player> getPlayers(@RequestParam String gameId) {
    	System.out.println("HERE PLAYERS");
        return playerRepo.findByGameId(gameId);
    }
  
 



   
}







	
