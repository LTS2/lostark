package com.ysmeta.lostark.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 방명록 request
 *
 * @author : minjooo
 * @fileName : ReqeustDTO
 * @since : 2024/09/03
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RequestDTO {

    private Long userId;
    private Long targetUserId;
    private String username;
    private String comment;
    private Long recruitmentId;
    private String goal;
}