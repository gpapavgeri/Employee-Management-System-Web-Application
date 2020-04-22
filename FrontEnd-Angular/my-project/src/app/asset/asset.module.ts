import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { TranslateModule } from '@ngx-translate/core';

import { AssetListingComponent } from './asset-listing/asset-listing.component';
import { AssetEditComponent } from './asset-edit/asset-edit.component';
import { AssetRoutingModule } from './asset-routing.module';

@NgModule({
  declarations: [
    AssetListingComponent,
    AssetEditComponent
  ],
  imports: [
    CommonModule,
    AssetRoutingModule,
    FormsModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    MaterialModule,
    MatFormFieldModule,
    MatSelectModule,
    TranslateModule
  ]
})
export class AssetModule { }
