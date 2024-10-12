<template>
  <!-- 重音 -->
  <el-popover
    placement="bottom"
    trigger="click"
    :popper-style="popperStyle"
    :show-arrow="false"
  >
    <template #reference>
      <DubbingButton
        title="数字符号"
        content="划选数字或符号，点选合适的读法"
        icon="MumberSymbol"
        @click="onClicked"
      />
    </template>
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

const { quillEditorRef } = storeToRefs(useDubbingStore());

const popperStyle = ref("width:auto");

const onClicked = () => {
  const pos = quillEditorRef.value.getCursorPosition();
  popperStyle.value = `width:auto;left:${pos.x}px;top:${pos.y}px`;
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
