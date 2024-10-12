<template>
  <a-dropdown v-if="isLogin" trigger="hover">
    <div class="login-in d-flex flex-row me-2">
      <img class="rounded-circle" :src="avatar" alt="" />
      <div class="ms-1">
        <div class="">
          <span v-if="userInfo">{{ userInfo.nickName }}</span>
          <span v-else>也不凡</span>
          <!-- <el-tag class="ms-1">包终生VIP</el-tag> -->
        </div>
        <!-- <div class="mt-1">到期时间：永久</div> -->
      </div>
    </div>
    <template #content>
      <!-- <a-doption @click="handleAccount">我的账户</a-doption> -->
      <!-- <a-doption>绑定手机号 15576364885</a-doption> -->
      <!-- <a-doption @click="handleLayout">布局管理</a-doption> -->
      <a-doption @click="handleLogout" style="padding: 0 28px">退出登录</a-doption>
    </template>
  </a-dropdown>
  <div class="d-flex align-items-center me-2" v-else>
    <a-button type="primary" @click="handleLogin">登录</a-button>
  </div>
</template>

<style lang="scss" scoped>
.login-in {
  align-items: center;
  padding: 0 10px;
  margin: 10px 0;
  img {
    width: 44px;
    height: 44px;
    display: block;
    line-height: 44px;
  }
  &:hover {
    box-shadow: 0 10px 30px 0 rgba(12, 43, 66, 0.1);
  }
}
</style>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import { getToken } from '@/utils/auth.ts'
import { userGetInfoApi } from '@/api/login'
import { ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const userInfo = ref<any>(null)
const isLogin = ref(false)
const router = useRouter()
const avatar = ref('')

onMounted(() => {
  if (getToken()) {
    isLogin.value = true
    userGetInfoApi().then((res: any) => {
      userInfo.value = res.user
      if (res.user.avatar) {
        avatar.value = '/prod-api' + res.user.avatar
      } else {
        avatar.value = 'https://peiyinshenqi-1254161078.cos.ap-shanghai.myqcloud.com/avatar/mFcMn2'
      }
      // console.log('用户信息11：', userInfo.value)
    })
  }
})

const handleAccount = () => {
  // router.push({
  //   name: "profile",
  // });
  ElMessage({
    message: '暂不需要',
  })
}

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    userStore.logout()
    isLogin.value = false
  })
}

const handleLayout = () => {
  ElMessage({
    message: '暂不需要',
  })
}

function handleLogin() {
  router.push({
    name: 'login',
  })
}
</script>
