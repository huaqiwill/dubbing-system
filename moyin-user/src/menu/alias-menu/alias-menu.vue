<script setup lang="ts">
// 别名 v1
import { onMounted, onUnmounted, ref, shallowRef, nextTick } from "vue";
import { BarButton, BarInput, BarPopover } from "@/components";
import { AliasFn } from "./alias-fn";
import type { IDomEditor } from "@wangeditor/editor";
import { getEmitter } from "@/core/emitter";
import type { SSMLBaseElement } from "@/core/base";
import { emitter } from "@/event-bus";
import { useEditorStore } from "@/stores";
import { ElMessage } from "element-plus";

const fn = shallowRef<AliasFn>();
const inputRef = ref<InstanceType<typeof BarInput>>();
const visible = ref(false);

onMounted(() => {
  emitter.on("editor-created", handleEditorCreated);
  nextTick(() => {
    const { editor } = useEditorStore();
    editor && handleEditorCreated(editor);
  });
});

onUnmounted(() => {
  emitter.off("editor-created", handleEditorCreated);
  const { editor } = useEditorStore();
  getEmitter(editor)?.off("ssml-remark-click", handleSSMLRemarkClick);
});

function handleEditorCreated(editor: IDomEditor) {
  getEmitter(editor).off("ssml-remark-click", handleSSMLRemarkClick);
  getEmitter(editor).on("ssml-remark-click", handleSSMLRemarkClick);
}

function handleSSMLRemarkClick(editor: IDomEditor, elem: SSMLBaseElement) {
  if (elem.type === "ssml-sub") {
    fn.value = undefined;
    handleClick(editor);
  }
}

function show() {
  if (visible.value) return;
  visible.value = true;
}

function hide() {
  if (!visible.value) return;
  visible.value = false;
}

async function handleClick(editor: IDomEditor) {
  fn.value ??= new AliasFn(editor);
  if (fn.value.isDisabled()) return;
  show();
  inputRef.value?.focus();
}

const text = ref("");

function handleSubmit() {
  hide();
  if (text.value) {
    fn.value?.exec({ value: text.value, label: text.value });
  }
}

function onTryListen() {
  ElMessage.warning({ message: "试听" });
}
</script>

<template>
  <BarPopover v-model:visible="visible" placement="bottom" :width="200">
    <template #reference>
      <BarButton icon="alias" @click="handleClick" class="">别名</BarButton>
    </template>
    <div class="alias">
      <BarInput
        ref="inputRef"
        v-model="text"
        @submit="handleSubmit"
        placeholder="请输入别名"
      ></BarInput>
      <div class="mt-2" style="font-size: 12px; color: #666">
        例如：例如：设置「YYDS」为「永远的神」
      </div>
      <div class="d-flex flex-row justify-content-between mt-2">
        <el-button type="primary" @click="onTryListen">试听</el-button>
        <el-button type="success" @click="handleSubmit">确定</el-button>
      </div>
    </div>
  </BarPopover>
</template>

<style lang="scss" scoped></style>
