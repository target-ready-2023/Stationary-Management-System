package com.targetindia.stationarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_email", unique = true, nullable = false)
    private String studentEmail;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @JsonIgnore
    @Column(name = "student_password", nullable = false)
    private String studentPassword;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private List<Transaction> transactions;
}
