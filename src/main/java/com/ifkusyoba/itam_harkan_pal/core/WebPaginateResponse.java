package com.ifkusyoba.itam_harkan_pal.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebPaginateResponse<T> {
    private String message;
    private T data;
    private boolean isSuccess;
}
