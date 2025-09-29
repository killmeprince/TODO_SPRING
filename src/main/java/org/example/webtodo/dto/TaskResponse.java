package org.example.webtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.webtodo.model.Status;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private Long id;
    private String name;
    private String info;
    private LocalDateTime dueAt;
    private Status status;
    private LocalDateTime creationDate;

}
