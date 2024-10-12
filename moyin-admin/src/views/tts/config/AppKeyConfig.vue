<template>
  <el-card class="mt-3">
    <template #header>
      <div style="font-size: 1.25rem; line-height: 2.25rem">
        <span>秘钥配置</span>
      </div>
    </template>
    <el-form :model="form" label-width="100px" style="max-width: 600px">
      <el-form-item label="appKey" label-position="left">
        <el-input v-model="form.appKey" placeholder="请输入秘钥" />
      </el-form-item>
      <el-form-item label="appSecret" label-position="left">
        <el-input v-model="form.appSecret" placeholder="请输入秘钥" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style lang="scss" scoped></style>

<script>
import { getCurrentAppKey, updateAppKey } from "@/api/tts/appKey";
export default {
  name: "AppKeyConfig",
  data() {
    return {
      form: {
        appKey: "",
        appSecret: "",
      },
    };
  },
  created() {
    getCurrentAppKey().then((res) => {
      this.form = res.data;
    });
  },
  methods: {
    onSubmit() {
      if (this.form.id) {
        updateAppKey(this.form).then((res) => {
          this.$message.success("保存成功");
        });
      } else {
        this.$message.error("请先获取秘钥");
      }
    },
  },
};
</script>
