package com.betterjob.model;

import com.betterjob.model.enums.*;
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
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long recruiterId;

    private String jobTitle;
    private IndustryEnum industry;
    private ExperienceEnum experience;
    private boolean undefinedPeriod;
    private int period;
    private ProgramEnum program;
    private int salaryLowerRange;
    private int salaryUpperRange;
    private CurrencyEnum currency;
    private LocationEnum location;
    @Column(columnDefinition="TEXT")
    private String description;
    @Lob
    private String requirements;
    @Lob
    private String responsibilities;
    @Lob
    private String benefits;
    private Date creationDate;
}
