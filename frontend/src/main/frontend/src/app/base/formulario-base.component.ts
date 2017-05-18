import {OnInit, OnDestroy} from "@angular/core";
import {NotificationsService} from "angular2-notifications";
import {FormGroup, FormControl} from "@angular/forms";

export abstract class FormularioBaseComponent implements OnInit, OnDestroy {

    protected formulario: FormGroup = null;

    constructor(protected notificationService: NotificationsService){
    }

    protected options = {
        timeOut: 5000,
        lastOnBottom: true,
        clickToClose: true,
        maxLength: 0,
        maxStack: 7,
        showProgressBar: true,
        pauseOnHover: true,
        preventDuplicates: false,
        preventLastDuplicates: 'visible',
        rtl: false,
        animate: 'scale',
        position: ['right', 'bottom']
    };

    errorCampo(campo : FormControl): boolean {
     return !this.formulario.valid && campo.hasError('required') && this.formulario.touched;
    }

    ngOnDestroy() {
    }

    ngOnInit() {
    }
}