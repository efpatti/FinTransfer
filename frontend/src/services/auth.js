import axios from "axios";

const API_URL = "/api/auth";

export const authService = {
 async signIn(username, password) {
  await axios.post("/api/log", {
   level: "info",
   message: "signIn request",
   details: { username },
  });
  try {
   const response = await axios.post(`${API_URL}/signin`, {
    username,
    password,
   });
   await axios.post("/api/log", {
    level: "info",
    message: "signIn response",
    details: { token: response.data.token ? "received" : "missing" },
   });
   if (response.data.token) {
    localStorage.setItem("jwt", response.data.token);
   }
   return response.data;
  } catch (error) {
   await axios.post("/api/log", {
    level: "error",
    message: "signIn error",
    details: error?.response?.data || error?.message,
   });
   throw error;
  }
 },
 async signUp(username, password) {
  await axios.post("/api/log", {
   level: "info",
   message: "signUp request",
   details: { username },
  });
  try {
   const response = await axios.post(`${API_URL}/signup`, {
    username,
    password,
   });
   await axios.post("/api/log", {
    level: "info",
    message: "signUp response",
    details: { token: response.data.token ? "received" : "missing" },
   });
   if (response.data.token) {
    localStorage.setItem("jwt", response.data.token);
   }
   return response.data;
  } catch (error) {
   await axios.post("/api/log", {
    level: "error",
    message: "signUp error",
    details: error?.response?.data || error?.message,
   });
   throw error;
  }
 },
 logout() {
  localStorage.removeItem("jwt");
  // Disparar evento de logout para notificar outros componentes
  window.dispatchEvent(new CustomEvent("auth-logout"));
 },
 getToken() {
  return localStorage.getItem("jwt");
 },
 isAuthenticated() {
  const token = this.getToken();
  if (!token) return false;

  // Verificar se o token não está expirado
  try {
   const payload = JSON.parse(atob(token.split(".")[1]));
   const now = Date.now() / 1000; // Converter para segundos
   return payload.exp > now;
  } catch (error) {
   console.error("Error parsing JWT token:", error);
   // Se não conseguir parsear o token, considerá-lo inválido
   this.logout();
   return false;
  }
 },
 getUserFromToken() {
  const token = this.getToken();
  if (!token) return null;

  try {
   const payload = JSON.parse(atob(token.split(".")[1]));
   return {
    username: payload.sub,
    exp: payload.exp,
    iat: payload.iat,
   };
  } catch (error) {
   console.error("Error parsing JWT token:", error);
   return null;
  }
 },
};

// Axios interceptor to attach JWT to requests
axios.interceptors.request.use((config) => {
 const token = authService.getToken();
 if (token) {
  config.headers.Authorization = `Bearer ${token}`;
 }
 return config;
});

// Axios interceptor to handle 401 responses (token expired)
axios.interceptors.response.use(
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
