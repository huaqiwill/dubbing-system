import request from '@/utils/request'

export function getEmotionNameListApi() {
  return request({
    url: '/moyin/dict/emotionNameList',
    method: 'get',
  })
}

export function getLanguageNameListApi() {
  return request({
    url: '/moyin/dict/languageNameList',
    method: 'get',
  })
}

export function getDomainNameListApi() {
  return request({
    url: '/moyin/dict/domainNameList',
    method: 'get',
  })
}

export function getEmotionListApi() {
  return request({
    url: '/moyin/dict/emotionList',
    method: 'get',
  })
}

export function getSpeakerEmotionListApi(speakerId: number) {
  return request({
    url: '/moyin/dict/getSpeakerEmotionList/' + speakerId,
    method: 'get',
  })
}

export function getSpeakerEmotionList(speakerId: number) {
  return request({
    url: '/moyin/dict/getSpeakerEmotionList/' + speakerId,
    method: 'get',
  })
}
