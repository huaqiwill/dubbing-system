import request from "@/utils/request";

// 查询配音员分类列表
export function listTtsRecord(query) {
  return request({
    url: "/moyin/ttsRecord/listAll",
    method: "get",
    params: query,
  });
}

// 查询配音员分类详细
export function getTtsRecord(id) {
  return request({
    url: "/moyin/ttsRecord/" + id,
    method: "get",
  });
}

// 修改配音员分类
export function updateTtsRecord(data) {
  return request({
    url: "/moyin/ttsRecord",
    method: "put",
    data: data,
  });
}

// 删除配音员分类
export function delTtsRecord(id) {
  return request({
    url: "/moyin/ttsRecord/" + id,
    method: "delete",
  });
}
