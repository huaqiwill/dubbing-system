import request from '@/utils/request'

export * from './mock'

// 标签管理
export function moyinTagList() {
  return request({
    url: '/moyin/tag/list',
    method: 'GET',
  })
}

// 分类列表
export function moyinCategoryList() {
  return request({
    url: '/moyin/category/list',
    method: 'GET',
  })
}

// 语言管理
export function moyinLanguageList() {
  return request({
    url: '/moyin/language/list',
    method: 'GET',
  })
}

// 情绪列表
export function moyinEmotionList() {
  return request({
    url: '/moyin/emotion/list',
    method: 'GET',
  })
}

// 配音员列表
export function moyinDubbingList(data: any) {
  return request({
    url: '/moyin/dubbing/list',
    method: 'GET',
    data,
  })
}

// 会员信息
export function moyinMemberInfo() {
  return request({
    url: '/moyin/member/info',
    method: 'POST',
  })
}

// 获取用户协议
export function getUserProtocol() {
  return request({
    url: '/moyin/websiteConfig/getUserProtocol',
    method: 'GET',
  })
}

// 查敏感词
export function sensitiveWordIdentify(text: string) {
  return request({
    url: '/moyin/sensitiveWord/identify',
    method: 'POST',
    data: text,
  })
}

export function speakerEditApi(speaker: any) {
  return request({
    url: '/moyin/speaker/editAlias',
    method: 'PUT',
    data: speaker,
  })
}
