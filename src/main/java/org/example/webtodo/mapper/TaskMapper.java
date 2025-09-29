package org.example.webtodo.mapper;

import org.example.webtodo.dto.TaskCreateRequest;
import org.example.webtodo.dto.TaskResponse;
import org.example.webtodo.dto.TaskUpdateRequest;
import org.example.webtodo.model.Task;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    TaskResponse toResponse(Task task);


    List<TaskResponse> toResponseList(List<Task> tasks);
    Task fromCreate(TaskCreateRequest dto);
    //TODO:map struct learn
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Task target, TaskUpdateRequest dto);
}
