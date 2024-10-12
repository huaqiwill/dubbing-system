import type { LabelValue } from './label-value'

export interface Speaker {
  id: string
  name: string
  displayName: string
  category: string
  avatar: string
  isFree: boolean
  isStar: boolean
  isSupper24K: boolean
  roles: (LabelValue & { avatar?: string; emoji?: string })[]
  styles: (LabelValue & { avatar?: string; emoji?: string })[]

  //
  headerImage: string
}

export function defaultSpeaker(): Speaker {
  return {
    id: '',
    category: '',
    avatar: '',
    isFree: false,
    isStar: false,
    isSupper24K: false,
    roles: [],
    styles: [],
    name: '',
    displayName: '',
    headerImage: '',
  }
}

export interface FilterSpeaker {
  word: string
  topFlag: string
  language: string
  domain: string
  category: string
  gender: string
  tag: string
}

export function defaultFilterSpeaker(): FilterSpeaker {
  return {
    domain: '',
    language: '',
    word: '',
    topFlag: '',
    category: '',
    gender: '',
    tag: '',
  }
}
