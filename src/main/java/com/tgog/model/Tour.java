package com.tgog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name="tours")
@NamedQuery(name = "Tour.updateTourShow", query = "UPDATE Tour c SET c.show = ?1 WHERE c.tourId = ?2")
public class Tour extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy ="native")
    @Column(name = "tour_id")
    private Integer tourId;

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate beginDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @NotBlank(message="Fees must not be blank")
    private String fees;

    private String imagePath;
    private String imagePath2;
    private String imagePath3;

    private String description;

    private Boolean show;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        REGULAR, CUSTOM, VIP;
    }
}
