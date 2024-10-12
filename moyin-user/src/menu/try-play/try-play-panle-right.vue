<script setup lang="ts">
import { ref } from "vue";
import TryPlayDialog from "./try-play-dialog.vue";
import RightPanle from "./right-panle.vue";

const panleVisible = ref(false);
</script>

<template>
  <TryPlayDialog v-model:visible="panleVisible">
    <div class="try-play-left w-100">
      <RightPanle></RightPanle>
    </div>
  </TryPlayDialog>
</template>

<style lang="scss" scoped></style>
