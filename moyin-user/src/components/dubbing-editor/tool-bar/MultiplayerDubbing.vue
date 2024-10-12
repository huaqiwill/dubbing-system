<template>
  <!-- 多人配音 -->
  <DubbingButton
    title="多人配音"
    icon="d"
    content="从光标处开始试听"
    @click="handleClicked"
  />
  <el-dialog v-model="dialogShow" title="多人配音">
    <div class="stress">
      <ul>
        <li>重读</li>
        <li>拖音</li>
        <li>重读+拖音</li>
      </ul>
    </div>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import {  DubbingButton } from "@/components";

const handleClicked = () => {
  dialogShow.value = true;
};
const dialogShow = ref(false);
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
