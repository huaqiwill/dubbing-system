<template>
  <div class="member-list">
    <MemberItem v-for="item in priceList" :key="item.name" :data="item"></MemberItem>
  </div>
</template>

<script setup>
import { MemberItem } from "./index";

const priceList = [
  {
    name: "月会员",
    price: 29,
    rawPrice: 39,
    descItems: [
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon01%403x.png",
        name: "解锁全部功能",
        desc: "配音更便捷高效",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon04%403x.png",
        name: "文案提取",
        desc: "一键语音转文字",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon07%403x.png",
        name: "多人配音",
        desc: "对话配音更高效",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon10%403x.png",
        name: "停顿调节",
        desc: "配音更自然合理",
      },
    ],
  },
  {
    name: "年会员",
    price: 79,
    rawPrice: 199,
    descItems: [
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon02%403x.png",
        name: "解锁全部声音",
        desc: "畅享210+音色",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon05%403x.png",
        name: "电脑版",
        desc: "编辑下载更方便",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon08%403x.png",
        name: "同步字幕",
        desc: "电脑版字幕导入PR",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon11%403x.png",
        name: "专属服务",
        desc: "客服专业技术支持",
      },
    ],
  },
  {
    name: "包终身",
    price: 99,
    rawPrice: 299,
    descItems: [
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon03%403x.png",
        name: "随心制作",
        desc: "每天100次导出",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon06%403x.png",
        name: "敏感词检测",
        desc: "让创作少违规",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon09%403x.png",
        name: "限时特价",
        desc: "终身VIP低至3折",
      },
      {
        img: "https://cdn.peiyinshenqi.club/resources/tts/icon/icon12%403x.png",
        name: "邀请赚佣金",
        desc: "多邀多得无上限",
      },
    ],
  },
];
</script>

<style lang="scss" scoped>
.member-list {
  display: flex;
  justify-content: space-around;

  .member-item {
  }
}
</style>
