import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'
import { Observable } from 'rxjs';

import { AssetType } from '../assetType/assetType';

@Injectable({
  providedIn: 'root'
})
export class AssetTypeService {

  private assetTypesUrl = 'http://localhost:8080/assetTypes';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(
    private http: HttpClient
  ) { }

  getAssetTypes(index?:number, size?: number, orderBy?: string): Observable<AssetType[]> {

    let parameters: HttpParams = new HttpParams();
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

    return this.http.get<AssetType[]>(`${this.assetTypesUrl}`, {
      params: parameters
    });

  }

  getTotalCount(): Observable<number> {
    const url = `${this.assetTypesUrl}` + '/count';
    return this.http.get<number>(url);
  }

  getAssetType(id: string): Observable<AssetType> {
    const url = `${this.assetTypesUrl}/${id}`;
    return this.http.get<AssetType>(url);
  }

  deleteAssetType(assetType: AssetType | string): Observable<AssetType> {
    const id = typeof assetType === 'string' ? assetType : assetType.id;
    const url = `${this.assetTypesUrl}/${id}`;

    return this.http.delete<AssetType>(url, this.httpOptions);
  }

  updateAssetType(assetType: AssetType): Observable<any> {
    return this.http.put(this.assetTypesUrl, assetType, this.httpOptions);
  }

  addAssetType(assetType: AssetType): Observable<AssetType> {
    return this.http.post<AssetType>(this.assetTypesUrl, assetType, this.httpOptions);
  }


}


