import type { FilterSpeaker, LabelValue } from '@/model'
import type { IEditorConfig } from '@wangeditor/editor'
import type { FilterBarSearch } from '@/components/bar-search'
import type { Speaker } from '@/model'
import { defaultAudioInfo, type AudioInfo } from '@/menu/conversion-menu/data'
import type { CancellationToken } from '@/utils'
import { defaultRecentUsageSpeaker, type RecentUsageSpeaker } from '@/menu/management-menu/data'
import { emitter } from '@/event-bus'
import mergeWith from 'lodash.mergewith'
// import {} from '@/api'

type Animation = { zoom: boolean; grayscale: boolean }
type FetchFunction = () => Promise<LabelValue[]>
type WordFetchFunction = (word: string) => Promise<LabelValue[]>
type FilterFetchFunction = (filter: FilterBarSearch) => Promise<LabelValue[]>
type FilterSpeakerFetchFunction = (filter: FilterSpeaker) => Promise<Speaker[]>

type PartialKey =
  | 'animation'
  | 'bgm'
  | 'special'
  | 'tryPlay'
  | 'conversion'
  | 'management'
  | 'editorConfig'
type PartialProps<T, K extends keyof T> = { [P in K]?: Partial<T[P]> }

export type PartialSSMLEditorConfig = Partial<Omit<SSMLEditorConfig, PartialKey>> &
  PartialProps<SSMLEditorConfig, PartialKey>

export interface SSMLEditorConfig {
  animation: Animation
  editorConfig: Partial<IEditorConfig> & {
    saveHtml?: (htmlGetter: () => string) => Promise<boolean>
    readHtml?: () => Promise<string | null>
    autoSave: boolean
    autoSaveWait: number
  }
  handleWarn: (message: string) => void
  handleError: (error: unknown) => void
  pinyin: {}
  english: { fetchData: WordFetchFunction }
  bgm: {
    menus: LabelValue[]
    fetchScene: FetchFunction
    fetchStyle: FetchFunction
    fetchData: FilterFetchFunction
  }
  special: {
    menus: LabelValue[]
    fetchScene: FetchFunction
    fetchStyle: FetchFunction
    fetchData: FilterFetchFunction
  }
  tryPlay: {
    play: (ssmlGetter: () => string) => Promise<AudioInfo>
    gender: LabelValue[]
    topFlag: LabelValue[]
    category: LabelValue[]
    fetchData: FilterSpeakerFetchFunction
    featchTag: FetchFunction
    fetchStar: (speaker: string, star: boolean) => Promise<boolean>
    selectSpeaker?: (speaker: Speaker, setter: (speaker: Speaker) => void) => void
  }
  conversion: {
    timeoutMilliseconds: number
    audioUpload: (file: File, token: CancellationToken) => Promise<AudioInfo>
    transfer: (opt: { audioId: string; speakerId: string }) => Promise<AudioInfo>
    fetchSpeaker: () => Promise<Speaker[]>
  }
  management: {
    recordRecentUsage: (recentUsage: RecentUsageSpeaker) => Promise<RecentUsageSpeaker>
    fetchRecentUsage: () => Promise<RecentUsageSpeaker[]>
    deleteRecentUsage: (id?: string) => Promise<void>
  }
}

function resolveList() {
  return () => Promise.resolve([])
}

function defaultSSMLEditorConfig(): SSMLEditorConfig {
  return {
    animation: { zoom: true, grayscale: true },
    editorConfig: { placeholder: '请输入内容...', autoSave: true, autoSaveWait: 5000 },
    handleWarn: (message) => console.warn(message),
    handleError: (error) => console.error(error),
    pinyin: {},
    english: { fetchData: resolveList() },
    bgm: {
      menus: [
        { label: '默认音效', value: '' },
        { label: '自定义音效', value: 'custom' },
        { label: '最近音效', value: 'history' },
      ],
      fetchScene: resolveList(),
      fetchStyle: resolveList(),
      fetchData: resolveList(),
    },
    special: {
      menus: [
        { label: '默认音效', value: '' },
        { label: '自定义音效', value: 'custom' },
        { label: '最近音效', value: 'history' },
      ],
      fetchScene: resolveList(),
      fetchStyle: resolveList(),
      fetchData: () => {
        return new Promise((resolve, reject) => {})
      },
    },
    conversion: {
      timeoutMilliseconds: 20000,
      audioUpload: () => Promise.resolve(defaultAudioInfo()),
      transfer: () => Promise.resolve(defaultAudioInfo()),
      fetchSpeaker: resolveList(),
    },
    management: {
      recordRecentUsage: () => Promise.resolve<RecentUsageSpeaker>(defaultRecentUsageSpeaker()),
      fetchRecentUsage: resolveList(),
      deleteRecentUsage: () => Promise.resolve(),
    },
    tryPlay: {
      play: () => Promise.resolve(defaultAudioInfo()),
      fetchData: resolveList(),
      featchTag: resolveList(),
      fetchStar: () => Promise.resolve(true),
      gender: [
        { label: '全部', value: '' },
        { label: '男声', value: 'Male' },
        { label: '女声', value: 'Female' },
      ],
      topFlag: [
        { label: '热榜', value: '' },
        { label: 'SVIP', value: 'SVIP' },
        { label: '付费', value: '付费' },
      ],
      category: [
        { label: '常用', value: '常用' },
        { label: '已购', value: '已购' },
        { label: '收藏', value: '收藏' },
        { label: '我的', value: '我的' },
      ],
    },
  }
}

function mergeSSMLEditorConfig(dest: SSMLEditorConfig, src: PartialSSMLEditorConfig) {
  return mergeWith(dest, src, (destValue, srcValue) => {
    if (Array.isArray(destValue) && Array.isArray(srcValue)) return srcValue
  }) as SSMLEditorConfig
}

const configList: Record<symbol, SSMLEditorConfig> = {}

export function setConfig(key: symbol, partConfig?: PartialSSMLEditorConfig) {
  if (configList[key]) return
  const defaultConfig = defaultSSMLEditorConfig()
  const ssmlEditorConfig = partConfig
    ? mergeSSMLEditorConfig(defaultConfig, partConfig)
    : defaultConfig
  emitter.on('error', ssmlEditorConfig.handleError)
  emitter.on('warn', ssmlEditorConfig.handleWarn)
  configList[key] = ssmlEditorConfig
}

export function getConfig(key: symbol) {
  return configList[key]
}
