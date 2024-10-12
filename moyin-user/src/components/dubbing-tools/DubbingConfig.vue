<template>
  <!-- 配音配置 -->
  <DubbingButton
    title="配音配置"
    content="从光标处开始试听"
    icon="settings"
    @click="okClicked"
  ></DubbingButton>

  <el-dialog v-model="dialogShow" title="配音配置" draggable>
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
import { DubbingButton } from "@/components";

const okClicked = () => {
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
