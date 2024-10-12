<template>
  <!-- 重音 -->
  <el-popover
    placement="bottom"
    trigger="click"
    popper-style="width:auto;"
    :show-arrow="false"
  >
    <template #reference>
      <DubbingButton
        title="配音配置"
        content="从光标处开始试听"
        icon="settings"
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
