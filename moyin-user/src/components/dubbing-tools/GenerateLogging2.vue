<template>
  <div>
    <a-button type="primary" status="danger" @click="onGeneraterLogging">
      <template #icon>
        <icon-upload />
      </template>
      生成记录
    </a-button>
    <el-dialog v-model="dialogVisible" title="生成记录">
      <div class="text-danger">只保存本设备的最近24小时记录</div>
      <el-table v-loading="loading" :data="ttsRecordList" style="width: 100%; margin-top: 15px">
        <el-table-column prop="createTime" label="生成时间" width="180" />
        <el-table-column prop="speaker" label="生成音色" width="180" />
        <el-table-column prop="text" label="生成内容" />
        <el-table-column fixed="right" label="操作" width="160" align="center">
          <template #default="scope">
            <!-- <el-button type="success" @click="onRecory(scope.row)">恢复</el-button> -->
            <el-button type="danger" @click="onDelete(scope.row)">删除</el-button>
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
  </div>
</template>
<style lang="scss" scoped></style>
<script setup>
// 生成记录 v2
import { onBeforeUpdate, onMounted, onUpdated, reactive, ref } from 'vue'
import { DubbingButton } from '@/components'
import { ttsRecordListApi, ttsRecordDelete } from '@/api/tts'
import { ElMessage, ElMessageBox } from 'element-plus'
import { IconUpload } from '@arco-design/web-vue/es/icon'

onMounted(() => {})

const dialogVisible = ref(false)
const ttsRecordList = ref([])
const loading = ref(false)
const pageTotal = ref(0)
const queryParams = reactive({
  pageSize: 10,
  pageNum: 1,
})

function onGeneraterLogging(params) {
  getTtsRecordList()
  dialogVisible.value = true
}

function getTtsRecordList() {
  loading.value = true
  ttsRecordListApi(queryParams).then((res) => {
// console.log(res)
    pageTotal.value = res.total
    ttsRecordList.value = res.rows
    loading.value = false
  })
}

function onRecory(row) {
  ElMessageBox.confirm('确认恢复吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    ElMessage({
      message: '恢复成功',
      type: 'success',
    })
    dialogVisible.value = false
  })
}

function onDelete(row) {
  ElMessageBox.confirm('确认删除吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    ttsRecordDelete(row.id).then((res) => {
      ElMessage({
        message: '删除成功',
        type: 'success',
      })
      getTtsRecordList()
    })
  })
}

function handleSizeChange(size) {
  queryParams.pageSize = size
  getTtsRecordList()
}

function handleCurrentChange(num) {
  queryParams.pageNum = num
  getTtsRecordList()
}
</script>
