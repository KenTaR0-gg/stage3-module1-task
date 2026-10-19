package com.mjc.school.controller;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.List;

public class NewsController {

    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;

    public NewsController(BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService) {
        this.newsService = newsService;
    }

    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    public NewsDtoResponse readById(Long id) {
        return newsService.readById(id);
    }

    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }

    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    public boolean delete(Long id) {
        return newsService.delete(id);
    }
}