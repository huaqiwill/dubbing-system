<template>
  <div class="dubbing-header">
    <ul class="tool-list">
      <!-- 试听 -->
      <li class="tool-item"><TryListening /></li>
      <li class="tool-line"></li>

      <!-- 多音字 -->
      <li class="tool-item"><Polyphone /></li>
      <!-- 重音 -->
      <li class="tool-item"><Stress /></li>
      <!-- 数字符号 -->
      <li class="tool-item"><NumericSymbol /></li>
      <!-- 连续 -->
      <li class="tool-item"><Continuous /></li>
      <!-- 别名 -->
      <li class="tool-item"><Alias /></li>
      <!-- 音标 -->
      <li class="tool-item"><PhoneticMark /></li>
      <li class="tool-line"></li>

      <!-- 多人配音 -->
      <li class="tool-item"><MultiplayerDubbing /></li>
      <!-- 局部变速 -->
      <li class="tool-item"><LocalSpeedChange /></li>
      <!-- 局部变音 -->
      <li class="tool-item"><LocalVoiceChange /></li>
      <li class="tool-line"></li>

      <!-- 停顿调节 -->
      <li class="tool-item"><PauseAdjust /></li>
      <!-- 插入停顿 -->
      <li class="tool-item"><InsertMute /></li>
      <!-- 符号停顿 -->
      <li class="tool-item"><SymbolMute /></li>
      <!-- 段落停顿 -->
      <li class="tool-item"><ParagraphMute /></li>
      <!-- 解说模式 -->
      <li class="tool-item"><NarrateMode /></li>
      <!-- 音量 -->
      <li class="tool-item"><Volume /></li>
      <li class="tool-line"></li>

      <!-- 单字纠音 -->
      <!-- <li class="tool-item"><SingleCorrection /></li> -->
      <!-- 批量纠音 -->
      <!-- <li class="tool-item"><BatchCorrection /></li> -->
      <!-- <li class="tool-line"></li> -->

      <!-- 文案提取 -->
      <!-- <li><CopyExtraction /></li> -->
      <!-- 查敏感词 -->
      <!-- <li class="tool-item"><FindSensitiveWords /></li> -->

      <!-- <li class="tool-line"></li> -->

      <!-- 生成配音 -->
      <li class="tool-item"><GenerateDubbing /></li>
      <!-- 下载配音 -->
      <li class="tool-item"><DownloadDubbing /></li>
      <!-- 下载视频 -->
      <li class="tool-item"><DownloadVideo /></li>
      <!-- 下载字幕 -->
      <li class="tool-item"><DownloadSrt /></li>
      <!-- 生成记录 -->
      <li class="tool-item"><GenerateLogging /></li>
      <!-- 一键清空 -->
      <li class="tool-item"><ClearText /></li>
      <!-- 配音配置 -->
      <!-- <li class="tool-item"><DubbingConfig /></li> -->
    </ul>
  </div>
</template>

<script setup>
import {
  Polyphone,
  Stress,
  NumericSymbol,
  Alias,
  PhoneticMark,
  Continuous,
  TryListening,
  PauseAdjust,
  InsertMute,
  SymbolMute,
  NarrateMode,
  ParagraphMute,
  Volume,
  MultiplayerDubbing,
  LocalSpeedChange,
  LocalVoiceChange,
  BatchCorrection,
  SingleCorrection,
  CopyExtraction,
  FindSensitiveWords,
  GenerateDubbing,
  DownloadDubbing,
  DownloadVideo,
  DownloadSrt,
  GenerateLogging,
  ClearText,
  DubbingConfig,
} from "./index";
</script>

<style scoped lang="scss">
.dubbing-header {
  // background-color: #f4f6f7;
  display: flex;
  flex-direction: row;
  padding: 4px;
  border: 1px solid #e5e6eb !important;
  border-bottom: none !important;

  height: 120px;
  .tool-list {
    padding: 0;
    list-style: none;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: start;

    .tool-line {
      height: 55px;
      font-size: 0;
      width: 2px;
      background-color: #dbdddf;
      margin: 0 4px;
      border-radius: 4px;
    }

    .tool-item {
      cursor: pointer;
      width: 55px;
      height: 55px;
      border-radius: 5px;
      cursor: pointer;

      .icon {
        display: block;
        width: 22px;
        height: 22px;
        color: black;
        font-weight: 800;
        margin: 0 auto;
        margin-top: 6px;
      }

      .name {
        margin-top: 8px;
        color: #666666;
        font-size: 12px;
        line-height: 12px;
        user-select: none;
        display: block;
        text-align: center;
        font-weight: 400;
      }

      &:hover {
        background-color: #e5e5e5;
      }
    }
  }
}
</style>
