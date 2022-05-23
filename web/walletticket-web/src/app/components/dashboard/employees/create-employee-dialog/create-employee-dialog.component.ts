import { UserDto } from './../../../../core/entity/user/UserDto';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

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

  constructor(private dialogRefe: MatDialogRef<CreateEmployeeDialogComponent>,
    private dialog: MatDialog,) { }

  ngOnInit() {
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
