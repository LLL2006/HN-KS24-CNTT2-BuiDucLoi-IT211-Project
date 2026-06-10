package com.re.it211project.repository;

import com.re.it211project.entity.Job;
import com.re.it211project.entity.JobApplication;
import com.re.it211project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByCandidate(User candidate, Pageable pageable);

    Page<JobApplication> findByJob(Job job, Pageable pageable);

    Optional<JobApplication> findByJobAndCandidate(Job job, User candidate);

    boolean existsByJobAndCandidate(Job job, User candidate);
}