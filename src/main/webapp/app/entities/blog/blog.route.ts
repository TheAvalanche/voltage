import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BlogComponent } from './blog.component';
import { BlogPopupComponent } from './blog-dialog.component';
import { BlogDeletePopupComponent } from './blog-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class BlogResolvePagingParams implements Resolve<any> {

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

export const blogRoute: Routes = [
  {
    path: 'blog',
    component: BlogComponent,
    resolve: {
      'pagingParams': BlogResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const blogPopupRoute: Routes = [
  {
    path: 'blog-new',
    component: BlogPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'blog/:id/edit',
    component: BlogPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'blog/:id/delete',
    component: BlogDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.blog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
