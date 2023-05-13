import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { UserRegistrationService } from './user-registration.service';
import {HttpClientModule} from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { OthelloComponent } from './othello/othello.component';
import { WinnerComponent } from './winner/winner.component';
import { RuleComponent } from './rule/rule.component';
import { AboutComponent } from './about/about.component';
import { HighScoresComponent } from './high-scores/high-scores.component';
import { GameMovesComponent } from './game-moves/game-moves.component';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
//import { NgxCaptchaModule } from 'ngx-captcha';
//import { RECAPTCHA_SETTINGS, RecaptchaSettings } from 'ng-recaptcha';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    HomeComponent,
    LoginComponent,
    OthelloComponent,
    WinnerComponent,
    RuleComponent,
    AboutComponent,
    HighScoresComponent,
    GameMovesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDatatableModule,
  ],
  providers: [UserRegistrationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
