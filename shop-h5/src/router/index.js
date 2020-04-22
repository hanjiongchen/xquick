import Vue from 'vue';
import Router from 'vue-router';
import Cookies from 'js-cookie'

import home from './home';
import items from './items';
import user from './user';
import order from './order';
import login from './login';

Vue.use(Router);

const RouterModel = new Router({
  routes: [...home, ...items, ...user, ...order, ...login]
});

RouterModel.beforeEach((to, from, next) => {
  if (!Cookies.get('token')) {
    if (to.meta.login) {
      next({ name: 'login', query: { redirect: to.name } });
      return;
    }
  }
  next();
});

export default RouterModel;
