<script setup lang="ts"></script>

<template>
  <div class="bar-wrapper d-flex flex-row justify-content-start align-items-center">
    <slot></slot>
  </div>
</template>

<style lang="scss" scoped>
.bar-wrapper {
  padding: 10px 10px;
}
</style>
