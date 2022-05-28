import { CompanyComponent } from './components/dashboard/company/company.component';
import { EmployeesComponent } from './components/dashboard/employees/employees.component';
import { HomeComponent } from './components/dashboard/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { CategoryComponent } from './components/dashboard/category/category.component';

const routes: Routes = [
	{
		path: 'login',
		component: LoginComponent,
		pathMatch: 'full',
	},
	{
		path: 'home',
		component: HomeComponent,
		pathMatch: 'full',
	},
	{
		path: 'employees',
		component: EmployeesComponent,
	},
	{
		path: 'category',
		component: CategoryComponent,
	},
	{
		path: 'company',
		component: CompanyComponent,
	},
	{ path: '', pathMatch: 'full', redirectTo: '/login'}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
