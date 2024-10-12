<template>
  <!-- 单字纠音 -->
  <DubbingButton
    title="单字纠音"
    content="从光标处开始试听"
    icon="SingleSorrection"
    @click="handleClicked"
  />
  <el-popover
    placement="bottom"
    trigger="click"
    popper-style="width:auto;"
    :show-arrow="false"
  >
    <template #reference> </template>
    <div class="stress">
      <ul>
        <li>重读</li>
        <li>拖音</li>
        <li>重读+拖音</li>
      </ul>
    </div>
  </el-popover>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";
import { ElMessage } from "element-plus";

const { editorRef } = storeToRefs(useDubbingStore());

const handleClicked = () => {
  let selectedText = editorRef.value.getSelectionText();
  if (Array.from(selectedText).length !== 1) {
    ElMessage({
      message: "请选择单个汉字！",
      type: "warning",
    });
    return;
  }

};
</script>

<style lang="scss" scoped>
.stress {
  ul {
    li {
      padding: 10px 0;
    }
  }
}
</style>
