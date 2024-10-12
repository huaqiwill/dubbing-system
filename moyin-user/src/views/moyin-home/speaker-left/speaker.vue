<template>
  <SpeakerLayout v-loading="loading">
    <template v-slot:content>
      <SpeakerSearch />
    </template>
    <template v-slot:sidebar>
      <SpeakerTagList />
    </template>
    <template v-slot:list>
      <SpeakerAvatarList />
    </template>
    <template v-slot:detail>
      <SpeakerInfoDetail />
    </template>
  </SpeakerLayout>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import { SpeakerLayout } from '.'
import { SpeakerTagList, SpeakerAvatarList, SpeakerInfoDetail, SpeakerSearch } from './coms'
import { useSpeakerStore } from '@/stores'

const speakerStore = useSpeakerStore()

const loading = ref(false)
onMounted(async () => {
  loading.value = true
  await speakerStore.getSpeakerList()
  await speakerStore.getCollectList()
  await speakerStore.getRecentList()
  loading.value = false
})
</script>
