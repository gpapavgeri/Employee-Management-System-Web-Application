import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Asset } from '../asset/asset';
import { Company } from '../company/company';
import { AssetType } from '../assetType/assetType';
import { Office } from '../office/office';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  private assetsUrl = 'http://localhost:8080/assets';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(
    private http: HttpClient
  ) { }

  getAssets(company?: Company, office?: Office, assetType?: AssetType, index?: number, size?: number, orderBy?: string): Observable<Asset[]> {

    let parameters: HttpParams = new HttpParams();
    if (company != null) { parameters = parameters.set('companyId', company.id) };
    if (office != null) { parameters = parameters.set('officeId', office.id) };
    if (assetType != null) { parameters = parameters.set('assetTypeId', assetType.id) };
    if (index != null) {
      let pindex: string;
      pindex = index.toString();
      parameters = parameters.set('pageNo', pindex);
    }
    if (size != null) {
      let psize: string;
      psize = size.toString();
      parameters = parameters.set('pageSize', psize);
    }
    if (orderBy != null) { parameters = parameters.set('sortBy', orderBy); }

    return this.http.get<Asset[]>(`${this.assetsUrl}`, {
      params: parameters
    });
  }

  getTotalCount(company?: Company, office?: Office, assetType?: AssetType): Observable<number> {
    const url = `${this.assetsUrl}` + '/count';
    let parameters: HttpParams = new HttpParams();
    if (company != null) { parameters = parameters.set('companyId', company.id) };
    if (office != null) { parameters = parameters.set('officeId', office.id) };
    if (assetType != null) { parameters = parameters.set('assetTypeId', assetType.id) };
    return this.http.get<number>(url, {
      params: parameters
    });
  }

  getAsset(id: string): Observable<Asset> {
    const url = `${this.assetsUrl}/${id}`;
    return this.http.get<Asset>(url);
  }

  deleteAsset(asset: Asset | string): Observable<Asset> {
    const id = typeof asset === 'string' ? asset : asset.id;
    const url = `${this.assetsUrl}/${id}`;

    return this.http.delete<Asset>(url, this.httpOptions);
  }

  updateAsset(asset: Asset): Observable<any> {
    return this.http.put(this.assetsUrl, asset, this.httpOptions).pipe(
      catchError(this.handleError));
  }

  addAsset(asset: Asset): Observable<Asset> {
    return this.http.post<Asset>(this.assetsUrl, asset, this.httpOptions);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error.message}`);
    }
    return throwError(
      alert(`Error message:\n${error.error.message}`));
  };

}


