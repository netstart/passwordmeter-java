import {Component} from '@angular/core';
import { NavController } from 'ionic-angular';
import {PasswodScoreProvider} from '../../providers/passwod-score/passwod-score';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  score: any;
  password: string;

  constructor(public navCtrl: NavController,
              private passwodScoreProvider: PasswodScoreProvider) {
  }

  checkPassword(): void {
    this.passwodScoreProvider.checkPassword(this.password).subscribe((response: any) => {
      this.score = response;
      console.log(this.score);
    });
  }
}
