package com.betterjob.domain;

import com.betterjob.domain.enums.ExperienceEnum;
import com.betterjob.domain.enums.IndustryEnum;
import com.betterjob.domain.enums.LocationEnum;
import com.betterjob.domain.enums.ProgramEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long userId;

    private String jobTitle;
    private IndustryEnum industry;
    private ExperienceEnum experience;
    private boolean undefinedPeriod;
    private int period;
    private ProgramEnum program;
    private int salaryLowerRange;
    private int salaryUpperRange;
    private LocationEnum location;
    private String description;
}
