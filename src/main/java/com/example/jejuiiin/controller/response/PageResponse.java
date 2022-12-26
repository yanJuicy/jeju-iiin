package com.example.jejuiiin.controller.response;

import com.example.jejuiiin.controller.response.message.SuccessMessage;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class PageResponse<T, EN> {

    private static final int DISPLAY_PAGES = 5;

    private int httpStatusCode;
    private String msg;
    private List<T> data;
    private int totalPage;
    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;
    private List<Integer> pageList;

    public PageResponse(int httpStatusCode, String msg, Page<EN> page, Function<EN, T> fn) {
        this.httpStatusCode = httpStatusCode;
        this.msg = msg;
        data = page.stream().map(fn).toList();
        totalPage = page.getTotalPages();
        makePageList(page.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / 10.0)) * DISPLAY_PAGES;
        start = tempEnd - DISPLAY_PAGES + 1;
        end = Math.min(totalPage, tempEnd);

        prev = start > 1;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toList());
    }

    public static <T, EN> PageResponse<T, EN> success(SuccessMessage message, Page<EN> page, Function<EN, T> fn) {
        return new PageResponse<>(message.getHttpStatusCode(), message.getMsg(), page, fn);
    }
}
