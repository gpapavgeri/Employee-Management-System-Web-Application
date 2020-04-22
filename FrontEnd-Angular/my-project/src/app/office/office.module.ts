import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { TranslateModule } from '@ngx-translate/core';

import { OfficeListingComponent } from './office-listing/office-listing.component';
import { OfficeEditComponent } from './office-edit/office-edit.component';
import { OfficeRoutingModule } from './office-routing.module';

@NgModule({
  declarations: [
    OfficeListingComponent,
    OfficeEditComponent
  ],
  imports: [
    CommonModule,
    OfficeRoutingModule,
    FormsModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    MaterialModule,
    MatFormFieldModule,
    MatSelectModule,
    TranslateModule
  ]
})
export class OfficeModule { }
