import request from "@/utils/request";

// 查询配音员分类列表
export function listTag(query) {
  return request({
    url: "/moyin/tag/list",
    method: "get",
    params: query,
  });
}

// 查询配音员分类详细
export function getTag(id) {
  return request({
    url: "/moyin/tag/" + id,
    method: "get",
  });
}

// 新增配音员分类
export function addTag(data) {
  return request({
    url: "/moyin/tag",
    method: "post",
    data: data,
  });
}

// 修改配音员分类
export function updateTag(data) {
  return request({
    url: "/moyin/tag",
    method: "put",
    data: data,
  });
}

// 删除配音员分类
export function delTag(id) {
  return request({
    url: "/moyin/tag/" + id,
    method: "delete",
  });
}
