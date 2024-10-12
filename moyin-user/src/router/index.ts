import { createRouter, createWebHistory } from 'vue-router'
import { Layout } from '@/layout'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/moyin-login'),
    meta: { title: '登录', icon: 'dashboard', affix: true },
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/moyin-register'),
    meta: { title: '注册', icon: 'dashboard', affix: true },
  },
  {
    path: '/',
    component: Layout,
    name: 'index',
    redirect: '/dubbing',
    meta: {
      title: '首页',
      icon: 'dashboard',
    },
    children: [
      {
        path: '/dubbing',
        component: () => import('@/views/moyin-home'),
        name: 'dubbing',
        meta: { title: '首页', icon: 'dashboard', affix: true },
      },
      {
        path: '/contact',
        name: 'contact',
        component: () => import('@/views/moyin-contact'),
        meta: { title: '联系我们', icon: 'dashboard', affix: true },
      },
      {
        path: '/member',
        name: 'member',
        component: () => import('@/views/moyin-member'),
        meta: { title: '会员管理', icon: 'dashboard', affix: true },
      },
      {
        path: '/profile',
        name: 'profile',
        component: () => import('@/views/moyin-profile'),
        meta: { title: '我的账户', icon: 'dashboard', affix: true },
      },
    ],
  },
]

const router = createRouter({
  routes,
  history: createWebHistory(),
})

export default router
