package com.tgog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@Entity
@Table(name="tours")
public class Trip extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy ="native")
    @Column(name = "trip_id")
    private int tripId;
    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Begin Date must not be blank")
    private Date beginDate;

    @NotBlank(message="End Date must not be blank")
    private Date endDate;

    @NotBlank(message="Fees must not be blank")
    private String fees;

    private String image;

}
