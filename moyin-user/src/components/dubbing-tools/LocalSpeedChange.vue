<template>
  <!-- 局部变速 -->
  <el-popover
    placement="bottom"
    trigger="click"
    :popper-style="popperStyle"
    :show-arrow="false"
  >
    <template #reference>
      <DubbingButton
        title="局部变速"
        content="从光标处开始试听"
        icon="SpeedChange"
        @click="onClicked"
      />
    </template>
    <div class="speed-adjust">
      <el-slider v-model="value1" :min="0" :max="2" :step="0.1" :marks="marks" />
    </div>
  </el-popover>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";

import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";

const { quillEditorRef } = storeToRefs(useDubbingStore());
const popperStyle = ref({
  width: "auto",
});

const onClicked = () => {
  const pos = quillEditorRef.value.getCursorPosition();
  popperStyle.value = {
    width: "auto",
    left: pos.x + "px",
    top: pos.y + "px",
  };
};

const marks = {
  0: "0x",
  0.1: "0.1x",
  0.2: "0.2x",
  0.3: "0.2x",
  0.4: "0.4x",
  0.5: "0.5x",
  0.6: "0.6x",
  0.7: "0.7x",
  0.8: "0.8x",
  0.9: "0.9x",
  1.0: "1.0x",
  1.1: "1.1x",
  1.2: "1.2x",
  1.3: "1.3x",
  1.4: "1.4x",
  1.5: "1.5x",
  1.6: "1.6x",
  1.7: "1.7x",
  1.8: "1.8x",
  1.9: "1.9x",
  2.0: "2.0x",
};

const value1 = ref(1);
</script>

<style lang="scss" scoped>
.speed-adjust {
  padding: 10px;
  width: 800px;
  ul {
    li {
      padding: 10px 0;
    }
  }
}
</style>
