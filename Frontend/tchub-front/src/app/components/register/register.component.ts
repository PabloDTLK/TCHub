import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { Credentials } from '../../model/credentials';
import { RegisterResponse } from '../../model/registerResponse';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerData = {
    username: '',
    password: '',
    confirmPassword: ''
  };
  isLoading = false;
  registrationError: string | null = null;
  registrationSuccess: string | null = null;
  backendError: string | null = null;


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(registerForm: NgForm): void {
    this.registrationError = null;
    this.registrationSuccess = null;
    this.backendError = null;

    if (registerForm.invalid) {
      this.registrationError = 'Por favor, completa todos los campos correctamente.';
      Object.keys(registerForm.controls).forEach(field => {
        registerForm.controls[field].markAsTouched({ onlySelf: true });
      });
      return;
    }

    if (this.registerData.password !== this.registerData.confirmPassword) {
        this.registrationError = "Las contraseñas no coinciden.";
        if (registerForm.controls['confirmPassword']) {
            registerForm.controls['confirmPassword'].setErrors({'passwordMismatch': true});
        }
        return;
    }

    this.isLoading = true;

    
    const dataToSend: Credentials = {
      username: this.registerData.username,
      password: this.registerData.password
    };

    this.authService.register(dataToSend).subscribe({
      next: (response: RegisterResponse) => {
        this.isLoading = false;
        if (!response.error) {
          this.registrationSuccess = '¡Registro exitoso! Ahora puedes iniciar sesión.';
          registerForm.resetForm();
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 3000);
        } else {
          if (response.error) {
            this.backendError = response.error;
            this.registrationError = response.error || 'Error en el registro. Revisa los campos.';
          } else {
            this.registrationError = response.error || 'Ocurrió un error durante el registro.';
          }
        }
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Registration API error:', err);
        this.registrationError = 'Error de conexión o del servidor. Por favor, inténtalo de nuevo más tarde.';
      }
    });
  }
}

