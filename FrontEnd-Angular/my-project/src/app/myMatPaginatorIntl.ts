import {MatPaginatorIntl} from '@angular/material/paginator';
import { TranslateParser, TranslateService } from '@ngx-translate/core';

export class MyMatPaginatorIntl extends MatPaginatorIntl {

  private rangeLabelIntl: string;

  constructor(private translateService: TranslateService, private translateParser: TranslateParser) {
    super();
    this.getTranslations();
  }

  getTranslations() {
    this.translateService.get([
      'paginator.itemsPerPage',
      'paginator.nextPage',
      'paginator.previousPage',
      'paginator.firstPage',
      'paginator.lastPage',
      'paginator.range'
    ])
      .subscribe(translation => {
        this.itemsPerPageLabel = translation['paginator.itemsPerPage'];
        this.nextPageLabel = translation['paginator.nextPage'];
        this.previousPageLabel = translation['paginator.previousPage'];
        this.firstPageLabel = translation['paginator.firstPage'];
        this.lastPageLabel = translation['paginator.lastPage'];
        this.rangeLabelIntl = translation['paginator.range'];
        this.changes.next();
      });
  }

  getRangeLabel = (page, pageSize, length) => {
    length = Math.max(length, 0);
    let startIndex = (page * pageSize);
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
      startIndex++;
    return this.translateParser.interpolate(this.rangeLabelIntl, { startIndex, endIndex, length });
  };




}