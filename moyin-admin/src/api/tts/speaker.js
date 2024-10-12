import request from "@/utils/request";

// 查询配音员列表
export function listSpeaker(query, data) {
  return request({
    url: "/moyin/speaker/list",
    method: "post",
    params: query,
    data: data,
  });
}

// 查询配音员详细
export function getSpeaker(id) {
  return request({
    url: "/moyin/speaker/" + id,
    method: "get",
  });
}

// 新增配音员
export function addSpeaker(data) {
  return request({
    url: "/moyin/speaker",
    method: "post",
    data: data,
  });
}

// 修改配音员
export function updateSpeaker(data) {
  return request({
    url: "/moyin/speaker",
    method: "put",
    data: data,
  });
}

// 删除配音员
export function delSpeaker(id) {
  return request({
    url: "/moyin/speaker/" + id,
    method: "delete",
  });
}
