<template>
  <div class="app-container">
    <el-tabs :tab-position="tabPosition" tab-position="left">
      <el-tab-pane label="网站配置">
        <FrontEndSiteConfig></FrontEndSiteConfig>
        <BackEndSiteConfig></BackEndSiteConfig>
        <AppKeyConfig></AppKeyConfig>
      </el-tab-pane>
      <el-tab-pane label="配音配置">
        <DubbingConfig></DubbingConfig>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style lang="scss">
.el-tabs__content {
  margin-left: 100px;
  overflow-y: auto;
  height: calc(100vh - 140px);
}
</style>

<script>
import AppKeyConfig from "./AppKeyConfig";
import FrontEndSiteConfig from "./FrontEndSiteConfig";
import BackEndSiteConfig from "./BackEndSiteConfig";
import DubbingConfig from "./DubbingConfig.vue";

export default {
  name: "Config",
  components: {
    AppKeyConfig,
    FrontEndSiteConfig,
    BackEndSiteConfig,
    DubbingConfig,
  },
  data() {
    return {
      tabPosition: "left",
    };
  },
};
</script>
