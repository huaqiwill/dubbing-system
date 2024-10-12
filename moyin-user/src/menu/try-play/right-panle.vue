<script setup lang="ts">
import { ElSlider, ElIcon, ElMessage } from 'element-plus'
// import SpeakerAvatar from './speaker-avatar.vue'
import PlayButton from './play-button.vue'
import type { CSSProperties } from 'vue'
import { reactive, ref, onUnmounted, computed, watch, onMounted, toRaw, inject } from 'vue'
import { formatTime } from '@/utils'
import { Star, StarFilled } from '@element-plus/icons-vue'
import { getConfig } from '@/config'
import StyleAvatar from './style-avatar.vue'
import { defaultSpeed, defaultPitch } from './data'
import { useSSMLStore, useTryPlayStore } from '@/stores'
import { defaultFilterSpeaker, type LabelValue, type Speaker } from '@/model'
import { emitter } from '@/event-bus'
import type { Arrayable } from 'element-plus/lib/utils/typescript'
import SimpleTag from './simple-tag.vue'
// import { EmotionList } from '@/components'
import { useDubbingStore } from '@/stores'
import { storeToRefs } from 'pinia'

const dubbingStore = useDubbingStore()
interface Mark {
  style: CSSProperties
  label: string
}

type Marks = Record<number, Mark | string>

const editorKey = inject<symbol>('editorKey')!
const ssmlEditorConfig = getConfig(editorKey)
// const { rootProsody, rootExpressAs } = useSSMLStore()
const { category, fetchData } = ssmlEditorConfig.tryPlay
const tryPlayStore = useTryPlayStore()

const timeMax = tryPlayStore.audioPlayer.duration
const currentTime = tryPlayStore.audioPlayer.currentTime
const time = ref(0)
const isInput = ref(false)

const speedRange = ref([0.5, 2.0])
const speed = ref(1)

const pitchRange = ref([-10, 10])
const pitch = ref(0)

const volumeRange = ref([0, 1])
const volume = ref(1)

const timeMaxText = computed(() => formatTime(timeMax.value))
const timeText = computed(() => formatTime(time.value))
const isStar = computed(() => tryPlayStore.speaker.isStar)

const speedMarks = reactive<Marks>(defaultSpeed())
const pitchMarks = reactive<Marks>(defaultPitch())

const flag = ref('')
const speakerList = ref<Speaker[]>([])

onMounted(async () => {
  await handleCategoryClick(category[0].value)
})

const emotions = ref<any>([])
const domains = ref<any>([])

const defaultStyleAvatar = ref({
  id: 0,
  name: '默认',
  imageUrl: '',
})

import { getSpeakerEmotionList } from '@/api/dict'
const speakerEmotionList = ref([])
emitter.on('speaker:select', (speaker: any) => {
  const { emotionIdSet, domainIdSet } = speaker
  const { emotionList, domainList } = dubbingStore
  emotions.value = emotionList.filter((emotion: any) => emotionIdSet.includes(emotion.id))
  domains.value = domainList.filter((domain: any) => domainIdSet.includes(domain.id))
// console.log("情绪",emotionIdSet,emotionList,emotions.value);
// console.log("领域",domainIdSet,domainList,domains.value);
  defaultStyleAvatar.value.imageUrl = speaker.headerImage

  getSpeakerEmotionList(speaker.id).then((res: any) => {
    speakerEmotionList.value = res.rows
  })
})

onMounted(() => {
  emitter.on('tryplay-speaker-update-star', handleUpdateStarTheCache)
})

onUnmounted(() => {
  emitter.off('tryplay-speaker-update-star', handleUpdateStarTheCache)
})

const { submitTtsData } = storeToRefs(dubbingStore)

watch(
  volume,
  (value) => {
    // newValue.roles.length > 0 && handleRoleClick(newValue.roles[0].value)
    // newValue.styles.length > 0 && handleStyleClick(newValue.styles[0].value)
    submitTtsData.value.volume = value
  },
  { immediate: false },
)

watch(
  pitch,
  (value) => {
    submitTtsData.value.pitch = value
    // rootProsody.pitch = value.toString()
  },
  { immediate: false },
)

watch(
  speed,
  (value) => {
    submitTtsData.value.speed = value
    // rootProsody.rate = value.toString()
  },
  { immediate: false },
)

watch(currentTime, (newValue) => {
  if (!isInput.value) time.value = newValue
})

function handleStar() {
  // await tryPlayStore.star(editorKey, !isStar.value)
  ElMessage({
    message: 'Star',
  })
}

function handleUpdateStarTheCache(speakerId: string, isStar: boolean) {
  const speakerCache = speakerList.value.find((v) => v.id === speakerId)
  if (speakerCache) speakerCache.isStar = isStar
}

// function handleRoleClick(value: string) {
//   rootExpressAs.role = value
// }

const selectedEmotion = ref(0)
function handleStyleClick(value: number) {
  // rootExpressAs.style = value
  selectedEmotion.value = value

  // 注意此处不能使用全等
  let filters: any = speakerEmotionList.value.filter((emotion: any) => emotion.emotionId == value)
// console.log(speakerEmotionList.value, value,filters);
  if (filters.length > 0) {
    let styleCallName = filters[0].styleCallName
    submitTtsData.value.speaker = styleCallName

    ElMessage({
      message: styleCallName,
    })
  }
}

async function handleCategoryClick(value: string) {
  flag.value = value
  try {
    speakerList.value = await fetchData({ ...defaultFilterSpeaker(), category: value })
  } catch (error) {
    emitter.emit('error', error)
  }
}

