<template>
  <!-- 试听 -->
  <DubbingButton
    title="试听"
    content="划选内容或点击光标，开始逐句试听"
    icon="play"
    @click="handleClicked"
  />
</template>

<script setup>
import { DubbingButton } from "@/components";
import { ElMessage } from "element-plus";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";

const { quillEditorRef } = storeToRefs(useDubbingStore());

const handleClicked = () => {
  ElMessage({
    message: quillEditorRef.value.getSelectionText(),
    type: "warning",
  });
};
</script>

<style lang="scss" scoped></style>
