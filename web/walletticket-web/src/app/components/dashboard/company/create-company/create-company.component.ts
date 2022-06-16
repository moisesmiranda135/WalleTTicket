import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';;
import { CompanyResponse } from 'src/app/core/entity/company/companyResponse';
import { CreateEmployeeDialogComponent } from '../../employees/create-employee-dialog/create-employee-dialog.component';



@Component({
  selector: 'app-create-company',
  templateUrl: './create-company.component.html',
  styleUrls: ['./create-company.component.scss']
})
export class CreateCompanyComponent implements OnInit {

  shortLink: string = "";
  loading: boolean = false; 
  file = {} as File; 

  company = {} as CompanyResponse;

  constructor(private dialogRefe: MatDialogRef<CreateEmployeeDialogComponent>,private dialog: MatDialog) { }

  ngOnInit() {
  }

  onChange(event: any): any {
    this.file = event?.target?.files[0];
  }

  saveCompany() {
    this.loading = !this.loading;
    this.dialogRefe.close({
      event: 'ok',
      company: this.company,
      image: this.file,
    });
  }
  
  closeDialog() {
    this.dialogRefe.close();
  }

}
