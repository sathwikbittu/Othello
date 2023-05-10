import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Player } from './player';
import { Observable } from 'rxjs';
//import {Socket, io} from 'socket.io-client';
import * as SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { Room } from './room';
import { HighScores } from './HighScores';

@Injectable({
  providedIn: 'root'
})
export class OthelloService {
  private ws: any;
  board: string[][] = [];

  //private socket: Socket;
  //private socket = new SockJS('/reversi');
  //stompClient = Stomp.over(this.socket);
  //private BASE_URL = 'http://localhost:4200';
  private baseUrl = 'http://localhost:8080'; // Replace with your own API endpoint
  
  constructor(private http: HttpClient) {
    //this.socket = io(this.baseUrl);

  }

  getBoard() {
    
    return this.http.get<any>(`${this.baseUrl}/board`);
  }
  connect() {
    const socket = new WebSocket("ws://localhost:8080/othello");

    this.ws = Stomp.over(socket);
    const _this = this;
    this.ws.connect({}, function(frame:any) {
      console.log('Connected: ' + frame);
      let data = JSON.stringify({
        'name' : 'Nishith'
      })
      _this.ws.send("/game/kai", {}, data);


    });
    // _this.ws.subscribe('/topic/board', (response: any) => {
    //   console.log("Response: "+response.body);
    // });
  }
  roomCreate(room: any){
    return this.http.post("http://localhost:8080/createRoom",room,{responseType:'text' as 'json'});

  }
  joinRoom(room: any){
    return this.http.post<Room>("http://localhost:8080/joinRoom",room,{responseType:'text' as 'json'});

  }

  highScores(){
    return this.http.get<HighScores[]>("http://localhost:8080/highScores");
  }
  // getBoardUpdates(): Observable<any> {
  //   return new Observable<any>(observer => {
  //     this.socket.on('boardUpdate', (board: any) => {
  //       observer.next(board);
  //     });
  //   });
  // }
  onConnect() {
    this.recieveData;
  }
  
  makeMove(row: number, col: number) {
    
    // let data = JSON.stringify({
    //   'name' : 'Nishith'
    // })
    // this.ws.send("/game/kai", {}, data);
    // this.recieveData();
    return this.http.post(`${this.baseUrl}/move?row=${row}&col=${col}`, null, { responseType: 'text' });
    //return this.socket.emit('make-move', { row, col });

}
sendData(){
  let data = JSON.stringify({
    'name' : 'Nishith'
  })
  this.ws.send("/game/kai", {}, data);
}
recieveData(): Observable<string[][]> {
  return new Observable(observer => {
  const _this = this;
  this.ws.subscribe("/topic/board", function(message: any) {
    console.log("MESSAGE:", message.body);
    let response = JSON.parse(message.body);
    _this.board = response.body.map((row: string) => Array.from(row));
    //_this.board = response.body;
    console.log("Board:", _this.board);
  }, function(error: any) {
    console.error("Subscription error:", error);
  });
  //return _this.board;
});
  
}
// makeMove(row: number, col: number) {
//   return this.stompClient.send("/topic/board", {}, JSON.stringify({row: row, col: col}));
// }

 
 
  
  
  // startGame() {
  //   return this.http.post<any>(`${this.baseUrl}/start`, {});
  // }

  startGame(gameId: string) {
    return this.http.get(`${this.baseUrl}/start-game/${gameId}`);
  }
  joinGame(playerName: string) {
    //return this.http.post(`${this.baseUrl}/join-game`, { playerName }, { responseType: 'text' });
    return this.http.post(`${this.baseUrl}/join-game?playerName=${playerName}`, null, { responseType: 'text' });

  }

  setReady(playerName: string, isReady: boolean) {
    //return this.http.post(`${this.baseUrl}/set-ready`, { playerName, isReady });
    return this.http.post<Player[]>(`${this.baseUrl}/set-ready?playerName=${playerName}&isReady=${isReady}`,null);

  }
  getPlayers(gameId: string) {
    return this.http.get<Player[]>(`${this.baseUrl}/players?gameId=${gameId}`);
  }
  // connectSocket() {
  //   if (!this.socket.connected) {
  //     this.socket.connect();
  //   }
  // }

  // disconnectSocket() {
  //   if (this.socket.connected) {
  //     this.socket.disconnect();
  //   }
  // }
  
  
}






// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Player } from './player';
// import { Observable } from 'rxjs';
// import { io,Manager,Socket,SocketOptions } from 'socket.io-client';

// @Injectable({
//   providedIn: 'root'
// })
// export class OthelloService {
//   private socket: Socket;
//   private baseUrl = 'http://localhost:8080'; // Replace with your own API endpoint

//   constructor(private http: HttpClient) {
//     const baseUrl = 'http://localhost:8080'; // Replace with your own API endpoint
//     const options = { transports: ['websocket'] } as Partial<SocketOptions>;
//     this.socket = io(baseUrl, options);

//   }
//   getBoard() {
//     return this.http.get<any>(`${this.baseUrl}/board`);
//   }
  
//   makeMove(row: number, col: number) {
//     return this.socket.emit('make-move', { row, col });
//   }

//   startGame(gameId: string) {
//     return this.http.get(`${this.baseUrl}/start-game/${gameId}`);
//   }

//   joinGame(username: string) {
//     this.socket.emit('join-game', { username });
//   }

//   setReady(username: string, isReady: boolean) {
//     this.socket.emit('set-ready', { username, isReady });
//   }

//   getPlayers(gameId: string) {
//     return this.http.get<Player[]>(`${this.baseUrl}/players/${gameId}`);
//   }

//   connectSocket() {
//     this.socket.connect();
//   }

//   disconnectSocket() {
//     this.socket.disconnect();
//   }
// }
