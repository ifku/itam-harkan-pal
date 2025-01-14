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
    private T data;
    private Pagination pagination;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Pagination {
        private long totalRecords;
        private int currentPage;
        private int totalPages;
        private Integer nextPage;
        private Integer prevPage;
    }
}
