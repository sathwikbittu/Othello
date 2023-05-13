import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../user';
import { UserRegistrationService } from '../user-registration.service';
import { AuthService } from '../auth.service';
import { HttpClient,HttpHeaders } from '@angular/common/http';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  token: string|undefined;
  constructor(private service: UserRegistrationService,private router: Router, private authService: AuthService, private http: HttpClient) {
    this.token = undefined;

   }

  ngOnInit(): void {
  }
  user: User = new User("","","","","","");
  message: any;
  public loginError: boolean = false;
  public userName: string = '';
  public userPassword: string = '';
  isLoading: boolean = false;
  public captchError: boolean = false;
  captcha: any;


  public captchaIsLoaded = false;
  public captchaSuccess = false;
  public captchaIsExpired = false;
  public captchaResponse?: string;

  public theme: 'light' | 'dark' = 'light';
  public size: 'compact' | 'normal' = 'normal';
  public lang = 'en';
 
  
  async onLogin(){
    const isLoggedIn =  await this.authService.loginAuth(this.user.userName, this.user.userPassword);
    this.isLoading = true;
    console.log("isloggedIn: "+isLoggedIn);
    if (isLoggedIn) {
      
      // If the login was successful, navigate to the home page
      //this.router.navigate(['/home']);
      window.location.href='/home';
    } else {
      // If the login was unsuccessful, set the loginError flag to true

      this.loginError = true;
      
      //this.router.navigate(['/login'])
    }
  }
    // let response = this.service.login(this.user);
    //   response.subscribe((data) => this.message = data);
    //   if(this.message=="success"){
    //     this.router.navigate(['/home']);
    //   }
    //   else{
    //     this.router.navigate(['/login']);
    //   }
  
 
  onRegister(){
    return this.router.navigate(['/register']);


  }
  onForgotPassword(){

  }

  

 }



