export class Room {
    roomId: number;
    userName: string;
    roomName: string;
    joinedPlayerName: string;
    
  
    constructor(roomId: number,username: string, roomName: string, joinedPlayerName: string) {
      this.userName = username;
      this.roomName = roomName;
      this.joinedPlayerName = joinedPlayerName;
      this.roomId = roomId;
      
    }
  }
  