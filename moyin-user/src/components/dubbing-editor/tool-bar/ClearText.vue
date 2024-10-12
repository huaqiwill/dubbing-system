<template>
  <!-- 一键清空 -->
  <DubbingButton title="一键清空" icon="clear" @click="handleClearText" />
</template>

<script setup>
import { ref } from "vue";
import {  DubbingButton } from "@/components";

import { ElMessageBox } from "element-plus";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";

const { quillEditorRef } = storeToRefs(useDubbingStore());

// 清空文本
const handleClearText = () => {
  ElMessageBox.confirm("确认清空文本吗?", "警告", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    const quill = quillEditorRef.value.getQuill();
    quill.setContents([{ insert: "\n" }]); // 设置内容为空
  });
};
</script>

<style lang="scss" scoped></style>
