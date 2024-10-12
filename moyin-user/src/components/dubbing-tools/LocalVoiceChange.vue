<template>
  <!-- 局部变音 -->
  <DubbingButton
    title="局部变音"
    content="从光标处开始试听"
    icon="VoiceChange"
    @click="handleMultiplayerDubbing"
  />

  <el-dialog v-model="dialogShow" title="局部变音" width="400" draggable>
    <div class="voice-adjust">
      <p>请在安静的环境中进行录音，以需要转换的风格，读出以下文案</p>
      <el-input v-model="textarea" :rows="2" type="textarea" placeholder="Please input" />
      <div class="btn-wrapper">
        <el-button type="primary" style="width: 100%" @click="handleDu"
          >实时录音</el-button
        >
      </div>
      <div class="btn-wrapper">
        <el-button type="success" style="width: 100%">上传录音</el-button>
      </div>
      <p>录音内容需要和滑选一致，音频限制60秒，滑选文案限制60个字</p>
    </div>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";

const handleDu = () => {
  alert("实时录音");
};

const textarea = ref("");
const handleMultiplayerDubbing = () => {
  dialogShow.value = true;
};

const dialogShow = ref(false);
</script>

<style lang="scss" scoped>
.voice-adjust {
  .btn-wrapper {
    width: 100%;
    margin-top: 15px;
  }
}
</style>
