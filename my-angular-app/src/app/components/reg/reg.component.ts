
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, AbstractControl, ValidatorFn, Validators} from '@angular/forms';
import { RegService } from '../../services/reg.service';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reg',
  standalone: true,
  imports: [ ReactiveFormsModule, FormsModule, CommonModule, HttpClientModule ],
  templateUrl: './reg.component.html',
  styleUrl: './reg.component.css'
})
export class RegComponent {
  regForm: FormGroup;
  errorMessage: string | null =null;

  constructor(private fb: FormBuilder, private regService: RegService, private router: Router, private authService: AuthService) {
    this.regForm = this.fb.group({
      login: [''],
      password: [''],
    });
  }

  onReg() {
    const formData = this.regForm.value;

    const jsonData= JSON.stringify(formData);
    if (formData.login.length<10 || formData.password.length<10 || formData.login.indexOf('.')!== -1 || formData.password.indexOf('.')!== -1){
      this.errorMessage="Password and login must have 10 chars and password must do not contain points";
    }
    else{
      this.regService.getResults(formData).subscribe((results) => {
        console.log(results.message);
        if (results.message != "Password and login must have 10 chars and password must do not contain points" && results.message != "User already exist"){
          this.authService.token=results.message;
          this.router.navigate(["table"]);
          this.authService.login();
        }
        else{
          this.errorMessage=results.message;
        }
      },
      (error)=>{
        this.errorMessage="User is already exist";
      });
    }

  }

  
  
  mvAuth(){
    this.router.navigate(["auth"]);
  }
  
}
