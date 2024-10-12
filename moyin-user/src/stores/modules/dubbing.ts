import { defineStore } from 'pinia'
// import {
//   getStoreSearchCriteria as getStoreSearchCriteriaApi,
//   getSpeakerEmotionList as getSpeakerEmotionListApi,
//   searchSpeakers as searchSpeakersApi,
// } from '@/api/moyin'
import {
  getEmotionNameListApi,
  getLanguageNameListApi,
  getDomainNameListApi,
  getEmotionListApi,
} from '@/api/dict'
import {
  getSpeakerListApi,
  getSpeakerEmotionListApi,
  getSpeakerListAllApi,
  speakerCollectListApi,
  speakerCollectRemoveApi,
  speakerCollectApi,
} from '@/api/tts'

export const useDubbingStore = defineStore('dubbing', {
  state: () => {
    return {
      /**
       * 配音文字
       */
      dubbingText: '',
      /**
       * 选中的配音文字
       */
      dubbingSelectedText: '',
      // 当前光标位置
      dubbingSelectedIndex: 0,
      // 当前选中的文本长度
      dubbingSelectedLength: 0,

      // 编辑器实例
      editorRef: null,
      // 音量，默认为 50
      dubbingVolume: 50,
      // 编辑器实例
      quillEditorRef: null,

      //
      ssmlRef: '',
      ssmlFormatRef: '',

      /**
       * 搜索条件
       */
      storeSearchCriteria: null,
      /**
       * 情绪列表
       */
      speakerEmotionList: null,
      /**
       * 领域列表
       */
      /**
       * 配音员列表
       */
      searchSpeakerList: [],
      /**
       * 全局配音员
       */
      globalSpeaker: null,
      /**
       * 全局语速
       */
      globalSpeed: 1,
      /**
       * 全局语调
       */
      globalIntonation: 0,

      // 领域、语言、情绪
      languageList: [],
      domainList: [],
      emotionList: [],
      emotionNameList: [],

      speakerList: [],
      speakerTotal: 0,

      // 配音员人数
      speakerCount: 0,
      // 风格数
      styleCount: 0,
      // 提交的数据
      submitTtsData: {
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
      },

      lastPlayUrl: '',

      // try play
      speakerListAll: [],
      speakerListAllBackup: [],
      speakerListCount: 0,

      globalEditorKey: null,

      // 用户收藏列表
      userCollectList: [],
      tempUseSpeakerList: [],
    }
  },
  getters: {},
  actions: {
    setStarOk(speakerId: number, speakerNotes: string) {
      speakerCollectApi({ speakerId, speakerNotes }).then(() => {
        this.getStarList()
      })
    },
    setStarNone(speakerId: number) {
      speakerCollectRemoveApi(speakerId)
      this.userCollectList = this.userCollectList.filter((item: any) => item.id !== speakerId)
    },
    getStarList() {
      speakerCollectListApi().then((res: any) => {
        this.userCollectList = res.rows
// console.log('获取的收藏列表：', this.userCollectList)
      })
    },
    setGlobaleEditorKey(globalEditorKey: any) {
      this.globalEditorKey = globalEditorKey
    },
    getGlobaleEditorKey() {
      return this.globalEditorKey
    },
    async getSpeakerListAll() {
      return await getSpeakerListAllApi().then((res: any) => {
        this.speakerListAll = res.rows
        this.speakerListAllBackup = res.rows
        this.speakerListCount = res.total
      })
    },
    generatorVoice() {},
    setLastPlayUrl(url: string) {
      this.lastPlayUrl = '/prod-api/moyin/tts/audition/' + url
    },
    getLastPlayUrl() {
      return this.lastPlayUrl
    },
    async getEmotionList() {
      return await getEmotionListApi().then((res: any) => {
        this.emotionList = res.rows
      })
    },
    async getLanguageNameList() {
      return await getLanguageNameListApi().then((res: any) => {
        this.languageList = res.rows
      })
    },
    async getEmotionNameList() {
      return await getEmotionNameListApi().then((res: any) => {
        this.emotionNameList = res.rows
      })
    },
    async getDomainNameList() {
      return await getDomainNameListApi().then((res: any) => {
        this.domainList = res.rows
      })
    },
    async getSpeakerList(params: any) {
      return await getSpeakerListApi(params).then((res: any) => {
        this.speakerList = res.rows
        this.speakerTotal = res.total
      })
    },

    /**
     * 获取全部搜索条件
     */
    async getStoreSearchCriteria() {
      // const res = await getStoreSearchCriteriaApi()
      // this.storeSearchCriteria = res.data
      // this.domainList = res.data['全部领域:domainId']
    },
    /**
     * 获取配音员情绪列表
     */
    async getSpeakerEmotionList() {
      return await getSpeakerEmotionListApi().then((res: any) => {
        this.speakerEmotionList = res.rows
// console.log(this.speakerEmotionList)
      })
    },
    /**
     * 配音员列表
     * @param queryParams
     * @param isAppend
     * @returns
     */
    async searchSpeakers(queryParams: any, isAppend: boolean = false) {
      // return searchSpeakersApi(queryParams).then((res) => {
      //   if (isAppend) {
      //     this.searchSpeakerList.push(...res.data.results)
      //   } else {
      //     this.searchSpeakerList = res.data.results
      //   }
      // })
    },
    setSpeakerListAll(speakerListAll: []) {
      this.speakerListAll.slice(0)
      this.speakerListAll = [...speakerListAll]
    },
  },
})
