package com.poc.FarmaLoginService.repository;


import com.poc.FarmaLoginService.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByEmail(String email);


}
