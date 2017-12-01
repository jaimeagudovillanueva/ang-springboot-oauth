import {Component} from '@angular/core';

import {SessionService} from '../services/session.service';

@Component({
    selector: 'principal',
    templateUrl: './principal.component.html'
})
export class PrincipalComponent {
	
	constructor(private sessionService: SessionService) {
	}
}