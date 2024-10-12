import request from '@/utils/request'

export function speakerListApi(params: any) {
  return request({
    url: '/moyin/speaker/listJustShow',
    method: 'get',
    params,
  })
}

export function speakerAliasEditApi(speakerId: number, alias: string) {
  return request({
    url: '/moyin/speaker/alias',
    method: 'put',
    data: {
      id: speakerId,
      alias,
    },
  })
}

export function speakerCollectListApi() {
  return request({
    url: '/moyin/speaker/collect/list',
    method: 'get',
  })
}

export function speakerCollectApi(speakerId: number) {
  return request({
    url: '/moyin/speaker/collect',
    method: 'post',
    data: {
      speakerId,
    },
  })
}

export function speakerCollectRemoveApi(speakerId: number) {
  return request({
    url: '/moyin/speaker/collect/' + speakerId,
    method: 'delete',
  })
}

export function speakerRecentListApi() {
  return request({
    url: '/moyin/speaker/recent/list',
    method: 'get',
  })
}

export function speakerRecentAddApi(speaker: any) {
  return request({
    url: '/moyin/speaker/recent',
    method: 'post',
    data: speaker,
  })
}
