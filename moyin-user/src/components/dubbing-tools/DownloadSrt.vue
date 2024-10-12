<template>
  <!-- 下载字幕 -->
  <DubbingButton
    title="下载字幕"
    content="从光标处开始试听"
    icon="srt"
    @click="handleClicked"
  />
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { ElMessage } from "element-plus";

const handleClicked = () => {
  ElMessage({
    message: "下载字幕",
  });
};
</script>

<style lang="scss" scoped></style>
