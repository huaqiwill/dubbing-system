<template>
  <!-- 查敏感词 -->
  <DubbingButton
    title="查敏感词"
    content="从光标处开始试听"
    icon="SensitiveWords"
    @click="handleFindSensitiveWords"
  />
  <el-dialog v-model="dialogShow" title="查敏感词">
    <div>包含多个短视频敏感词，让创作者少违规、少限流。如果存在敏感词，会标红。</div>
    <div>
      <el-input type="textarea" v-model="text"></el-input>
    </div>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";

const dialogShow = ref(false);
const handleFindSensitiveWords = () => {
  dialogShow.value = true;
};

const text = ref("");
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
