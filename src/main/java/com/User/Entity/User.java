package com.User.Entity;

import com.Acknowledgement.Entity.Acknowledgement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID uuid = UUID.randomUUID();
  @NotEmpty
  private String name;
  @NotEmpty
  @Pattern(regexp = "^(.+)@(\\S+)$")
  private String email;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Acknowledgement> acknowledgement;

}
