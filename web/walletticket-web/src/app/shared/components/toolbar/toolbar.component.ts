import { AuthService } from 'src/app/core/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  userRole!: string;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit() {
    this.userRole = this.authService.getRole() || '';
  }

  exit(){
    localStorage.clear();
    this.router.navigate(['login']);
  }

}
