import request from '@/utils/request'

export function getSpeakerEmotionList() {
  return request({
    url: 'https://openapi.mobvoi.com/apis/tts-web-api/v1/speaker/getSpeakerEmotionList',
    method: 'get',
    headers: {
      isToken: false,
      repeatSubmit: false,
    },
  })
}

export function getStoreSearchCriteria() {
  return request({
    url: 'https://openapi.mobvoi.com/apis/tts-web-api/v1/storeSpeakerV2/getStoreSearchCriteria',
    method: 'get',
    headers: {
      isToken: false,
      repeatSubmit: false,
    },
  })
}

/**
 * params: {
      ...params,
      keyWord: '',
      domainId: '',
      emotion: '',
      specificLanguage: '',
      gender: '',
      age: '',
      vipAuth: '',
      sort: '',
      mainEmotion: '',
      pageSize: 30,
      pageNum: 1,
    },
 * @param params 
 * @returns 
 */
export function searchSpeakers(params: any) {
  return request({
    url: 'https://openapi.mobvoi.com/open-apis/speaker/searchSpeakers',
    method: 'get',
    headers: {
      isToken: false,
      repeatSubmit: false,
    },
    params,
  })
}

export function getWords(word: string) {
  return request({
    headers: {
      isToken: false,
      repeatSubmit: false,
    },
    url: 'https://voice-maker-pc-nodetts.moyin.com/tts/getWords',
    method: 'get',
    params: { word: word, lang: 'zh-cn', ww_token: '1581ca2346e72c2a2bd3f6a1b72e67c0' },
  })
}

export function tnList(word: string) {
  return request({
    headers: {
      isToken: false,
      repeatSubmit: false,
    },
    url: 'https://voice-maker-pc-nodetts.moyin.com/tts/tnList',
    method: 'get',
    params: { word: word, ww_token: '1581ca2346e72c2a2bd3f6a1b72e67c0' },
  })
}

