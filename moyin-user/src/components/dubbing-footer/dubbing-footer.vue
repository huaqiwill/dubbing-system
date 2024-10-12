<template>
  <div
    class="d-flex align-items-center justify-content-between"
    style="padding: 10px; border-top: 1px solid #dee2e6"
  >
    <div>
      <span>当前字符数：{{ currentCount }}</span>
      <span class="ms-3">今日剩余字符：{{ remainCount }} / 6000</span>
      <el-tag v-if="submitParams.speaker" class="ms-2">{{ submitParams.speaker }}</el-tag>
    </div>
    <div class="d-flex">
      <GenerateLogging2></GenerateLogging2>
      <a-button class="ms-2" type="primary" status="success" @click="handleDownload">
        <template #icon>
          <icon-to-bottom />
        </template>
        下载配音
      </a-button>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>

<script setup lang="ts">
import { useSpeakerStore, useUserStore } from '@/stores'
import { IconToBottom } from '@arco-design/web-vue/es/icon'
import { GenerateLogging2 } from '@/components/dubbing-tools'
import { onMounted, ref } from 'vue'
import { getRemainApi } from '@/api/tts'
import { emitter } from '@/event-bus'

const speakerStore = useSpeakerStore()
const submitParams = speakerStore.getSubmitParams()
const remainCount = ref(6000)
const limitCount = ref(6000)
const currentCount = ref(0)

onMounted(() => {
  emitter.on('remain:count', () => {
    getRemainApi().then((res: any) => {
      remainCount.value = res.data.remain
      limitCount.value = res.data.limit
    })
  })

  emitter.emit('remain:count')

  emitter.on('editor:change:count', (count: number) => {
    currentCount.value = count
  })
})

function handleDownload() {
  let lastPlayUrl = speakerStore.getLastPlayUrl()
  if (lastPlayUrl) {
    window.location.href = lastPlayUrl
  }
}
</script>
