import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { PublicBlogComponent } from './';

export const PUBLIC_BLOG_ROUTE: Route = {
  path: 'public-blog',
  component: PublicBlogComponent,
  data: {
    authorities: [],
    pageTitle: 'blog.title'
  }
};
