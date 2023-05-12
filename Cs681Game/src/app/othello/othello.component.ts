import { Component, OnInit } from '@angular/core';
import { OthelloService } from '../othello.service';
import { data, error } from 'jquery';
import { Observable } from 'rxjs';
import * as SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { Move } from '../move';

@Component({
  selector: 'app-othello',
  templateUrl: './othello.component.html',
  styleUrls: ['./othello.component.css']
})
export class OthelloComponent implements OnInit {

  board: string[][] = [];
  wrongMove: string = '';
  currentPlayer: string = 'B';
  title = 'WebSocketChatRoom';
  greetings: string[] = [];
  disabled = true;
  newmessage: string = '';
  private stompClient = '';
  validMoves: number = 0;
  private ws: any;
  winner: string = '';
  currentUser = localStorage.getItem("userName") || '{}';
  player = localStorage.getItem(localStorage.getItem("userName")|| '{}');
  wrongUser: any;
  moved: boolean = false;
  wrongMoveFadeOut: boolean = false;
  moves: Move[] = [];

  constructor(private othelloService: OthelloService) { }

  ngOnInit() {
    this.moved = false;
    console.log("user: "+localStorage.getItem("userName") || '{}');
    console.log("player:"+localStorage.getItem(localStorage.getItem("userName") || '{}'))
    const socket = new WebSocket("ws://localhost:8080/othello");

    this.ws = Stomp.over(socket);
    const _this = this;
    this.ws.connect({}, function(frame:any) {
        console.log('Connected: ' + frame);
      // let data = JSON.stringify({
      //   'name' : 'Nishith'
      // })
      // _this.ws.send("/game/kai", {}, data);
      _this.refreshBoard();

    });




    // this.othelloService.recieveData().subscribe((board: string[][]) => {
    //   this.board = board;
    //   console.log('Board updated:', this.board);
    // });
    //this.othelloService.connectSocket();
    //this.othelloService.connect();
      //this.onConnect();
    //this.refreshBoard();

  

    // this.othelloService.getBoardUpdates().subscribe((board: string[][]) => {
    //   this.board = board;
    //   console.log('Board updated:', this.board);
    // });
  }
  onConnect() {
    this.othelloService.recieveData().subscribe((board: string[][]) => {
      this.board = board;
      console.log('Board updated:', this.board);
    });
  }

  async refreshBoard() {
    const _this = this;
    let data = JSON.stringify({
      'name': 'Nishith'
    });
  
    await new Promise<void>(resolve => {
      _this.ws.send("/game/kai", {}, data);
      _this.ws.subscribe("/topic/board", async function (message: any) {
        console.log("MESSAGE:", message.body);
        let response = JSON.parse(message.body);
        _this.board = response.body.map((row: string) => Array.from(row));
        console.log("Board:", _this.board);
        
  
        console.log("BOARD LENGTH:" + _this.board.length);
        _this.ws.send("/game/currentPlayer", {}, data);
        _this.ws.subscribe("/topic/currentPlayer", function (message: any) {
          let response = JSON.parse(message.body);
          _this.currentPlayer = response.body;
        });
        if (_this.currentPlayer == _this.player) {
          _this.wrongUser = '';
        }
        const flattenedBoard = _this.board.flat();
        console.log("FALTEENNNNN:::: "+flattenedBoard);
        const isFull = !flattenedBoard.includes(' ');
        console.log("IS FUL:::::::: "+isFull);
  
        if (isFull) {
          _this.ws.send("/game/winner", {}, localStorage.getItem("roomId"));
          _this.ws.subscribe("/topic/gameWinner", function (message: any) {
            console.log("WINNER:", message.body);
            let response = JSON.parse(message.body);
            console.log("winner:" + response.body);
            _this.winner = response.body;
  
            if (_this.winner === 'black') {
              alert("The winner is black");
              localStorage.removeItem(localStorage.getItem("userName") || '{}');
              window.location.href = "/home";
            } else if (_this.winner === 'white') {
              alert("The winner is white");
              localStorage.removeItem(localStorage.getItem("userName") || '{}');
              window.location.href = "/home";
            } else if (_this.winner === 'tie') {
              alert("tie");
              localStorage.removeItem(localStorage.getItem("userName") || '{}');
              window.location.href = "/home";
            }
          });
        }
  
        resolve();
      });
    });
  
  
        

        



        // new Promise<void>(resolve => {
        //   console.log("board length: "+_this.board.length);
        //   for(let i=0; i<_this.board.length; i++){
        //    console.log("HERE in i");
        //    for(let j=0; j<_this.board[i].length; j++){
        //      console.log("Board in winner: "+_this.board[i][j]);
        //      if(_this.board[i][j] === ''){
        //        _this.isFull = false;
        //        break;
        //      }
        //    }
        //    if(!_this.isFull){
        //      break;
        //    }
        //  }
        //   });
        //   if(_this.isFull){
        //    _this.ws.send("/game/winner", {}, data);
        //    _this.ws.subscribe("/topic/gameWinner", function(message: any) {
        //      console.log("WINNER:", message.body);
        //      let response = JSON.parse(message.body);
        //      if(response!="")
        //      console.log("winner:"+response.body);
        //      _this.winner = response.body;
       
        //   }
        //  )}
         

      
   
   //const isBoardFull = this.board.every(row => row.every(cell => cell !== ''));
   
    // for (let i = 0; i < this.board.length; i++) {
    //       for (let j = 0; j < this.board[i].length; j++) {
    //         if (this.board[i][j] === null && this.isValidMove(i, j)) {
    //           this.validMoves++;
    //       }
    //     }
    //   }


    // this.othelloService.getBoard()
    //   .subscribe(board => {
    //     this.board = board;
    //     console.log("HERE: " + this.board.length);
    //     console.log("type: "+typeof this.board);
    //     for (let i = 0; i < this.board.length; i++) {
    //       for (let j = 0; j < this.board[i].length; j++) {
    //         console.log(this.board[i][j]);
    //       }
    //     }
    //   });
   

  }
  async movedOrNot(){
   
    const _this = this;
    let data = JSON.stringify({
      'name' : 'Nishith'
    })
    await new Promise<void>( resolve => {
      _this.ws.send("/game/movedOrNot",{}, data);
      _this.ws.subscribe("/topic/moved", function(message:any){

      console.log("MOVED INFO:", message.body);
      let response = JSON.parse(message.body);
      if(response.body=="MOVED" && _this.currentPlayer=='B' && _this.player=='B'){
          console.log("IN MOVING IN B");
          _this.currentPlayer = 'W'
          //_this.moved=false;
      }
      if(response.body=="MOVED" && _this.currentPlayer=='W' && _this.player=='W'){
        console.log("IN MOVING IN W");
        _this.currentPlayer = 'B'
        //_this.moved=false;
    }
    resolve();


  }, function(error: any) {
    console.error("Subscription error:", error);
    })
  })
}

