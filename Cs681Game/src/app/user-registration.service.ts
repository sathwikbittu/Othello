import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  constructor(private http:HttpClient) { }

  public registration(user: any){
    return this.http.post<string>("http://localhost:8080/registration",user,{responseType:'text' as 'json'});

  }
  public otpVerification(user: any){
    return this.http.post<string>("http://localhost:8080/otpVerification",user,{responseType:'text' as 'json'});
  }
  public login(user:any){
    return this.http.post("http://localhost:8080/login",user,{responseType:'text' as 'json'});
  }



  public welcome(token: any) {
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get<string>("http://localhost:8080/", {headers, responseType: 'text' as 'json' });
  }

}
