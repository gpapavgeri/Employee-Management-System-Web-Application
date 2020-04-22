import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { AfterViewInit, ViewChild } from '@angular/core';

import { CompanyListingComponent } from '../company/company-listing/company-listing.component';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  language = 'EN';

  constructor(private translate: TranslateService,
    private router: Router) {
    this.translate.setDefaultLang(this.language);
  }

  orderBy: 'None';

  ngOnInit() {

  }

  setLanguage(lang: string) {
    if (this.language === lang) return;
    this.language = lang;
    this.translate.use(this.language);
  }


}
