import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http'
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { DlDateTimeDateModule, DlDateTimePickerModule } from 'angular-bootstrap-datetimepicker';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CompanyService } from './shared/company.service';
import { CompanyModule } from './company/company.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { NotificationService } from './shared/notification.service';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { BranchModule } from './branch/branch.module';
import { OfficeModule } from './office/office.module';
import { EmployeeModule } from './employee/employee.module';
import { AssetModule } from './asset/asset.module';
import { AssetTypeModule } from './assetType/assetType.module';
import { CreateAssetTypeDialogComponent } from './create-asset-type-dialog/create-asset-type-dialog.component';


// AoT requires an exported function for factories
export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    ConfirmationDialogComponent,
    NavigationBarComponent,
    CreateAssetTypeDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CompanyModule,
    BrowserAnimationsModule,
    FormsModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    MaterialModule,
    FlexLayoutModule,
    BranchModule,
    MatMenuModule,
    MatSelectModule,
    OfficeModule,
    EmployeeModule,
    AssetModule,
    AssetTypeModule,
    DlDateTimeDateModule,
    DlDateTimePickerModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [HttpClient]
      },
      defaultLanguage: 'EN'
  })
  ],
  providers: [
    CompanyService,
    NotificationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
