package com.aoa.acknowledgement.entity;

import com.aoa.user.entity.UserDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class Acknowledgement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String name;

  @NotEmpty
  @Size(min = 3)
  private String kindOfAcknowledgement;
  private UUID uuid = UUID.randomUUID();
  private Long payout;
  private String date;
  private String sourcePath;
  private String savePath;
  @JsonIgnore
  @ManyToOne
  private UserDetail user;
  private Instant createdOn;


  @PrePersist
  public void init() {
    createdOn = Instant.now();
  }
}
