<script setup lang="ts">
// tag v1 item
import { computed } from 'vue'

defineEmits<{ click: [value: string | number] }>()
const props = withDefaults(
  defineProps<{
    activate?: boolean
    value?: string | number
    small?: boolean
  }>(),
  {
    activate: false,
    value: '',
    small: false,
  },
)

const style = computed(() => {
  return props.small
    ? {
        'max-width': '160px',
      }
    : {
        height: '20px',
        lineHeight: '20px',
        fontSize: '0.65rem',
        minWidth: '50px',
        maxWidth: '160px',
      }
})
</script>

<template>
  <span
    class="tag-item d-inline-block text-black text-center text-nowrap text-truncate rounded-pill"
    :class="{ selected: activate, 'px-1 small': small }"
    @click="$emit('click', value)"
    style="cursor: pointer; border: 1px solid transparent; box-sizing: content-box; padding: 0 5px"
    :style="style"
  >
    <slot></slot>
  </span>
</template>

<style lang="scss" scoped>
.selected {
  background-color: rgb(64, 158, 255);
  color: #fff !important;
}

.small {
  height: 16px;
  line-height: 16px;
}
</style>
