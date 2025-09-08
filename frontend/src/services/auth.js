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
    details: response.data,
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
    details: response.data,
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
 },
 getToken() {
  return localStorage.getItem("jwt");
 },
 isAuthenticated() {
  return !!localStorage.getItem("jwt");
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
