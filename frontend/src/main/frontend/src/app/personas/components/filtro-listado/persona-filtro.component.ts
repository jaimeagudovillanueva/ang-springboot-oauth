import {Component, OnInit, EventEmitter, Output, forwardRef, Inject} from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {Router, UrlSerializer} from '@angular/router';

import {UtilsService} from "../../../services/utils.service";
import {SecurityService} from '../../../services/security.service';

import {ListadoPersonasComponent} from '../listado/persona-listado.component';
import {SessionService} from "../../../services/session.service";

@Component({
  selector: 'filtro-listado',
  templateUrl: './persona-filtro.component.html'
})
export class PersonaFiltroComponent implements OnInit {

  public nombre_filtro = '';
  private formulario: FormGroup;
  private path = null;

  onEnviarFormulario() {
    this._parent.onEnviar(this.formulario);
  }

  constructor(
    public securityService: SecurityService,
    public router: Router,
    public urlSerializer: UrlSerializer,
    @Inject(forwardRef(() => ListadoPersonasComponent)) private _parent: ListadoPersonasComponent, 
        private sessionService: SessionService) {

    this.path = '/personas';
    this.formulario = new FormGroup({
      nombre: new FormControl(''),
      primerApellido: new FormControl(''),
      segundoApellido: new FormControl(''),
      nif: new FormControl('')
    });
  }

  public restablecer() {
    this.formulario.reset();
    this.formulario.controls['nombre'].setValue('');
    this.formulario.controls['primerApellido'].setValue('');
    this.formulario.controls['segundoApellido'].setValue('');
    this.formulario.controls['nif'].setValue('');
    this.onEnviarFormulario();
  }

  public canNavigateTo(path: string, methods: Array<any>)
  {
    var tree=this.router.createUrlTree([path]);
    var url= this.urlSerializer.serialize(tree);
    return this.securityService.canNavigateTo(path,methods);
  }

  ngOnInit() {
  }

 public expandirContraerContenido()
  {
      this.sessionService.expandirContraerContenido();
  }
}