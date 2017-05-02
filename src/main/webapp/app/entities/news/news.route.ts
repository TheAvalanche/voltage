import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { NewsComponent } from './news.component';
import { NewsPopupComponent } from './news-dialog.component';
import { NewsDeletePopupComponent } from './news-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class NewsResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const newsRoute: Routes = [
  {
    path: 'news',
    component: NewsComponent,
    resolve: {
      'pagingParams': NewsResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.news.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const newsPopupRoute: Routes = [
  {
    path: 'news-new',
    component: NewsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.news.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'news/:id/edit',
    component: NewsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.news.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'news/:id/delete',
    component: NewsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.news.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
