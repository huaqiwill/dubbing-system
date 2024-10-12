import { type UserConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default function (): UserConfig {
  return {
    plugins: [vue()],
    resolve: {
      alias: {
        '@': resolve(__dirname, 'src'),
      },
    },
    server: {
      host: '0.0.0.0',
      port: 8080,
      open: true,
      proxy: {
        ['api']: {
          target: `http://localhost:8080`,
          changeOrigin: true,
          rewrite: (path) => path.replace(new RegExp('^api'), ''),
        },
      },
    },
  }
}
