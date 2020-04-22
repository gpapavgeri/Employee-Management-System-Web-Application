import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { TranslateModule } from '@ngx-translate/core';

import { BranchListingComponent } from './branch-listing/branch-listing.component';
import { BranchEditComponent } from './branch-edit/branch-edit.component';
import { BranchRoutingModule } from './branch-routing.module';

@NgModule({
  declarations: [
    BranchListingComponent,
    BranchEditComponent
  ],
  imports: [
    CommonModule,
    BranchRoutingModule,
    FormsModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    MaterialModule,
    MatFormFieldModule,
    MatSelectModule,
    TranslateModule
  ]
})
export class BranchModule { }
