<template>
  <div>
    <div class="flex">
      <button>插入停顿</button>
      <button>连续</button>
      <button>儿童化</button>
      <button>插入日期</button>
      <button>效果音</button>
      <button>背景音</button>
      <button>局部变速</button>
      <button>重读</button>
      <button>多音字</button>
      <button>中文</button>
    </div>
    <textarea name="" id="" v-model="voiceForm.text" rows="20"></textarea>
    <div class="flex">
      <button>开始试听</button>
      <button>试听效果</button>
      <button>下载音频</button>
      <button>转换记录</button>
      <button>一键清空</button>
      <button>字幕生成</button>
    </div>
    <div>
      <ul class="flex list-style-none">
        <li
          style="margin-left: 10px"
          v-for="category in categoryList"
          :key="category"
        >
          {{ category }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script >
export default {
  data() {
    return {
      voiceForm: {
        text: ""
      },
      // 版本号
      version: "3.8.6",
      categoryList: [
        "全部", "上新", "收藏", "推荐", "热门", "有声书", "通用", "广告解说", "童声", "方言", "外语", "多情感", "客服数字人", "女生", "通用"
      ]
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped>
.flex {
  display: flex;
  flex-direction: row;
}
.list-style-none {
  list-style: none;
}
</style>
