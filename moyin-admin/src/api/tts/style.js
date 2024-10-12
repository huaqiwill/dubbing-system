import request from "@/utils/request";

// 查询配音员分类列表
export function listStyle(query) {
  return request({
    url: "/moyin/style/list",
    method: "get",
    params: query,
  });
}

// 查询配音员分类详细
export function getStyle(id) {
  return request({
    url: "/moyin/style/" + id,
    method: "get",
  });
}

// 新增配音员分类
export function addStyle(data) {
  return request({
    url: "/moyin/style",
    method: "post",
    data: data,
  });
}

// 修改配音员分类
export function updateStyle(data) {
  return request({
    url: "/moyin/style",
    method: "put",
    data: data,
  });
}

// 删除配音员分类
export function delStyle(id) {
  return request({
    url: "/moyin/style/" + id,
    method: "delete",
  });
}
