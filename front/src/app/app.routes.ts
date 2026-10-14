import { Routes } from '@angular/router';
import { ListCustomer } from './page/list-customer/list-customer';
import { FormCustomer } from './page/form-customer/form-customer';

export const routes: Routes = [
  { path: '', redirectTo: '/customers', pathMatch: 'full' },
  { path: 'customers', component: ListCustomer },
  { path: 'customers/new', component: FormCustomer },
  { path: 'customers/:id', component: FormCustomer },
  { path: '**', redirectTo: '/customers' }
];
