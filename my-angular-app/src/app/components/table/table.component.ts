// app/search-form/search-form.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  standalone: true,
  imports: [ ReactiveFormsModule, FormsModule, CommonModule, HttpClientModule ],
  styleUrls: ['./table.component.css'],
})
export class TableComponent implements OnInit {
  searchForm: FormGroup;
  results: any[] =[];
  rValue: string ="R/2";
  r2Value: string ="R"
  negrValue: string ="-R/2";
  negr2Value: string ="-R"
  errorMessage: string | null =null;

  constructor(private fb: FormBuilder, private apiService: ApiService, private router: Router, private authService: AuthService) {
    this.searchForm = this.fb.group({
      x: [''],
      y: [''],
      r: [''],
    });
  }



  ngOnInit(){
    console.log("Init");
    const formData = this.searchForm.value;
    this.apiService.getResults(formData).subscribe((results) => {
      this.results = results;
      console.log(results);
    });
  }

  onSubmit() {
    const formData = this.searchForm.value;
    if (formData.x<-5 || formData.x>5){
      this.errorMessage="x need be >5 and <5"
    }
    else{
      this.errorMessage=""
      if (formData.r!="" || formData.x!="" || formData.y!=""){
        const svg = document.getElementById('svg');
        const point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
          point.setAttribute('cx', `${ ((parseFloat(formData.x) - 2 * formData.r) * (100 / formData.r) + 400).toFixed(0) }`);
          point.setAttribute('cy', `${ (200 - (100 / formData.r) * (parseFloat(formData.y))).toFixed(0) }`);
          point.setAttribute('r', "3");
          if (this.validate(formData.x, formData.y, formData.r)){
            point.setAttribute('fill', 'green');
          }
          else{
            point.setAttribute('fill', 'red');
          }
          svg?.appendChild(point);
          this.apiService.getResults(formData).subscribe((results) => {
            this.results = results;
            console.log(results);
        });
      }
      else{
        this.errorMessage="Choose R and y";
      }
    }
  }

  
  onClear(){
    const formData = this.searchForm.value;
    formData.x="";
    formData.y="";
    formData.r="";
    this.apiService.clearResults().subscribe((results) => {
      console.log(results);
      this.results =[];
    });
    const pointsSVG = document.querySelectorAll('#svg circle');
    const svg = document.getElementById('svg');
      pointsSVG.forEach(function(point) {
        point.parentNode?.removeChild(point);
    });
  }

  handleClick(event: MouseEvent) {
    const formData = this.searchForm.value;
    if (formData.r!=""){
      this.errorMessage="";
      const svg = document.getElementById('svg');
      const point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
      point.setAttribute('cx', `${event.offsetX}`);
      point.setAttribute('cy', `${event.offsetY}`);
      if (this.validate(((event.offsetX - 400) / (100 / formData.r) + (2 * formData.r)), ((400 - event.offsetY) / (100 / formData.r) - (2 * formData.r)), formData.r)){
        point.setAttribute('fill', 'green');
      }
      else{
        point.setAttribute('fill', 'red');
      }
      point.setAttribute('r', "3");
      svg?.appendChild(point);
      
      this.apiService.getResultsSVG(`${((event.offsetX - 400) / (100 / formData.r) + (2 * formData.r)).toFixed(2)}`, `${((400 - event.offsetY) / (100 / formData.r) - (2 * formData.r)).toFixed(2)}`, formData.r).subscribe((results) => {
        this.results = results;
        console.log(results);
    });;
    }
    else{
      this.errorMessage="Choose R";
    }
    
  }

  onLogout(){
    this.apiService.logout().subscribe(result =>{
      console.log(result);
    });

    this.authService.logout();

    this.router.navigate(["auth"]);
  }

  onRChange(){
    const formData = this.searchForm.value;
    const pointsSVG = document.querySelectorAll('#svg circle');
    const svg = document.getElementById('svg');
      pointsSVG.forEach(function(point) {
        point.parentNode?.removeChild(point);
    });

    this.rValue=`${formData.r/2}`;
    this.r2Value=formData.r;
    this.negr2Value=`${-formData.r}`
    this.negrValue=`${-formData.r/2}`

    if (formData.r!=0){
      this.results.forEach(result =>{
        const point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
        point.setAttribute('cx', `${ ((parseFloat(result.x) - 2 * formData.r) * (100 / formData.r) + 400).toFixed(0) }`);
        point.setAttribute('cy', `${ (200 - (100 / formData.r) * (parseFloat(result.y))).toFixed(0) }`);
        point.setAttribute('r', "3");
        if (this.validate(result.x, result.y, formData.r)){
          point.setAttribute('fill', 'green');
        }
        else{
          point.setAttribute('fill', 'red');
        }
        svg?.appendChild(point);
      })
    }    
  }

  validate (x: number, y: number, r_value:number): Boolean{
    const r = parseFloat(`${r_value}`);
    if (r>0){
      if (x>=0 && y>=0 && x<=r && y<=-x+r){
        return true;
      }
      if (x>=0 && y<=0 && x<=r && y>=-r){
        return true;
      }
      if (x<=0 && y<=0 && Math.sqrt(x*x+y*y)<=r){
        return true;
      }
      return false;
    }
    else{
      if (x>=0 && y>=0 && Math.sqrt(x*x+y*y)<=-r){
        return true;
      }
      if (x<=0 && y>=0 && x>=r && y<=-r){
        return true;
      }
      if (x<=0 && y<=0 && y>=-x+r){
        return true;
      }
      return false;
    }
  }

}
