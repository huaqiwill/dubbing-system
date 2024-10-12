<template>
  <div class="d-flex overflow-hidden" style="width: 100%; height: 100%">
    <Speaker />
    <EditorView :config="config" />
  </div>
</template>

<style scoped lang="scss"></style>

<script setup lang="ts">
import { Speaker } from './speaker-left'
import { EditorView } from './speaker-editor'
import config from '@/config/mock'
</script>
