<template>
  <el-card>
    <template #header>
      <div style="font-size: 1.25rem; line-height: 2.25rem">
        <span>配音配置</span>
      </div>
    </template>
    <el-form :model="form" label-width="100px" style="max-width: 600px">
      <el-form-item label="用户字符限制" label-position="left">
        <el-input v-model="form.websiteName" placeholder="请输入用户字符限制" />
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
