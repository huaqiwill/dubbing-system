import { defineConfig, loadEnv } from "vite";
import path from "path";
import vue from "@vitejs/plugin-vue";
import { createHtmlPlugin } from "vite-plugin-html";
import { createSvgIconsPlugin } from "vite-plugin-svg-icons";
import { resolve } from "path";
import dts from "vite-plugin-dts";

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd());
  const name = env.VITE_APP_TITLE || "配音工具"; // 网页标题
  const port = parseInt(env.VITE_PORT || "8091"); // 端口

  return {
    base: env.NODE_ENV === "production" ? "/" : "/",
    resolve: {
      // 设置路径别名
      alias: {
        "@": resolve(__dirname, "./src"),
        "*": resolve(""),
      },
    },
    build: {
      outDir: "dist",
      assetsDir: "static",
      sourcemap: false,
      rollupOptions: {
        output: {
          chunkFileNames: "static/js/[name].[hash].js",
          entryFileNames: "static/js/[name].[hash].js",
          assetFileNames: "static/[ext]/[name].[hash].[ext]",
        },
      },
    },
    server: {
      host: "0.0.0.0",
      port: port,
      open: true,
      proxy: {
        ['/prod-api']: {
          target: `http://localhost:8080`,
          changeOrigin: true,
          rewrite: (path) =>
            path.replace(new RegExp("^" + env.VITE_APP_BASE_API), ""),
        },
      },
    },
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: `@import "@/assets/variables.scss";`,
          sassOptions: {
            outputStyle: "expanded",
          },
        },
      },
    },
    plugins: [
      vue(),
      createHtmlPlugin({
        minify: true,
        inject: {
          data: {
            title: name,
          },
        },
      }),
      createSvgIconsPlugin({
        // 指定目录(svg存放目录）
        iconDirs: [path.resolve(process.cwd(), "src/assets/icons")],
        // 使用 svg 图标的格式（name为图片名称）
        symbolId: "icon-[name]",
        //生成组件插入位置 只有两个值 boby-last | body-first
        inject: "body-last",
      }),
    ],
    optimizeDeps: {
      include: [],
    },
  };
});
