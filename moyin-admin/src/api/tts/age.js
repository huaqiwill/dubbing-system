import request from "@/utils/request";

export function listAge(query) {
  return request({
    url: "/moyin/age/list",
    method: "get",
    params: query,
  });
}

export function getAge(id) {
  return request({
    url: "/moyin/age/" + id,
    method: "get",
  });
}

export function addAge(data) {
  return request({
    url: "/moyin/age",
    method: "post",
    data: data,
  });
}

export function updateAge(data) {
  return request({
    url: "/moyin/age",
    method: "put",
    data: data,
  });
}

export function delAge(id) {
  return request({
    url: "/moyin/age/" + id,
    method: "delete",
  });
}
