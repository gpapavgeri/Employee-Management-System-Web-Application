import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CompanyListingComponent } from './company-listing/company-listing.component';
import { CompanyEditComponent } from './company-edit/company-edit.component';

const routes: Routes = [
  { path: '', component: CompanyListingComponent },
  { path: 'new', component: CompanyEditComponent },
  { path: ':id', component: CompanyEditComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CompanyRoutingModule { }
