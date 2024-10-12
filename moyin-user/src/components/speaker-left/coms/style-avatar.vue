<script setup lang="ts">
import { defaultAvatar } from '@/config'
// import type { StyleAvatarData } from "./data";
import { onMounted } from 'vue'

defineEmits<{ click: [value: string] }>()

const props = defineProps({
  activate: {
    type: Boolean,
    default: false,
  },
  data: Object,
})

const data: any = props.data

onMounted(() => {})

function getRandomColor() {
  const mainstreamColors = [
    '#2ecc71',
    '#3498db',
    '#9b59b6',
    '#e74c3c',
    '#1abc9c',
    '#e67e22',
    '#d35400',
    '#c0392b',
    '#16a085',
    '#27ae60',
    '#ff1493',
  ]

  const randomIndex = Math.floor(Math.random() * mainstreamColors.length)
  return mainstreamColors[randomIndex]
}

const bgColor = getRandomColor()
</script>

<template>
  <div
    class="anchor-avatar d-flex flex-column align-items-center text-center justify-content-center position-relative"
    @click="$emit('click', data.id)"
  >
    <!-- <div
      v-if="data.emoji"
      class="rounded-circle d-flex border-info justify-content-center align-items-center"
      style="height: 30px; width: 30px"
      :style="{ 'background-color': bgColor }"
      :class="{ 'border border-2 border-warning': activate }"
    >
      {{ data.emoji }}
    </div> -->
    <div class="img-box rounded-circle" :class="{ 'border-warning': activate }">
      <img :src="data.imageUrl || defaultAvatar()" />
    </div>
    <div class="anchor-avatar-name text-black" style="font-size: 0.65rem; margin-top: 5px">
      {{ data.name }}
    </div>
  </div>
</template>

<style lang="scss" scoped>
.anchor-avatar {
  .img-box {
    width: 30px;
    height: 30px;
    overflow: hidden;
    border: 3px solid transparent;

    img {
      cursor: pointer;
      pointer-events: all;
      user-select: none;
      -webkit-user-drag: none;
      -webkit-user-select: none;

      width: 30px;
      height: 30px;

      display: block;
      scale: 1.2;
      // &.active {
      // }
    }
  }

  .anchor-avatar-name {
    font-size: 12px;
  }
}
</style>
