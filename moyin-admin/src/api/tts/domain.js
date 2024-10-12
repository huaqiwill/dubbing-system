import request from "@/utils/request";

// 查询领域列表
export function listDomain(query) {
  return request({
    url: "/moyin/domain/list",
    method: "get",
    params: query,
  });
}

// 查询领域详细
export function getDomain(id) {
  return request({
    url: "/moyin/domain/" + id,
    method: "get",
  });
}

// 新增领域
export function addDomain(data) {
  return request({
    url: "/moyin/domain",
    method: "post",
    data: data,
  });
}

// 修改领域
export function updateDomain(data) {
  return request({
    url: "/moyin/domain",
    method: "put",
    data: data,
  });
}

// 删除领域
export function delDomain(id) {
  return request({
    url: "/moyin/domain/" + id,
    method: "delete",
  });
}
