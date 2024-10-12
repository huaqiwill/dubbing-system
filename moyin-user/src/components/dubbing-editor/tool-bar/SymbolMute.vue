<template>
  <!-- 符号停顿 -->
  <DubbingButton
    title="符号停顿"
    content="从光标处开始试听"
    icon="symbol"
    @click="handleClicked"
  />

  <el-dialog v-model="dialogShow" title="符号静音">
    <div>
      <h2>智能分句模式</h2>
      <p>
        开启：智能分句开启后，将使用更自然真人的高阶模型算法，极大的提升发音人的自然度，更加媲美真人。
      </p>
      <p>关闭：关闭后，将优先根据标点符号进行分句，可精准控制标点符号断句时长。</p>
      <p>注意：符号静音设置为全局设置，改动后全局生效</p>
    </div>
    <el-table :data="tableData" stripe>
      <el-table-column prop="symbol" label="符号" width="180" />
      <el-table-column prop="name" label="名称" width="180" />
      <el-table-column prop="muteDuration" label="静音时长" />
      <el-table-column label="操作">
        <el-button>恢复默认</el-button>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import {  DubbingButton } from "@/components";

const tableData = [
  {
    symbol: "，",
    name: "逗号",
    muteDuration: "200",
  },
  {
    symbol: "。",
    name: "句号",
    muteDuration: "200",
  },
  {
    symbol: "；",
    name: "分号",
    muteDuration: "200",
  },
  {
    symbol: "！",
    name: "感叹号",
    muteDuration: "200",
  },
  {
    symbol: "？",
    name: "问号",
    muteDuration: "200",
  },
];

const handleClicked = () => {
  dialogShow.value = true;
};

const dialogShow = ref(false);
</script>

<style lang="scss" scoped>
.popper-class {
  min-width: 20px !important;
  width: auto;
}

.polyphone {
  display: flex;
}

ul {
  background-color: #fff;
  display: flex;
  gap: 10px;

  li {
    border: 1px solid #e5e5e5;
    padding: 4px 10px;
    border-radius: 4px;
    cursor: pointer;

    &:hover {
      color: #6aa4fc;
      background-color: #e8f1fe;
    }
  }
}
</style>
