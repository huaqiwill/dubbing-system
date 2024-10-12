<script setup lang="ts">
import { ref, inject, onMounted, type Ref } from 'vue'
import { useConstrainDragBounds } from '@/components'
import { useDraggable, useElementBounding } from '@vueuse/core'
import { useTryPlayStore } from '@/stores'
import PlayButton from './play-button.vue'
// import { storeToRefs } from 'pinia'

const emit = defineEmits<{ 'update:visible': [value: boolean] }>()
defineProps<{ visible: boolean }>()

const boxRef = ref<HTMLDivElement>()
const dragContainerBoxRef = inject<Ref<HTMLElement | undefined>>('dragContainerBox')
const editorViewBoxBounds = useElementBounding(dragContainerBoxRef)
const playButtonRef = ref<InstanceType<typeof PlayButton>>()
const recordClientX = ref<number>(0)
const recordClientY = ref<number>(0)

const tryPlayStore = useTryPlayStore()

const { position } = useDraggable(boxRef, {
  onStart: (_, event) => {
    return isClick(event.clientX, event.clientY) ? false : undefined
  },
})
const { style } = useConstrainDragBounds(boxRef, dragContainerBoxRef, position)


onMounted(() => {
  // 初始位置
  // const point = {
  //   x: editorViewBoxBounds.x.value + editorViewBoxBounds.width.value - 10,
  //   y: editorViewBoxBounds.y.value ,
  // }
  const point = {
    x: editorViewBoxBounds.x.value ,
    y: editorViewBoxBounds.y.value + editorViewBoxBounds.height.value,
  }
  position.value = point
})

function handleMouseup(event: MouseEvent) {
  const callback = () => {
    if (!isClick(event.clientX, event.clientY)) return
    if (isPlayButtonClick(event)) return playButtonRef.value?.play()
    return emit('update:visible', false)
  }

  callback()
  resetRecordClient()
}

function resetRecordClient() {
  recordClientX.value = 0
  recordClientY.value = 0
}

function handleMousedown(event: MouseEvent) {
  recordClientX.value = event.clientX
  recordClientY.value = event.clientY
}

function isClick(x: number, y: number) {
  const offset = 10
  const res =
    x > recordClientX.value - offset &&
    x < recordClientX.value + offset &&
    y > recordClientY.value - offset &&
    y < recordClientY.value + offset
  return res
}

function isPlayButtonClick(event: MouseEvent) {
  const target = event.target as HTMLElement
  return playButtonRef.value?.divBox?.contains(target) || false
}
</script>

<template>
  <div
    v-show="visible"
    ref="boxRef"
    class="try-play-circel user-select-none overflow-hidden"
    :style="style"
    style="position: fixed"
    @mousedown="handleMousedown"
    @mouseup="handleMouseup"
  >
    <div class="avatar d-flex flex-row justify-content-center align-items-center">
      <PlayButton ref="playButtonRef" @click.capture.stop :size="40"></PlayButton>
      <div class="text-white ms-2" style="font-size: 14px">
        {{ tryPlayStore.speaker.name }}
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.try-play-circel {
  height: 60px;
  width: auto;
  padding: 0 8px;
  // background-color: #b3acac2d;
  background-color: #00273b;
  border-radius: 8px;
  // border: 1px solid #f53f3f;

  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;

  .avatar {
    width: 90px;
    height: 90px;
  }
}
</style>
