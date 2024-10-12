<template>
  <!-- 解说模式 -->
  <el-popover
    placement="bottom"
    trigger="click"
    popper-style="width:auto;"
    :show-arrow="false"
  >
    <template #reference>
      <DubbingButton title="解说模式" content="从光标处开始试听" icon="xj" />
    </template>
    <div class="stress">
      <h2>全文停顿查找替换设置</h2>
      <el-switch v-model="value" size="large" />
      <span>{{ activeText }}</span>
      <hr />
      <div>
        影视解说模式
        <p>开启：全文的中停顿、长停顿将改为短停顿，节奏更快</p>
        <p>关闭：全文的中停顿、长停顿将恢复成默认，节奏偏慢</p>
      </div>
      <hr />
      <p>关闭解说模式后，可以自定义设置停顿参数</p>
    </div>
  </el-popover>
</template>

<script setup>
import { ref, watch } from "vue";
import { DubbingButton } from "@/components";

const value = ref(true);
const activeText = ref("已启用");

watch(value, (newVal, oldVal) => {
  activeText.value = newVal ? "已启用" : "已禁用";
});
</script>

<style lang="scss" scoped></style>
