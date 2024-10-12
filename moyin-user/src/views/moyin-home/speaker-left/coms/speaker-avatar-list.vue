<template>
  <div
    style="height: 100%; width: 100%; border-left: 1px solid #fff"
    class="overflow-x-hidden overflow-y-auto scrollbar-custom"
  >
    <div
      v-if="speakerList && speakerList.length > 0"
      class="d-flex flex-row flex-wrap justify-content-start"
    >
      <div
        style="margin: 8px 0; flex: 0 0 68px; height: 68px"
        v-for="item in speakerList"
        :key="item.id"
      >
        <SpeakerAvatar
          :data="item"
          :activate="item.id === selectedSpeakerId"
          @click="handleClick(item)"
        ></SpeakerAvatar>
      </div>
    </div>
    <div v-else style="margin: 8px">没有搜索到相关配音员~</div>
  </div>
</template>

<style lang="scss" scoped></style>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { SpeakerAvatar } from '.'
import { emitter } from '@/event-bus'
import { useSpeakerStore, useTryPlayStore } from '@/stores'

const speakerStore = useSpeakerStore()
const tryPlayStore = useTryPlayStore()
const speakerList = ref<any>()
const selectedSpeakerId = ref()
const selectedSpeaker = ref<any>()

let isFirstSelect = true

onMounted(() => {
  emitter.on('speaker:loading:ok', () => {
    speakerList.value = speakerStore.getSpeakerListLocal()

    if (speakerList.value.length > 0 && isFirstSelect) {
      handleClick(speakerList.value[0])
      isFirstSelect = false
    }
  })

  emitter.on('speaker:update:alias', (alias: string) => {
    selectedSpeaker.value.alias = alias
    let speaker = speakerList.value.filter((speaker: any) => speaker.id == selectedSpeakerId.value)
    if (speaker) {
      speaker.alias = alias
    }
  })
})

function handleClick(speaker: any) {
  selectedSpeakerId.value = speaker.id
  selectedSpeaker.value = speaker
  emitter.emit('speaker:select', speaker)
  tryPlayStore.setSpeakerForce(speaker)
// console.log('选择了speaker', speaker)
}
</script>
