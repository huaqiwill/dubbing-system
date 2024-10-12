import request from "@/utils/request";

export function listSensitive(query) {
  return request({
    url: "/moyin/sensitive/list",
    method: "get",
    params: query,
  });
}

export function getSensitive(id) {
  return request({
    url: "/moyin/sensitive/" + id,
    method: "get",
  });
}

export function addSensitive(data) {
  return request({
    url: "/moyin/sensitive",
    method: "post",
    data: data,
  });
}

export function updateSensitive(data) {
  return request({
    url: "/moyin/sensitive",
    method: "put",
    data: data,
  });
}

export function delSensitive(id) {
  return request({
    url: "/moyin/sensitive/" + id,
    method: "delete",
  });
}
