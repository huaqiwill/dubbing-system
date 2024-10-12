import { defineStore } from 'pinia'
import {
  getEmotionNameListApi,
  getLanguageNameListApi,
  getDomainNameListApi,
  getEmotionListApi,
  getSpeakerEmotionListApi,
} from '@/api/dict'
import {
  speakerRecentListApi,
  speakerListApi,
  speakerCollectApi,
  speakerCollectListApi,
  speakerCollectRemoveApi,
  speakerAliasEditApi,
} from '@/api/speaker'
import { emitter } from '@/event-bus'
import { reactive, ref } from 'vue'

export const useSpeakerStore = defineStore('speaker', () => {
  let collectList: any[] = []
  let recentList: any[] = []

  let emotionNameList: any[] = []
  let languageNameList: any[] = []
  let domainNameList: any[] = []

  let speakerEmotionList: any[] = []

  // 配音员列表
  const speakerList = ref<any>([])
  const speakerCount = ref(0)

  let speakerListBackup: any[] = []
  let lastPlayUrl: string = ''

  // 查询参数
  const queryParams = {
    emotionId: '',
    languageId: '',
    domainId: '',
    name: '',
  }

  const submitParams = reactive({
    text: '',
    rawText: '',
    speaker: '',
    audioType: 'mp3',
    speed: 1.0,
    convert: '',
    rate: 24000,
    volume: 1.0,
    pitch: 0,
    symbolSil: 'semi_250,exclamation_300,question_250,comma_200,stop_300,pause_150,colon_200',
    ignoreLimit: true,
    genSrt: false,
    mergeSymbol: false,
  })

  return {
    queryParams,
    submitParams,
    speakerList,

    getSpeakerCount() {
      return speakerCount
    },
    // speaker emotion
    async getSpeakerEmotionList(speakerId: number) {
      return await getSpeakerEmotionListApi(speakerId).then((res: any) => {
        speakerEmotionList = res.rows
        return speakerEmotionList
      })
    },
    getSpeakerEmotionListLocal() {
      return speakerEmotionList
    },
    // submit params
    updateSubmitParam(item: any, value: any) {
      const key = item as keyof typeof submitParams
      submitParams[key] = value
    },
    getSubmitParams() {
      return submitParams
    },
    // domain name list
    getDomainListLocal() {
      return domainNameList
    },
    // collect
    async collect(speakerId: number) {
      return await speakerCollectApi(speakerId).then(() => {
        this.getCollectList()
      })
    },
    async collectRemove(speakerId: number) {
      return await speakerCollectRemoveApi(speakerId).then(() => {
        this.getCollectList()
      })
    },
    async aliasEdit(speakerId: number, alias: string) {
      return await speakerAliasEditApi(speakerId, alias).then(() => {
        this.getCollectList()
      })
    },
    async getCollectList() {
      return await speakerCollectListApi().then((res: any) => {
        collectList = res.rows
        return collectList
      })
    },
    getCollectListLocal() {
      return collectList
    },
    // recent
    async getRecentList() {
      return await speakerRecentListApi().then((res: any) => {
        recentList = res.rows
        return recentList
      })
    },
    // speaker
    getSpeakerListBackup() {
      return speakerListBackup
    },

    async getEmotionList() {
      return await getEmotionListApi().then((res: any) => {
        emotionNameList = res.rows
        return emotionNameList
      })
    },
    async getLanguageNameList() {
      return await getLanguageNameListApi().then((res: any) => {
        languageNameList = res.rows
        return languageNameList
      })
    },

    getEmotionNameListLocal() {
      return emotionNameList
    },

    async getEmotionNameList() {
      return await getEmotionNameListApi().then((res: any) => {
        emotionNameList = res.rows
        return emotionNameList
      })
    },
    async getDomainNameList() {
      return await getDomainNameListApi().then((res: any) => {
        domainNameList = res.rows
        return domainNameList
      })
    },
    async getSpeakerList() {
      return await speakerListApi(queryParams).then((res: any) => {
        this.setSpeakerList(res.rows)
        // speakerListBackup = res.rows
        speakerCount.value = res.total
        return speakerList.value
      })
    },
    selectSpeaker(speaker: any) {
      emitter.emit('speaker:select', speaker)
    },
    getSpeakerListLocal() {
      return speakerList.value
    },
    setSpeakerList(list: any) {
      // 防止传过来的List出现问题
      speakerList.value = list
      emitter.emit('speaker:loading:ok')
    },
    // play
    setLastPlayUrl(url: string) {
      lastPlayUrl = '/prod-api/moyin/tts/audition/' + url
    },
    getLastPlayUrl() {
      return lastPlayUrl
    },
  }
})
