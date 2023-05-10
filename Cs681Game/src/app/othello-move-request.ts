export interface OthelloMoveRequest {
    player: number;
    board: number[][];
    row: number;
    col: number;
  }
  
  export class OthelloMoveRequestImpl implements OthelloMoveRequest {
    constructor(public player: number, public board: number[][], public row: number, public col: number) {}
  }
  