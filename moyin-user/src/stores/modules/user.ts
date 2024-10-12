import { defineStore } from 'pinia'
import { userGetInfoApi, login as userLogin, logout as userLogout } from '@/api/login'
import { setToken, removeToken } from '@/utils/auth'
import { ref } from 'vue'
import router from '@/router'

export const useUserStore = defineStore('user', () => {
  let token = ''
  let isLogin = false
  const userInfo = ref<any>(null)

  return {
    userInfo,

    isLogin() {
      return isLogin
    },
    getToken() {
      return token
    },
    getUserInfo() {
      return userInfo.value
    },
    async login(loginForm: object) {
      return await userLogin(loginForm).then((res: any) => {
        setToken(res.token)
        router.push({ path: '/' }).catch(() => {})
        isLogin = true
        token = res.token
        userGetInfoApi().then((res: any) => {
          userInfo.value = res.user
// console.log(userInfo.value)
        })
      })
    },
    logout() {
      userLogout().then(() => {
        isLogin = false
        token = ''
        removeToken()
        router.push({
          name: 'login',
        })
      })
    },
  }
})
