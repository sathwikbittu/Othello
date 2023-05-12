import { Component, OnInit } from '@angular/core';
import { GameMoves } from '../game-moves';
import { HttpClient } from '@angular/common/http';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';

// Register fonts with pdfMake
(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;
@Component({
  selector: 'app-game-moves',
  templateUrl: './game-moves.component.html',
  styleUrls: ['./game-moves.component.css']
})
export class GameMovesComponent implements OnInit {

  games: GameMoves[] = [];
  player: any;
  filteredGames: GameMoves[] = [];
  searchKeyword: string = '';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.player = localStorage.getItem("userName") || '{}';
    this.getGames();
  }
  // Inside the component class


  getGames() {
    this.http.get<GameMoves[]>('http://localhost:8080/getGames').subscribe(
      (games: GameMoves[]) => {
        // Handle the retrieved games here
        console.log(games);
        // Assign the games to a component property if needed
        this.games = games;
        this.filteredGames = games;

      },
      (error) => {
        // Handle error
        console.error('Error retrieving games:', error);
      }
    );
  }

  applyFilter(): void {
    if (this.searchKeyword.trim() === '') {
      this.filteredGames = this.games;
    } else {
      this.filteredGames = this.games.filter((game) => {
        return game.moves.some((move) =>
          move.player.toLowerCase().includes(this.searchKeyword.toLowerCase())
        );
      });
    }
  }

  clearFilter(): void {
    this.searchKeyword = '';
    this.filteredGames = this.games;
  }


  downloadGameMovesAsPDF(game: GameMoves): void {
    const docDefinition = {
      content: [
        { text: `Game ${game.id} Moves`, style: 'header' },
        { text: '\n' }, // Add some spacing
  
        ...game.moves.map(move => {
          const playerLabel = move.player === this.player ? 'You' : move.player;
          return { text: `${playerLabel} moved to row ${move.moveRow}, column ${move.moveColumn}` };
        })
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true,
          marginBottom: 10
        }
      }
    };
  
    const pdfDocGenerator = pdfMake.createPdf(docDefinition);
    pdfDocGenerator.download(`game_${game.id}_moves.pdf`);
  }

}

