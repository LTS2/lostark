package com.ysmeta.lostark.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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