package org.example.webtodo.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateRequest {
    @NotBlank
    private String name;
    @Size(max = 100)
    private String description;
    @FutureOrPresent
    private LocalDateTime dueAt;
}
