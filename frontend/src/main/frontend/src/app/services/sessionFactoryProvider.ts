import {SessionService} from './session.service';

export function sessionFactoryProvider() {
    return SessionService.getInstance();
}