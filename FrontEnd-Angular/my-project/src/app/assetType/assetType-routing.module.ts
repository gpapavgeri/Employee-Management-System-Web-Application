import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AssetTypeListingComponent } from './asset-type-listing/asset-type-listing.component';
import { AssetTypeEditComponent } from './asset-type-edit/asset-type-edit.component';

const routes: Routes = [

  { path: '', component: AssetTypeListingComponent },
  { path: 'new', component: AssetTypeEditComponent },
  { path: ':id', component: AssetTypeEditComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssetTypeRoutingModule { }
