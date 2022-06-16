import { UserDto } from './../../../../core/entity/user/UserDto';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserResponse } from 'src/app/core/entity/user/UserResponse';

@Component({
  selector: 'app-create-employee-dialog',
  templateUrl: './create-employee-dialog.component.html',
  styleUrls: ['./create-employee-dialog.component.scss']
})
export class CreateEmployeeDialogComponent implements OnInit {

  employee = {} as UserDto;
  dialogsTitle = true;

  hide = true;
  hide2 = true;

  constructor(private dialogRefe: MatDialogRef<CreateEmployeeDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: { employee: UserDto},
    private dialog: MatDialog,) { }

  ngOnInit() {
    if (this.data) {
      this.employee = this.data.employee
    }
  }

  saveForm() {
    this.dialogRefe.close({
      event: 'ok',
      employee: this.employee,
    })
  }

  closeDialog() {
    this.dialogRefe.close();
  }

}
