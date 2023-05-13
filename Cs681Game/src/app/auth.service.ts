import { Injectable } from '@angular/core';
import { UserRegistrationService } from './user-registration.service';
import { User } from './user';
import { data } from 'jquery';
import { HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user: User = new User("","","","","","");
  message: any;
  //isLoggedIn = false;
  constructor(private loginService: UserRegistrationService, private cookieService: CookieService) { }

  isLoggedIn(){
    // Check if the user is logged in
    let token = localStorage.getItem("token")
    if(token==undefined || token=='' || token==null){
      return false
    }
    else{
    return true;
    }
  }
  loginUser(token: any){
    localStorage.setItem("token",token)
    console.log("token: "+token);
    return true;
  }
  getUserName(token: any) {
    const key = token;
    const userName = localStorage.getItem(key);
    console.log(`User ${token} name is ${userName}`);
    return userName;
  }
  setUserName(token: any, username: any) {
    const key = token;
    localStorage.setItem(key, username);
    console.log(`User ${token} name set to ${username}`);
    return true;
  }
  
  // setUserName(username: any){
  //   localStorage.setItem("userName",username)
  //   console.log("token: "+username);
  //   return true;
  // }

  async loginAuth(username: any, password: any): Promise<boolean> {
    // Perform authentication and set the isLoggedIn flag in local storage
    this.user.userName = username;
    this.user.userPassword = password;
    try {
      let response = await this.loginService.login(this.user).toPromise();
      console.log("data: ", response); // Check if data is being properly returned
      this.message = response;
      console.log("message: ", this.message); // Check if message is being properly assigned
      if (this.message != null) {
        localStorage.setItem("userName", username);
        this.cookieService.set("token", this.message);
        this.loginUser(this.message);
        this.setUserName(this.message,this.user.userName);
        //new HttpHeaders().set('Authorization', `Bearer ${this.message}`);
        
        console.log("login successful");
        return true;
      }
      console.log("login failed");
      return false;
    } catch (error) {
      console.log("Error: ", error); // Check if there are any errors
      console.log("login failed");
      return false;
    }
  // Return false immediately, as the response is not yet available

      // if (JSON.stringify(this.message)=="success") {
      //   localStorage.setItem('isLoggedIn', 'true');
      //   return true;
      // }

  }
  
  logout(): void {
    // Clear the isLoggedIn flag from local storage
    localStorage.removeItem('isLoggedIn');
  }
  async getUserNameApi(token: string){
    let response = await this.loginService.getUserName(token).toPromise();
    return response;
    
  }
  

  // isAuthenticated(): boolean {
  //   if (this.token) {
  //     const decodedToken: any = jwt_decode(this.token);
  //     const expirationTime = decodedToken.exp * 1000; // Convert expiration time to milliseconds
  //     const currentTime = Date.now();

  //     // Check if the token is expired by comparing the current time with the expiration time
  //     if (expirationTime > currentTime) {
  //       // Token is not expired
  //       return true;
  //     }
  //   }

  //   // Token is either not set or expired
  //   return false;
  // }
}