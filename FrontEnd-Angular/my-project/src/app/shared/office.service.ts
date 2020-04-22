import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Office } from '../office/office';
import { Branch } from '../branch/branch';
import { Company } from '../company/company';

@Injectable({
  providedIn: 'root'
})
export class OfficeService {

  private officesUrl = 'http://localhost:8080/offices';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(
    private http: HttpClient
  ) { }

  getOffices(company?: Company, branch?: Branch, index?: number, size?: number, orderBy?: string): Observable<Office[]> {

    let parameters: HttpParams = new HttpParams();
    if (company != null){
      parameters = parameters.set('companyId', company.id);
    }
    if (branch != null) { 
      parameters = parameters.set('branchId', branch.id); 
    };
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

    return this.http.get<Office[]>(`${this.officesUrl}`, {
      params: parameters
    });
  }

  getTotalCount(company?: Company, branch?: Branch): Observable<number> {
    const url = `${this.officesUrl}` + '/count';
    let parameters: HttpParams = new HttpParams();
    if (company != null) {
      parameters = parameters.set('companyId', company.id);
    }
    return this.http.get<number>(url, {
      params: parameters
    });
  }

  getOfficeWithLists(id: string): Observable<Office> {
    const url = `${this.officesUrl}/${id}`;
    return this.http.get<Office>(url);
  }

  deleteOffice(office: Office | string): Observable<Office> {
    const id = typeof office === 'string' ? office : office.id;
    const url = `${this.officesUrl}/${id}`;
    return this.http.delete<Office>(url, this.httpOptions);
  }

  updateOffice(office: Office): Observable<any> {
    return this.http.put(this.officesUrl, office, this.httpOptions).pipe(
      catchError(this.handleError));
  }

  addOffice(office: Office): Observable<Office> {
    return this.http.post<Office>(this.officesUrl, office, {
      params: new HttpParams().set('branchId', office.branch.id)
    });
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


