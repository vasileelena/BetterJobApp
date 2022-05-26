package com.betterjob.repository;

import com.betterjob.domain.Job;
import com.betterjob.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJobRepository extends JpaRepository<Job, Long> {
    List<Job> findJobByUserId(Long userId);
    Job findJobById(Long jobId);
}
