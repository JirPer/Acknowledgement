package com.User.repository;

import com.User.entity.UserDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, Long> {

  Optional<UserDetail> findByEmail(String email);

}
