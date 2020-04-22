import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeListingComponent } from './employee-listing/employee-listing.component';
import { EmployeeEditComponent } from './employee-edit/employee-edit.component';

const routes: Routes = [
  
  { path: 'new', component: EmployeeEditComponent },
  { path: ':id', component: EmployeeEditComponent },

  { path: '', component: EmployeeListingComponent },
  { path: ':officeId', component: EmployeeListingComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
