import { AuthService } from 'src/app/core/services/auth.service';
import { CategoryService } from 'src/app/core/services/category.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { CompanyService } from 'src/app/core/services/company.service';
import { TicketService } from 'src/app/core/services/ticket.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  userNameLogued = '';

  categoryTotalItems = 0;
  companyTotalItems = 0;
  ticketTotalItems = 0;
  userTotals = 0;

  constructor(private categoryService: CategoryService,
    private companyService: CompanyService,
    private ticketService: TicketService,
    private authService: AuthService,) {
  }

  ngOnInit() {
    this.getData();
  }

  getData() {
    //Get Name of User logued
    this.userNameLogued = this.authService.getUserName()!;
    //Get Number of Categories
    this.categoryService.getList().subscribe((result) => {
      this.categoryTotalItems = result.length
    })
    //Get Number of Companyes
    this.companyService.getList().subscribe((result) => {
      this.categoryTotalItems = result.length
    })
    //Get Number of Tickets
    this.ticketService.getList().subscribe((result) => {
      this.ticketTotalItems = result.length
    })
    //Get Number of Users
    this.authService.getAllUsers().subscribe((result) => {
      this.userTotals = result.length
    })
  }

}
