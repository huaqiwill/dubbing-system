<template>
  <!-- 音量 -->
  <el-popover
    placement="bottom"
    trigger="click"
    popper-style="width:auto;"
    :show-arrow="false"
  >
    <template #reference>
      <!-- 音量 -->
      <DubbingButton
        title="音量"
        content="从光标处开始试听"
        icon="volume"
        @click="handleClicked"
        placement="left"
      />
    </template>
    <div class="volume">
      <span class="demonstration">音量</span>
      <el-slider v-model="dubbingVolume" />
      <el-input
        v-model="dubbingVolume"
        :formatter="formatPercentage"
        :parser="parsePercentage"
      ></el-input>
    </div>
  </el-popover>
</template>

<script setup>
import { ref } from "vue";
import {  DubbingButton } from "@/components";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";

const { dubbingVolume } = storeToRefs(useDubbingStore());

const formatPercentage = (value) => {
  if (!value) return "";
  // Ensure the input is a valid number and add % at the end
  const numberValue = parseFloat(value.replace(/[^\d.-]/g, ""));
  if (isNaN(numberValue)) return "";
  return `${numberValue.toLocaleString()}%`;
};

const parsePercentage = (value) => {
  if (!value) return "";
  // Remove % and commas to get the pure number
  return value.replace(/[^\d.-]/g, "");
};

const handleClicked = () => {};
</script>

<style lang="scss" scoped>
.volume {
}
</style>
