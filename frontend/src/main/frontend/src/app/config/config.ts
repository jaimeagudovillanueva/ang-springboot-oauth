import { environment } from '../../environments/environment';

const CONFIG: Object = {

  global: {

    rest_url_base: environment.restUrl + '/archivo',

    oauth_login_end_point_url: environment.restUrl + '/archivo/oauth/token',
    oauth_client_id: '05355B83243D1A89ECEE677A97690D73',
    oauth_client_secret: 'BAF1129D03CE333CF4D9EB40EBD1754E'
    
  }
};

export const Config = CONFIG;
