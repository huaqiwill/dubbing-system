<template>
  <div
    style="height: 100%; width: 100%; border-left: 1px solid #fff"
    class="overflow-x-hidden overflow-y-auto scrollbar-custom"
  >
    <div class="d-flex flex-row flex-wrap justify-content-start">
      <div
        style="margin: 8px 0; flex: 0 0 68px; height: 68px"
        v-for="item in speakerList"
        :key="item.id"
      >
        <SpeakerAvatar
          :data="item"
          :activate="item.id === selectSpeakerId"
          @click="handleClick(item)"
        ></SpeakerAvatar>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>

<script setup lang="ts">
import { onMounted, shallowRef, ref } from 'vue'
import { SpeakerAvatar } from '.'
import { emitter } from '@/event-bus'
import { useSpeakerStore, useTryPlayStore } from '@/stores'

const speakerStore = useSpeakerStore()
const tryPlayStore = useTryPlayStore()
const speakerList = shallowRef<any>([])
const selectSpeakerId = ref()

let isFirstSelect = true

onMounted(async () => {
  await speakerStore.getSpeakerList()

  emitter.on('speaker:loading:ok', () => {
    speakerList.value = speakerStore.getSpeakerListLocal()
    if (speakerList.value.length > 0 && isFirstSelect) {
      handleClick(speakerList.value[0])
      isFirstSelect = false
    }
  })
})

function handleClick(speaker: any) {
  selectSpeakerId.value = speaker.id
  emitter.emit('speaker:select', speaker)
  tryPlayStore.setSpeakerForce(speaker)
// console.log('选择了speaker', speaker)
}
</script>
