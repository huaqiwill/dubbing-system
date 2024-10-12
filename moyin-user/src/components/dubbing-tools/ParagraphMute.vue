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
        title="段落停顿"
        content="从光标处开始试听"
        icon="paragraph"
      ></DubbingButton>
    </template>
    <div>
      <el-radio-group v-model="radio1" class="ml-4">
        <el-radio :value="1">所有段落</el-radio>
        <el-radio :value="2">当前段落</el-radio>
      </el-radio-group>
      <el-checkbox v-model="checked1" label="显示静音段落" />
    </div>
    <el-button-group>
      <el-button type="primary" @click="handleMute(100)">100ms</el-button>
      <el-button type="primary" @click="handleMute(150)">150ms</el-button>
      <el-button type="primary" @click="handleMute(200)">200ms</el-button>
      <el-button type="primary" @click="handleMute(300)">300ms</el-button>
      <el-button type="primary" @click="handleMute(400)">400ms</el-button>
      <el-button type="primary" @click="handleMute(600)">600ms</el-button>
    </el-button-group>
  </el-popover>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";

const { editorRef } = storeToRefs(useDubbingStore());

const radio1 = ref(1);
const checked1 = ref(false);

const handleMute = (value) => {
  editorRef.value.insertElement(value);
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
