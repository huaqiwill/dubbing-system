<template>
  <!-- 别名 -->
  <el-popover
    placement="bottom"
    trigger="click"
    :popper-style="popperStyle"
    :show-arrow="false"
  >
    <template #reference>
      <DubbingButton
        title="音标"
        content="划选数字或符号，点选合适的读法"
        icon="Mark"
        @click="onClicked"
      />
    </template>
    <div class="alias">
      <el-input placeholder="请输入英文音标" v-model="aliasInput"></el-input>
      <p class="desc">
        <a href="http://www.youdao.com/w/eng/a/">有道音标</a>
        <a href="https://fanyi.baidu.com/#en/zh/a">百度音标</a>
      </p>
      <div class="btn-group">
        <el-button type="primary" @click="onTryListen">试听</el-button>
        <el-button type="success" @click="onOk">确定</el-button>
      </div>
    </div>
  </el-popover>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";
const { quillEditorRef } = storeToRefs(useDubbingStore());

const aliasInput = ref("");

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

const onTryListen = () => {};

const onOk = () => {};
</script>

<style lang="scss" scoped>
.alias {
  .desc {
    font-size: 12px;
    color: #666;
    margin: 10px 0;
  }
  .btn-group {
    display: flex;
    justify-content: space-between;
  }
}
</style>
