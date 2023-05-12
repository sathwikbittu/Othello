import { Component, OnInit } from '@angular/core';
import { OthelloService } from '../othello.service';
import { HighScores } from '../highScores';
import { Router } from '@angular/router';
@Component({
  selector: 'app-high-scores',
  templateUrl: './high-scores.component.html',
  styleUrls: ['./high-scores.component.css']
})
export class HighScoresComponent implements OnInit {
  highScore: HighScores = new HighScores(0,"",0,0,0); 
  highScores: HighScores[] = [];
  displayedColumns: string[] = ['userId', 'userName', 'numberOfMatches', 'numberOfWins', 'highestScore'];

  constructor(private othelloService: OthelloService,private router: Router) { }

  ngOnInit(): void {
    this.othelloService.highScores()
      .subscribe((data: HighScores[]) => {
        this.highScores = data;
      });
  }
  goHome() {
    this.router.navigate(['/home']);
  }

}
