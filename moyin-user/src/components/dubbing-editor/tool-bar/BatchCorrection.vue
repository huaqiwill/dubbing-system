<template>
  <!-- 批量纠音 -->
  <DubbingButton
    title="批量纠音"
    content="从光标处开始试听"
    icon="BatchSorrection"
    @click="handleClicked"
  />

  <el-dialog v-model="dialogShow" title="多音字检测">
    <p>点击要修改发音的汉字，选择想要的发音</p>
    <p>若有多音字发音不对，可输入发音相同的字替代</p>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import {  DubbingButton } from "@/components";

const dialogShow = ref(false);

const handleClicked = () => {
  dialogShow.value = true;
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
