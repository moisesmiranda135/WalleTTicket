import { CompanyResponse } from './../../../../core/entity/company/companyResponse';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-company-item',
  templateUrl: './company-item.component.html',
  styleUrls: ['./company-item.component.scss']
})
export class CompanyItemComponent implements OnInit {

  @Input() companyInput!: CompanyResponse;

  constructor() { }

  ngOnInit() {
  }

}
