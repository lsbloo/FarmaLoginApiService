package com.poc.FarmaLoginService.repository;

import com.poc.FarmaLoginService.model.TypeRoles;
import com.poc.FarmaLoginService.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);

    @Query(value = "select role_id from user_auth_roles where user_id=?1", nativeQuery = true)
    List<Integer> findIdsRoleUserByUserId(Long userId);

    @Query(value = "select * from roles where id=?1", nativeQuery = true)
    Role getRoleById(Integer id);
}
