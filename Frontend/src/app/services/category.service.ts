import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  url=environment.apiUrl;// Replace 'your-api-base-url' with the base URL of your API.

  constructor(private httpClint: HttpClient) { }

  addNewCategory(data: any){
    return this.httpClint.post(this.url+ "/category/add", data,{
      headers:new HttpHeaders().set('Content-Type',"application/json")
    });
  }

  getCategories(){
    return this.httpClint.get(this.url+"/category/get");
  }

  updateCategory(data: any){
    return this.httpClint.post(this.url+ "/category/update", data,{
      headers:new HttpHeaders().set('Content-Type',"application/json")
    });
  }
  getFilteredCategorys(){
    return this.httpClint.get(this.url+"/category/get?filterValue=true")
  }
}
