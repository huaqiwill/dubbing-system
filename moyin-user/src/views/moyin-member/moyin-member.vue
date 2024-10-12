<template>
  <el-card class="member">
    <div class="member-header">
      <div>
        <h2 class="title">会员VIP</h2>
        <div class="desc">解锁12项权益</div>
      </div>
    </div>
    <MemberList></MemberList>
    <div class="member-footer">
      <p>温馨提示：</p>
      <p>1.会员时长一个月=30天，会员时长到期后不会自动续费</p>
      <p>
        2.开通会员则默认您已同意配音神器《用户协议》，所有声音可以试听效果，会员开通后非软件问题不支持退款，请试听满意后再购买
      </p>
      <p>
        3.超强情感声音为平台接入的第三方服务，有合成字符数限制。月会员限额10万字符，年和永久会员限额25万字符，消耗完后可购买，低至成本价万字2元
      </p>
      <p>4.文案提取每日限制2小时（视频时长）</p>
    </div>
  </el-card>
</template>

<script setup>
import { MemberList } from "./index";
</script>

<style scoped lang="scss">
.member {
  width: 800px;
  margin: 0 auto;
  margin-top: 15px;

  .member-header {
    height: 138px;
    background-color: #dab97f;
    padding: 15px;
    border-radius: 10px;
    display: flex;
    justify-content: center;
    align-items: center;

    .title {
      font-size: 25px;
      font-weight: 400;
      font-family: "微软雅黑";
    }

    .desc {
      font-size: 14px;
      font-family: "微软雅黑";
      margin-top: 10px;
    }
  }

  .member-list {
  }

  .member-footer {
    margin-top: 10px;
    font-size: 12px;
    color: #888;
  }
}
</style>
