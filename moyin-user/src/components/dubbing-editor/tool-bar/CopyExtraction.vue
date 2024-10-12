<template>
  <!-- 文本提取 -->
  <el-popover
    placement="bottom"
    trigger="click"
    popper-style="width:auto;"
    :show-arrow="false"
  >
    <template #reference>
      <DubbingButton
        title="文案提取"
        content="从光标处开始试听"
        icon="TextExtraction"
      ></DubbingButton>
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
import {  DubbingButton } from "@/components";
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
