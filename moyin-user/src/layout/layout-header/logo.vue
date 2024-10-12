<template>
  <div class="logo">
    <SvgIcon name="logo" width="24" height="24"></SvgIcon>
    <h1>魔音配音工具</h1>
  </div>
</template>

<script setup>
import { SvgIcon } from "@/components";
</script>

<style scoped lang="scss">
.logo {
  display: flex;
  align-items: center;
  justify-content: center;

  .icon {
    display: block;
  }

  h1 {
    font-size: 20px;
    margin-left: 10px;
    color: #000;
  }
}
</style>
