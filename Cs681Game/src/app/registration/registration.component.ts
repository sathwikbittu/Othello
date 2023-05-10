import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, NgForm, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../user';
import { UserRegistrationService } from '../user-registration.service';
import * as $ from 'jquery';
import * as bootstrap from "bootstrap";
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})


export class RegistrationComponent implements OnInit {
  registrationForm!: FormGroup;
  otpCodeEntered: String = "";
  isBlurred = false;
  displayOTPModal = false;
  showButton: boolean = false;
  timer: number = 120;
  interval: any;
  confirmPassword: String = "";
  generatedOTP: String = "";
  showOTPInput = false;
  user: User = new User("","","","","","");
  message:any;
  value:any;
  constructor(private service: UserRegistrationService ,private authService: AuthService,private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    $('#otpModal').modal('hide')  

  }
  async onSubmit() {
    console.log("HERE");
    this.service.registration(this.user)
      .subscribe(data=>{
        this.value = data;
        console.log("MES: "+this.value)
        if(this.value=="hi"){
        this.check();
        }
      })
      // console.log("MES: "+this.value)
      // if(this.message=="hi"){
      //   const isLoggedIn = await this.authService.loginAuth(this.user.userName,this.user.userPassword);
      //   if (isLoggedIn) {
      //     // If the login was successful, navigate to the home page
      //     //this.router.navigate(['/home']);
    
      //     window.location.href='/home';
      //   } 
      // }
  
  }
  async check(){
    const isLoggedIn = await this.authService.loginAuth(this.user.userName,this.user.userPassword);
    if (isLoggedIn) {
      // If the login was successful, navigate to the home page
      //this.router.navigate(['/home']);

      window.location.href='/home';
    } 

  }

  showSubmitButton(){
    this.showButton = true;

  }
  startTimer() {
    let count = 120;
    this.timer = count;
    this.interval = setInterval(() => {
      if (count > 0) {
        count--;
        this.timer = count;
      } else {
        clearInterval(this.interval);
        $('#otpModal').modal('hide');
      }
    }, 1000);
  }
  generateOTP() {
    this.isBlurred = true;
    this.displayOTPModal = true;
    this.service.otpVerification(this.user)
      .subscribe(data=>{
        this.generatedOTP = data;
        console.log("OTP: "+this.generatedOTP);
        this.startTimer();


      })
     
    // Generate a random 6-digit OTP code
      //this.startTimer();

      
      //this.generatedOTP = this.message;
      //console.log("OTP: "+this.generatedOTP);

    // Call API to send OTP code to user's phone number
    // ...

    // Show OTP input field
  }
  close(){
    this.displayOTPModal = false;
    this.isBlurred = false;

  }

  submitOTP() {
    if (this.generatedOTP === this.otpCodeEntered) {
      this.onSubmit();
      console.log('OTP verification success!');

     
    } else {

      console.log('OTP verification failed!');
      alert("OTP Entered is invalid, Please recheck")
      clearInterval(this.timer);
      this.otpCodeEntered = "";
      this.close()
      clearInterval(this.interval);
    $('#otpModal').modal('hide');
    }
  }
  
  }

