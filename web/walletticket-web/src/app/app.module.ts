import { CompanyComponent } from './components/dashboard/company/company.component';
import { CreateEmployeeDialogComponent } from './components/dashboard/employees/create-employee-dialog/create-employee-dialog.component';
import { EmployeesComponent } from './components/dashboard/employees/employees.component';
import { ToolbarComponent } from './shared/components/toolbar/toolbar.component';
import { HomeComponent } from './components/dashboard/home/home.component';
import { MaterialImportsModule } from './core/modules/material-imports.module';
import { LoginComponent } from './components/login/login.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatNativeDateModule } from '@angular/material/core';
import { CoreModule } from '@angular/flex-layout';
import { CategoryComponent } from './components/dashboard/category/category.component';
import { CategoryFormDialogComponent } from './components/dashboard/category/category-form-dialog/category-form-dialog.component';
import { TokenInterceptor } from './core/interceptor/token.interceptor';
import { CompanyItemComponent } from './components/dashboard/company/company-item/company-item.component';
import { AdminComponent } from './components/dashboard/admin/admin.component';
import { CreateCompanyComponent } from './components/dashboard/company/create-company/create-company.component';
import { CreateAdminDialogComponent } from './components/dashboard/admin/create-admin-dialog/create-admin-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ToolbarComponent,
    CategoryComponent,
    CategoryFormDialogComponent,
    EmployeesComponent,
    CreateEmployeeDialogComponent,
    CompanyComponent,
    CompanyItemComponent,
    AdminComponent,
    CreateCompanyComponent,
    CreateAdminDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialImportsModule,
    FormsModule,
    HttpClientModule,
    MatNativeDateModule,
    CoreModule,
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
