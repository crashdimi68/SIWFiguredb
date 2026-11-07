import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { fileURLToPath, URL } from 'node:url';
// La build genera un bundle statico che Spring Boot serve direttamente da
// src/main/resources/static/js/react-figure/ (vedi template cercaFigure.html).
//
// Questa cartella (frontend/react-figure/) sta alla radice del progetto,
// separata da src/main/resources. Il path relativo per outDir e' quindi
// ../../src/main/resources/static/js/react-figure/ .
//
// I file .js/.css hanno nomi fissi (no hash) cosi' Thymeleaf puo' referenziarli
// staticamente senza dover leggere un manifest.
export default defineConfig({
    plugins: [react()],
    base: '/js/react-figure/',
    server: {
        port: 5173,
        proxy: {
            '/api': 'http://localhost:8081',
        },
    },
    build: {
        outDir: fileURLToPath(new URL('../../src/main/resources/static/js/react-figure', import.meta.url)),
        emptyOutDir: true,
        rollupOptions: {
            output: {
                entryFileNames: 'assets/index.js',
                chunkFileNames: 'assets/[name].js',
                assetFileNames: 'assets/[name][extname]',
            },
        },
    },
});
