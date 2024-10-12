<template>
  <!-- 情绪 -->
  <div class="emotion-list d-flex flex-wrap mt-3">
    <div
      class="emotion-item mt-1 d-flex flex-column align-items-center"
      :class="{ selected: selectedEmotionId == '' }"
      @click="handleDefaultEmotionSelect()"
    >
      <div class="emotion-image">
        <img :src="headerImage" />
      </div>
      <div>默认</div>
    </div>
    <div
      v-for="(item, index) in emotions"
      :key="index"
      class="emotion-item mt-1 d-flex flex-column align-items-center"
      :class="{ selected: selectedEmotionId == item.id }"
      @click="handleEmotionSelect(item)"
    >
      <div class="emotion-image">
        <img :src="item.imageUrl" />
      </div>
      <div>{{ item.name }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {  ref } from "vue";

defineProps({
  emotions: {
    type: Object,
  },
  headerImage: {
    type: String,
  },
});

const selectStyleCallName = ref(null);
const speakerEmotionList = ref([]);
const selectedEmotionId = ref("");

function handleSelect(id: number) {
  let speakerInfoList = speakerEmotionList.value.filter(
    (speakerEmotion: any) => speakerEmotion.emotionId == id
  );
  if (speakerInfoList.length == 0) {
    return;
  }
  let speakerInfo: any = speakerInfoList[0];
  selectStyleCallName.value = speakerInfo.styleCallName;
}

function handleDefaultEmotionSelect() {
  handleSelect(0);
  selectedEmotionId.value = "";
}

function handleEmotionSelect(item: any) {
  handleSelect(item.id);
  selectedEmotionId.value = item.id;
}
</script>

<style lang="scss" scoped>
$image-width: 32px;
$image-height: 32px;

.emotion-list {
  .emotion-item {
    margin-right: 15px;

    &.selected .emotion-image {
      border: 3px solid #fbc703;
    }

    .emotion-image {
      overflow: hidden;
      width: $image-width;
      height: $image-height;
      overflow: hidden;
      border-radius: 50%;
      border: 3px solid transparent;
      text-align: center;
      vertical-align: center;
      cursor: pointer;

      img {
        scale: 1.2;
        width: $image-width;
        height: $image-height;
      }
    }
  }
}
</style>
