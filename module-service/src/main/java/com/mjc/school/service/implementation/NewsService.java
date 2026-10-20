package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ValidatorException;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.validator.NewsValidator; // <-- Импортируем нашего инспектора

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseRepository<NewsModel, Long> newsRepository;

    private final NewsValidator validator = new NewsValidator();

    public NewsService(BaseRepository<NewsModel, Long> newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return newsRepository.readAll().stream()
                .map(NewsMapper.INSTANCE::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        NewsModel model = newsRepository.readById(id);
        if (model == null) {
            throw new ValidatorException("ERROR-003", "News with id " + id + " does not exist.");
        }
        return NewsMapper.INSTANCE.modelToDto(model);
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        validator.validateNewsDto(createRequest);

        NewsModel model = NewsMapper.INSTANCE.dtoToModel(createRequest);
        model.setCreateDate(LocalDateTime.now());
        model.setLastUpdateDate(LocalDateTime.now());

        NewsModel savedModel = newsRepository.create(model);
        return NewsMapper.INSTANCE.modelToDto(savedModel);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        validator.validateNewsDto(updateRequest);

        if (newsRepository.readById(updateRequest.getId()) == null) {
            throw new ValidatorException("ERROR-003", "News with id " + updateRequest.getId() + " does not exist.");
        }

        NewsModel model = NewsMapper.INSTANCE.dtoToModel(updateRequest);
        model.setLastUpdateDate(LocalDateTime.now());

        NewsModel updatedModel = newsRepository.update(model);
        return NewsMapper.INSTANCE.modelToDto(updatedModel);
    }

    @Override
    public Boolean delete(Long id) {
        if (newsRepository.readById(id) == null) {
            throw new ValidatorException("ERROR-003", "News with id " + id + " does not exist.");
        }
        return newsRepository.delete(id);
    }
}