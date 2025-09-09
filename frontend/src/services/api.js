import axios from "axios";

export const api = axios.create({
 baseURL: "/api",
});

// Função para configurar interceptors (será chamada após authService estar disponível)
export const configureApiInterceptors = (authService) => {
 // Axios interceptor to attach JWT to requests
 api.interceptors.request.use((config) => {
  const token = authService.getToken();
  if (token) {
   config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
 });

 // Axios interceptor to handle 401 responses (token expired)
 api.interceptors.response.use(
  (response) => response,
  (error) => {
   if (error.response?.status === 401) {
    // Token expirado ou inválido
    authService.logout();
    // Redirecionar para login se não estiver já na página de login
    if (
     window.location.pathname !== "/signin" &&
     window.location.pathname !== "/signup"
    ) {
     window.location.href = "/signin";
    }
   }
   return Promise.reject(error);
  }
 );
};
