<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import TryPlayCircle from './try-play-circle.vue'
import TryPlayPanle from './try-play-panle.vue'
// import TryPlayPanleRight from "./try-play-panle-right.vue";
import { useDubbingStore } from '@/stores'
import { emitter } from '@/event-bus'

const dubbingStore = useDubbingStore()
const circleVisible = ref(true)
const panleVisible = computed({
  get: () => !circleVisible.value,
  set: (value) => {
    circleVisible.value = !value
  },
})

const loading = ref(false)
const style = ref({
  pointerEvents: 'all',
})
onMounted(async () => {
  loading.value = true
  style.value.pointerEvents = 'none'
  await dubbingStore.getSpeakerList()
  loading.value = false
  style.value.pointerEvents = 'all'
  emitter.emit('speaker:loading:ok')
  // setTimeout(() => {}, 1000);
})
</script>

<template>
  <Teleport to="body">
    <div class="ssml-editor-root">
      <TryPlayCircle
        v-loading="loading"
        v-model:visible="circleVisible"
        :style="style"
      ></TryPlayCircle>
      <TryPlayPanle v-model:visible="panleVisible"></TryPlayPanle>
      <!-- <TryPlayPanleRight v-model:visible="panleVisible"></TryPlayPanleRight> -->
    </div>
  </Teleport>
</template>

<style lang="scss" scoped></style>
