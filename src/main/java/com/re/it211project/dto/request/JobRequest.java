package com.re.it211project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {

    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotBlank(message = "Địa điểm không được để trống")
    private String location;

    @NotNull(message = "Lương không được để trống")
    private Double salary;

    @NotNull(message = "Hạn nộp không được để trống")
    private LocalDate deadline;
}