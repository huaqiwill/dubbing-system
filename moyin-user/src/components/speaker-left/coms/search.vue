<template>
  <div class="d-flex mt-1" style="padding: 5px 10px">
    <a-input v-model="searchName" :placeholder="placeholder" @input="handleSearch"></a-input>
    <el-button type="primary" class="ms-2" @click="handleRecent">最近</el-button>
    <el-button type="primary" class="ms-2" @click="handleCollect">收藏</el-button>
    <el-button type="primary" class="ms-2" @click="handleAll">全部</el-button>
  </div>
</template>

<style lang="scss" scoped>
:deep(.arco-input-wrapper) {
  background-color: #fff;
}
</style>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { useSpeakerStore } from '@/stores'

const speakerStore = useSpeakerStore()
const searchName = ref('')

const speakerCount = speakerStore.getSpeakerCount()
const placeholder = computed(() => {
  return `共${speakerCount.value}款配音师，输入名称搜索`
})

function handleSearch() {
  // const collectList = speakerStore.getCollectListLocal()
  let speakerList = speakerStore.getSpeakerListBackup()
  if (searchName.value !== '') {
    // 筛选出包含在CollectList的Speaker
    // let collectSpeakerList = speakerList.filter((speaker: any) => {
    //   return collectList.find(
    //     (collect: any) =>
    //       speaker.id == collect.speakerId && collect.speakerNotes.includes(searchName.value),
    //   )
    // })
    speakerList = speakerList.filter((speaker: any) => {
      if (speaker.name && speaker.name.includes(searchName.value)) {
        return true
      }
      if (speaker.alias && speaker.alias.includes(searchName.value)) {
        return true
      }
      return false
    })
    // for (let i = 0; i < collectSpeakerList.length; i++) {
    //   let collectSpeaker: any = collectSpeakerList[i]
    //   if (speakerList.length === 0) {
    //     speakerList.push(collectSpeaker)
    //   } else {
    //     let result = speakerList.find((speaker: any) => speaker.id == collectSpeaker.id)
    //     if (!result) {
    //       speakerList.push(collectSpeaker)
    //     }
    //   }
    // }
  }
  speakerStore.setSpeakerList(speakerList)
}

async function handleRecent() {
  // const speakerListBackup = speakerStore.getSpeakerListBackup()
  let recentList = await speakerStore.getRecentList()
  // let speakerList = speakerListBackup.filter((speaker: any) => {
  //   return !!recentList.find((recent: any) => recent.speakerId == speaker.id)
  // })
// console.log(recentList)

  speakerStore.setSpeakerList(recentList)
}

async function handleCollect() {
  const speakerListBackup = speakerStore.getSpeakerListBackup()
  let collectList = await speakerStore.getCollectList()
  let speakerList = speakerListBackup.filter((speaker: any) => {
    return !!collectList.find((collect: any) => collect.speakerId == speaker.id)
  })
  speakerStore.setSpeakerList(speakerList)
}

function handleAll() {
  speakerStore.setSpeakerList(speakerStore.getSpeakerListBackup())
}
</script>
