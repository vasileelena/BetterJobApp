package com.betterjob.repository;

import com.betterjob.model.UserJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserJobRepository extends JpaRepository<UserJob, Long> {
    UserJob findByJobIdAndUserId(Long jobId, Long userId);
    UserJob findByAppliedAndUserId(boolean applied, Long userId);
    UserJob findBySavedAndUserId(boolean saved, Long userId);
}
