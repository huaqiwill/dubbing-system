import request from "@/utils/request";

export function listLexicon(query) {
  return request({
    url: "/moyin/lexicon/list",
    method: "get",
    params: query,
  });
}

export function getLexicon(id) {
  return request({
    url: "/moyin/lexicon/" + id,
    method: "get",
  });
}

export function addLexicon(data) {
  return request({
    url: "/moyin/lexicon",
    method: "post",
    data: data,
  });
}

export function updateLexicon(data) {
  return request({
    url: "/moyin/lexicon",
    method: "put",
    data: data,
  });
}

export function delLexicon(id) {
  return request({
    url: "/moyin/lexicon/" + id,
    method: "delete",
  });
}
