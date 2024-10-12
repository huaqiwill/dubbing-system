<template>
  <!-- 下载配音 -->
  <DubbingButton
    title="下载配音"
    content="从光标处开始试听"
    icon="download"
    @click="onClicked"
  />
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { ElMessage } from "element-plus";
import { downloadAudio } from "@/api/tts";

const onClicked = () => {
  downloadAudio("0CE40145E9DC86E1F04F3D8516FD7FC343E793F3FBB7982EAF03A305B76E6042").then(
    (res) => {
// console.log(res);
// console.log(res);
      const blob = new Blob([res], { type: "audio/mp3" });
      const localUrl = (window.URL || webkitURL).createObjectURL(blob);
      const audio = document.createElement("audio");
      audio.style.display = "none"; // 防止影响页面布局
      audio.controls = true;
      document.body.appendChild(audio);
      audio.src = localUrl;
      audio.playbackRate = 1.3; // 语速
      audio.play();

      // 语音播放完毕后，需要手动释放内存
      audio.onended = function () {
        document.body.removeChild(audio);
        URL.revokeObjectURL(localUrl);
      };
    }
  );
};
</script>

<style lang="scss" scoped></style>
