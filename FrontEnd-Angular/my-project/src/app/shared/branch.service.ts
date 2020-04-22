import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Branch } from '../branch/branch';
import { Company } from '../company/company';

@Injectable({
  providedIn: 'root'
})
export class BranchService {

  private branchesUrl = 'http://localhost:8080/branches';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(
    private http: HttpClient
  ) { }

  getBranches(company?: Company, index?: number, size?: number, orderBy?: string): Observable<Branch[]> {

    let parameters: HttpParams = new HttpParams();
    if (company != null) { parameters = parameters.set('companyId', company.id) };
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

    return this.http.get<Branch[]>(`${this.branchesUrl}`, {
      params: parameters
    });
  }

  getTotalCount(company?: Company): Observable<number> {
    const url = `${this.branchesUrl}` + '/count';
    let parameters: HttpParams = new HttpParams();
    if (company != null) {
      parameters = parameters.set('companyId', company.id);
    }
    return this.http.get<number>(url, {
      params: parameters
    });
  }

  getBranch(id: string): Observable<Branch> {
    const url = `${this.branchesUrl}/${id}`;
    return this.http.get<Branch>(url);
  }

  getBranchWithOffices(id: string): Observable<Branch> {
    const url = `${this.branchesUrl}/offices/${id}`;
    return this.http.get<Branch>(url);
  }

  deleteBranch(branch: Branch | string): Observable<Branch> {
    const id = typeof branch === 'string' ? branch : branch.id;
    const url = `${this.branchesUrl}/${id}`;

    return this.http.delete<Branch>(url, this.httpOptions);
  }

  updateBranch(branch: Branch): Observable<any> {
    return this.http.put(this.branchesUrl, branch, this.httpOptions);
  }

  addBranch(branch: Branch): Observable<Branch> {
    return this.http.post<Branch>(this.branchesUrl, branch, {
      params: new HttpParams().set('companyId', branch.company.id)
    });
  }


}


