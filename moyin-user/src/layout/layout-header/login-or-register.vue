<template>
  <el-dialog v-model="dialogShow" width="500">
    <div class="cont-tab">
      <div @click="handleLogin" :class="loginSelected">用户登录</div>
      <div @click="handleRegister" :class="registerSelected">用户注册</div>
    </div>
    <el-card class="card">
      <el-form label-width="auto" :model="form" :rules="formRules">
        <el-form-item size="large">
          <el-input :prefix-icon="User" v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item size="large">
          <el-input :prefix-icon="Lock" v-model="form.password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item size="large">
          <el-input :prefix-icon="Key" v-model="form.code" placeholder="请输入验证码">
            <template #append>
              <el-button>获取验证码</el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item size="large">
          <el-button type="primary" class="full-width" @click="login" v-if="isLogin">
            登录
          </el-button>
          <el-button type="primary" class="full-width" @click="register" v-else> 注册 </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <template #footer>
      <div class="dialog-footer">
        登录即同意
        <a href="" @click.prevent="userProtocol">《用户服务协议》</a>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, watch, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { User, Lock, Key } from '@element-plus/icons-vue'

const userProtocol = () => {
  ElMessage('用户协议')
}

// 属性
const props = defineProps({
  show: Boolean,
})

const dialogShow = ref(props.show)
watch(
  () => props.show,
  (newValue) => {
    dialogShow.value = newValue
  },
)

const isLogin = ref(true)

const handleLogin = () => {
  isLogin.value = true
}

const handleRegister = () => {
  isLogin.value = false
}

const login = () => {
  ElMessage('登录')
}

const form = reactive({
  username: '',
  password: '',
  code: '',
})

const formRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  code: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
}

const register = () => {
  ElMessage('注册')
}

// TabBar
const userLoginActiveName = ref('login')
const handleUserLoginTabClick = (tab, event) => {
// console.log(tab, event)
}

const loginSelected = computed(() => {
  return isLogin.value ? 'selected' : ''
})

const registerSelected = computed(() => {
  return isLogin.value ? '' : 'selected'
})
</script>

<style scoped lang="scss">
.el-dialog__body {
  margin: 10px 20px;
}

.cont-tab {
  display: flex;
  flex-direction: row;
  justify-content: center;

  div {
    width: 50%;
    text-align: center;
    padding: 20px;
    font-size: 20px;
    cursor: pointer;
  }

  .selected {
    border-bottom: 2px solid #409eff;
    color: #409eff;
  }
}

.el-card {
  border: none !important;
  margin-top: 10px;
  background-color: #ffffff;
  box-shadow: none;
  height: 250px;

  .el-card__body {
    padding-bottom: 0 !important;

    button {
      margin-bottom: 0;
    }
  }
}

.full-width {
  width: 100%;
}

.dialog-footer {
  text-align: center;
  font-size: 12px;

  a {
    color: #409eff;
  }
}
</style>
