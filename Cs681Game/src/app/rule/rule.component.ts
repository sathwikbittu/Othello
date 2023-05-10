import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-rule',
  templateUrl: './rule.component.html',
  styleUrls: ['./rule.component.css']
})
export class RuleComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }


  goHome() {
    this.router.navigate(['/home']);
  }


}
