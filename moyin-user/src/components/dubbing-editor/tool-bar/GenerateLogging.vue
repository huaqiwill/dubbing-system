<template>
  <!-- 生成记录 -->
  <DubbingButton
    title="生成记录"
    content="从光标处开始试听"
    icon="log"
    @click="handleClicked"
  />

  <el-dialog v-model="dialogVisible" :title="生成记录">
    <p>只保存本设备的最近24小时记录</p>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="date" label="Date" width="180" />
      <el-table-column prop="name" label="Name" width="180" />
      <el-table-column prop="address" label="Address" />
    </el-table>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import {  DubbingButton } from "@/components";

const dialogVisible = ref(false);

const handleClicked = () => {
  dialogVisible.value = true;
};

const tableData = [
  {
    date: "2016-05-03",
    name: "Tom",
    address: "No. 189, Grove St, Los Angeles",
  },
  {
    date: "2016-05-02",
    name: "Tom",
    address: "No. 189, Grove St, Los Angeles",
  },
  {
    date: "2016-05-04",
    name: "Tom",
    address: "No. 189, Grove St, Los Angeles",
  },
  {
    date: "2016-05-01",
    name: "Tom",
    address: "No. 189, Grove St, Los Angeles",
  },
];
</script>

<style lang="scss" scoped></style>
