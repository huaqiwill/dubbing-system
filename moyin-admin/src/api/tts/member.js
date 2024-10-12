import request from '@/utils/request'

// 查询配音员情绪列表
export function listMember(query) {
    return request({
        url: '/moyin/member/list',
        method: 'get',
        params: query
    })
}

// 查询配音员情绪详细
export function getMember(id) {
    return request({
        url: '/moyin/member/' + id,
        method: 'get'
    })
}

// 新增配音员情绪
export function addMember(data) {
    return request({
        url: '/moyin/member',
        method: 'post',
        data: data
    })
}

// 修改配音员情绪
export function updateMember(data) {
    return request({
        url: '/moyin/member',
        method: 'put',
        data: data
    })
}

// 删除配音员情绪
export function delMember(id) {
    return request({
        url: '/moyin/member/' + id,
        method: 'delete'
    })
}
