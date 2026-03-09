package com.jobportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String skills;

    private int experience;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Recruiter → Jobs relationship
    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Job> postedJobs;
}