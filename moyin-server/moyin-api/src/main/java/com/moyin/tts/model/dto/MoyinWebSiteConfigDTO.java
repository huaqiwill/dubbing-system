package com.moyin.tts.model.dto;

import com.moyin.common.core.domain.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 31734
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MoyinWebSiteConfigDTO extends BaseEntity {

    String logo;

    String title;

    String language;

}
