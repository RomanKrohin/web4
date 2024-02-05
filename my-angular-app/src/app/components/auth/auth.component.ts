import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { error } from 'console';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [ ReactiveFormsModule, FormsModule, CommonModule, HttpClientModule ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  authForm: FormGroup;
  errorMessage: string | null =null;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.authForm = this.fb.group({
      login: [''],
      password: [''],
    });
  }

  ngOnInit(){
    if (sessionStorage.getItem("auth") == "true") {
      this.router.navigate(['table']);
    }
  }

  onAuth() {
    const formData = this.authForm.value;
    this.authService.getResults(formData).subscribe((results) => {
      if (results.message != "wrong"){
        this.authService.token=results.message;
        this.authService.login();
        this.errorMessage="";
        this.router.navigate(["table"]);
      }
      else{
        this.errorMessage="Wrong password or login";
      }

    },
    (error)=>{
      this.errorMessage="Wrong password or login";
    });
  }

  mvReg(){
    this.router.navigate(["reg"]);
  }
  

}

