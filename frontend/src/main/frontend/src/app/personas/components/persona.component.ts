import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {FormGroup, FormControl, Validators} from "@angular/forms";

import {PersonaService} from '../services/persona.service';

@Component({
  selector: 'persona',
  templateUrl: './persona.component.html',
  providers: [ PersonaService  ]
})
export class PersonaComponent implements OnInit {

  id: number;
  datos: Object;
  link: string;
  
  @Input() soloLectura: boolean;

  protected formulario: FormGroup = null;
  
  constructor(public route: ActivatedRoute, public personaService: PersonaService,
              public location: Location, public router: Router) {
    route.params.subscribe(params => { this.id = params['id']; });
    
    this.formulario = new FormGroup({
        nif: new FormControl('', [Validators.required])
      });
  }

  ngOnInit(): void {
	this.soloLectura = this.route.snapshot.queryParams['soloLectura'];
    this.personaService.get(this.id).then(
    	res => { 
          this.renderPersona(res);
          this.renderLink(res);
    	}
    );
  }

  back(): void {
	this.router.navigate(['/personas']); 
  }

  renderPersona(res: any): void {
	 this.formulario.controls['nif'].setValue(res.datos.nif);
	 this.datos = res.datos;
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
     this.personaService.queryLink(this.link).subscribe((res: any) => {
        this.renderPersona(res);
     });
  }
}