import { Component, OnInit, computed, inject, input, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Customer } from '../../model/customer';
import { CustomerService } from '../../service/customer.service';

@Component({
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatProgressBarModule,
  ],
  selector: 'app-form-customer',
  styleUrl: './form-customer.css',
  templateUrl: './form-customer.html',
})
export class FormCustomer implements OnInit {
  private customerService = inject(CustomerService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  private fb = inject(FormBuilder).nonNullable;

  /** Route param `:id`; undefined on `/customers/new`. */
  id = input<string>();

  isEdit = computed(() => !!this.id());
  loading = signal(false);
  saving = signal(false);

  form = this.fb.group({
    firstName: ['', [Validators.required, Validators.maxLength(100)]],
    lastName: ['', [Validators.required, Validators.maxLength(100)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(150)]],
  });

  ngOnInit() {
    const id = this.id();
    if (!id) {
      return;
    }

    this.loading.set(true);
    this.customerService.findById(Number(id)).subscribe({
      next: (customer) => {
        this.form.patchValue(customer);
        this.loading.set(false);
      },
      error: (error) => {
        console.error('Error fetching customer:', error);
        this.loading.set(false);
        this.snackBar.open('Cliente não encontrado.', 'Fechar', { duration: 4000 });
        this.router.navigate(['/customers']);
      },
    });
  }

  salvar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const customer: Customer = this.form.getRawValue();
    const id = this.id();
    const request = id
      ? this.customerService.update(Number(id), { ...customer, id: Number(id) })
      : this.customerService.create(customer);

    this.saving.set(true);
    request.subscribe({
      next: () => {
        this.saving.set(false);
        this.snackBar.open(id ? 'Cliente atualizado.' : 'Cliente cadastrado.', 'Fechar', {
          duration: 3000,
        });
        this.router.navigate(['/customers']);
      },
      error: (error) => {
        console.error('Error saving customer:', error);
        this.saving.set(false);
        this.snackBar.open('Erro ao salvar cliente.', 'Fechar', { duration: 4000 });
      },
    });
  }
}
