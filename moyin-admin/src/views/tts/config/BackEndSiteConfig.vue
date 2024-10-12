<template>
  <el-card class="mt-3">
    <template #header>
      <div style="font-size: 1.25rem; line-height: 2.25rem">
        <span>后台站点配置</span>
      </div>
    </template>
    <el-form :model="form" label-width="100px" style="max-width: 600px">
      <el-form-item label="网站名称" label-position="left">
        <el-input v-model="form.websiteName" placeholder="请输入网站名称" />
      </el-form-item>
      <el-form-item label="网站图标" label-position="left">
        <el-input v-model="form.websiteName" placeholder="请输入秘钥" />
      </el-form-item>
      <el-form-item label="默认头像" label-position="left">
        <el-input v-model="form.websiteName" placeholder="默认头像" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
export default {
  name: "SiteConfig",
  data() {
    return {
      form: {
        websiteName: "配音工具",
      },
    };
  },
  methods: {
    onSubmit() {},
  },
};
</script>

<style lang="scss" scoped></style>
