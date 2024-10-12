<template>
  <!-- 试听 -->
  <DubbingButton
    title="试听"
    content="划选内容或点击光标，开始逐句试听"
    icon="play"
    @click="handleClicked"
  />
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { ElMessage } from "element-plus";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";
import { downloadAudio, tts } from "@/api/tts";
const Base64 = require("js-base64").Base64;

const { quillEditorRef } = storeToRefs(useDubbingStore());

const audioSrc = ref("");

const handleClicked = () => {
  let text = quillEditorRef.value.getText() || "";

// console.log(Base64.encode(text));

  if (text.length === 1) {
    ElMessage({
      message: "请输入文本后重试",
      type: "warning",
    });
    return;
  }

  let data = {
    text: "1212",
    speaker: "",
    audioType: "mp3",
    speed: 1,
    convert: "",
    rate: "",
    volume: 1,
    pitch: "",
    symbolSil: "",
    ignoreLimit: true,
    genSrt: true,
    mergeSymbol: false,
  };

// console.log(data);

  // tts(data).then((res) => {
// console.log(res);
  //   // audioSrc.value = "/prod-api/moyin/tts/audition/" + res.data;
  //   const audio = new Audio("/prod-api/moyin/tts/audition/" + res.data);
  //   audio.play();
  // });
};
</script>

<style lang="scss" scoped></style>
