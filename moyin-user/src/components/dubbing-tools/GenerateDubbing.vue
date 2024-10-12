<template>
  <!-- 生成配音 -->
  <DubbingButton
    title="生成配音"
    content="从光标处开始试听"
    icon="genarator"
    @click="handleClicked"
  />
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { ElMessage } from "element-plus";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";

const { editorRef } = storeToRefs(useDubbingStore());

const handleClicked = () => {
  let html = editorRef.value.getHtml();
  ElMessage({
    message: html,
  });
};
</script>

<style lang="scss" scoped></style>
