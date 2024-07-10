package org.example.javafinalproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stops")
@Getter
@Setter
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 10)
    private String code;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String detail;

    @NotBlank
    @Min(1)
    private Integer number;

    public Stop() {
    }

    public Stop(String code, String name, String detail, Integer number) {
        this.code = code;
        this.name = name;
        this.detail = detail;
        this.number = number;
    }
}
