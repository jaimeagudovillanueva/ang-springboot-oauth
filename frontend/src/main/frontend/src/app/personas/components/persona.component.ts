import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

import {PersonaService} from '../services/persona.service';

@Component({
  selector: 'persona',
  templateUrl: './persona.component.html'
})
export class PersonaComponent implements OnInit {

  id: number;
  datos: Object;
  link: string;

  constructor(public route: ActivatedRoute, public personaService: PersonaService,
              public location: Location) {
    route.params.subscribe(params => { this.id = params['id']; });
  }

  ngOnInit(): void {
    /*this.personaService
      .getPersona(this.id)
      .subscribe((res: any) => { 
          this.renderPersona(res);
          this.renderLink(res);
     });*/
  }

  back(): void {
    this.location.back();
  }

  renderPersona(res: any): void {
    this.datos = res;
  }

  renderLink(res: any): void {
    res.links.forEach(element => {
         if (element.rel === 'persona_sin_nif') {
            this.link = element.href;
            return;
        }
    });
  }

  realizarAccionLink(): void {
     /*this.personaService.queryLink(this.link).subscribe((res: any) => {
        this.renderPersona(res);
     });*/
  }
}