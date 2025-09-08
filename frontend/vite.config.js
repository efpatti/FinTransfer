import { defineConfig } from "vite";
import tailwindcss from "tailwindcss";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
 plugins: [vue(), tailwindcss()],
 server: {
  port: 5173,
  proxy: {
   "/api": "http://localhost:8080",
  },
 },
});
