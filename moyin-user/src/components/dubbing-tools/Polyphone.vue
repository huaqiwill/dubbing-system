<template>
  <!-- 多音字 -->
  <DubbingButton
    title="多音字"
    content="划选单个汉字，单击完成发音纠错"
    icon="dyz"
    @click="onClicked"
  />
  <el-dialog v-model="dialogShow" title="多音字" :width="300">
    <div>
      <el-button @click="onSelect">de</el-button>
      <el-button @click="onSelect">di</el-button>
      <el-button @click="onSelect">di2</el-button>
      <el-button @click="onSelect">di4</el-button>
      <el-button @click="onSelect">di</el-button>
      <el-button @click="onOk">确定</el-button>
    </div>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";
import { ElMessage } from "element-plus";

const { quillEditorRef } = storeToRefs(useDubbingStore());

const dialogShow = ref(false);

const onSelect = (item) => {};

// 多音字
const onClicked = () => {
  const quill = quillEditorRef.value.getQuill();
  const range = quill.getSelection();
  if (range) {
    const bounds = quill.getBounds(range.index, range.length);
    const text = quill.getText(range.index, range.length);
// console.log(bounds, text);
    if (text === "" || text.length !== 1) {
      ElMessage({
        message: "请划选文字",
        type: "warning",
      });

      return;
    }
    dialogShow.value = true;
  }
};

const onOk = () => {};
</script>

<style lang="scss" scoped></style>
