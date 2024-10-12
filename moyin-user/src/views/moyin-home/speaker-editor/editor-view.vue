<template>
  <div
    ref="boxRef"
    class="ssml-editor-root editor-view"
    @click="handleClick"
    style="width: 100%; height: 100%"
  >
    <div class="editor-box">
      <div class="d-flex justify-content-between">
        <EditorBar></EditorBar>
        <UserInfo></UserInfo>
      </div>
      <div class="editor-core-container pt-1">
        <EditorCore @change="handleChange" @created="handleCreated"></EditorCore>
        <slot name="sidebar"></slot>
      </div>
      <slot name="footer">
        <DubbingFooter />
      </slot>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.editor-view {
  background-color: var(--tool-bg-color);
  width: 100%;
  height: 100%;

  .editor-box {
    background-color: var(--tool-bg-grey-color);
    width: 100%;
    height: calc(100% - 60px);

    .editor-core-container {
      margin: 0 auto;
      width: 100%;
      background-color: #fff;
      padding: 5px 0 5px 0;
      height: calc(100% - 75px);
      display: flex;
    }
  }

  .dubbing-footer {
    height: 50px;
    background-color: #f4f6f7;
  }
}
</style>

<script setup lang="ts">
import { DubbingFooter } from './speaker-footer'
import EditorCore from './editor-core.vue'
import EditorBar from './editor-bar.vue'
import { type IDomEditor } from '@wangeditor/editor'
import { emitter } from '@/event-bus.ts'
import { ref, provide, onMounted, onUnmounted, toRaw, computed } from 'vue'
import { type PartialSSMLEditorConfig, setConfig } from '@/config'
import { ElMessage } from 'element-plus'
import { useDubbingStore } from '@/stores'
import { ElMessageBox } from 'element-plus'
import { UserInfo } from './user-info'

const dubbingStore = useDubbingStore()
const boxRef = ref<HTMLDivElement>()
const props = withDefaults(
  defineProps<{ config?: PartialSSMLEditorConfig; editorKey?: symbol }>(),
  {
    editorKey: () => Symbol('editorKey'),
  },
)

// 设置拖拽容器盒子,如果想要在整个页面可拖拽,将boxRef换为ref(document.body)即可
setConfig(props.editorKey, toRaw(props.config))
provide('dragContainerBox', boxRef)
provide('editorKey', props.editorKey)

const emit = defineEmits<{ created: [editor: IDomEditor]; change: [editor: IDomEditor] }>()

onMounted(() => {
  document.addEventListener('keydown', handleKeyDown)
  dubbingStore.setGlobaleEditorKey(props.editorKey)
})

onUnmounted(() => {
  document.removeEventListener('keydown', handleKeyDown)
})

function handleCreated(editor: IDomEditor) {
  emit('created', editor)
}

function isAnsi(char: string) {
  return char.charCodeAt(0) >= 0 && char.charCodeAt(0) <= 127
}

function calculateDubbingLength(text: string) {
  let dubbingLength = 0
  for (let i = 0; i < text.length; i++) {
    if (isAnsi(text.charAt(i))) {
      dubbingLength += 1
    } else {
      dubbingLength += 2
    }
  }
  return dubbingLength
}

function handleChange(editor: IDomEditor) {
  emit('change', editor)
  let count = calculateDubbingLength(editor.getText()) || 0
  emitter.emit('editor:change:count', count)
}

function handleClick(ev: MouseEvent) {
  emitter.emit('view-click', ev)
}

function handleKeyDown(ev: KeyboardEvent) {
  emitter.emit('view-keydown', ev)
}
</script>
