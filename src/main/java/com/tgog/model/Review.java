package com.tgog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name="reviews")
@NamedQuery(name = "Review.updateReviewShow", query = "UPDATE Review c SET c.show = ?1 WHERE c.reviewId = ?2")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy ="native")
    @Column(name = "review_id")
    private int reviewId;

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Review must not be blank")
    @Size(min=10, message="Review must be at least 10 characters long")
    private String reviewText;

    private Boolean show;

}
