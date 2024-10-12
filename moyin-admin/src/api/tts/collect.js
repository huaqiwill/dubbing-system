import request from "@/utils/request";

export function listCollect(query) {
  return request({
    url: "/moyin/collect/list",
    method: "get",
    params: query,
  });
}

export function getCollect(id) {
  return request({
    url: "/moyin/collect/" + id,
    method: "get",
  });
}

export function addCollect(data) {
  return request({
    url: "/moyin/collect",
    method: "post",
    data: data,
  });
}

export function updateCollect(data) {
  return request({
    url: "/moyin/collect",
    method: "put",
    data: data,
  });
}

export function delCollect(id) {
  return request({
    url: "/moyin/collect/" + id,
    method: "delete",
  });
}
