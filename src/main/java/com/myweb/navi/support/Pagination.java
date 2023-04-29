package com.myweb.navi.support;

import java.util.HashMap;

import lombok.Builder;

public class Pagination {
	
    private static final int DISPLAY_PAGE_NUM = 5; // 한 번에 표시할 페이지 개수 = 5
    private final int postsPerPage; // 한 페이지당 표시할 글 개수
    private final int totalPosts; // 모든 글 개수
    private final int currentPage; // 현재 페이지 번호
    private final HashMap<String, Object> pageInfo = new HashMap<>(); // 페이지 정보를 담을 Map
    
    @Builder
    public Pagination(int totalPosts, int currentPage, int postsPerPage) {
        this.totalPosts = totalPosts;
        this.currentPage = currentPage;
        this.postsPerPage = postsPerPage;
    }

    private int calculateTotalPages() {
        return ((totalPosts - 1) / postsPerPage) + 1;
    }

    private int calculateEndPage(int currentPage, int totalPages) {
        int endPage = (((currentPage - 1) / DISPLAY_PAGE_NUM) + 1) * DISPLAY_PAGE_NUM;

        if (totalPages < endPage) {
            endPage = totalPages;
        }

        return endPage;
    }

    private int calculateStartPage(int currentPage) {
        return ((currentPage - 1) / DISPLAY_PAGE_NUM) * DISPLAY_PAGE_NUM + 1;
    }

    private boolean hasPrev(int startPage) {
        return startPage != 1;
    }

    private boolean hasNext(int endPage, int totalPages) {
        return endPage != totalPages;
    }

    public HashMap<String, Object> getPageInfo() {
        int totalPages = calculateTotalPages();
        int endPage = calculateEndPage(currentPage, totalPages);
        int startPage = calculateStartPage(currentPage);

        pageInfo.put("totalPages", totalPages);
        pageInfo.put("endPage", endPage);
        pageInfo.put("startPage", startPage);
        pageInfo.put("hasPrev", hasPrev(startPage));
        pageInfo.put("hasNext", hasNext(endPage, totalPages));

        return pageInfo;
    }
}