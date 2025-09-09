import axios from "axios";
import { configureApiInterceptors } from "./api";

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

// Configurar interceptors da API para incluir JWT token
configureApiInterceptors(authService);
