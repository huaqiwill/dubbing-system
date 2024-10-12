<template>
  <!-- 查敏感词 -->
  <DubbingButton
    title="查敏感词"
    content="从光标处开始试听"
    icon="SensitiveWords"
    @click="handleFindSensitiveWords"
  />
  <el-dialog v-model="dialogShow" title="查敏感词" draggable>
    <p>包含多个短视频敏感词，让创作者少违规、少限流。如果存在敏感词，会标红。</p>
    <div style="margin-top: 15px">
      <el-input type="textarea" v-model="text"></el-input>
    </div>
    <template #footer>
      <a-button type="primary" @click="handleFind">查敏感词</a-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { sensitiveWordIdentify } from "@/api";

const dialogShow = ref(false);
const handleFindSensitiveWords = () => {
  dialogShow.value = true;
};

const handleFind = () => {
  sensitiveWordIdentify(text).then((res) => {
// console.log(res);
  });
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
