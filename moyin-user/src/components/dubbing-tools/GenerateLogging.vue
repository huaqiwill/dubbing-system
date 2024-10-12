<script setup>
import { onMounted, reactive, ref } from "vue";
import { DubbingButton } from "@/components";
import { ttsRecordListApi, ttsRecordDelete } from "@/api/tts";
import { ElMessage, ElMessageBox } from "element-plus";

const dialogVisible = ref(false);

const handleClicked = () => {
  getTtsRecordList();
  dialogVisible.value = true;
};

/**
 * 数据列表
 */
const tableData = ref([]);
/**
 * 查询参数
 */
const queryParams = reactive({
  pageSize: 10,
  pageNum: 1,
});
/**
 * 获取日志记录
 */
const getTtsRecordList = () => {
  loading.value = true;
  ttsRecordListApi(queryParams).then((res) => {
// console.log(res);
    pageTotal.value = res.total;
    tableData.value = res.rows;
    loading.value = false;
  });
};

/**
 * 点击删除按钮事件
 * @param row
 */
const onDelete = (row) => {
  ElMessageBox.confirm("确认删除吗？", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    ttsRecordDelete(row.id).then((res) => {
      ElMessage({
        message: "删除成功",
        type: "success",
      });
      getTtsRecordList();
    });
  });
};

/**
 * 是否加载中
 */
const loading = ref(false);

onMounted(() => {});

/** 
 * 分页总数
 */
const pageTotal = ref(0);
/**
 *
 * @param size
 */
const handleSizeChange = (size) => {
  queryParams.pageSize = size;
  getTtsRecordList();
};
/**
 *
 * @param num
 */
const handleCurrentChange = (num) => {
  queryParams.pageNum = num;
  getTtsRecordList();
};
</script>
<template>
  <!-- 生成记录 -->
  <DubbingButton
    title="生成记录"
    content="从光标处开始试听"
    icon="log"
    @click="handleClicked"
  />
  <el-dialog v-loading="loading" v-model="dialogVisible" title="生成记录">
    <div class="text-danger">只保存本设备的最近24小时记录</div>
    <el-table :data="tableData" style="width: 100%; margin-top: 15px">
      <el-table-column prop="createTime" label="生成时间" width="180" />
      <el-table-column prop="speaker" label="生成音色" width="180" />
      <el-table-column prop="text" label="生成内容" />
      <el-table-column fixed="right" label="操作" width="120" align="center">
        <template #default="scope">
          <el-button type="primary" @click="onDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="mt-4 d-flex justify-content-end">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="pageTotal"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </el-dialog>
</template>
<style lang="scss" scoped></style>
