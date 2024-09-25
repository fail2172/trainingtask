package org.example.model.dto;

import org.example.model.Task;

public class TaskMapper {
    public static Task toModel(TaskDto dto) {
        return new Task(
                dto.getId(),
                dto.getName(),
                dto.getStatus(),
                dto.getStart(),
                dto.getFinish(),
                dto.getEstimate());
    }

    public static TaskDto toDto(Task model) {
        return new TaskDto(
                model.getId(),
                model.getName(),
                model.getStatus(),
                model.getStart(),
                model.getFinish(),
                model.getEstimate(),
                model.getProject().getId(),
                model.getEmployee().getId());
    }
}
