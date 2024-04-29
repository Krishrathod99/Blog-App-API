package com.example.Blogging.Application.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private int id;
    private String roleName;



//    One role can associate to many user

}
