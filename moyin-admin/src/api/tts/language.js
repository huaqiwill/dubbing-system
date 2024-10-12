import request from "@/utils/request";

// 查询语言分类列表
export function listLanguage(query) {
  return request({
    url: "/moyin/language/list",
    method: "get",
    params: query,
  });
}

// 查询语言分类详细
export function getLanguage(id) {
  return request({
    url: "/moyin/language/" + id,
    method: "get",
  });
}

// 新增语言分类
export function addLanguage(data) {
  return request({
    url: "/moyin/language",
    method: "post",
    data: data,
  });
}

// 修改语言分类
export function updateLanguage(data) {
  return request({
    url: "/moyin/language",
    method: "put",
    data: data,
  });
}

// 删除语言分类
export function delLanguage(id) {
  return request({
    url: "/moyin/language/" + id,
    method: "delete",
  });
}
