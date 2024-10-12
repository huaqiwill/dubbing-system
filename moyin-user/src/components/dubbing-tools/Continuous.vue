<template>
  <!-- 连续 -->
  <DubbingButton
    title="连续"
    content="划选数字或符号，点选合适的读法"
    icon="zy"
    @click="onClicked"
  ></DubbingButton>
</template>

<script setup>
import { DubbingButton } from "@/components";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";

const { quillEditorRef } = storeToRefs(useDubbingStore());

const onClicked = () => {
  const quill = quillEditorRef.value.getQuill();
  const range = quill.getSelection();
  // quill.formatText(range.index, range.length, { highlight: "yellow" });
  // quill.insertEmbed(range.index, "StressBlot", "Some Value");
  // quill.insertEmbed(range.index, "stress", "Rectangle Value");
  // quill.setSelection(range.index + 1); // 将光标移到矩形后面
  quill.insertText(range.index, "200", { "custom-inline": "Custom Inline Value" });
  quill.setSelection(range.index + 1); // 将光标移到自定义 Inline 后面
};
</script>

<style lang="scss" scoped></style>
