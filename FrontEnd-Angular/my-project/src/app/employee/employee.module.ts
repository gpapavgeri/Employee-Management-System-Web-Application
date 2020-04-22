import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { TranslateModule } from '@ngx-translate/core';
import { DlDateTimeDateModule, DlDateTimePickerModule } from 'angular-bootstrap-datetimepicker';

import { EmployeeListingComponent } from './employee-listing/employee-listing.component';
import { EmployeeEditComponent } from './employee-edit/employee-edit.component';
import { EmployeeRoutingModule } from './employee-routing.module';

@NgModule({
  declarations: [
    EmployeeListingComponent,
    EmployeeEditComponent
  ],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    FormsModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    MaterialModule,
    MatFormFieldModule,
    MatSelectModule,
    TranslateModule,
    DlDateTimeDateModule,
     DlDateTimePickerModule
  ]
})
export class EmployeeModule { }
