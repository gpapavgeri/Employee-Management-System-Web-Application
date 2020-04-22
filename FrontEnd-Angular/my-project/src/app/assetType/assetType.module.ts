import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { TranslateModule } from '@ngx-translate/core';

import { AssetTypeListingComponent } from './asset-type-listing/asset-type-listing.component';
import { AssetTypeEditComponent } from './asset-type-edit/asset-type-edit.component';
import { AssetTypeRoutingModule } from './assetType-routing.module';

@NgModule({
  declarations: [
    AssetTypeListingComponent,
    AssetTypeEditComponent
  ],
  imports: [
    CommonModule,
    AssetTypeRoutingModule,
    FormsModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    MaterialModule,
    MatFormFieldModule,
    MatSelectModule,
    TranslateModule
  ]
})
export class AssetTypeModule { }
