<template>
  <div class="dubbing-dialog">
    <el-dialog v-model="model" title="配音员详情" draggable overflow>
      <!-- 信息 -->
      <div class="d-flex flex-wrap">
        <div v-if="info.headerImage" class="header-image">
          <img :src="info.headerImage" alt="" />
        </div>
        <div class="ms-2">
          <div class="" style="font-size: 18px" v-if="info.name">
            {{ info.name }}
            <span>({{ info.language }})</span>
          </div>
          <div class="mt-1" v-if="info.behavior" style="color: #888">
            {{ info.behavior }}
          </div>
        </div>
      </div>

      <!-- 领域 -->
      <div class="mt-3 d-flex gap-2">
        <div
          class="mt-1 d-flex flex-column align-items-center"
          v-for="(item, index) in domainSet"
          :key="index"
        >
          <div>{{ item.name }}</div>
        </div>
      </div>

      <!-- 情绪 -->
      <div class="d-flex flex-wrap emotion-list mt-3">
        <div
          class="mt-1 d-flex flex-column align-items-center emotion-item"
          :class="{ selected: selectedEmotionId == '' }"
          @click="handleDefaultEmotionSelect()"
        >
          <div class="emotion-image">
            <img :src="info.headerImage" />
          </div>
          <div>默认</div>
        </div>
        <div
          v-for="(item, index) in emotionSet"
          :key="index"
          class="mt-1 d-flex flex-column align-items-center emotion-item"
          :class="{ selected: selectedEmotionId == item.id }"
          @click="handleEmotionSelect(item)"
        >
          <div class="emotion-image">
            <img :src="item.imageUrl" />
          </div>
          <div>{{ item.name }}</div>
        </div>
      </div>

      <div class="mt-3">
        <div>配音师配置</div>
      </div>

      <!-- 语速、语调 -->
      <div class="mt-3">
        语速 {{ speed }} px
        <a-slider v-model="speed" class="mt-2" :min="0" :max="2" :step="0.05" />
      </div>
      <div class="mt-3">
        语调 {{ pitch }}
        <a-slider v-model="pitch" class="mt-2" :min="-10" :max="10" :step="0.2" />
      </div>
      <div class="mt-3">
        音量 {{ volume }}
        <a-slider v-model="volume" class="mt-2" :min="0" :max="1" :step="0.01" />
      </div>

      <template #footer>
        <a-button @click="handleCancel">取消</a-button>
        <a-button @click="handleOk" class="ms-2" type="primary">确定</a-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {
  defineModel,
  onMounted,
  onUpdated,
  ref,
  onBeforeUpdate,
  onBeforeMount,
  inject,
} from 'vue'
import { useDubbingStore } from '@/stores'
import { storeToRefs } from 'pinia'
import { getSpeakerEmotionList } from '@/api/dict'
import { ElMessage } from 'element-plus'
import { useTryPlayStore } from '@/stores'

/**
 * 用来控制dialog是否显示
 */
const model = defineModel({
  type: Boolean,
  required: true,
})

/**
 * 传递配音员信息
 */
const props = defineProps({
  info: {
    type: Object,
  },
})

const tryPlayStore = useTryPlayStore()
const dubbingStore = useDubbingStore()
const { domainList, globalSpeaker, globalSpeed, globalIntonation, submitTtsData } =
  storeToRefs(dubbingStore)

/**
 * 情绪、领域列表
 */
const emotionSet = ref([])
const domainSet = ref([])
const languageSet = ref([])
const speakerEmotionCacheVOList = ref([])

/**
 * 语速、语调、音量、选择的情绪
 */
const speed = ref(1)
const pitch = ref(0)
const volume = ref(1)
const selectedEmotion = ref(null)
const selectStyleCallName = ref(null)
const speakerEmotionList = ref([])
const selectedEmotionId = ref('')

