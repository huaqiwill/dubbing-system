package com.moyin.tts.domain;

import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MoyinMember extends BaseEntity {

    private Integer id;

    private Integer userId;

    private String userName;

    private String phoneNumber;

    /**
     * 会员等级
     */
    private Integer level;

    /**
     * 会员状态
     * 1、正常 2、冻结
     */
    private Integer status;

    /**
     * 会员积分
     */
    private Integer points;
    private Integer count;
    private Integer total;
    private Date startTime;
    private Date endTime;

    /**
     * 会员最近一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 会员最近一次登录IP
     */
    private String lastLoginIp;

    /**
     * 会员注册时间
     */
    private Date registerTime;

    private String updateBy;
    private Date updateTime;
}
