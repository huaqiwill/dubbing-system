<template>
  <el-card class="dubbing-list">
    <!-- 搜索 -->
    <el-input
      class="dubbing-search"
      v-model="searchContent"
      placeholder="共763款配音师，输入名称搜索"
      :suffix-icon="Search"
    />

    <!-- 标签 -->
    <ul class="search-tag" v-for="tag in searchCriteriaList" :key="tag.name">
      <div class="label">{{ tag.name }}</div>
      <li
        :class="currentTag === item ? 'selected' : ''"
        v-for="item in tagList"
        :key="item"
        @click="handleTagClicked(item)"
      >
        <span>{{ item }}</span>
      </li>
    </ul>

    <!-- 性别 -->
    <ul class="search-gender">
      <div class="label">性别</div>
      <li
        :class="currentGender === item.name ? 'selected' : ''"
        v-for="item in genderList"
        @click="handleGenderClicked(item)"
        :key="item"
      >
        <span>{{ item.name }}</span>
      </li>
    </ul>
    <!-- 分类 -->
    <ul class="search-category">
      <div class="label">分类</div>
      <li
        :class="currentCategory === item ? 'selected' : ''"
        v-for="item in categoryList"
        @click="handleCategoryClicked(item)"
        :key="item"
      >
        <span>{{ item }}</span>
      </li>
    </ul>
    <!-- 语言 -->
    <ul class="search-language">
      <div class="label">语言</div>
      <li
        :class="currentLanguage === item.name ? 'selected' : ''"
        v-for="item in languageList"
        @click="handleLanguageClicked(item)"
        :key="item"
      >
        <span>{{ item.name }}</span>
      </li>
    </ul>
    <!-- 配音员 -->
    <ul class="speaker-list">
      <li
        :class="currentSpeaker === item.id ? 'selected' : ''"
        v-for="item in speakerList"
        @click="handleSpeakerClicked(item)"
        :key="item"
      >
        <div class="speaker-tag">{{ item.tag }}</div>
        <div class="speaker-img">
          <img :src="item.img" alt="" />
        </div>
        <div class="speaker-info">
          <span>{{ item.name }}</span>
        </div>
      </li>
    </ul>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { Search } from "@element-plus/icons-vue";
import { moyinCategoryList, moyinDubbingList, moyinEmotionList } from "@/api";
import { ElLoading } from "element-plus";
import {
  getSpeakerEmotionList,
  getStoreSearchCriteria,
  searchSpeakers,
} from "@/api/moyin";

const tagList = ref(["热榜", "至臻", "SVIP", "付费"]);
const genderList = ref(["全部", "男声", "女声"]);
const categoryList = ref([
  "全部大哥",
  "影视大哥",
  "情感大哥",
  "助理大哥",
  "全部大小",
  "影视2",
  "情感2",
  "助理2",
]);
const languageList = ref(["全部", "英文", "德语"]);
const speakerList = ref([
  {
    id: 1,
    name: "康郧西",
    tag: "付费",
    img:
      "https://mobvoi-growth-public.ticwear.com/image/moyingongfang/2023-11-03-8811501069080134477.jpeg?iopcmd=thumbnail&type=8&width=80&height=80",
  },
  {
    id: 2,
    name: "康郧西",
    tag: "付费",
    img:
      "https://mobvoi-growth-public.ticwear.com/image/moyingongfang/2023-11-03-8811501069080134477.jpeg?iopcmd=thumbnail&type=8&width=80&height=80",
  },
  {
    id: 3,
    name: "康郧西",
    tag: "付费",
    img:
      "https://mobvoi-growth-public.ticwear.com/image/moyingongfang/2023-11-03-8811501069080134477.jpeg?iopcmd=thumbnail&type=8&width=80&height=80",
  },
]);

const speakerEmotionList = ref([]);
const storeSearchCriteria = ref([]);
// const genderList = ref([]);
const ageList = ref([]);
// const languageList = ref([]);
const vipAuthList = ref([]);
const domainList = ref([]);
const mainEmotionList = ref([]);
const sortList = ref([]);

onMounted(() => {
  // const loadingInstance = ElLoading.service({});
  // 并行处理异步请求
  // Promise.all([moyinCategoryList(), moyinDubbingList(), moyinEmotionList()]).then(
  //   ([categoryRes, emotionRes, dubbingRes]) => {
  //     // 处理分类列表
  //     categoryList.value = categoryRes.rows.map((category) => ({
  //       label: category.name,
  //       value: category.id,
  //     }));
  //     // 处理情绪列表
  //     emotionList.value = emotionRes.rows.map((emotion) => ({
  //       label: emotion.name,
  //       value: emotion.id,
  //     }));
  //     // 处理配音员列表
  //     dubbingList.value = dubbingRes.rows;
  //     loadingInstance.close();
  //   }
  // );

  getSpeakerEmotionList().then((res) => {
    speakerEmotionList.value = res.data;
  });

  const searchCriteriaList = ref([]);

  getStoreSearchCriteria().then((res) => {
    storeSearchCriteria.value = res.data;
    let keys = Object.keys(storeSearchCriteria.value);
    let list = [];
    for (let key of keys) {
      list.push({
        raw: key,
        name: key.split(":")[0],
        value: key.split(":")[1],
      });
    }
    searchCriteriaList.value = list;
  });
});

