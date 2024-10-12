import request from "@/utils/request";

export function userNameList() {
  return request({
    url: "/moyin/dict/userNameList",
    method: "get",
  });
}

export function speakerNameList() {
  return request({
    url: "/moyin/dict/speakerNameList",
    method: "get",
  });
}

export function lexiconNameList() {
  return request({
    url: "/moyin/dict/lexiconNameList",
    method: "get",
  });
}

export function domainNameList() {
  return request({
    url: "/moyin/dict/domainNameList",
    method: "get",
  });
}

export function languageNameList() {
  return request({
    url: "/moyin/dict/languageNameList",
    method: "get",
  });
}

export function emotionNameList() {
  return request({
    url: "/moyin/dict/emotionNameList",
    method: "get",
  });
}
