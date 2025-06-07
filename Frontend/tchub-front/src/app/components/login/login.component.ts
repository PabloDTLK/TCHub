import { Component, Inject } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { LoginResponse } from '../../model/loginResponse';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginData = {
    username: '',
    password: ''
  };
  isLoading = false;
  loginError: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(loginForm: NgForm): void {
    if (loginForm.invalid) {
      this.loginError = 'Por favor, introduce tu usuario y contraseña.';
      
      Object.keys(loginForm.controls).forEach(field => {
        const control = loginForm.controls[field];
        control.markAsTouched({ onlySelf: true });
      });
      return;
    }

    this.isLoading = true;
    this.loginError = null;

    this.authService.login(this.loginData).subscribe({
      next: (response: LoginResponse) => {
        this.isLoading = false;
        if (response && response.token) {
          
          this.router.navigate(['/mis-colecciones']);
        } else if (response.error) {
          
          this.loginError = response.error;
        } else {
          
          this.loginError = 'Usuario o contraseña incorrectos.';
        }
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Login API error:', err);
        
        this.loginError = 'Error de conexión. Por favor, inténtalo de nuevo más tarde.';
      }
    });
  }
}
