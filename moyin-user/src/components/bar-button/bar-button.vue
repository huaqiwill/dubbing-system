<script setup lang="ts">
import { useEditorStore } from '@/stores'
import { type IDomEditor } from '@wangeditor/editor'
import throttle from 'lodash.throttle'

const emit = defineEmits<{ click: [editor: IDomEditor] }>()
withDefaults(
  defineProps<{
    icon?: string
    disabled?: boolean
  }>(),
  { icon: 'play', disabled: false },
)

const handleClick = throttle(() => {
  const { editor } = useEditorStore()
  if (editor) emit('click', editor)
})
</script>

<template>
  <button
    :disabled="disabled"
    class="play-menu d-flex flex-column justify-content-center align-items-center px-2 py-1 btn clickable"
    @click="handleClick"
    @mousedown.prevent
  >
    <div
      class="play-menu-icon d-flex justify-content-center align-items-center"
      style="height: 22px; width: 22px"
    >
      <slot name="icon">
        <span class="fs-3 iconfont-moyin" :class="[`moyin-icon_${icon}`]"></span>
      </slot>
    </div>
    <div class="play-men-font fw-normal" style="font-size: 11px">
      <slot></slot>
    </div>
  </button>
</template>

<style lang="scss" scoped>
button {
  width: 65px;
  height: 65px;
  border: none;
}
.play-menu-icon {
  color: rgb(198, 19, 19);
}
</style>
