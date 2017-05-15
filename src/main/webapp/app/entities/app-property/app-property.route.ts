import {UserRouteAccessService} from '../../shared';

import {AppPropertyComponent} from './app-property.component';
import {AppPropertyPopupComponent} from './app-property-dialog.component';
import {AppPropertyDeletePopupComponent} from './app-property-delete-dialog.component';

import {Principal} from '../../shared';
import {Routes} from '@angular/router';


export const appPropertyRoute: Routes = [
    {
        path: 'app-property',
        component: AppPropertyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voltageApp.appProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appPropertyPopupRoute: Routes = [
    {
        path: 'app-property-new',
        component: AppPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voltageApp.appProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'app-property/:id/edit',
        component: AppPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voltageApp.appProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'app-property/:id/delete',
        component: AppPropertyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voltageApp.appProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
