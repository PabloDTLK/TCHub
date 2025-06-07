import { Component, ElementRef, HostListener } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isMenuOpen = false;
  logoPath = "logo.svg";

  userIsLoggedIn = false;
  private authSubscription!: Subscription;

  constructor(
    private elementRef: ElementRef,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.authSubscription = this.authService.isLoggedIn.subscribe(status => {
      this.userIsLoggedIn = status;
      if (!status) {
        this.closeMenu();
      }
    });
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  closeMenu(): void {
    this.isMenuOpen = false;
  }

  handleLogout(): void {
    this.authService.logout();
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    if (!this.elementRef.nativeElement.contains(event.target as Node) && this.isMenuOpen) {
      this.closeMenu();
    }
  }
}
