package com.re.it211project.dto.request;

import com.re.it211project.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateApplicationStatusRequest {

    @NotNull(message = "Trạng thái hồ sơ không được để trống")
    private ApplicationStatus status;

    @NotNull(message = "Phản hồi không được để trống")
    private String feedback;
}