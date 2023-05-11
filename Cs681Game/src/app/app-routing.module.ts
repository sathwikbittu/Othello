import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { AuthGuard } from './auth-gaurd.service';
import { OthelloComponent } from './othello/othello.component';
import { RuleComponent } from './rule/rule.component';
import { AboutComponent } from './about/about.component';
import { HighScores } from './highScores';
import { HighScoresComponent } from './high-scores/high-scores.component';
const routes: Routes = [
  {path:"",redirectTo:"login",pathMatch:"full"},
  {path:"register",component:RegistrationComponent},
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  {path:"home",component:HomeComponent, canActivate: [AuthGuard]},
  {path:"login",component:LoginComponent},
  {path:"othello",component:OthelloComponent},
  { path: 'rules', component: RuleComponent },
  {path : 'about', component: AboutComponent},
  {path: 'high-score', component: HighScoresComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
