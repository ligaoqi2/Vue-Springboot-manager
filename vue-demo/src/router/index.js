import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/UserView.vue'
import Layout from "@/layout/Layout.vue";
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import PersonView from "@/views/PersonView.vue";
import UserView from "@/views/UserView.vue";
import BookView from "@/views/BookView.vue";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/user",
    children: [
      {
        path: 'user',
        name: 'UserView',
        component: UserView,
      },
      {
        path: '/person',
        name: 'Person',
        component: PersonView
      },
      {
        path: '/book',
        name: 'Book',
        component: BookView
      },
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
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