const currentTag = ref("热榜");
const handleTagClicked = (value) => {
  currentTag.value = value;
};

const currentGender = ref("全部");
const handleGenderClicked = (value) => {
  currentGender.value = value.name;
};

const currentCategory = ref("全部");
const handleCategoryClicked = (value) => {
  currentCategory.value = value;
};

const currentLanguage = ref("全部");
const handleLanguageClicked = (value) => {
  currentLanguage.value = value.name;
};

const currentSpeaker = ref("全部");
const handleSpeakerClicked = (speaker) => {
  currentSpeaker.value = speaker.id;
};

const searchContent = ref("");
</script>

<style scoped lang="scss">
.dubbing-list {
  height: 100%;
  background-color: #333;
  padding: 15px;
  // border-radius: 6px;

  :depp(.el-input) {
    margin-bottom: 15px;
    .el-input__wrapper {
      background-color: #4e76b4 !important;
      // border-radius: 16px !important;
      input {
        caret-color: white;
      }
    }
  }

  .search-tag {
    display: flex;
    gap: 16px;
    margin-bottom: 15px;
    flex-wrap: wrap;
    .label {
      font-size: 12px;
      color: #fff;
    }
    li {
      flex: 0 0 calc(100% / 6); /* 每个元素占1/7的宽度 */
      border-radius: 2px;
      font-size: 12px;
      cursor: pointer;
      color: hsla(0, 0%, 100%, 0.6);
      font-family: "微软雅黑";
      &.selected span {
        color: #fff;
        background: hsla(0, 0%, 100%, 0.2);
        border: 1px solid #fff;
      }
      span {
        padding: 3px 8px;
        border: 1px solid transparent;
      }
    }
  }

  .search-gender {
    display: flex;
    gap: 16px;
    margin-bottom: 15px;
    flex-wrap: wrap;
    .label {
      font-size: 12px;
      color: #fff;
    }
    li {
      flex: 0 0 calc(100% / 6); /* 每个元素占1/7的宽度 */
      border-radius: 2px;
      font-size: 12px;
      cursor: pointer;
      color: hsla(0, 0%, 100%, 0.6);
      font-family: "微软雅黑";
      &.selected span {
        color: #fff;
        background: hsla(0, 0%, 100%, 0.2);
        border: 1px solid #fff;
      }
      span {
        padding: 3px 8px;
        border: 1px solid transparent;
      }
    }
  }

  .search-category {
    display: flex;
    gap: 16px;
    margin-bottom: 15px;
    flex-wrap: wrap;
    .label {
      font-size: 12px;
      color: #fff;
    }
    li {
      flex: 0 0 calc(100% / 6); /* 每个元素占1/7的宽度 */
      border-radius: 2px;
      font-size: 12px;
      cursor: pointer;
      color: hsla(0, 0%, 100%, 0.6);
      font-family: "微软雅黑";
      &.selected span {
        color: #fff;
        background: hsla(0, 0%, 100%, 0.2);
        border: 1px solid #fff;
      }
      span {
        padding: 3px 8px;
        border: 1px solid transparent;
      }
    }
  }

  .search-language {
    display: flex;
    gap: 16px;
    margin-bottom: 30px;
    flex-wrap: wrap;
    .label {
      font-size: 12px;
      color: #fff;
    }
    li {
      flex: 0 0 calc(100% / 6); /* 每个元素占1/7的宽度 */
      border-radius: 2px;
      font-size: 12px;
      cursor: pointer;
      color: hsla(0, 0%, 100%, 0.6);
      font-family: "微软雅黑";
      &.selected span {
        color: #fff;
        background: hsla(0, 0%, 100%, 0.2);
        border: 1px solid #fff;
      }
      span {
        padding: 3px 8px;
        border: 1px solid transparent;
      }
    }
  }

  .speaker-list {
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
    li {
      position: relative;

      &.selected .speaker-img {
        border-radius: 50%;
        border: 2px solid #ffc900;
      }

      .speaker-tag {
        position: absolute;
        left: 29px;
        top: -5px;
        width: auto;
        font-size: 10px;
        height: 14px;
        min-width: 26px;
        background-color: #f74c4a;
        border-radius: 6px 6px 6px 1px;
        z-index: 2;
        color: #fff;
        padding: 0 2px;
      }

      .speaker-img {
        width: 40px;
        height: 40px;
        margin-bottom: 8px;
        cursor: pointer;
        border: 2px solid transparent;

        img {
          width: 100%;
          height: 100%;
          border-radius: 50%;
          vertical-align: middle;
          text-align: center;
          border: none;
        }
      }
      .speaker-info {
        font-size: 12px;
        color: hsla(0, 0%, 100%, 0.6);
        font-family: "微软雅黑";
        text-align: center;
      }
    }
  }
}
</style>