onBeforeUpdate(async () => {
  if (model.value) {
    const { emotionList, domainList, languageList } = dubbingStore

    speakerEmotionCacheVOList.value = props.info?.speakerEmotionCacheVOList || []
    // await dubbingStore.getSpeakerEmotionList();
    // await dubbingStore.getStoreSearchCriteria();

    const emotionIdSet = props.info?.emotionIdSet || []
    emotionSet.value = emotionList.filter((emotion) => emotionIdSet.includes(emotion.id))
// console.log('情绪集合', emotionList, emotionIdSet, emotionSet.value)

    const domainIdSet = props.info.domainIdSet || []
    domainSet.value = domainList.filter((domain) => domainIdSet.includes(domain.id))
// console.log('领域集合', domainIdSet, domainList, domainSet.value)

    const languageIdSet = props.info.languageIdSet || []
    languageSet.value = languageList.filter((language) => languageIdSet.includes(language.id))
// console.log('语言集合', languageIdSet, languageList, languageSet.value)

    let speakerId = props.info.id
    getSpeakerEmotionList(speakerId).then((res) => {
      speakerEmotionList.value = res.rows
    })
  }
})

function handleDefaultEmotionSelect() {
  // 从情绪列表中筛选出符合条件的数据
  let speakerInfoList = speakerEmotionList.value.filter(
    (speakerEmotion) => speakerEmotion.emotionId == 0,
  )

  // 如果筛选的数据为空
  if (speakerInfoList.length == 0) {
    return
  }

  // 播放数据
  let speakerInfo = speakerInfoList[0]
// console.log(speakerInfo)

  selectStyleCallName.value = speakerInfo.styleCallName

  selectedEmotionId.value = ''

  // ElMessage({
  //   message: selectStyleCallName.value,
  // });
}

function handleCancel() {
  model.value = false
}

function handleOk() {
// console.log("拿到的数据222222====", props.info);
  model.value = false
  // globalSpeaker.value = props.info;
  // globalSpeed.value = speed.value;
  // globalIntonation.value = intonation.value;

  submitTtsData.value.speed = speed.value
  submitTtsData.value.volume = volume.value
  submitTtsData.value.pitch = pitch.value
  submitTtsData.value.speaker = selectStyleCallName.value

// console.log(submitTtsData.value)

// console.log(editorKey);

  // const editorKey = dubbingStore.getGlobaleEditorKey();
  tryPlayStore.setSpeakerForce(props.info)
}

function handleEmotionSelect(item) {
// console.log('拿到的值：', item)

  selectedEmotionId.value = item.id

  // let speaker = props.info.name + "|" + item.emotionNam;
  // let speakerEmotionCacheVOList = props.info.speakerEmotionCacheVOList;

  // 从情绪列表中筛选出符合条件的数据
  let speakerInfoList = speakerEmotionList.value.filter(
    (speakerEmotion) => speakerEmotion.emotionId == item.id,
  )

  // 如果筛选的数据为空
  if (speakerInfoList.length == 0) {
    return
  }

  // 播放数据
  let speakerInfo = speakerInfoList[0]
// console.log(speakerInfo)

  // const audio = new Audio(speakerInfo.demoUrl);
  // audio.play();

  selectStyleCallName.value = speakerInfo.styleCallName

  // ElMessage({
  //   message: selectStyleCallName.value,
  // });
}
</script>

<style lang="scss" scoped>
:deep(.el-dialog__body) {
  padding: 2px 20px;
}

.header-image {
  width: 40px;
  height: 40px;
  background-color: red;
  img {
    width: 100%;
    height: 100%;
  }
}

$image-width: 32px;
$image-height: 32px;

.emotion-list {
  .emotion-item {
    margin-right: 15px;

    &.selected .emotion-image {
      border: 3px solid #fbc703;
    }

    .emotion-image {
      overflow: hidden;
      width: $image-width;
      height: $image-height;
      overflow: hidden;
      border-radius: 50%;
      border: 3px solid transparent;
      text-align: center;
      vertical-align: center;
      cursor: pointer;

      img {
        scale: 1.2;
        width: $image-width;
        height: $image-height;
      }
    }
  }
}
</style>
