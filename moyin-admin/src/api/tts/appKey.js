import request from "@/utils/request";

export function listAppKey(query) {
  return request({
    url: "/moyin/appKey/list",
    method: "get",
    params: query,
  });
}

export function getAppKey(id) {
  return request({
    url: "/moyin/appKey/" + id,
    method: "get",
  });
}

export function addAppKey(data) {
  return request({
    url: "/moyin/appKey",
    method: "post",
    data: data,
  });
}

export function updateAppKey(data) {
  return request({
    url: "/moyin/appKey",
    method: "put",
    data: data,
  });
}

export function delAppKey(id) {
  return request({
    url: "/moyin/appKey/" + id,
    method: "delete",
  });
}

export function getCurrentAppKey() {
  return request({
    url: "/moyin/appKey/current",
    method: "get",
  });
}
