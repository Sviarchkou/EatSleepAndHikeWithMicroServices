import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [vue()],
/*  server: {
    port: 5173, // Порт фронтенда остается 5173
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Порт и адрес бэкенда
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },*/
  resolve: {
    alias: {
      '@': '/src',
    },
  },
});