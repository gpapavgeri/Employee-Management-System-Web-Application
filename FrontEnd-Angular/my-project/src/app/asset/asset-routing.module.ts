import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssetListingComponent } from './asset-listing/asset-listing.component';
import { AssetEditComponent } from './asset-edit/asset-edit.component';

const routes: Routes = [

  { path: 'new', component: AssetEditComponent },
  { path: ':id', component: AssetEditComponent },

  { path: '', component: AssetListingComponent },
  { path: ':companyId', component: AssetListingComponent }
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssetRoutingModule { }
