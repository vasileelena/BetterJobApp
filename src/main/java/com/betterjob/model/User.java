package com.betterjob.model;

import com.betterjob.model.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private RoleEnum role;
    // for recruiters
    private String company;
    // for users
    private Date birthDate;
    private String description;
    private String location;
    private String skills;
    private boolean uploadedCV;
}
