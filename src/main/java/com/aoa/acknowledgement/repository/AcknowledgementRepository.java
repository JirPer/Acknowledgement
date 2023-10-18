package com.aoa.acknowledgement.repository;

import com.aoa.acknowledgement.entity.Acknowledgement;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcknowledgementRepository extends JpaRepository<Acknowledgement, Long> {

  Optional<Acknowledgement> findByName(String name);
  Optional<Acknowledgement> findByUuid(UUID uuid);
}