<script setup lang="ts">
// 播放 v2
import { ElIcon, ElMessage } from 'element-plus'
import { Loading } from '@element-plus/icons-vue'
import { BarButton } from '@/components'
import throttle from 'lodash.throttle'
import { serializeToSSML } from '@/serialize'
import { useEditorStore, useTryPlayStore } from '@/stores'
import { storeToRefs } from 'pinia'
import { useSpeakerStore } from '@/stores'
import { emitter } from '@/event-bus'
import { onMounted } from 'vue'
import { speakerRecentAddApi } from '@/api/speaker'

const editorStore = useEditorStore()
const { editor } = storeToRefs(editorStore)
const tryPlayStore = useTryPlayStore()
const { audioPlayer, play } = tryPlayStore
const playState = audioPlayer.playState
const speakerStore = useSpeakerStore()

withDefaults(defineProps<{ disabled?: boolean }>(), { disabled: false })

onMounted(() => {
  // 点击配音生成的时候
  emitter.on('tryplay-generator', () => {
    handleClick()
  })
})

const handleClick = throttle(async () => {
  if (!editor.value) {
    return
  }

  let text = serializeToSSML()
  let raw = editor.value.getText()

  if (!text || text === '' || !raw || raw === '') {
    ElMessage({
      message: '请先输入文本',
      type: 'warning',
    })
    return
  }

  // 检查其他参数
  // const submitParams = speakerStore.getSubmitParams()

  speakerStore.updateSubmitParam('text', text)
  speakerStore.updateSubmitParam('rawText', raw)

  speakerRecentAddApi(tryPlayStore.speaker)
    .then(() => {
// console.log('加入最近成功')
    })
    .catch((err) => {
// console.log('已加入', err)
    })

  play()
})
</script>

<template>
  <BarButton @click="handleClick" :disabled="disabled">
    <template #icon>
      <ElIcon v-if="tryPlayStore.isLoading" class="is-loading">
        <Loading></Loading>
      </ElIcon>
      <span v-else-if="playState === 'paused'" class="fs-3 iconfont-moyin moyin-icon_play"></span>
      <span v-else class="iconfont icon-pause"></span>
    </template>
    播放
  </BarButton>
</template>

<style lang="scss" scoped></style>
