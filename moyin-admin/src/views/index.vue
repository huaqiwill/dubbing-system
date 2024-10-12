<template>
  <div class="app-container home">
    <div>
      <div class="d-flex">
        <div class="card p-2 text-center" style="width: 150px">
          <span style="font-size: 1.2rem">用户</span>
          <span class="mt-2" style="font-size: 1.1rem">{{
            reportInfo.userCount
          }}</span>
        </div>
        <div class="card p-2 text-center ms-3" style="width: 141px">
          <span style="font-size: 1.2rem">在线用户</span>
          <span class="mt-2 text-center" style="font-size: 1.1rem">{{
            reportInfo.userOnlineCount
          }}</span>
        </div>
        <div class="card p-2 text-center ms-3" style="width: 141px">
          <span style="font-size: 1.2rem">配音员</span>
          <span class="mt-2 text-center" style="font-size: 1.1rem">{{
            reportInfo.speakerCount
          }}</span>
        </div>
        <div class="card p-2 text-center ms-3" style="width: 141px">
          <span style="font-size: 1.2rem">配音风格</span>
          <span class="mt-2 text-center" style="font-size: 1.1rem">{{
            reportInfo.styleCount
          }}</span>
        </div>
        <div class="card p-2 text-center ms-3" style="width: 141px">
          <span style="font-size: 1.2rem">配音次数</span>
          <span class="mt-2 text-center" style="font-size: 1.1rem">{{
            reportInfo.ttsCount
          }}</span>
        </div>
        <div class="card p-2 text-center ms-3" style="width: 141px">
          <span style="font-size: 1.2rem">配音总字符</span>
          <span class="mt-2 text-center" style="font-size: 1.1rem">{{
            reportInfo.ttsTextCount
          }}</span>
        </div>
      </div>
    </div>

    <el-row class="mt-3" :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="12">
        <el-table
          v-loading="loading1"
          :data="userTodayTts"
          height="485"
          style="width: 100%"
          class="card"
        >
          <el-table-column prop="userName" label="用户名" width="width">
          </el-table-column>
          <el-table-column prop="nickName" label="用户昵称" width="width">
          </el-table-column>
          <el-table-column prop="ttsCount" label="今日配音次数" width="width">
          </el-table-column>
          <el-table-column
            prop="ttsTextCount"
            label="今日配音字符数"
            width="width"
          >
          </el-table-column>
          <el-table-column
            prop="ttsRemainCount"
            label="今日剩余字符数"
            width="width"
          >
          </el-table-column>
        </el-table>
        <pagination
          v-show="total1 > 0"
          :total="total1"
          :page.sync="queryParams1.pageNum"
          :limit.sync="queryParams1.pageSize"
          @pagination="getUserTodayStatistics"
        />
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12">
        <el-table
          v-loading="loading2"
          :data="userTotalTts"
          height="485"
          style="width: 100%"
          class="card"
        >
          <el-table-column prop="userName" label="用户名" width="width">
          </el-table-column>
          <el-table-column prop="nickName" label="用户昵称" width="width">
          </el-table-column>
          <el-table-column prop="ttsCount" label="本月配音次数" width="width">
          </el-table-column>
          <el-table-column
            prop="ttsTextCount"
            label="本月配音字符数"
            width="width"
          >
          </el-table-column>
          <el-table-column
            prop="ttsRemainCount"
            label="本月剩余字符数"
            width="width"
          >
          </el-table-column>
        </el-table>
        <pagination
          v-show="total2 > 0"
          :total="total2"
          :page.sync="queryParams2.pageNum"
          :limit.sync="queryParams2.pageSize"
          @pagination="getUserTotalStatistics"
        />
      </el-col>
    </el-row>

    <div class="mt-4">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="8">
          <el-card class="update-log">
            <div slot="header" class="clearfix">
              <span>联系信息</span>
            </div>
            <div class="body">
              <p>
                <i class="el-icon-s-promotion"></i> 官网：<el-link
                  href="http://www.ruoyi.vip"
                  target="_blank"
                  >http://www.ruoyi.vip</el-link
                >
              </p>
              <p>
                <i class="el-icon-user-solid"></i> QQ群：<s> 满937441 </s>
                <s> 满887144332 </s> <s> 满180251782 </s> <s> 满104180207 </s>
                <s> 满186866453 </s> <s> 满201396349 </s> <s> 满101456076 </s>
                <s> 满101539465 </s> <s> 满264312783 </s> <s> 满167385320 </s>
                <s> 满104748341 </s> <s> 满160110482 </s> <s> 满170801498 </s>
                <s> 满108482800 </s> <s> 满101046199 </s> <s> 满136919097 </s>
                <s> 满143961921 </s>
                <a
                  href="http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=1HmEGh7zKA_CKI2E-pGInPTlC5jS9mc_&authKey=XaiUf1wfbSTEecm4lDMtIMsc6g%2BoETxjBm1BbZPr6IfuMGRj7oG4GEeu7jtzNaw%2F&noverify=0&group_code=174951577"
                  target="_blank"
                  >174951577</a
                >
              </p>
              <p>
                <i class="el-icon-chat-dot-round"></i> 微信：<a
                  href="javascript:;"
                  >/ *若依</a
                >
              </p>
              <p>
                <i class="el-icon-money"></i> 支付宝：<a
                  href="javascript:;"
                  class="支付宝信息"
                  >/ *若依</a
                >
              </p>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="8">
          <el-card class="update-log">
            <div slot="header" class="clearfix">
              <span>捐赠支持</span>
            </div>
            <div class="body">
              <img src="@/assets/images/pay.png" alt="donate" width="100%" />
              <span
                style="display: inline-block; height: 30px; line-height: 30px"
                >你可以请作者喝杯咖啡表示鼓励</span
              >
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {
  reportInfoApi,
  getUserTodayStatisticsApi,
  getUserTotalStatisticsApi,
} from "@/api/report/report";

export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "1.0.0",
      reportInfo: {
        userCount: 0,
        userOnlineCount: 0,
        speakerCount: 0,
        styleCount: 0,
        ttsCount: 0,
        ttsTextCount: 0,
      },
      userTodayTts: [],
      userTotalTts: [],
      queryParams1: {
        pageSize: 10,
        pageNum: 1,
      },
      queryParams2: {
        pageSize: 10,
        pageNum: 1,
      },
      total1: 0,
      total2: 0,
      loading1: false,
      loading2: false,
    };
  },
  created() {
    this.getReportData();
    this.getUserTodayStatistics();
    this.getUserTotalStatistics();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    getReportData() {
      reportInfoApi().then((res) => {
        this.reportInfo.speakerCount = res.data.speakerCount;
        this.reportInfo.userCount = res.data.userCount;
        this.reportInfo.userOnlineCount = res.data.userOnlineCount;
        this.reportInfo.styleCount = res.data.styleCount;
        this.reportInfo.ttsCount = res.data.ttsCount;
        this.reportInfo.ttsTextCount = res.data.ttsTextCount;
      });
    },
    getUserTodayStatistics() {
      this.loading1 = true;
      getUserTodayStatisticsApi(this.queryParams1).then((res) => {
        this.userTodayTts = res.rows;
        this.total1 = res.total;
        this.loading1 = false;
      });
    },
    getUserTotalStatistics() {
      this.loading2 = true;
      getUserTotalStatisticsApi(this.queryParams2).then((res) => {
        this.userTotalTts = res.rows;
        this.total2 = res.total;
        this.loading2 = false;
      });
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
