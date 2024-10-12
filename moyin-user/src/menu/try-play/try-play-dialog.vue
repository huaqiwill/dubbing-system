<script setup lang="ts">
import { onMounted, onUnmounted, ref, inject, type Ref } from 'vue'
import { useConstrainDragBounds } from '@/components'
import { useDraggable, useElementBounding } from '@vueuse/core'
import { emitter } from '@/event-bus'

const emit = defineEmits<{ 'update:visible': [value: boolean] }>()
const props = defineProps<{ visible: boolean }>()

const boxRef = ref<HTMLElement>()
const handleRef = ref<HTMLElement>()
const dragContainerBoxRef = inject<Ref<HTMLElement | undefined>>('dragContainerBox')
const editorViewBoxBounds = useElementBounding(dragContainerBoxRef)

onMounted(() => {
  emitter.on('view-keydown', handleKeyDownEsc)
})

onUnmounted(() => {
  emitter.off('view-keydown', handleKeyDownEsc)
})

function handleKeyDownEsc(event: KeyboardEvent) {
  if (event.code === 'Escape') {
    props.visible && handleMinimize()
  }
}

const { position } = useDraggable(handleRef, {
  initialValue: { x: 40, y: 40 },
})
const { style } = useConstrainDragBounds(boxRef, dragContainerBoxRef, position)

onMounted(() => {
  const point = {
    x: editorViewBoxBounds.x.value + (editorViewBoxBounds.width.value - 890) / 2,
    y: editorViewBoxBounds.y.value + (editorViewBoxBounds.height.value - 390) / 2,
  }
  position.value = point
})

function handleMinimize() {
  emit('update:visible', false)
}
</script>

<template>
  <div
    ref="boxRef"
    v-show="visible"
    :style="style"
    style="position: fixed"
    class="try-play user-select-none card z-3 shadow"
  >
    <div class="box ms-2">
      <div
        class="text-center d-flex flex-row justify-content-between"
        style="height: 30px"
      >
        <div ref="handleRef" class="h-100 w-100 text-start" style="cursor: move">
          <span class="dubbing-title">配音员列表</span>
        </div>
        <button @click="handleMinimize" class="btn btn-sm border-0" style="width: 45px">
          <span class="iconfont icon-zuixiaohua text-white fs-6"></span>
        </button>
      </div>
      <div class="try-play-body">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
$width: 858px;
$height: 450px;

.dubbing-title {
  text-align: left;
  color: #fff;
  line-height: 30px;
}

.try-play {
  width: $width;
  background-color: #00273b;
  padding-right: 8px;

  .try-play-body {
    border-top: 1px solid #3463ab;
    height: $height;

    .try-play-left {
      height: $height - 15px;
    }

    .try-play-right {
      height: $height - 15px;
      border-left: 1px solid #3463ab;
    }

    :deep(.el-input__wrapper) {
      background-color: transparent;
    }
  }
}
</style>
