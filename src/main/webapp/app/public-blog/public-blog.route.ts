import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../shared';
import {PublicBlogComponent} from './';

export const PUBLIC_BLOG_ROUTE: Routes = [
    {
        path: 'public-blog',
        component: PublicBlogComponent,
        data: {
            authorities: [],
            pageTitle: 'blog.title'
        }
    },
    {
        path: 'public-blog/category/:category',
        component: PublicBlogComponent,
        data: {
            authorities: [],
            pageTitle: 'blog.title'
        }
    }];
