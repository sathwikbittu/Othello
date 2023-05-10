export class HighScores {
    userId: number;
    userName: string;
    numberOfMatches: number;
    numberOfWins: number;
    highestScore: number;
    
    
  
    constructor(userId: number,username: string, numberOfMatches: number, numberOfWins: number,highestScore: number) {
      this.userId = userId
      this.userName = username;
      this.numberOfMatches = numberOfMatches;
      this.numberOfWins = numberOfWins;
      this.highestScore = highestScore;
      
    }
  }
  