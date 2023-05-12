import { Component, OnInit } from '@angular/core';
import { GameMoves } from '../game-moves';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-game-moves',
  templateUrl: './game-moves.component.html',
  styleUrls: ['./game-moves.component.css']
})
export class GameMovesComponent implements OnInit {
  games: GameMoves[] = [];
  player: any;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.player = localStorage.getItem("userName") || '{}';
    this.getGames();
  }

  getGames() {
    this.http.get<GameMoves[]>('http://localhost:8080/getGames').subscribe(
      (games: GameMoves[]) => {
        // Handle the retrieved games here
        console.log(games);
        // Assign the games to a component property if needed
        this.games = games;
      },
      (error) => {
        // Handle error
        console.error('Error retrieving games:', error);
      }
    );
  }
}