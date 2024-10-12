<template>
  <!-- 批量纠音 -->
  <DubbingButton
    title="批量纠音"
    content="从光标处开始试听"
    icon="BatchSorrection"
    @click="onClicked"
  />
  <el-dialog v-model="dialogShow" title="多音字检测" draggable>
    <div class="">点击要修改发音的汉字，选择想要的发音</div>
    <div class="mt-2">若有多音字发音不对，可输入发音相同的字替代</div>
    <el-input class="mt-2"></el-input>
    <template #footer>
      <a-button type="primary" class="" @click="onCancel">取消</a-button>
      <a-button type="primary" class="ms-2" @click="onOk">确定</a-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import { DubbingButton } from "@/components";

const dialogShow = ref(false);

// 批量纠音按钮
const onClicked = () => {
  dialogShow.value = true;
};

// 取消
const onCancel = () => {
  dialogShow.value = false;
};

// 确定
const onOk = () => {
  dialogShow.value = false;
};
</script>

<style lang="scss" scoped>

.stress {
  ul {
    li {
      padding: 10px 0;
    }
  }
}
</style>
