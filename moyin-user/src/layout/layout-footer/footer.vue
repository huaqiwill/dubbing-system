<template>
  <div class="footer">
    <p>Copyright ©「{{ title }}」{{ startYear }}-{{ endYear }} {{ record }}</p>
  </div>
</template>

<script setup>
import { ref } from "vue";
const title = ref("魔音配音工具");
const record = ref("鄂ICP备2021001460号-2");
const startYear = ref("2024");
const endYear = ref("2024");
</script>

<style scoped lang="scss">
.footer {
  padding: 10px;
  background-color: #fff;
  border-top: 1px solid #e5e6eb;

  p {
    text-align: center;
    color: #333;
    font-size: 12px;
  }
}
</style>
