import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  // {
  //   path: '',
  //   loadChildren: () => import('./company/company.module').then(m => m.CompanyModule)
  // },
  {
    path: 'company',
    loadChildren: () => import('./company/company.module').then(m => m.CompanyModule)
  },
  {
    path: 'branch',
    loadChildren: () => import('./branch/branch.module').then(m => m.BranchModule)
  },
  {
    path: 'office',
    loadChildren: () => import('./office/office.module').then(m => m.OfficeModule)
  },
  {
    path: 'employee',
    loadChildren: () => import('./employee/employee.module').then(m => m.EmployeeModule)
  },
  {
    path: 'asset',
    loadChildren: () => import('./asset/asset.module').then(m => m.AssetModule)
  },
  {
    path: 'assetType',
    loadChildren: () => import('./assetType/assetType.module').then(m => m.AssetTypeModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
