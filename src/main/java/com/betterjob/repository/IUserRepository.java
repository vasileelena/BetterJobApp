package com.betterjob.repository;

import com.betterjob.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

public interface IUserRepository extends JpaRepository<User, Long>, IUserRepositoryCustom {
    User findUserByEmail(String email);
    User findUserById(Long id);

    @Modifying
    @Query("update User u set " +
            "u.firstName = ?1, " +
            "u.lastName = ?2, " +
            "u.description = ?3, " +
            "u.birthDate = ?4, " +
            "u.company = ?5, " +
            "u.cv = ?6 where u.id = ?7")
    void updateUser(String firstName,
                         String lastName,
                         String description,
                         Date birthDate,
                         String company,
                         File cv,
                         Long id);
}
