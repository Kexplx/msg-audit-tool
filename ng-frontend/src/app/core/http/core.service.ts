import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const connectionStrings = {
  production: '...',
  development: 'http://localhost:8080',
};

const compileTimeSwitchedString = connectionStrings.development;

@Injectable({
  providedIn: 'root',
})
export class CoreService {
  constructor(private http: HttpClient) {}

  //

  //

  //

  getFaccrits() {
    return this.http.get(compileTimeSwitchedString + '/faccrits');
  }
}
