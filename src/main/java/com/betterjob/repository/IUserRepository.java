package com.betterjob.repository;

import com.betterjob.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long>, IUserRepositoryCustom {
    User findUserByEmail(String email);

}