// function handleSpeakerClick(value: Speaker) {
//   tryPlayStore.setSpeaker(editorKey, toRaw(value))
// }

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

// function handleSpeakerDetailShow() {
//   emitter.emit('tryplay-speaker-detail-show', tryPlayStore.speaker)
// }
</script>

<template>
  <div
    class="mt-2 right-panle w-100 px-3 text-white overflow-x-hidden overflow-y-auto scrollbar-none"
    style="
      height: 400px;
      font-size: 0.65rem;
      border: 1px solid #3463ab;
      border-left: none;
      max-width: 328px;
      min-width: 328px;
    "
  >
    <div class="mt-2 d-flex text-center justify-content-between align-items-center">
      <div class="me-auto d-flex flex-row align-items-center">
        <PlayButton :size="42"></PlayButton>
        <div
          class="ms-2 d-flex flex-column justify-content-between"
          style="height: 42px; font-size: 14px"
        >
          <div class="d-flex dlex-row column-gap-2 align-items-end">
            <span style="font-size: 1rem">{{ tryPlayStore.speaker.name }}</span>
          </div>
          <div class="d-flex flex-row column-gap-2 align-items-center">
            <ElIcon @click="handleStar" class="fs-6" :style="{ color: isStar ? 'red' : 'white' }">
              <StarFilled v-if="isStar"></StarFilled>
              <Star v-else></Star>
            </ElIcon>
            <span>{{ speed }}x</span>
            <span v-if="tryPlayStore.speaker.isSupper24K" class="badge text-bg-primary px-2">
              24K
            </span>
          </div>
        </div>
      </div>
      <div class="d-flex flex-column align-items-end">
        <div class="text-info">音频时长，请以生成后的为准</div>
        <div class="mt-1 d-flex flex-row align-items-end">
          <a-slider
            style="width: 100px"
            :max="timeMax"
            v-model="time"
            size="small"
            @input="handleTimeInput"
            @change="handleTimeChange"
            :format-tooltip="formatTime"
          ></a-slider>
        </div>
        <div class="mt-1">
          <span class="ms-3">{{ timeText }}</span>
          <span>/</span>
          <span>{{ timeMaxText }}</span>
        </div>
      </div>
    </div>

    <!-- 标签 -->
    <div style="padding: 0 5px" class="mt-2 role-list">
      <div
        class="d-flex flex-row flex-wrap justify-content-start align-items-center row-gap-2 column-gap-3"
      >
        <div v-for="item in domains" :key="item.id">
          {{ item.name }}
        </div>
      </div>
    </div>

    <!-- 情绪 -->
    <!-- <EmotionList emotions="emotions"></EmotionList> -->
    <ul class="mt-2 d-flex flex-row flex-wrap justify-content-start align-items-center row-gap-1">
      <li @click="handleStyleClick(0)" style="padding: 5px 3px">
        <StyleAvatar :activate="0 === selectedEmotion" :data="defaultStyleAvatar"></StyleAvatar>
      </li>
      <li
        @click="handleStyleClick(item.id)"
        v-for="(item, index) in emotions"
        :key="index"
        style="padding: 5px 3px"
      >
        <StyleAvatar :activate="item.id === selectedEmotion" :data="item"></StyleAvatar>
      </li>
    </ul>

    <!-- <div class="my-3">
      <SimpleTag
        activate
        @click="handleSpeakerDetailShow"
        style="background-color: #5a92f4; padding: 0 3px; border: none !important"
        >配音师详情</SimpleTag
      >
    </div> -->
    <div class="right-box mt-3">
      <div>
        <span>语速：{{ speed }}x</span>
      </div>
      <a-slider
        class="mt-2"
        v-model="speed"
        :min="speedRange[0]"
        :max="speedRange[1]"
        :step="0.05"
      ></a-slider>
    </div>
    <div class="right-box">
      <div>
        <span>语调：{{ pitch }}</span>
      </div>
      <a-slider
        class="mt-2"
        v-model="pitch"
        :min="pitchRange[0]"
        :max="pitchRange[1]"
        :step="0.2"
      ></a-slider>
    </div>
    <div class="right-box">
      <div>
        <span>音量：{{ volume }}</span>
      </div>
      <a-slider
        class="mt-2"
        v-model="volume"
        :min="volumeRange[0]"
        :max="volumeRange[1]"
        :step="0.01"
      ></a-slider>
    </div>
    <!-- <div style="width: 295px"></div> -->

    <!-- <div>
      <div class="d-flex flex-row gap-3 my-3" style="margin: 0 0 16px 0 !important">
        <SimpleTag
          custom-class="gap-3"
          @click="handleCategoryClick"
          v-for="(item, index) in category"
          :key="index"
          :value="item.value"
          :activate="item.value === flag"
          style="background-color: #5a92f4"
        >
          {{ item.label }}
        </SimpleTag>
      </div>
      <ul
        class="d-flex flex-row flex-wrap row-gap-2 column-gap-3 mb-3"
        style="min-height: 100px"
      >
        <li
          @click="handleSpeakerClick(item)"
          v-for="(item, index) in speakerList"
          :key="index"
        >
          <SpeakerAvatar
            :activate="item.name === tryPlayStore.speaker.name"
            :data="{
              label: item.displayName,
              value: item.name,
              avatar: item.avatar,
              isFree: item.isFree,
            }"
          ></SpeakerAvatar>
        </li>
      </ul>
    </div> -->
  </div>
</template>

<style lang="scss" scoped>
.right-box {
  height: 50px;
}
</style>
