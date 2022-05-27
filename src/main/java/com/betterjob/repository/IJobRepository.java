package com.betterjob.repository;

import com.betterjob.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IJobRepository extends JpaRepository<Job, Long>, IJobRepositoryCustom {
    List<Job> findJobByRecruiterId(Long recruiterId);
    Job findJobById(Long jobId);

}
