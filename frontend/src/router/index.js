import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import SignIn from "../views/SignIn.vue";
import SignUp from "../views/SignUp.vue";
import { useAuth } from "../services/useAuth";

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
  meta: { requiresGuest: true }, // Redirecionar usuários logados
 },
 {
  path: "/signup",
  name: "SignUp",
  component: SignUp,
  meta: { requiresGuest: true }, // Redirecionar usuários logados
 },
];

const router = createRouter({
 history: createWebHistory(),
 routes,
});

router.beforeEach((to, from, next) => {
 const { isAuthenticated, checkAuthStatus } = useAuth();

 // Verificar status de autenticação atual
 checkAuthStatus();

 if (to.meta.requiresAuth && !isAuthenticated.value) {
  // Rota requer autenticação mas usuário não está logado
  next({ name: "SignIn" });
 } else if (to.meta.requiresGuest && isAuthenticated.value) {
  // Rota é para usuários não logados mas usuário está logado
  next({ name: "Home" });
 } else {
  next();
 }
});

export default router;
