import request from '@/utils/request'

/**
 * 生成配音、播放
 * @param data
 * @returns
 */
export function tts(data: any) {
  return request({
    url: '/moyin/tts',
    method: 'post',
    data,
  })
}

/**
 * 获取多音字
 * @param word
 * @returns
 */
export function getWords(word: any) {
  return request({
    url: '/moyin/tts/tnList',
    method: 'get',
    data: { word },
  })
}

/**
 * 数值读法
 * @param word
 * @returns
 */
export function tnList(word: any) {
  return request({
    url: '/moyin/tts/tnList',
    method: 'get',
    data: { word },
  })
}

/**
 * 下载音频
 */
export function downloadAudio(url: string) {
  return request({
    url: '/moyin/tts/audition/' + url,
    method: 'get',
  })
}

/**
 * 下载视频
 * @param url
 * @returns
 */
export function downloadVideo(url: string) {
  return request({
    url: '/moyin/tts/video/' + url,
    method: 'get',
  })
}

/**
 * 下载字幕
 * @param url
 * @returns
 */
export function downloadSrt(url: string) {
  return request({
    url: '/moyin/tts/srt/' + url,
    method: 'get',
  })
}

/**
 * tts记录
 * @param params
 * @returns
 */
export function ttsRecordListApi(params: any) {
  return request({
    url: '/moyin/tts/ttsRecordList',
    method: 'get',
    params,
  })
}

/**
 * 删除tts记录
 * @param id
 * @returns
 */
export function ttsRecordDelete(id: any) {
  return request({
    url: '/moyin/tts/ttsRecordDelete/' + id,
    method: 'delete',
  })
}

export function getSpeakerListApi(params: any) {
  return request({
    url: '/moyin/speaker/list',
    method: 'get',
    params: params,
  })
}

export function getSpeakerInfoApi(id: string) {
  return request({
    url: '/moyin/speaker/' + id,
    method: 'get',
  })
}

export function getSpeakerListAllApi() {
  return request({
    url: '/moyin/speaker/listAll',
    method: 'get',
  })
}

export function getSpeakerEmotionListApi() {
  return request({
    url: '/moyin/speaker/listAll',
    method: 'get',
  })
}

export function speakerCollectApi(data: any) {
  return request({
    url: '/moyin/speaker/memberCollectSpeaker',
    method: 'post',
    data: data,
  })
}

export function speakerCollectListApi() {
  return request({
    url: '/moyin/speaker/memberCollectSpeaker/list',
    method: 'get',
  })
}

export function speakerCollectRemoveApi(id: number) {
  return request({
    url: '/moyin/speaker/memberCollectSpeaker/' + id,
    method: 'delete',
  })
}

export function getRemainApi() {
  return request({
    url: '/moyin/tts/remain',
    method: 'get',
  })
}
