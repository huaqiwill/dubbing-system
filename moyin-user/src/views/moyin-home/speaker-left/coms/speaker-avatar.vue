<script setup lang="ts">
// 单个头像 v2
import { defaultAvatar } from '@/config'
import { ref, watch } from 'vue'

defineEmits<{ click: [value: string] }>()

const props = defineProps({
  activate: {
    type: Boolean,
    default: false,
  },
  data: Object,
})

const speaker = ref<any>(null)
speaker.value = props.data

watch(
  () => props.data,
  (newVal) => {
    speaker.value = newVal
  },
)
</script>

<template>
  <div
    class="anchor-avatar d-flex flex-column align-items-center text-center justify-content-center position-relative"
    @click="$emit('click', speaker)"
  >
    <!-- <span
      v-if="!speaker.isFree"
      class="position-absolute top-0 start-100 translate-middle text-bg-danger text-nowrap rounded px-1"
      style="font-size: 0.65rem"
    >
      付费
    </span> -->
    <img
      :src="speaker.headerImage || defaultAvatar()"
      class="rounded-circle border border-3"
      style="height: 40px; width: 40px; border-color: transparent"
      :class="{ 'border border-3 border-warning': activate }"
    />
    <div class="anchor-avatar-name text-black mt-1">{{ speaker.name }}</div>
    <div class="text-black mt-1" style="font-size: 0.65rem" v-if="speaker.alias">
      ({{ speaker.alias }})
    </div>
  </div>
</template>

<style lang="scss" scoped>
.anchor-avatar {
  img {
    cursor: pointer;
    pointer-events: all;
    -webkit-user-drag: none; /* Safari */
    user-select: none; /* Standard syntax */
  }

  .anchor-avatar-name {
    font-size: 12px;
  }
}
</style>
