<template>
  <div class="speaker-layout" style="width: 100%; height: 100%">
    <div class="speaker-search">
      <slot name="content"></slot>
    </div>
    <div class="speaker-list d-flex flex-row">
      <div class="speaker-sidebar">
        <slot name="sidebar"></slot>
      </div>
      <div class="speaker-list-info">
        <slot name="list"></slot>
      </div>
    </div>
    <div class="speaker-detail">
      <slot name="detail"></slot>
    </div>
  </div>
</template>

<style lang="scss" scoped>
$width: 600px;
$left-width: 240px;
$right-width: 360px;
$search-height: 45px;
$info-height: 400px;

.speaker-layout {
  max-width: $width;
  min-width: $width;
  background-color: #e4e8eb;

  .speaker-search {
    height: $search-height;
    width: $width;
    // background-color: red;
  }

  .speaker-list {
    border-top: 1px solid #fff;
    border-bottom: 1px solid #fff;
    height: calc(100% - $info-height + $search-height);
    .speaker-sidebar {
      // background-color: darkblue;
      width: $left-width;
    }
    .speaker-list-info {
      // background-color: red;
      width: $right-width;
    }
  }

  .speaker-detail {
    // background-color: blue;
    height: $info-height;
    width: $width;
  }
}
</style>

<script lang="ts" setup></script>
