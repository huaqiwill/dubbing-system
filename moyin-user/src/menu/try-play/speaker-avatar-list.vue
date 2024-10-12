<script setup lang="ts">
// 头像列表部分

import { inject, onMounted, onUnmounted, reactive, ref, shallowRef, watch } from 'vue'
import SpeakerAvatar from './speaker-avatar.vue'
import type { FilterSpeaker, Speaker } from '@/model'
import { useTryPlayStore } from '@/stores'
// import { getConfig } from "@/config";
import type { SpeakerAvatarData } from './data'
import { emitter } from '@/event-bus'
import { useDubbingStore } from '@/stores'
import { getSpeakerListApi, getSpeakerInfoApi } from '@/api/tts'
// import { storeToRefs } from "pinia";

const dubbingStore = useDubbingStore()
const props = defineProps<{ filter: FilterSpeaker }>()
const editorKey = inject<symbol>('editorKey')!
// const ssmlEditorConfig = getConfig(editorKey);
// const { fetchData } = ssmlEditorConfig.tryPlay;
const tryPlayStore = useTryPlayStore()
const speakerAvatarList = ref<SpeakerAvatarData[]>([])
const speakerList = shallowRef([])
// const queryParams = reactive({
//   pageSize: 50,
//   pageNum: 1,
// });
const total = ref(0)

watch(
  () => props.filter,
  async () => {
    await handleFetchData()
  },
)

emitter.on('speaker:loading:ok', () => {
  const { speakerListAll, speakerListCount } = dubbingStore
  total.value = speakerListCount
  speakerAvatarList.value = speakerListAll.map((v: any) => ({
    label: v.name,
    value: v.id,
    avatar: v.headerImage,
    isFree: true,
  }))
  // if (speakerAvatarList.value.length > 0) handleClick(speakerAvatarList.value[0].value);
})

onMounted(async () => {
  emitter.on('tryplay-speaker-update-star', handleUpdateStarTheCache)
})

onUnmounted(() => {
  emitter.off('tryplay-speaker-update-star', handleUpdateStarTheCache)
})

function handleClick(value: string) {
// console.log(value)
  getSpeakerInfoApi(value).then((res: any) => {
    let speaker = res.data
// console.log(speaker);
    tryPlayStore.setSpeaker(editorKey, speaker)
    emitter.emit('speaker:select', speaker)
    // speaker && tryPlayStore.setSpeaker(editorKey, speaker);
  })
}

function handleUpdateStarTheCache(speakerId: string, isStar: boolean) {
  const item: any = speakerList.value.find((v: any) => v.id === speakerId)
  if (item) {
    item.isStar = isStar
  }
}

async function handleFetchData() {
  // try {
// console.log("属性1：", props.filter);

  //   speaderCache.value = await fetchData({ ...toRaw(props.filter) });

// console.log("属性2：", speaderCache.value);

  //   // getSpeakerList().then(res=>{

  //   // })
  // } catch (error) {
  //   emitter.emit("error", error);
  // }

  getSpeakerListApi({}).then((res: any) => {
    // speakerList.value = res.rows;
// console.log("获取配音员列表：", res.rows);
// console.log(res.rows)
    speakerAvatarList.value = res.rows.map((v: any) => ({
      label: v.name,
      value: v.id,
      avatar: v.headerImage,
      isFree: true,
    }))
  })
}
</script>

<template>
  <div
    style="
      height: 400px;
      max-width: 273px;
      min-width: 273px;
      border: 1px solid #3463ab;
      border-left: none;
    "
    class="mt-2 overflow-x-hidden overflow-y-auto scrollbar-none"
  >
    <div class="d-flex flex-row flex-wrap justify-content-start">
      <div
        style="margin: 8px 0; flex: 0 0 68px; height: 68px"
        v-for="item in speakerAvatarList"
        :key="item.value"
      >
        <SpeakerAvatar
          :data="item"
          :activate="item.value === tryPlayStore.speaker.id"
          @click="handleClick(item.value)"
        ></SpeakerAvatar>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
