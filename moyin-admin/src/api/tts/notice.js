import request from '@/utils/request'

// 查询配音员情绪列表
export function listNotice(query) {
  return request({
    url: '/moyin/notice/list',
    method: 'get',
    params: query
  })
}

// 查询配音员情绪详细
export function getNotice(id) {
  return request({
    url: '/moyin/notice/' + id,
    method: 'get'
  })
}

// 新增配音员情绪
export function addNotice(data) {
  return request({
    url: '/moyin/notice',
    method: 'post',
    data: data
  })
}

// 修改配音员情绪
export function updateNotice(data) {
  return request({
    url: '/moyin/notice',
    method: 'put',
    data: data
  })
}

// 删除配音员情绪
export function delNotice(id) {
  return request({
    url: '/moyin/notice/' + id,
    method: 'delete'
  })
}
