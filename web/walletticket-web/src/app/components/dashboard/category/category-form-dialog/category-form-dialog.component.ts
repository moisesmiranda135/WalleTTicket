import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { CategoryResponse } from 'src/app/core/entity/category/categoryListResponse';
import { CommunicationList } from 'src/app/core/util/iconsList/communicationList';

@Component({
  selector: 'app-category-form-dialog',
  templateUrl: './category-form-dialog.component.html',
  styleUrls: ['./category-form-dialog.component.scss']
})
export class CategoryFormDialogComponent implements OnInit {

  category = {} as CategoryResponse;

  iconListCommunication = CommunicationList;

  constructor(private dialogRefe: MatDialogRef<CategoryFormDialogComponent>,
    private dialog: MatDialog, private fb: FormBuilder) { }

  ngOnInit(): void {
    
  }

  saveCategory() {
    this.dialogRefe.close({
      event: 'ok',
      category: this.category,
    });
  }

  changeIconValue(iconValue: string) {
    this.category.icon = iconValue;
  }

  closeDialog() {
    this.dialogRefe.close();
  }

  generateIcons() : Array<string> {
    var listIcons = Object.keys(this.iconListCommunication);
    return listIcons.slice(listIcons.length / 2);
  }

}
