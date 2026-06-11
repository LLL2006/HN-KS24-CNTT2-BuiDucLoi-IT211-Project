package com.re.it211project.service;

import com.re.it211project.dto.request.JobRequest;
import com.re.it211project.dto.response.JobResponse;
import com.re.it211project.entity.Company;
import com.re.it211project.entity.Job;
import com.re.it211project.entity.User;
import com.re.it211project.enums.JobStatus;
import com.re.it211project.mapper.JobMapper;
import com.re.it211project.repository.JobRepository;
import com.re.it211project.security.CustomUserDetails;
import com.re.it211project.service.impl.JobServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobMapper jobMapper;

    @InjectMocks
    private JobServiceImpl jobService;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createJob_success() {
        Company company = Company.builder()
                .id(1L)
                .companyName("ABC Company")
                .build();

        User employer = User.builder()
                .id(1L)
                .company(company)
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(employer),
                        null
                )
        );

        JobRequest request = JobRequest.builder()
                .title("Java Backend")
                .description("Spring Boot")
                .location("Ha Noi")
                .salary(1500.0)
                .deadline(LocalDate.now().plusDays(30))
                .build();

        Job savedJob = Job.builder()
                .id(1L)
                .title("Java Backend")
                .status(JobStatus.PENDING)
                .build();

        JobResponse response = JobResponse.builder()
                .id(1L)
                .title("Java Backend")
                .status(JobStatus.PENDING)
                .build();

        when(jobRepository.save(any(Job.class))).thenReturn(savedJob);
        when(jobMapper.toResponse(savedJob)).thenReturn(response);

        JobResponse result = jobService.createJob(request);

        assertEquals(JobStatus.PENDING, result.getStatus());
        verify(jobRepository).save(any(Job.class));
    }
}