<script setup lang="ts">
// 单个头像 v2
// label、avatar、isFree、value

import { defaultAvatar } from "@/config";
import type { SpeakerAvatarData } from "./data";

defineEmits<{ click: [value: string] }>();
withDefaults(defineProps<{ activate?: boolean; data?: SpeakerAvatarData }>(), {
  activate: false,
  data: () => ({ label: "", value: "" }),
});
</script>

<template>
  <div
    class="anchor-avatar d-flex flex-column align-items-center text-center justify-content-center position-relative"
    @click="$emit('click', data.value)"
  >
    <span
      v-if="!data.isFree"
      class="position-absolute top-0 start-100 translate-middle text-bg-danger text-nowrap rounded px-1"
      style="font-size: 0.65rem"
    >
      付费
    </span>
    <img
      :src="data.avatar || defaultAvatar()"
      class="rounded-circle border border-3"
      style="height: 40px; width: 40px; border-color: transparent"
      :class="{ 'border border-3 border-warning': activate }"
    />
    <div class="anchor-avatar-name text-white mt-1">{{ data.label }}</div>
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
