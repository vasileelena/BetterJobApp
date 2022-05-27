package com.betterjob.model.payloads;

import lombok.Getter;

@Getter
public class ApplyOrSaveJobForUserPayload {
    private Long userId;
    private Long jobId;
    private boolean apply;
}
