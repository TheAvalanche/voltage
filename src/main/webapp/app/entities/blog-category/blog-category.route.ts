import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BlogCategoryComponent } from './blog-category.component';
import { BlogCategoryPopupComponent } from './blog-category-dialog.component';
import { BlogCategoryDeletePopupComponent } from './blog-category-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class BlogCategoryResolvePagingParams implements Resolve<any> {

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

export const blogCategoryRoute: Routes = [
  {
    path: 'blog-category',
    component: BlogCategoryComponent,
    resolve: {
      'pagingParams': BlogCategoryResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.blogCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const blogCategoryPopupRoute: Routes = [
  {
    path: 'blog-category-new',
    component: BlogCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.blogCategory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'blog-category/:id/edit',
    component: BlogCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.blogCategory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'blog-category/:id/delete',
    component: BlogCategoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'voltageApp.blogCategory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
