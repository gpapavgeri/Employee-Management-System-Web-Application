import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OfficeListingComponent } from './office-listing/office-listing.component';
import { OfficeEditComponent } from './office-edit/office-edit.component';

const routes: Routes = [
  
  { path: 'new', component: OfficeEditComponent },
  { path: ':id', component: OfficeEditComponent },

  { path: '', component: OfficeListingComponent },
  { path: ':branchId', component: OfficeListingComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OfficeRoutingModule { }
