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

async function handleSearch() {
  speakerStore.queryParams.name = searchName.value
  let searchList = await speakerStore.getSpeakerList()
// console.log('搜索数据', searchName.value)

  speakerStore.setSpeakerList(searchList)
}

async function handleRecent() {
  let recentList = await speakerStore.getRecentList()
  speakerStore.setSpeakerList(recentList)
}

async function handleCollect() {
  let collectList = await speakerStore.getCollectList()
  speakerStore.setSpeakerList(collectList)
}

async function handleAll() {
  speakerStore.queryParams.name = ''
  speakerStore.queryParams.emotionId = ''
  speakerStore.queryParams.languageId = ''
  speakerStore.queryParams.domainId = ''
  let speakerList = await speakerStore.getSpeakerList()
  speakerStore.setSpeakerList(speakerList)
}
</script>
