import { NgModule } from '@angular/core';
import { MatButtonModule} from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule} from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatPaginatorModule, MatPaginatorIntl } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { TranslateService, TranslateParser } from '@ngx-translate/core';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

import { MyMatPaginatorIntl } from '../myMatPaginatorIntl'
import { MatInputModule } from '@angular/material/input';


const MaterialComponents = [
  MatButtonModule,
  MatDialogModule,
  MatIconModule,
  MatSnackBarModule,
  MatPaginatorModule,
  MatTableModule,
  MatToolbarModule,
  MatListModule,
  MatSidenavModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatInputModule
]

@NgModule({
  declarations: [],
  imports: [MaterialComponents],
  exports: [MaterialComponents],
  providers: [
    { provide: MatPaginatorIntl,
      useFactory: (translateService: TranslateService, translateParser: TranslateParser) => new MyMatPaginatorIntl(translateService, translateParser), deps:[TranslateService, TranslateParser]  }
  ]
})
export class MaterialModule { }
