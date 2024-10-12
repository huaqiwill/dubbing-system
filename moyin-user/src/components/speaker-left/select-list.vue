<script setup lang="ts">
import { type SelectLabel } from "./types";
import { ref } from "vue";
const dataList = ref<SelectLabel>();

const handleTagSelect = (item: SelectLabel) => {

};
</script>

<template>
  <div class="select-list">
    <a-space wrap>
      <a-tag
        class="selected"
        v-for="item in dataList"
        :key="item.name"
        bordered
        @click="handleTagSelect(item)"
      >
        {{ item.name }}
      </a-tag>
    </a-space>
  </div>
</template>

<style lang="scss" scoped></style>
