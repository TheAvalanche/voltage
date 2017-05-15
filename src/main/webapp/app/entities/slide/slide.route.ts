import {Routes} from '@angular/router';
import {UserRouteAccessService, Principal} from '../../shared';
import {SlideComponent} from './slide.component';
import {SlidePopupComponent} from './slide-dialog.component';
import {SlideDeletePopupComponent} from './slide-delete-dialog.component';


export const slideRoute: Routes = [
    {
        path: 'slide',
        component: SlideComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voltageApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const slidePopupRoute: Routes = [
    {
        path: 'slide-new',
        component: SlidePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voltageApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'slide/:id/edit',
        component: SlidePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voltageApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'slide/:id/delete',
        component: SlideDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voltageApp.slide.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
