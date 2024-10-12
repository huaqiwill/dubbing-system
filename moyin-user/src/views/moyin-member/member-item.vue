<script setup>
import { onMounted } from 'vue'
import { ElMessageBox } from 'element-plus'

onMounted(() => {
// console.log(props)
})

const props = defineProps({
  data: {
    type: Object,
    default: () => {
      return {}
    },
  },
})

const handleBuy = () => {
  ElMessageBox.alert('测试中... 会员暂不可购买', '提示', {
    confirmButtonText: '确定',
    callback: (action) => {
      if (action === 'confirm') {
        //
      }
    },
  })
}
</script>
<template>
  <div class="member-item">
    <div class="member-item-box">
      <div class="item-line">
        <div class="item-title">{{ data.name }}</div>
      </div>
      <div class="item-price">￥ {{ data.price }} 元</div>
      <div class="item-btn" @click="handleBuy">立即购买</div>
    </div>
    <ul class="member-desc-list">
      <li class="member-desc-item" v-for="(item, index) in data.descItems" :key="index">
        <div class="icon-item">
          <img :src="item.img" alt="" />
        </div>
        <div class="item-desc-container">
          <div class="item-title">{{ item.name }}</div>
          <div class="item-desc">{{ item.desc }}</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<style lang="scss" scoped>
.member-item {
  .member-item-box {
    border: 2px solid #e5e7e9;
    margin-bottom: 20px;
    margin-top: 20px;
    border-radius: 5px;

    &:hover {
      border: 2px solid #f6ba8e;
    }

    .item-line {
      border-bottom: 2px solid #e5e7e9;

      .item-title {
        margin: 10px 20px;
        font-size: 18px;
        font-weight: 400;
      }
    }

    .item-price {
      margin: 0 20px;
      font-size: 24px;
      font-weight: 400;
      margin-top: 10px;
    }

    .item-btn {
      margin: 0 20px;
      margin-top: 10px;
      color: #fff;
      background-color: #c78067;
      padding: 5px 10px;
      border-radius: 5px;
      text-align: center;
      margin-bottom: 15px;
      cursor: pointer;
      font-size: 14px;
    }
  }

  .member-desc-list {
    .member-desc-item {
      display: flex;
      margin-bottom: 15px;

      .icon-item {
        width: 32px;
        height: 32px;

        img {
          width: 100%;
          height: 100%;
        }
      }

      .item-desc-container {
        margin-left: 10px;

        .item-title {
          font-size: 16px;
          color: #333333;
        }

        .item-desc {
          font-size: 12px;
          color: #888888;
        }
      }
    }
  }
}
</style>
