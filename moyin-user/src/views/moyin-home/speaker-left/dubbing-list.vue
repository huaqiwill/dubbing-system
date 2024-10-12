<script setup>
import { ref, reactive, onMounted, computed, onUpdated } from "vue";
import { Search } from "@element-plus/icons-vue";
import { moyinCategoryList, moyinDubbingList, moyinEmotionList } from "@/api";
import { ElLoading } from "element-plus";
import { DubbingInfo } from "./index";
import {
  getSpeakerEmotionList,
  getStoreSearchCriteria,
  searchSpeakers,
} from "@/api/moyin";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";
import { getSpeakerListApi } from "@/api/tts";
import { useTryPlayStore } from "@/stores";

const tryPlayStore = useTryPlayStore();
const dubbingStore = useDubbingStore();
const {
  speakerEmotionList,
  storeSearchCriteria,
  searchCriteriaList,
  searchSpeakerList,
} = storeToRefs(dubbingStore);
const speakerInfo = ref();
const dialogShow = ref(false);
const loading = ref(false);
const showAllCriteria = ref(false); // 控制是否显示所有搜索条件
const queryParams = reactive({
  // keyWord: "",
  // domainId: "",
  // emotion: "",
  // specificLanguage: "",
  // gender: "",
  // age: "",
  // vipAuth: "",
  // sort: "",
  // mainEmotion: "",
  name: "",
  pageSize: 30,
  pageNum: 1,
});

const maxPageNum = ref(0);

const speakerList = ref([]);

const total = ref(0);

onMounted(async () => {
  // loading.value = true;

  // await dubbingStore.getSpeakerEmotionList();
  // await dubbingStore.getStoreSearchCriteria();
  // await dubbingStore.searchSpeakers(queryParams);
  

  getSpeakerListApi(queryParams).then((res) => {
    speakerList.value = res.rows;
    total.value = res.total;

    if (speakerList.value.length > 0) {
      tryPlayStore.setSpeakerForce(speakerList.value[0]);
    }
  });

  // 异步加载情绪集合
  // dubbingStore.getSpeakerEmotionList()

// console.log("情绪集合", speakerEmotionList.value);
// console.log("搜索条件", storeSearchCriteria.value);

  // const speakerEmotionPromise = getSpeakerEmotionList().then((res) => {
  //   speakerEmotionList.value = res.data;
  // });

  // const storeSearchCriteriaPromise = getStoreSearchCriteria().then((res) => {
  //   storeSearchCriteria.value = res.data;
  //   let keys = Object.keys(storeSearchCriteria.value);
  //   let list = [];
  //   for (let key of keys) {
  //     list.push({
  //       raw: key,
  //       name: key.split(":")[0],
  //       value: key.split(":")[1],
  //     });
  //   }
  //   searchCriteriaList.value = list;
  // });

  // const searchSpeakersPromise = getSearchSpeakers();

  // await Promise.all([
  //   speakerEmotionPromise,
  //   storeSearchCriteriaPromise,
  //   searchSpeakersPromise,
  // ]);

  // loading.value = false;
});

// 展示配音员信息
const onShowSpeakerInfo = (item) => {
  speakerInfo.value = item;
  dialogShow.value = true;
};

//
const hasMoreCriteria = computed(() => {
  return searchCriteriaList.value.length > 3 && !showAllCriteria.value;
});

// 计算属性，用于决定显示哪些搜索条件
const displayedSearchCriteria = computed(() => {
  if (showAllCriteria.value) {
    return searchCriteriaList.value;
  }
  // 假设我们只显示前3个，您可以根据需要调整这个数量
  return searchCriteriaList.value.slice(0, 3);
});

//
const toggleCriteriaDisplay = () => {
  showAllCriteria.value = !showAllCriteria.value;
};

//
const currentSpeaker = ref("");
const handleSpeakerClicked = (item) => {
  currentSpeaker.value = item.name;
};

// const speakerEmotionList = ref([]);
// const storeSearchCriteria = ref([]);
// const searchCriteriaList = ref([]);
// const searchSpeakerList = ref([]);

const getSearchSpeakers = () => {
  return searchSpeakers(queryParams).then((res) => {
    searchSpeakerList.value = res.data.results;
  });
};

/**
 *
 */
const currentTag = ref("热榜");
/**
 *
 */
const handleTagClicked = (value) => {
  currentTag.value = value.name;
};

//
// const searchContent = ref("");
const onSearchSpeaker = () => {
  // getSearchSpeakers();
  getSpeakerListApi(queryParams).then((res) => {
    speakerList.value = res.rows;
// console.log("拿到的结果", speakerList.value);
  });
};

//
const handleTagSelect = (tag, index) => {
// console.log(tag, index);
};

