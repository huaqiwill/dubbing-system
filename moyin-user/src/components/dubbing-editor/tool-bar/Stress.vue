<template>
  <!-- 重音 -->
  <el-popover
    :teleported="true"
    placement="bottom"
    trigger="click"
    :popper-style="popperStyle"
    :show-arrow="false"
  >
    <template #reference>
      <DubbingButton
        title="重音"
        content="划选需要重音的内容，选择重音"
        icon="zy2"
        @click="handleClicked"
      />
    </template>
    <div class="stress">
      <ul>
        <li @click="onStress()">重读</li>
        <li @click="onDrag()">拖音</li>
        <li @click="onStressAndDrag()">重读+拖音</li>
      </ul>
    </div>
  </el-popover>
</template>

<script setup>
import { onMounted, ref } from "vue";
import {  DubbingButton } from "@/components";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";
import { ElMessage } from "element-plus";
import { Quill } from "@vueup/vue-quill";

const Inline = Quill.import("blots/inline");
const { quillEditorRef } = storeToRefs(useDubbingStore());

onMounted(() => {
  // 重音
  class StressBlot extends Inline {
    static create(value) {
      let node = super.create();
      node.setAttribute(
        "style",
        `width: 100px; height: 50px; background-color: lightblue;user-select:none;padding:5px;border-radius:5px;`
      );
      node.setAttribute("data-value", value);
      node.setAttribute("contenteditable", "false"); // 设置为可编辑
      return node;
    }

    static value(node) {
      return node.getAttribute("data-value");
    }
  }
  StressBlot.blotName = "StressBlot";
  StressBlot.tagName = "span"; // 使用 span 元素或其他适合的元素
  Quill.register(StressBlot);

  // 拖音
  class DragBlot extends Inline {
    static create(value) {
      let node = super.create();
      node.setAttribute(
        "style",
        `width: 100px; height: 50px; background-color: lightblue;user-select:none;padding:5px;border-radius:5px;`
      );
      node.setAttribute("data-value", value);
      node.setAttribute("contenteditable", "false"); // 设置为可编辑
      node.setAttribute("innerHTML", "拖音");
      return node;
    }

    static value(node) {
      return node.getAttribute("data-value");
    }
  }
  DragBlot.blotName = "DragBlot";
  DragBlot.tagName = "span"; // 使用 span 元素或其他适合的元素
  Quill.register(DragBlot);

  // 重读+拖音
  class StressAndDragBlot extends Inline {
    static create(value) {
      let node = super.create();
      node.setAttribute(
        "style",
        `width: 100px; height: 50px; background-color: lightblue;user-select:none;padding:5px;border-radius:5px;`
      );
      node.setAttribute("data-value", value);
      node.setAttribute("contenteditable", "false"); // 设置为可编辑
      node.setAttribute("innerHTML", "拖音");
      return node;
    }

    static value(node) {
      return node.getAttribute("data-value");
    }
  }
  StressAndDragBlot.blotName = "StressAndDragBlot";
  StressAndDragBlot.tagName = "span"; // 使用 span 元素或其他适合的元素
  Quill.register(StressAndDragBlot);
});

const onStress = () => {
  const quill = quillEditorRef.value.getQuill();
  quill.focus();
// console.log(quill);
  const range = quill.getSelection();
// console.log(range);
  if (range) {
    quill.insertText(range.index, "200", { StressBlot: "Custom Inline Value" });
    quill.setSelection(range.index + 1); // 将光标移到自定义 Inline 后面
  }
};

const onDrag = () => {
  const quill = quillEditorRef.value.getQuill();
  quill.focus();
  const range = quill.getSelection();
  if (range) {
    quill.insertText(range.index, "200", { DragBlot: "Custom Inline Value" });
    quill.setSelection(range.index + 1); // 将光标移到自定义 Inline 后面
  }
};

const popperStyle = ref(`width:auto;`);

const handleClicked = () => {
  const quill = quillEditorRef.value.getQuill(); // 获取 Quill 实例
  const range = quill.getSelection(); // 获取当前光标位置
  if (range) {
    const editorElement = quillEditorRef.value.editor.querySelector(".ql-speaker-editor");
    const rect = editorElement.getBoundingClientRect();
    const bounds = quill.getBounds(range.index, range.length); // 获取光标位置的边界信息
    const x = rect.x + bounds.right;
    const y = rect.y + bounds.bottom;
    popperStyle.value = `width:auto;left:${x}px;top:${y}px`;
  }
};

const onStressAndDrag = () => {
  const quill = quillEditorRef.value.getQuill(); // 获取 Quill 实例
  quill.focus();
  const range = quill.getSelection(); // 获取当前光标位置
  if (range) {
    quill.insertText(range.index, "200", { StressAndDragBlot: "Custom Inline Value" });
    quill.setSelection(range.index + 1); // 将光标移到自定义 Inline 后面
  }
};
</script>

<style lang="scss" scoped>
.stress {
  ul {
    li {
      padding: 10px;
      border-radius: 4px;
      cursor: pointer;
      &:hover {
        color: #000;
        background-color: #ccc;
      }
    }
  }
}
</style>
