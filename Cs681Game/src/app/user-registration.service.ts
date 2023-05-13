import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  constructor(private http:HttpClient) { }

  public registration(user: any){
    return this.http.post<string>("https://localhost:8443/registration",user,{responseType:'text' as 'json'});

  }
  public otpVerification(user: any){
    return this.http.post<string>("https://localhost:8443/otpVerification",user,{responseType:'text' as 'json'});
  }
  public login(user:any){
    return this.http.post("https://localhost:8443/login",user,{responseType:'text' as 'json'});
  }
  public getUserName(token: string){
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const options = { headers: headers };

  return this.http.get<string>("https://localhost:8443/getUserName", options);
  }



  public welcome(token: any) {
    let tokenStr = 'Bearer ' + token;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.http.get<string>("https://localhost:8443/", {headers, responseType: 'text' as 'json' });
  }

}
