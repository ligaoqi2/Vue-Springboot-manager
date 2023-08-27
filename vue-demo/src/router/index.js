import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Layout from "@/layout/Layout.vue";
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/home",
    children: [
      {
        path: 'home',
        name: 'HomeView',
        component: HomeView,
      }
    ]
  },

  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
