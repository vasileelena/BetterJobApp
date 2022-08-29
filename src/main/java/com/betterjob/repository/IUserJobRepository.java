package com.betterjob.repository;

import com.betterjob.model.UserJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserJobRepository extends JpaRepository<UserJob, Long> {
    UserJob findByJobIdAndUserId(Long jobId, Long userId);

    List<UserJob> findByAppliedAndUserId(boolean applied, Long userId);

    List<UserJob> findBySavedAndUserId(boolean saved, Long userId);

    List<UserJob> findByAppliedAndJobId(boolean applied, Long jobId);

    UserJob findByJobIdAndUserIdAndApplied(Long jobId, Long userId, boolean applied);

    UserJob findByJobIdAndUserIdAndSaved(Long jobId, Long userId, boolean saved);

    void deleteAllByJobId(Long jobId);
}
