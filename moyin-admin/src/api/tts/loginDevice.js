import request from "@/utils/request";

// 查询领域列表
export function listLoginDevice(query) {
  return request({
    url: "/moyin/loginDevice/list",
    method: "get",
    params: query,
  });
}

// 查询领域详细
export function getLoginDevice(id) {
  return request({
    url: "/moyin/loginDevice/" + id,
    method: "get",
  });
}

// 删除领域
export function delLoginDevice(id) {
  return request({
    url: "/moyin/loginDevice/" + id,
    method: "delete",
  });
}
