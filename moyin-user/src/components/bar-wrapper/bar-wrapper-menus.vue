<script setup lang="ts">
// 是否显示竖线
const showLine = false;

const style = showLine
  ? "height: 50px; width: 2px; margin: 1px 14px"
  : "height: 50px; width: 0; margin: 1px 14px"; // 宽度不显示
</script>

<template>
  <div
    class="bar-wrapper-menus d-flex flex-row justify-content-center align-items-center"
  >
    <div class="menus d-flex flex-row justify-content-center align-items-center">
      <slot></slot>
    </div>
    <div v-if="showLine" class="divider" :style="style"></div>
  </div>
</template>

<style lang="scss" scoped>
$colors: var(--tool-green-border-color), var(--tool-cyan-color), var(--tool-orange-color),
  var(--tool-purple-color), var(--tool-yellow-color);

@for $i from 0 to 5 {
  .bar-wrapper-menus:nth-child(#{$i + 1}) .divider {
    // background-color: nth($colors, $i + 1);
    background-color: #ced3d6;
  }
}
</style>
