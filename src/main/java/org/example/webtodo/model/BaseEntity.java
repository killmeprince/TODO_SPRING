package org.example.webtodo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;

    @Column(updatable = false)
    private LocalDateTime creationDate;

    @PrePersist
    protected void OnCreation() {
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }

    }

}
