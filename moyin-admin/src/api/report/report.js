import request from "@/utils/request";

export function reportInfoApi() {
  return request({
    url: "/moyin/report",
    method: "get",
  });
}

export function getUserTodayStatisticsApi(params) {
  return request({
    url: "/moyin/report/getUserTodayStatistics",
    method: "get",
    params,
  });
}

export function getUserTotalStatisticsApi(params) {
  return request({
    url: "/moyin/report/getUserTotalStatistics",
    method: "get",
    params,
  });
}
