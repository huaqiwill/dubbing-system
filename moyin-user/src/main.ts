import '@wangeditor/editor/dist/css/style.css'
import 'highlight.js/styles/atom-one-dark.css'
import './assets/main.scss'

import hljs from 'highlight.js/lib/core'
import xml from 'highlight.js/lib/languages/javascript'
import hljsVuePlugin from '@highlightjs/vue-plugin'

hljs.registerLanguage('xml', xml)

import { createApp } from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'
import router from '@/router'
import 'virtual:svg-icons-register'

// element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// Arco
import ArcoVue from '@arco-design/web-vue'
import '@arco-design/web-vue/dist/arco.css'

const pinia = createPinia()
const app = createApp(App)

//
app.use(hljsVuePlugin)
app.use(router)
app.use(ArcoVue)
app.use(pinia)
app.use(ElementPlus)
app.mount('#app')
