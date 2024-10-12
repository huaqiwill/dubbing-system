<script setup lang="ts">
import { ref } from "vue";
import TryPlayDialog from "./try-play-dialog.vue";
import LeftPanle from "./left-panle.vue";
import RightPanle from "./right-panle.vue";
import { useDubbingStore } from "@/stores";
import { storeToRefs } from "pinia";
import { emitter } from "@/event-bus";

const dubbingStore = useDubbingStore();
const panleVisible = ref(false);
const searchName = ref("");

const { speakerListAll } = storeToRefs(dubbingStore);

function handleInput() {
  const { speakerListAllBackup } = dubbingStore;
  speakerListAll.value = [...speakerListAllBackup];
  if (searchName.value !== "") {
    speakerListAll.value = speakerListAll.value.filter((speaker: any) =>
      speaker.name.includes(searchName.value)
    );
  }
  emitter.emit("speaker:loading:ok");
}
</script>

<template>
  <TryPlayDialog v-model:visible="panleVisible">
    <div class="try-play-left w-100">
      <div class="d-flex">
        <LeftPanle></LeftPanle>
        <RightPanle></RightPanle>
      </div>
      <div class="d-flex mt-1">
        <a-input
          v-model="searchName"
          placeholder="共663款配音师，输入名称搜索"
          @input="handleInput"
        ></a-input>
        <a-button type="primary" class="ms-1">最近</a-button>
        <a-button type="primary" class="ms-1">收藏</a-button>
      </div>
    </div>
  </TryPlayDialog>
</template>

<style lang="scss" scoped></style>
