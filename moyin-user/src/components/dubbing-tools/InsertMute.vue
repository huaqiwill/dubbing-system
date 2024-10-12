<template>
  <!-- 插入静音 -->
  <el-popover
    placement="bottom"
    trigger="click"
    :popper-style="popperStyle"
    :show-arrow="false"
  >
    <template #reference>
      <DubbingButton
        title="插入静音"
        content="从光标处开始试听"
        icon="InsertMute"
        @click="onClicked"
      />
    </template>
    <div>
      <el-radio-group v-model="radio1" class="ml-4">
        <el-radio :value="1">所有段落</el-radio>
        <el-radio :value="2">当前段落</el-radio>
      </el-radio-group>
      <el-checkbox v-model="checked1" label="显示静音段落" />
    </div>
    <el-button-group>
      <el-button type="primary" @click="onInsertMute(100)">100ms</el-button>
      <el-button type="primary" @click="onInsertMute(150)">150ms</el-button>
      <el-button type="primary" @click="onInsertMute(200)">200ms</el-button>
      <el-button type="primary" @click="onInsertMute(300)">300ms</el-button>
      <el-button type="primary" @click="onInsertMute(400)">400ms</el-button>
      <el-button type="primary" @click="onInsertMute(600)">600ms</el-button>
    </el-button-group>
  </el-popover>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";
import { ElMessage } from "element-plus";

const { quillEditorRef } = storeToRefs(useDubbingStore());

const checked1 = ref(false);
const radio1 = ref(false);

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

const onInsertMute = (value) => {
  editorRef.value.insertElement(value);
};
</script>

<style lang="scss" scoped>
.popper-class {
  min-width: 20px !important;
  width: auto;
}

.polyphone {
  display: flex;
}

ul {
  background-color: #fff;
  display: flex;
  gap: 10px;

  li {
    border: 1px solid #e5e5e5;
    padding: 4px 10px;
    border-radius: 4px;
    cursor: pointer;

    &:hover {
      color: #6aa4fc;
      background-color: #e8f1fe;
    }
  }
}
</style>
