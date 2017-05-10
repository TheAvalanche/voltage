import {Route} from '@angular/router';

import {UserRouteAccessService} from '../shared';
import {PublicBlogDetailComponent} from './';

export const PUBLIC_BLOG_DETAIL_ROUTE: Route = {
    path: 'public-blog/:id',
    component: PublicBlogDetailComponent,
    data: {
        authorities: [],
        pageTitle: 'voltageApp.blog.home.title'
    }
};
