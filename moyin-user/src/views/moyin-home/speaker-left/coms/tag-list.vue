<template>
  <div class="tag-list d-flex" style="width: 100%; height: 100%">
    <!-- language -->
    <div
      class="overflow-y-auto overflow-x-hidden scrollbar-none"
      style="padding: 10px 0; width: 80px"
    >
      <div class="item d-flex flex-column justify-content-center row-gap-2">
        <div class="text-center">
          <SimpleTag
            @click="handleLanguageClick(item.value)"
            v-for="(item, index) in languages"
            :key="index"
            :value="item.value"
            :activate="selectedLanguage === item.value"
          >
            {{ item.label }}
          </SimpleTag>
        </div>
      </div>
    </div>
    <!-- domains -->
    <div
      style="border-left: 1px solid #fff; padding: 10px 0; width: 80px"
      class="overflow-y-auto overflow-x-hidden scrollbar-none"
    >
      <div class="d-flex flex-column justify-content-center row-gap-2">
        <div class="text-center">
          <SimpleTag
            @click="handleDomainClick(item.value)"
            v-for="(item, index) in domains"
            :key="index"
            :value="item.value"
            :activate="selectedDomain === item.value"
          >
            {{ item.label }}
          </SimpleTag>
        </div>
      </div>
    </div>

    <div
      style="border-left: 1px solid #fff; padding: 10px 0; width: 80px"
      class="overflow-y-auto overflow-x-hidden scrollbar-none"
    >
      <div class="item d-flex flex-column justify-content-center row-gap-2">
        <div class="text-center">
          <SimpleTag
            @click="handleEmotionClick(item.value)"
            v-for="(item, index) in tags"
            :key="index"
            :value="item.value"
            :activate="selectedEmotion === item.value"
          >
            {{ item.label }}
          </SimpleTag>
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.tag-list {
  margin-left: 5px;
  .list {
    padding: 10px 0;
    width: 80px;
  }
}
</style>

<script setup lang="ts">
import { SimpleTag } from '.'
import { onMounted, ref, shallowRef } from 'vue'
import { useSpeakerStore } from '@/stores'

const speakerStore = useSpeakerStore()
const tags = shallowRef<any>([])
const domains = shallowRef<any>([])
const languages = shallowRef<any>([])
const selectedLanguage = ref('')
const selectedDomain = ref('')
const selectedEmotion = ref('')

onMounted(async () => {
  let emotionList = await speakerStore.getEmotionList()
  let domainList = await speakerStore.getDomainNameList()
  let languageList = await speakerStore.getLanguageNameList()

  tags.value = [
    {
      label: '全部情绪',
      value: '',
    },
    ...emotionList.map((item: any) => {
      return {
        label: item.name,
        value: item.id,
      }
    }),
  ]

  domains.value = [
    {
      label: '全部领域',
      value: '',
    },
    ...domainList.map((item: any) => {
      return {
        label: item.name,
        value: item.id,
      }
    }),
  ]

  languages.value = [
    {
      label: '全部语言',
      value: '',
    },
    ...languageList.map((item: any) => {
      return {
        label: item.name,
        value: item.id,
      }
    }),
  ]
})

async function filterSpeaker() {
  // const speakerListAllBackup = speakerStore.getSpeakerListBackup()
  // let speakerList = [...speakerListAllBackup]

  // if (selectedLanguage.value !== '') {
  //   speakerList = speakerList.filter((speaker: any) =>
  //     speaker.languageIdSet.includes(selectedLanguage.value),
  //   )
  // }
  // if (selectedEmotion.value !== '') {
  //   speakerList = speakerList.filter((speaker: any) =>
  //     speaker.emotionIdSet.includes(selectedEmotion.value),
  //   )
  // }
  // if (selectedDomain.value !== '') {
  //   speakerList = speakerList.filter((speaker: any) =>
  //     speaker.domainIdSet.includes(selectedDomain.value),
  //   )
  // }

// console.log('情绪：', selectedEmotion.value)
// console.log('领域：', selectedDomain.value)
// console.log('语言：', selectedLanguage.value)

  speakerStore.queryParams.emotionId = selectedEmotion.value
  speakerStore.queryParams.domainId = selectedDomain.value
  speakerStore.queryParams.languageId = selectedLanguage.value

  const speakerList = await speakerStore.getSpeakerList()
// console.log(speakerList)

  speakerStore.setSpeakerList(speakerList)
}

function handleLanguageClick(languageId: string) {
  selectedLanguage.value = languageId
  filterSpeaker()
}

function handleDomainClick(domainId: string) {
  selectedDomain.value = domainId
  filterSpeaker()
}

function handleEmotionClick(emotionId: string) {
  selectedEmotion.value = emotionId
  filterSpeaker()
}
</script>
