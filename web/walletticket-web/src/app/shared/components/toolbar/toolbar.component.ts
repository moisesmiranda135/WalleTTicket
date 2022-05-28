import { AuthService } from 'src/app/core/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  userNameLogued = '';
  userRole!: string;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit() {
    this.userRole = this.authService.getRole() || '';
    this.userNameLogued = this.authService.getUserName()!;
  }

  exit(){
    localStorage.clear();
    this.router.navigate(['login']);
  }

}
