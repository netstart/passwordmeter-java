import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';


@Injectable()
export class PasswodScoreProvider {

  constructor(public http: HttpClient) {
  }

  checkPassword(password: string): Observable<any> {
   return this.http.get<any>(`http://localhost:8081/api/passwod-score/check-password/${password}`);
  }

}
