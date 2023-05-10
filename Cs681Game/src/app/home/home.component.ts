import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NavigationExtras, Router } from '@angular/router';
import { OthelloService } from '../othello.service';
import { Player } from '../player';
import { Stomp } from '@stomp/stompjs';
import { Room } from '../room';
import { Rooms } from '../rooms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  board: any;
  players: any[] = [];
  currentUser = localStorage.getItem("userName") || '{}';
  isReady: boolean = false;
  gameId: any = '';
  otherPlayerReady: boolean = false;
  roomLength =0;
  data = 'room';
  rooms: Room[] = [];
  private ws: any;
  roomName = "";
  room: Room = new Room(0,"","","");
  message: any;
  roomInfo: Room = new Room(0,"","","");
  roomOccupied: boolean = false;

  getRooms: Rooms[] = [];
  
  constructor(private http: HttpClient, private router: Router, private othelloService: OthelloService,) { }

  home() {
    this.router.navigate(['/home']);
  }

  async ngOnInit() {
    const socket = new WebSocket("ws://localhost:8080/othello");

    this.ws = Stomp.over(socket);
    const _this = this;
    this.ws.connect({}, async function(frame:any) {
      _this.room.roomName = _this.currentUser;
      _this.room.userName = _this.currentUser;
      //await _this.startGame();
      _this.ws.send("/game/rooms", {}, _this.data);
      _this.ws.subscribe("/topic/rooms", function(message: any) {
        console.log("MESSAGE:", message.body);
       let response = JSON.parse(message.body);
       console.log("Rooms: "+response.body);
       _this.getRooms = response.body.map((row: Rooms) => {
          _this.roomLength = _this.getRooms.length;
          let room = new Room(row.roomId,row.userName, row.roomName, row.joinedPlayerName);
          if(room.joinedPlayerName!="" && _this.currentUser==room.userName){
            localStorage.setItem(room.userName,"B");
            let num = _this.room.roomId;
            localStorage.setItem("roomId",num.toString());
            localStorage.setItem(room.joinedPlayerName,"W");
            window.location.href="/othello";
          }
          console.log("Room name:", room.joinedPlayerName);
          return room;
        
      });
       //_this.board = response.body;
       
       console.log("Board:", _this.rooms);
     }, function(error: any) {
       console.error("Subscription error:", error);
     });
    });

   
  }
  

  logout() {
    // Clear the user's authentication token or any relevant session data here
    // ...
    localStorage.removeItem('token');
    //this.othelloService.joinGame(this.currentUser)
    // Redirect the user to the login page
    window.location.href='/login';
  }

  async joinGame() {
    // make a request to join the game and get the game id
    this.othelloService.joinGame(this.currentUser)
      .subscribe(gameId => {
        this.gameId = gameId;
      });
  }

   ready() {
    //await this.joinGame();
    // set the current user as ready for the game
    this.othelloService.setReady(this.currentUser, true)
      .subscribe((players: Player[]) => {
        this.players = players;
        this.isReady = true;
        this.players.length= 2;
        console.log("players: "+this.players.length );
        if (this.players.length == 2) {
          this.waitForOtherPlayerReady();
        }
      });
  }

  waitForOtherPlayerReady() {
    // wait for the other player to become ready
    const interval = setInterval(() => {
      this.othelloService.getPlayers(this.gameId).subscribe((players: Player[]) => {

      
        const otherPlayer = players.find(player => player.name !== this.currentUser);
        if (otherPlayer && otherPlayer.isReady) {
          clearInterval(interval);
          this.otherPlayerReady = true;
          if (this.isReady && this.otherPlayerReady) {
            this.startGame();
          }
        
          }
        });
    }, 1000);
  }

  
  createRoom() {
    this.othelloService.roomCreate(this.room).subscribe(response => {
      console.log("Created room successfully");
      alert("Room Has Been Created");
      this.ngOnInit();
    });
  }
  async joinRoom(room: Room) {
    const _this= this;
    const socket = new WebSocket("ws://localhost:8080/othello");
    room.joinedPlayerName = this.currentUser;
    this.ws = Stomp.over(socket);
    this.ws.connect({}, async function(frame:any) {
    // this.othelloService.joinRoom(room).subscribe((roomInfo: Room) => {
    //   console.log("RoomInfo: "+roomInfo);
     //     this.roomInfo = roomInfo; // update roomInfo with the selected room details
    //     window.location.href = '/othello';
    //   }
    // });
      await new Promise<void>(resolve => {
     _this.ws.send("/game/join", {}, JSON.stringify(room));
        _this.ws.subscribe("/topic/joinRoom", async function(message: any) {
          console.log("after joined game:", message.body);
          let response = JSON.parse(message.body);
          console.log("RoomsInfo: "+response.body.joinedPlayerName);
          _this.roomInfo = new Room(response.body.roomId, response.body.userName, response.body.roomName, response.body.joinedPlayerName);
            console.log("kai:"+_this.currentUser  +_this.roomInfo.userName  +_this.roomInfo.joinedPlayerName);
            
              
            if(_this.currentUser==_this.roomInfo.userName || _this.currentUser==_this.roomInfo.joinedPlayerName){
              localStorage.setItem(_this.roomInfo.userName,"B");
              localStorage.setItem(_this.roomInfo.joinedPlayerName,"W");
              console.log("userName: "+localStorage.getItem(_this.roomInfo.userName));
              console.log("joined: "+localStorage.getItem(_this.roomInfo.joinedPlayerName));
              let num = _this.roomInfo.roomId;
              localStorage.setItem("roomId",num.toString());
                await _this.ngOnInit();
                window.location.href='/othello';
            }
            resolve();

})
})
    })

  }

  async startGame() {
    // start the game with the game id
    // this.othelloService.startGame(this.gameId)
    //   .subscribe(board => this.board = board);


      window.location.href='/othello';

}
}
