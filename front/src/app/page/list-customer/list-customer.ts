import { Component, OnInit, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Customer } from '../../model/customer';
import { CustomerService } from '../../service/customer.service';

@Component({
  imports: [
    RouterLink,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatProgressBarModule,
  ],
  selector: 'app-list-customer',
  styleUrl: './list-customer.css',
  templateUrl: './list-customer.html',
})
export class ListCustomer implements OnInit {
  private customerService = inject(CustomerService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'actions'];
  customers = signal<Customer[]>([]);
  loading = signal(false);

  ngOnInit() {
    this.load();
  }

  load() {
    this.loading.set(true);
    this.customerService.findAll().subscribe({
      next: (data) => {
        this.customers.set(data);
        this.loading.set(false);
      },
      error: (error) => {
        console.error('Error fetching customers:', error);
        this.loading.set(false);
        this.snackBar.open('Erro ao carregar clientes.', 'Fechar', { duration: 4000 });
      },
    });
  }

  editar(customer: Customer) {
    this.router.navigate(['/customers', customer.id]);
  }

  excluir(customer: Customer) {
    if (!confirm(`Deseja excluir o cliente ${customer.firstName} ${customer.lastName}?`)) {
      return;
    }

    this.customerService.delete(customer.id!).subscribe({
      next: () => {
        this.customers.update((list) => list.filter((c) => c.id !== customer.id));
        this.snackBar.open('Cliente excluído.', 'Fechar', { duration: 3000 });
      },
      error: (error) => {
        console.error('Error deleting customer:', error);
        this.snackBar.open('Erro ao excluir cliente.', 'Fechar', { duration: 4000 });
      },
    });
  }
}
