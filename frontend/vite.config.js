import { defineConfig } from "vite";
import tailwindcss from "tailwindcss";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
 plugins: [vue(), tailwindcss()],
 server: {
  port: 5173,
  proxy: {
   "/api": {
    target: "http://localhost:8080",
    changeOrigin: true,
    rewrite: (path) => path.replace(/^\/api/, ""),
   },
  },
 },
});
