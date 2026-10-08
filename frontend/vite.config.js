import { defineConfig, loadEnv } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), "");
  // Spring Boot run:      http://localhost:8080
  // Tomcat 9 WAR deploy:  http://localhost:8080/studentloan
  const backend = env.BACKEND_URL || "http://localhost:8080";
  return {
    plugins: [react()],
    server: { port: 5173, proxy: { "/api": backend } },
  };
});
