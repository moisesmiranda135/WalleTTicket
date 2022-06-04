import { AuthService } from 'src/app/core/services/auth.service';
import { CategoryService } from 'src/app/core/services/category.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { CompanyService } from 'src/app/core/services/company.service';
import { TicketService } from 'src/app/core/services/ticket.service';
import Swal from 'sweetalert2';


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
    //Get Number of Companys
    this.companyService.getList().subscribe((result) => {
      this.companyTotalItems = result.length
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

  navigate(page: string){
    if (page === "/employees" && this.authService.getRole() === "EMPLOYEE") {
      Swal.fire({
        text: 'Esta función solo esta habilitada para administradores',
        icon: 'error',
        showConfirmButton: false,
        timer: 2000,
      })
    } else if (page === "/admin" && this.authService.getRole() === "EMPLOYEE") {
      Swal.fire({
        text: 'Esta función solo esta habilitada para administradores',
        icon: 'error',
        showConfirmButton: false,
        timer: 2000,
      })
    } else {
      window.location.assign(`${page}`);
    }
  }

}
