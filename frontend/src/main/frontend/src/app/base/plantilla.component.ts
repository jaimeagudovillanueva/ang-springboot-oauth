import {Component} from '@angular/core';

import {SessionService} from '../services/session.service';
import {SecurityService} from '../services/security.service';
import {LoginService} from '../login/login.service';

@Component({
  selector: 'app-plantilla',
  templateUrl: './plantilla.component.html', 
})
export class PlantillaComponent {

  constructor(private sessionService: SessionService,
              private securityService: SecurityService,
              private loginService: LoginService) {
  }

  public notif_options = {
    timeOut: 3000
  }

  logout() {
    this.loginService.logout();
  }
}
 