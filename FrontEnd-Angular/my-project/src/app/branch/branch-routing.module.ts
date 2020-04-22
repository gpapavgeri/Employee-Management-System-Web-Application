import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BranchListingComponent } from './branch-listing/branch-listing.component';
import { BranchEditComponent } from './branch-edit/branch-edit.component';

const routes: Routes = [

  { path: 'new', component: BranchEditComponent },
  { path: ':id', component: BranchEditComponent },

  { path: '', component: BranchListingComponent },
  { path: ':companyId', component: BranchListingComponent },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BranchRoutingModule { }
