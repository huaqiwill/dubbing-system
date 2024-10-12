<template>
  <div class="header">
    <a-menu mode="horizontal" :default-selected-keys="['开始配音']" class="menu-container">
      <a-menu-item key="0" :style="{ padding: 0, marginRight: '38px', cursor: 'pointer' }" disabled>
        <Logo></Logo>
      </a-menu-item>
      <a-menu-item v-for="menu in menus" :key="menu.name">
        <router-link :to="menu.path">{{ menu.name }}</router-link>
      </a-menu-item>
    </a-menu>

    <!-- <div class="menu-container">
      <ul
        default-active="开始配音"
        class="menu-list"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
      >
        <li class="menu-item selected" v-for="menu in menus" :key="menu.name">
          <router-link :to="menu.path">{{ menu.name }}</router-link>
        </li>
      </ul>
    </div> -->
    <a-dropdown v-if="isLogin" trigger="hover">
      <div class="login-in d-flex flex-row me-2">
        <img
          src="https://peiyinshenqi-1254161078.cos.ap-shanghai.myqcloud.com/avatar/mFcMn2"
          alt=""
        />
        <div class="ms-1">
          <div class="">
            <span>也不凡</span>
            <el-tag class="ms-1">包终生VIP</el-tag>
          </div>
          <div class="mt-1">到期时间：永久</div>
        </div>
      </div>
      <template #content>
        <a-doption @click="handleAccount">我的账户</a-doption>
        <a-doption>绑定手机号 15576364885</a-doption>
        <a-doption @click="handleLayout">布局管理</a-doption>
        <a-doption @click="handleLogout">退出登录</a-doption>
      </template>
    </a-dropdown>
    <a-space class="me-3" v-else>
      <a-link href="/login">登录</a-link>
      <a-link href="/register">注册</a-link>
    </a-space>

    <!-- 
    <div class="login-container">
      <el-button type="primary" class="login" link v-if="isLogin">
        <router-link to="/login">登录</router-link>
      </el-button>
      <el-button type="primary" class="login" link size="large" v-if="isLogin">
        <router-link to="/register">注册</router-link>
      </el-button>

      <el-dropdown style="margin-top: 25px" v-else>
        <el-avatar
          :size="50"
          src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
        />
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div> -->
    <!-- <LoginOrRegister :show="loginOrRegisterDialogShow"></LoginOrRegister> -->
  </div>
</template>

<style scoped lang="scss">
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

.header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  // border-bottom: 1px solid #dcdfe6;

  .menu-container {
    width: calc(100% - 200px);
    height: 100%;

    .menu-list {
      display: flex;
      gap: 20px;
      align-items: center;
      margin-left: 20px;

      .menu-item {
        &:hover a {
          color: red;
        }

        &.selected a {
          color: blue;
        }
      }

      .contact-item {
        position: relative;

        &:hover .contact-image {
          display: block;
        }

        .contact-image {
          position: absolute;
          display: none;
          width: 200px;
          height: auto;
          top: 60px;
          left: -50%;
          z-index: 999;

          img {
            width: 100%;
            height: 100%;
          }
        }
      }
    }
  }

  .login-container {
    display: flex;
    align-items: center;

    .login {
      cursor: pointer;
      user-select: none;
      align-self: center;
    }

    .el-avatar {
      margin-right: 10px;
    }
  }
}
</style>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { Logo } from './index'
import { useUserStore } from '@/stores'
import { ElMessageBox } from 'element-plus'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { userGetInfoApi } from '@/api/login'

const router = useRouter()
const userStore = useUserStore()
const { isLogin } = storeToRefs(userStore)
const userInfo = ref(null)
const menus = [
  {
    path: '/dubbing',
    name: '开始配音',
  },
  {
    path: '/member',
    name: '购买会员',
  },
  {
    path: '/contact',
    name: '联系我们',
  },
]

onMounted(() => {
  userGetInfoApi().then((res: any) => {
    userInfo.value = res.data
// console.log(userInfo)
  })
})

// 退出登录
function handleLogout() {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    userStore.logout()
    isLogin.value = false
  })
}

// 账户管理
function handleAccount() {
  router.push({
    name: 'profile',
  })
}

//布局管理
const handleLayout = () => {}
</script>
