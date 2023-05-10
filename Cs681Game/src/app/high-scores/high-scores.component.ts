import { Component, OnInit } from '@angular/core';
import { OthelloService } from '../othello.service';
import { HighScores } from '../HighScores';

@Component({
  selector: 'app-high-scores',
  templateUrl: './high-scores.component.html',
  styleUrls: ['./high-scores.component.css']
})
export class HighScoresComponent implements OnInit {
  highScore: HighScores = new HighScores(0,"",0,0,0); 
  highScores: HighScores[] = [];
  displayedColumns: string[] = ['userId', 'userName', 'numberOfMatches', 'numberOfWins', 'highestScore'];

  constructor(private othelloService: OthelloService) { }

  ngOnInit(): void {
    this.othelloService.highScores()
      .subscribe((data: HighScores[]) => {
        this.highScores = data;
      });
  }
  

}