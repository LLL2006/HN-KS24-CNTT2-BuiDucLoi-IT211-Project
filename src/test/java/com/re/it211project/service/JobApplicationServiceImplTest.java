package com.re.it211project.service;

import com.re.it211project.dto.request.JobApplicationRequest;
import com.re.it211project.dto.response.JobApplicationResponse;
import com.re.it211project.entity.Job;
import com.re.it211project.entity.JobApplication;
import com.re.it211project.entity.User;
import com.re.it211project.enums.ApplicationStatus;
import com.re.it211project.enums.JobStatus;
import com.re.it211project.mapper.JobApplicationMapper;
import com.re.it211project.repository.JobApplicationRepository;
import com.re.it211project.repository.JobRepository;
import com.re.it211project.security.CustomUserDetails;
import com.re.it211project.service.impl.JobApplicationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceImplTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobApplicationMapper jobApplicationMapper;

    @InjectMocks
    private JobApplicationServiceImpl jobApplicationService;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void applyJob_success() {
        User candidate = User.builder()
                .id(1L)
                .cvUrl("https://cloudinary.com/cv.pdf")
                .build();

        Job job = Job.builder()
                .id(1L)
                .status(JobStatus.APPROVED)
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(candidate),
                        null
                )
        );

        JobApplicationRequest request = JobApplicationRequest.builder()
                .jobId(1L)
                .coverLetter("Tôi muốn ứng tuyển")
                .build();

        JobApplication savedApplication = JobApplication.builder()
                .id(1L)
                .status(ApplicationStatus.PENDING)
                .build();

        JobApplicationResponse response = JobApplicationResponse.builder()
                .id(1L)
                .status(ApplicationStatus.PENDING)
                .build();

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
        when(jobApplicationRepository.existsByJobAndCandidate(job, candidate)).thenReturn(false);
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(savedApplication);
        when(jobApplicationMapper.toResponse(savedApplication)).thenReturn(response);

        JobApplicationResponse result = jobApplicationService.applyJob(request);

        assertEquals(ApplicationStatus.PENDING, result.getStatus());
    }
}