  async makeMove(row: number, col: number) {
    this.player = localStorage.getItem((localStorage.getItem("userName")|| '{}'));
    console.log("PLAYERRRRRRRRRR: "+this.player);
    console.log("userName"+localStorage.getItem("userName"));
    console.log("Current playeerrrrr"+this.currentPlayer)
   
    this.othelloService.makeMove(row, col)
      .subscribe(async response => {
        if (response === "Moved") {
          const move: Move = {
            moveRow: row,
            moveColumn: col,
            player: localStorage.getItem("userName") || '{}'
            // Assuming you have a variable that holds the current player
          };
          this.moves.push(move);
          this.othelloService.saveMove(localStorage.getItem("roomId") || '{}', move)
            .subscribe(async response =>{
              if(response==="MoveSaved"){
                console.log("SAVED THE MOVE");
              }
            })
          this.wrongMove = '';
          this.moved = true;
          await this.refreshBoard();
          this.moved = false;
        }
      },
        error=>{
          this.wrongMove = 'WRONGMOVE';
      });
  }
  
  
  
  

  async switchPlayer() {
    console.log("SWITCHING PLAYER");
    if (this.currentPlayer === 'B') {
      this.currentPlayer = 'W';
    } else {
      this.currentPlayer = 'B';
    }
  }

  showError() {
    this.wrongUser= "It's not your turn";
    this.wrongMoveFadeOut = false;
    setTimeout(() => {
      this.wrongMoveFadeOut = true;
    }, 2000);
    this.refreshBoard();
  }
  

  // isValidMove(row: number, col: number): boolean {
  //   // Check if cell is empty
  //   if (this.board[row][col] !== null) {
  //     return false;
  //   }
  
  //   const opponent = this.currentPlayer === 'B' ? 'W' : 'B';
  //   let isValid = false;
  
  //   // Check if the move is adjacent to an opponent's piece
  //   for (let x = -1; x <= 1; x++) {
  //     for (let y = -1; y <= 1; y++) {
  //       if (x === 0 && y === 0) {
  //         continue;
  //       }
  
  //       let i = row + x;
  //       let j = col + y;
  
  //       if (i < 0 || i >= this.board.length || j < 0 || j >= this.board[i].length) {
  //         continue;
  //       }
  
  //       if (this.board[i][j] === opponent) {
  //         // Continue in this direction until edge of board or empty cell
  //         while (i >= 0 && i < this.board.length && j >= 0 && j < this.board[i].length) {
  //           if (this.board[i][j] === null) {
  //             break;
  //           }
  
  //           if (this.board[i][j] === this.currentPlayer) {
  //             isValid = true;
  //             break;
  //           }
  
  //           i += x;
  //           j += y;
  //         }
  //       }
  
  //       if (isValid) {
  //         break;
  //       }
  //     }
  
  //     if (isValid) {
  //       break;
  //     }
  //   }
  
  //   return isValid;
  // }
  

  

}
