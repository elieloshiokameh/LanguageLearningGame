package com.languagegame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.languagegame.domain.RoleEnum;
import com.languagegame.domain.Role;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByType(RoleEnum type);
}
