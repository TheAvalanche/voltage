import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PublicNewsDetailComponent } from './';

export const PUBLIC_NEWS_DETAIL_ROUTE: Route = {
  path: 'public-news/:id',
  component: PublicNewsDetailComponent,
  data: {
    authorities: [],
    pageTitle: 'voltageApp.news.home.title'
  }
};
