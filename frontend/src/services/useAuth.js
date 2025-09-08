import { ref, computed } from "vue";
import { authService } from "./auth";

// Estado global de autenticação
const isAuthenticated = ref(false);
const user = ref(null);
const authToken = ref(null);

// Inicializar estado baseado no localStorage
const initializeAuth = () => {
 const token = authService.getToken();
 if (token) {
  authToken.value = token;
  isAuthenticated.value = true;
  // Você pode decodificar o JWT para extrair informações do usuário
  try {
   const payload = JSON.parse(atob(token.split(".")[1]));
   user.value = {
    username: payload.sub,
    exp: payload.exp,
    iat: payload.iat,
   };
  } catch (error) {
   console.error("Error parsing JWT:", error);
   // Se o token estiver inválido, limpar
   authService.logout();
   isAuthenticated.value = false;
   user.value = null;
   authToken.value = null;
  }
 }
};

// Verificar se o token ainda é válido
const isTokenValid = computed(() => {
 if (!user.value || !user.value.exp) return false;
 return Date.now() < user.value.exp * 1000;
});

// Composable para usar a autenticação
export function useAuth() {
 const login = async (username, password) => {
  try {
   const response = await authService.signIn(username, password);

   if (response.token) {
    authToken.value = response.token;
    isAuthenticated.value = true;

    // Decodificar JWT para extrair informações do usuário
    const payload = JSON.parse(atob(response.token.split(".")[1]));
    user.value = {
     username: payload.sub,
     exp: payload.exp,
     iat: payload.iat,
    };

    // Disparar evento personalizado para notificar outros componentes
    window.dispatchEvent(
     new CustomEvent("auth-changed", {
      detail: { authenticated: true, user: user.value },
     })
    );
   }

   return response;
  } catch (error) {
   throw error;
  }
 };

 const register = async (username, password) => {
  try {
   const response = await authService.signUp(username, password);

   if (response.token) {
    authToken.value = response.token;
    isAuthenticated.value = true;

    // Decodificar JWT para extrair informações do usuário
    const payload = JSON.parse(atob(response.token.split(".")[1]));
    user.value = {
     username: payload.sub,
     exp: payload.exp,
     iat: payload.iat,
    };

    // Disparar evento personalizado para notificar outros componentes
    window.dispatchEvent(
     new CustomEvent("auth-changed", {
      detail: { authenticated: true, user: user.value },
     })
    );
   }

   return response;
  } catch (error) {
   throw error;
  }
 };

 const logout = () => {
  authService.logout();
  isAuthenticated.value = false;
  user.value = null;
  authToken.value = null;

  // Disparar evento personalizado para notificar outros componentes
  window.dispatchEvent(
   new CustomEvent("auth-changed", {
    detail: { authenticated: false, user: null },
   })
  );
 };

 const checkAuthStatus = () => {
  const token = authService.getToken();
  if (token && !isTokenValid.value) {
   // Token expirado, fazer logout
   logout();
   return false;
  }
  return !!token;
 };

 // Inicializar na primeira chamada
 if (!authToken.value) {
  initializeAuth();
 }

 return {
  // Estado
  isAuthenticated: computed(() => isAuthenticated.value && isTokenValid.value),
  user: computed(() => user.value),
  authToken: computed(() => authToken.value),

  // Métodos
  login,
  register,
  logout,
  checkAuthStatus,

  // Utilidades
  isTokenValid,
 };
}

// Inicializar estado na primeira importação
initializeAuth();
