import request from "@/utils/request";

export function listEmotion(query) {
  return request({
    url: "/moyin/emotion/list",
    method: "get",
    params: query,
  });
}

export function getEmotion(id) {
  return request({
    url: "/moyin/emotion/" + id,
    method: "get",
  });
}

export function addEmotion(data) {
  return request({
    url: "/moyin/emotion",
    method: "post",
    data: data,
  });
}

export function updateEmotion(data) {
  return request({
    url: "/moyin/emotion",
    method: "put",
    data: data,
  });
}

export function delEmotion(id) {
  return request({
    url: "/moyin/emotion/" + id,
    method: "delete",
  });
}
