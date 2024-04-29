package com.example.Blogging.Application.Repositories;

import com.example.Blogging.Application.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role , Integer> {

}
