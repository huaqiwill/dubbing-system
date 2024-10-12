import { defineStore } from 'pinia'

export const useLayoutStore = defineStore('layout', {
  state: () => {
    return {
      /**
       * 是否显示页脚
       */
      showFooter: true,
      /**
       * 是否显示侧边栏
       */
      showSidebar: true,
    }
  },
})
