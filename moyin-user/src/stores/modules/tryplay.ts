import { defineStore } from 'pinia'
import { computed, readonly, ref, shallowRef } from 'vue'
import { defaultSpeaker, type Speaker } from '@/model'
import { useSSMLStore } from './ssml'
import AudioPlayer from '@/menu/conversion-menu/audio-player'
// import { serializeToSSML } from '@/serialize'
import { sleep } from '@/utils'
import { emitter } from '@/event-bus'
// import type { AudioInfo } from '@/menu/conversion-menu/data'
import { getConfig } from '@/config'
import { tts } from '@/api/tts'
import { useSpeakerStore } from './speaker'

export const useTryPlayStore = defineStore('--speaker-editor-try-play', () => {
  const ssmlStore = useSSMLStore()
  const speakerStore = useSpeakerStore()
  const _audioPlayer = shallowRef(new AudioPlayer())
  const _speaker = ref<any>(defaultSpeaker())
  const _isLoading = ref(false)

  const speaker = computed(() => readonly(_speaker.value))
  const audioPlayer = computed(() => _audioPlayer.value)
  const isLoading = computed(() => _isLoading.value)

  function setSpeakerForce(value: Speaker) {
    _speaker.value = value
    ssmlStore.rootVoice.name = value.name
    emitter.emit('tryplay-speaker-select', value)
  }

  const setSpeaker = (key: symbol, value: Speaker) => {
    function setter(value: Speaker) {
      _speaker.value = value

      ssmlStore.rootVoice.name = value.name

      emitter.emit('tryplay-speaker-select', value)
    }
    const config = getConfig(key)
    if (config.tryPlay.selectSpeaker) {
      config.tryPlay.selectSpeaker(value, setter)
    } else {
      // 默认
      setter(value)
    }
  }

  const star = async (key: symbol, isStar: boolean) => {
    const speakerId = _speaker.value.id
    const config = getConfig(key)
    const resIsStar = await config.tryPlay.fetchStar(speakerId, isStar)
    _speaker.value.isStar = resIsStar
    emitter.emit('tryplay-speaker-update-star', _speaker.value.id, resIsStar)
  }

  function pausePlay() {
    audioPlayer.value.pause()
  }

  async function playSimple(url: any) {
    if (isLoading.value) {
      _isLoading.value = false
      audioPlayer.value.cancel()
      return
    }
    if (audioPlayer.value.playState.value === 'playing') {
      audioPlayer.value.pause()
      return
    }
    try {
      _isLoading.value = true

      await audioPlayer.value.load(url)
      await sleep(200)
      if (isLoading.value) {
        _isLoading.value = false
        audioPlayer.value.play()
      }
    } catch (error) {
      emitter.emit('error', error)
    } finally {
      emitter.emit('remain:count')
      _isLoading.value = false
    }
  }

  // 播放
  async function play() {
    if (isLoading.value) {
      _isLoading.value = false
      audioPlayer.value.cancel()
      return
    }
    if (audioPlayer.value.playState.value === 'playing') {
      audioPlayer.value.pause()
      return
    }
    try {
      _isLoading.value = true
      const submitParams = speakerStore.getSubmitParams()
// console.log('播放参数', submitParams)

      const audio = await tts(submitParams)
      // const audio = await fetchAudio(serializeToSSML)
      speakerStore.setLastPlayUrl(audio.data)
      const src = speakerStore.getLastPlayUrl()
// console.log('当前的音频地址：' + src)

      await audioPlayer.value.load(src)
      await sleep(200)
      if (isLoading.value) {
        _isLoading.value = false
        audioPlayer.value.play()
      }
    } catch (error) {
      emitter.emit('error', error)
    } finally {
      emitter.emit('remain:count')
      _isLoading.value = false
    }
  }

  return {
    speaker,
    setSpeaker,
    setSpeakerForce,
    pausePlay,
    star,
    playSimple,
    audioPlayer,
    isLoading,
    play,
  }
})
