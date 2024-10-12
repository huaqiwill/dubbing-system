import type { LabelValue } from '@/model'

export interface SpeakerAvatarData extends LabelValue {
  avatar?: string
  isFree?: boolean
}

export interface StyleAvatarData extends LabelValue {
  avatar?: string
  emoji?: string
}

export const defaultSpeed = () => ({
  0: '0',
  0.1: '0.1',
  0.2: '0.2',
  0.3: '0.3',
  0.4: '0.4',
  0.5: '0.5',
  0.6: '0.6',
  0.7: '0.7',
  0.8: '0.8',
  0.9: '0.9',
  1.0: '1.0',
  1.1: '1.1',
  1.2: '1.2',
  1.4: '1.4',
  1.5: '1.5',
  1.75: '1.75',
  2.0: '2.0',
})

export const defaultPitch = () => ({
  0: '0',
  1: '1',
  2: '2',
  3: '3',
  4: '4',
  5: '5',
  6: '6',
  7: '7',
  8: '8',
  9: '9',
  10: '10',
  '-1': '-1',
  '-2': '-2',
  '-3': '-3',
  '-4': '-4',
  '-5': '-5',
  '-6': '-6',
  '-7': '-7',
  '-8': '-8',
  '-9': '-9',
  '-10': '-10',
})
