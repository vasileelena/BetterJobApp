package com.betterjob.repository;

import com.betterjob.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long>, IUserRepositoryCustom {
    User findUserByEmail(String email);

}