// 对话框点击确定后
const handleOk = () => {
  dialogShow.value = false;
};

const bottom = ref(false);

const list = ref();

onMounted(() => {
  if (list.value) {
// console.log("高度", list.value.offsetHeight); // 获取元素的高度
  }
});
/**
 *
 * @param event
 */
const handleScroll = async () => {
  bottom.value = false;
// console.log("符合条件");
  queryParams.pageNum++;

  getSpeakerListApi(queryParams).then((res) => {
    speakerList.value.push(...res.rows);
// console.log("拿到的结果", speakerList.value);
    bottom.value = true;
  });

  // await dubbingStore.searchSpeakers(queryParams, true);
};

const maxHeight = ref("calc(100vh - 50px)");

const placeholderText = computed(() => {
  return `共${total.value}款配音师，输入名称搜索`;
});
</script>

<template>
  <a-card class="dubbing-list-1" v-loading="loading">
    <!-- 搜索 -->
    <a-input
      class="dubbing-search"
      v-model="queryParams.name"
      :placeholder="placeholderText"
      allow-clear
      @input="onSearchSpeaker"
      @clear="onSearchSpeaker"
    />

    <!-- 配音员 -->
    <a-list
      class="speaker-list mt-2"
      style="width: 100%; padding-left: 3px"
      :gridProps="{ span: 6 }"
      :bordered="false"
      @reach-bottom="handleScroll"
      :scrollbar="true"
      :max-height="maxHeight"
    >
      <a-list-item
        class="d-flex align-items-center"
        style="padding: 0px"
        v-for="(item, index) in speakerList"
        :key="index"
        @click="onShowSpeakerInfo(item)"
      >
        <div class="speaker-item">
          <div class="speaker-img mt-3 circle" style="width: 40px; height: 40px">
            <img :src="item.headerImage" alt="" />
          </div>
          <div class="text-nowrap mt-1 text-center" style="font-size: 12px">
            <span>{{ item.name }}</span>
          </div>
        </div>
      </a-list-item>
      <template #scroll-loading>
        <div v-if="bottom">没有更多数据</div>
        <a-spin v-else />
      </template>
    </a-list>
  </a-card>

  <DubbingInfo v-model="dialogShow" :info="speakerInfo"></DubbingInfo>
</template>

<style scoped lang="scss">
$width: 400px;

.arco-list-wrapper .arco-spin .arco-scrollbar .arco-scrollbar-container {
  max-height: 700px !important;
}

.arco-tag {
  cursor: pointer;
}

.arco-tag.selected {
  color: red;
}

// .arco-list-wrapper {
//   ::deep .arco-list-content-wrapper {
//     max-height: 500px;
//     height: 500px;
//   }
// }

.dubbing-list-1 {
  height: 100%;
  max-width: $width;
  min-width: $width;
  padding: 0;
  background-color: #e4e8eb;


  .dubbing-search {
    margin: 0 3px;
    width: calc(100% - 10px);
  }

  .search-tag {
    display: flex;
    gap: 16px;
    margin-bottom: 15px;
    border-top: 1px solid #aeb4b7;
    padding-top: 15px;
    flex-wrap: wrap;
    .label {
      font-size: 12px;
    }
    li {
      border-radius: 2px;
      font-size: 12px;
      cursor: pointer;
      font-family: "微软雅黑";
      &.selected span {
        color: red;
        background: hsla(0, 76%, 43%, 0.2);
        border: 1px solid #fff;
      }
      span {
        padding: 3px 8px;
        border: 1px solid transparent;
      }
    }
  }
}

.speaker-list {
  .speaker-item {
    position: relative;
    width: 80px;
    height: 90px;
    // border-radius: 5px;
    // padding: 14px 10px;
    border: 1px solid #d3dee7;
    margin-bottom: 8px;

    // &.selected .speaker-img {
    //   border-radius: 50%;
    //   border: 2px solid #ffc900;
    // }

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

    .speaker-count {
      font-size: 12px;
      margin-top: 5px;
      text-align: center;
    }

    .speaker-style {
      font-size: 12px;
      margin-top: 5px;
      text-align: center;
      color: red;
    }

    .speaker-img {
      width: 40px;
      height: 40px;
      margin-bottom: 8px;
      cursor: pointer;
      // border: 2px solid transparent;
      vertical-align: middle;
      margin: 0 auto;

      img {
        -webkit-user-drag: none;
        width: 100%;
        height: 100%;
        border-radius: 50%;
        vertical-align: middle;
        text-align: center;
        border: none;
      }
    }

    .speaker-info {
      margin-top: 5px;
      font-size: 12px;
      font-family: "微软雅黑";
      text-align: center;
    }
  }
}
</style>
