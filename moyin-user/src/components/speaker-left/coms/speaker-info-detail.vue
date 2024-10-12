<template>
  <div
    style="height: 100%; width: 100%; padding: 10px; font-size: 0.65rem"
    class="right-panle px-3 text-black overflow-x-hidden overflow-y-auto scrollbar-none position-relative"
  >
    <div class="d-flex text-center justify-content-between align-items-center pe-4">
      <div class="me-auto d-flex flex-row align-items-center">
        <PlayButton :size="42"></PlayButton>
        <div
          class="ms-2 d-flex flex-column justify-content-between"
          style="height: 42px; font-size: 14px"
        >
          <div class="d-flex dlex-row column-gap-2 align-items-end">
            <span style="font-size: 1rem">
              {{ speakerInfo.name }}
              <span v-if="speakerInfo.alias">({{ speakerInfo.alias }})</span>
            </span>
            <span
              class="badge text-bg-primary px-2"
              style="cursor: pointer"
              @click="handleEditAlias"
            >
              编辑
            </span>
            <span>{{ speed }}x</span>
          </div>
          <div class="d-flex flex-row column-gap-2 align-items-center">
            <ElIcon @click="handleStar" class="fs-6" :style="{ color: isStar ? 'red' : 'black' }">
              <Star v-if="!isStar"></Star>
              <StarFilled v-if="isStar"></StarFilled>
            </ElIcon>
            <span v-if="isSupper48K" class="badge text-bg-primary px-2"> 48K </span>
            <span v-else class="badge text-bg-primary px-2"> 24K </span>
            <span class="px-2"> {{ speakerInfo.behavior }} </span>
          </div>
        </div>
      </div>

      <el-dialog title="编辑别名" v-model="dialogVisible" width="180">
        <el-input v-model="speakerNotes" placeholder="请输入备注"></el-input>
        <template #footer>
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleEditAliasOk">确 定</el-button>
        </template>
      </el-dialog>

      <div class="d-flex flex-column">
        <div class="d-flex flex-row justify-content-between align-items-center">
          <div class="me-4" style="font-size: 1rem">({{ speakerInfo.language }})</div>
          <div class="text-primary" style="font-size: 0.8rem">音频时长，请以生成后的为准</div>
        </div>
        <div class="d-flex">
          <div class="mt-1 d-flex flex-row align-items-center">
            <a-slider
              class="mt-1"
              style="
                width: 160px;
                border: 1px solid #fff;
                padding: 5px 15px;
                background-color: transparent;
                border-radius: 4px;
              "
              :max="timeMax"
              v-model="time"
              size="small"
              @input="handleTimeInput"
              @change="handleTimeChange"
              :format-tooltip="formatTime"
            ></a-slider>
            <div class="mt-1">
              <span class="ms-3">{{ timeText }}</span>
              <span> / </span>
              <span>{{ timeMaxText }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 标签 -->
    <div style="padding: 0 5px" class="mt-3 role-list">
      <div
        class="d-flex flex-row flex-wrap justify-content-start align-items-center row-gap-2 column-gap-3"
      >
        <div v-for="(item, index) in domains" :key="index">
          {{ item.name }}
        </div>
      </div>
    </div>

    <!-- 情绪 -->
    <div class="me-4" style="background-color: #f5f5f5; border-radius: 4px; height: 122px">
      <div
        class="mt-2 d-flex flex-row flex-wrap justify-content-start align-items-center row-gap-1 overflow-y-scroll scrollbar-none"
        style="max-height: 122px"
      >
        <div @click="handleEmotionClick(0)" style="padding: 5px 6px">
          <StyleAvatar :activate="0 === selectedEmotion" :data="defaultStyleAvatar"></StyleAvatar>
        </div>
        <div
          @click="handleEmotionClick(item.id)"
          v-for="(item, index) in emotions"
          :key="index"
          style="padding: 5px 6px"
        >
          <StyleAvatar :activate="item.id === selectedEmotion" :data="item"></StyleAvatar>
        </div>
      </div>
    </div>

    <!-- 参数 -->
    <div class="d-flex flex-wrap position-absolute" style="left: 10px; right: 10px; bottom: 120px">
      <div class="w-50">
        <span style="width: 60px">语速：</span>
        <el-select style="width: calc(100% - 60px)" v-model="speed" @change="handleSpeedChange">
          <el-option
            v-for="(item, index) in speedList"
            :key="index"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <div class="w-50">
        <span style="width: 60px">语调：</span>
        <el-select style="width: calc(100% - 60px)" v-model="pitch" @change="handlePitchChange">
          <el-option
            v-for="(item, index) in pitchList"
            :key="index"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <div class="w-50 d-flex align-items-center mt-2">
        <span style="width: 33px">音量：</span>
        <el-slider
          style="
            width: calc(100% - 94px);
            border: 1px solid #fff;
            padding: 0 15px;
            background-color: #fff;
            border-radius: 4px;
          "
          v-model="volume"
          @change="handleVolumeChange"
          :min="0"
          :max="1"
          :step="0.01"
        />
      </div>
      <div class="w-50 mt-2">
        <span style="width: 60px">格式：</span>
        <el-select
          style="width: calc(100% - 60px)"
          v-model="foramtter"
          @change="handleFormatChange"
        >
          <el-option
            v-for="(item, index) in foramtterList"
            :key="index"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.right-box {
  height: 50px;
}
</style>

<script setup lang="ts">
import { ElSlider, ElIcon, ElMessage, ElMessageBox } from 'element-plus'
import { PlayButton, StyleAvatar } from '.'
import { ref, computed, watch, onMounted } from 'vue'
import { formatTime } from '@/utils'
import { Star, StarFilled } from '@element-plus/icons-vue'
import { emitter } from '@/event-bus'
import type { Arrayable } from 'element-plus/lib/utils/typescript'
import { useSpeakerStore, useTryPlayStore } from '@/stores'
import { defaultPitch, defaultFormatter, defaultSpeed } from './data'
import { getSpeakerInfoApi } from '@/api/tts'

const speakerStore = useSpeakerStore()
const tryPlayStore = useTryPlayStore()
const timeMax = tryPlayStore.audioPlayer.duration
const currentTime = tryPlayStore.audioPlayer.currentTime
const time = ref(0)
const isInput = ref(false)
const speed = ref(1)
const pitch = ref(0)
const volume = ref(1)
const timeMaxText = computed(() => formatTime(timeMax.value))
const timeText = computed(() => formatTime(time.value))
const isStar = ref(false)
const emotions = ref<any>([])
const domains = ref<any>([])
const selectedEmotion = ref(0)
const speedList = defaultSpeed()
const pitchList = defaultPitch()
const defaultStyleAvatar = ref({
  id: 0,
  name: '默认',
  imageUrl: '',
})
const isSupper48K = ref(false)
const foramtterList = defaultFormatter()
const foramtter = ref('mp3')
const speakerNotes = ref('')

const speakerInfo = ref<any>({
  name: '暂未选择',
  behavior: '行为暂时为空',
})

function handleEditAlias() {
  if (!isStar.value) {
    speakerNotes.value = ''
  }
  dialogVisible.value = true
}

onMounted(() => {
  emitter.on('speaker:select', async (speaker: any) => {
    await getSpeakerInfoApi(speaker.id).then((res) => {
      speakerInfo.value = res.data
// console.log('配音员详情', speakerInfo.value)
    })

    const { emotionIdSet, domainIdSet } = speakerInfo.value

    // 情绪
    let emotionList = speakerStore.getEmotionNameListLocal()
    emotions.value = emotionList.filter((emotion: any) => emotionIdSet.includes(emotion.id))

    // 领域
    let domainList = speakerStore.getDomainListLocal()
    domains.value = domainList.filter((domain: any) => domainIdSet.includes(domain.id))

    // 默认头像
    defaultStyleAvatar.value.imageUrl = speaker.headerImage

    // speaker emotion
    let speakerEmotionList = await speakerStore.getSpeakerEmotionList(speaker.id)
    let speakerEmotion = speakerEmotionList.find((em: any) => em.emotionId == 0)
    if (speakerEmotion) {
      isSupper48K.value = speakerEmotion.styleCallName.includes('48k')
    }
    handleEmotionClick(0)

    // star
    const collectList = speakerStore.getCollectListLocal()
    let collect: any = collectList.find((collect: any) => collect.speakerId == speaker.id)
    if (collect) {
      isStar.value = true
      speakerNotes.value = collect.speakerNotes
    } else {
      isStar.value = false
    }
  })
})

watch(currentTime, (newValue) => {
  if (!isInput.value) time.value = newValue
})

const dialogVisible = ref(false)
async function handleStar() {
  if (isStar.value) {
    ElMessageBox({
      title: '取消收藏',
      message: '是否确认取消收藏？取消收藏后将删除别名。',
      showCancelButton: true,
      confirmButtonText: '确认',
      cancelButtonText: '取消',
    }).then(() => {
      // 直接取消
      speakerStore.collectRemove(speakerInfo.value.id)
      isStar.value = false
      speakerInfo.value.alias = ''
      ElMessage({
        message: '取消收藏成功',
        type: 'success',
      })
    })
  } else {
    // 打开对话框
    isStar.value = true
    await speakerStore.collect(speakerInfo.value.id)
    ElMessage({
      message: '收藏成功',
      type: 'success',
    })
  }
}

async function handleEditAliasOk() {
  await speakerStore.aliasEdit(speakerInfo.value.id, speakerNotes.value)
  isStar.value = true
  dialogVisible.value = false
  speakerInfo.value.alias = speakerNotes.value
  ElMessage({
    message: '编辑成功',
    type: 'success',
  })
}

function handleEmotionClick(emotionId: number) {
  selectedEmotion.value = emotionId
  // 注意此处不能使用全等
  let speakerEmotionList = speakerStore.getSpeakerEmotionListLocal()
  let emotion: any = speakerEmotionList.find((emotion: any) => emotion.emotionId == emotionId)
  if (emotion) {
    speakerStore.updateSubmitParam('speaker', emotion.styleCallName)
  }
}

function handleTimeInput() {
  isInput.value = true
}

function handleTimeChange(time: Arrayable<number>) {
  if (!(time instanceof Array)) {
    currentTime.value = time
    tryPlayStore.audioPlayer.play()
  }
  isInput.value = false
}

function handleFormatChange() {
  speakerStore.updateSubmitParam('audioType', foramtter.value)
}

function handleSpeedChange() {
  speakerStore.updateSubmitParam('speed', speed.value)
}

function handlePitchChange() {
  speakerStore.updateSubmitParam('pitch', pitch.value)
}

function handleVolumeChange() {
  speakerStore.updateSubmitParam('volume', volume.value)
}
</script>
