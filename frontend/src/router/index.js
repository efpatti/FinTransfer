import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import SignIn from "../views/SignIn.vue";
import SignUp from "../views/SignUp.vue";
import { authService } from "../services/auth";

const routes = [
 {
  path: "/",
  name: "Home",
  component: Home,
  meta: { requiresAuth: true },
 },
 {
  path: "/signin",
  name: "SignIn",
  component: SignIn,
 },
 {
  path: "/signup",
  name: "SignUp",
  component: SignUp,
 },
];

const router = createRouter({
 history: createWebHistory(),
 routes,
});

router.beforeEach((to, from, next) => {
 if (to.meta.requiresAuth && !authService.isAuthenticated()) {
  next({ name: "SignIn" });
 } else {
  next();
 }
});

export default router;
