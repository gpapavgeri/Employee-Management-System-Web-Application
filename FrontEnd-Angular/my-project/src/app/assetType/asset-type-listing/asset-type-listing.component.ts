import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';

import { AssetType } from '../assetType';
import { AssetTypeService } from '../../shared/assetType.service';
import { DialogService } from '../../shared/dialog.service';
import { NotificationService } from '../../shared/notification.service';

@Component({
  selector: 'app-asset-type-listing',
  templateUrl: './asset-type-listing.component.html',
  styleUrls: ['./asset-type-listing.component.css']
})
export class AssetTypeListingComponent implements OnInit {

  assetTypes: AssetType[];
  orderBy: string;
  assetTypeFields = [
    { value: "id", name: "Id" },
    { value: "type", name: "Type" }
  ];

  // MatPaginator Inputs
  length = 100;
  pageSize = 10;
  pageIndex = 1;
  pageSizeOptions: number[] = [2, 4, 6, 8, 10];

  // MatPaginator Output
  page: PageEvent;

  constructor(
    private assetTypeService: AssetTypeService,
    private dialog: MatDialog,
    private dialogService: DialogService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.getAssetTypes();
    this.getTotalCount();
  }

  getAssetTypes() {
    this.assetTypeService.getAssetTypes(this.pageIndex, this.pageSize, this.orderBy)
      .subscribe(data => this.assetTypes = data);
  }

  getTotalCount() {
    this.assetTypeService.getTotalCount()
      .subscribe(data => this.length = data);
  }

  delete(assetType: AssetType): void {
    this.dialogService.openConfirmDialog()
      .afterClosed().subscribe(res => {
        if (res) {
          this.assetTypeService.deleteAssetType(assetType).subscribe(result => {
            this.notificationService.warn('Deleted successfully!');
            this.assetTypeService.getTotalCount().subscribe(data => {
              this.length = data;
              this.getAssetTypes();
            });
          });
        }
      });
  }

  sortAssetTypes() {
    this.getAssetTypes();
  }

  paginateAssetTypes(page: PageEvent) {
    this.pageSize = page.pageSize;
    this.pageIndex = page.pageIndex + 1;
    this.getAssetTypes();
  }

  clearFilters() {
    this.orderBy = '';
    this.getAssetTypes();
  }

}
