import { Routes, RouterModule } from '@angular/router';
import { TableComponent } from './components/table/table.component';
import { AuthComponent } from './components/auth/auth.component';
import { RegComponent } from './components/reg/reg.component';
import {AuthGuard} from './services/auth.guard';
import { NgModule } from '@angular/core';

export const routes: Routes = [
    {path: "table", component: TableComponent, canActivate: [ AuthGuard ]},
    {path: "auth", component: AuthComponent},
    {path: "reg", component: RegComponent},
    {path: "**", redirectTo: "auth"}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
  })
  export class AppRoutingModule {}