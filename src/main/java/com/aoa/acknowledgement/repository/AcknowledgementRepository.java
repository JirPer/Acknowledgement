package com.aoa.acknowledgement.repository;

import com.aoa.acknowledgement.entity.Acknowledgement;
import com.aoa.user.entity.UserDetail;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AcknowledgementRepository extends JpaRepository<Acknowledgement, Long> {

  @Query("SELECT u FROM UserDetail u WHERE u.id = :id")
  Optional<UserDetail> findUserById(Long id);
  Optional<Acknowledgement> findByUuid(UUID uuid);
}
