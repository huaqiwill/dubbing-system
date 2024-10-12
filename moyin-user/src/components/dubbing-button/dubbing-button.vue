<script setup>
import { SvgIcon } from "@/components";
import { ref, computed } from "vue";

const props = defineProps({
  title: {
    type: String, // 参数类型
    default: "按钮", //默认值
    required: true, //是否必传
  },
  content: {
    type: String, // 参数类型
    default: undefined,
  },
  icon: {
    type: String, // 参数类型
    default: "play", //默认值
    required: true, //是否必传
  },
  placement: {
    type: String,
    default: "bottom-start",
  },
});

const hasContent = ref(true);

const emit = defineEmits(["click"]);

const handleAudition = () => {
  emit("click");
};
</script>

<template>
  <div class="tool-container">
    <div v-if="hasContent" class="tool-item" @click="handleAudition">
      <SvgIcon :name="icon"></SvgIcon>
      <div class="name">{{ title }}</div>
    </div>
    <el-popover
      v-else
      :placement="placement"
      trigger="hover"
      :content="content"
      effect="light"
    >
      <template #reference>
        <!-- <a-button type="primary" @click="handleAudition">
          <SvgIcon :name="icon"></SvgIcon>
          <div class="name">{{ title }}</div>
        </a-button> -->
        <div class="tool-item" @click="handleAudition">
          <SvgIcon :name="icon"></SvgIcon>
          <div class="name">{{ title }}</div>
        </div>
      </template>
    </el-popover>
  </div>
</template>

<style lang="scss" scoped>
.tool-container {
  .tool-item {
    cursor: pointer;
    width: 55px;
    height: 55px;
    padding: 2px 0;
    border-radius: 3px;

    .icon {
      display: block;
      width: 22px;
      height: 22px;
      color: black;
      font-weight: 800;
      margin: 0 auto;
      margin-top: 6px;
    }

    .name {
      margin-top: 8px;
      color: #666666;
      font-size: 12px;
      line-height: 12px;
      user-select: none;
      display: block;
      text-align: center;
      font-weight: 400;
    }

    &:hover {
      background-color: #e5e5e5;
    }
  }
}
</style>
