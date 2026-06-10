package com.re.it211project.repository;

import com.re.it211project.entity.Job;
import com.re.it211project.entity.User;
import com.re.it211project.enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByStatus(JobStatus status, Pageable pageable);

    Page<Job> findByEmployer(User employer, Pageable pageable);

    Page<Job> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndSalaryBetweenAndStatus(
            String keyword,
            String location,
            Double minSalary,
            Double maxSalary,
            JobStatus status,
            Pageable pageable
    );
}