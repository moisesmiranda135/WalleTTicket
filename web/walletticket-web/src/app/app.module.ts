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
import { HttpClientModule } from '@angular/common/http';
import { MatNativeDateModule } from '@angular/material/core';
import { CoreModule } from '@angular/flex-layout';
import { CategoryComponent } from './components/dashboard/category/category.component';
import { CategoryFormDialogComponent } from './components/dashboard/category/category-form-dialog/category-form-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ToolbarComponent,
    CategoryComponent,
    CategoryFormDialogComponent,
    EmployeesComponent,
    CreateEmployeeDialogComponent
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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
