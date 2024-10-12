<template>
  <div class="login-container">
    <el-card>
      <h2 class="title">用户注册</h2>
      <el-form label-width="auto" :model="form" :rules="formRules">
        <el-form-item size="large">
          <el-input :prefix-icon="User" v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item size="large">
          <el-input
            :prefix-icon="User"
            v-model="form.username"
            placeholder="请输入用户名"
          />
        </el-form-item>
        <el-form-item size="large">
          <el-input
            :prefix-icon="Lock"
            v-model="form.password"
            placeholder="请输入密码"
          />
        </el-form-item>
        <el-form-item size="large">
          <el-input :prefix-icon="Key" v-model="form.code" placeholder="请输入验证码">
            <template #append>
              <el-button>获取验证码</el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item size="large">
          <el-button
            type="primary"
            class="full-width"
            @click="handleRegister"
            style="width: 100%"
          >
            注册
          </el-button>
        </el-form-item>
        <el-form-item size="large">
          <div class="login-footer">
            <div>没有账号？<a href="" @click.prevent="handleLogin">立即登录</a></div>
            <div>
              注册即同意
              <a href="" @click.prevent="handleUserProtocol">《用户服务协议》 </a>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, watch, computed, defineProps } from "vue";
import { ElMessage } from "element-plus";
import { User, Lock, Key } from "@element-plus/icons-vue";
import { useRoute, useRouter } from "vue-router";
import { getUserProtocol } from "@/api";

const route = useRoute();
const router = useRouter();

/**
 * 显示用户协议
 */
const handleUserProtocol = () => {
  getUserProtocol().then((res) => {
    ElMessage(res.data);
  });
};

const props = defineProps({
  show: Boolean,
});

const dialogShow = ref(props.show);
watch(
  () => props.show,
  (newValue) => {
    dialogShow.value = newValue;
  }
);

const isLogin = ref(true);

/**
 * 注册
 */
const handleRegister = () => {
  ElMessage({
    message: "用户注册",
  });
};

/**
 * 跳转到登录页面
 */
const handleLogin = () => {
  router.push({
    name: "login",
  });
};

const form = reactive({
  email: "",
  username: "",
  password: "",
  code: "",
});

const formRules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
};

// TabBar
const userLoginActiveName = ref("login");
const handleUserLoginTabClick = (tab, event) => {
// console.log(tab, event);
};
</script>

<style scoped lang="scss">
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  background-color: #eee;

  .title {
    margin-bottom: 15px;
  }

  .login-footer {
    display: flex;
    justify-content: space-between;
    div:nth-child(1) {
      margin-right: 120px;
    }
  }

  .el-card {
    width: 500px;
  }
}
</style>
