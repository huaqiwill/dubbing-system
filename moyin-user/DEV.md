# 开发指南

## 环境

```sh
# 安装yalc
npm install -g yalc
```

## 本地开发及使用

1.编译和发布本项目

```sh
# 编译项目
yarn build
# 发布项目到本地(可使用yalc dir命令查看发布位置)
yalc push
```

2.在自己的项目中

```sh
# 将原有 @mekumiao/ssml-speaker-editor 依赖换为本地依赖
yalc add @mekumiao/ssml-speaker-editor
# 更新包
yarn
```

3.由于`node_modules`缓存原因需要删除以下目录,然后重启应用

```txt
node_modules/.cache
node_modules/.vite
```
