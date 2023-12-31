package com.aoa.controller;

import com.aoa.dto.AcknowledgementDTO;
import com.aoa.entity.Acknowledgement;
import com.aoa.service.AcknowledgementService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acknowledgements")
public class AcknowledgementController {

  @Autowired
  private AcknowledgementService acknowledgementService;

  @PostMapping("/{uid}")
  public ResponseEntity<AcknowledgementDTO> createNewAcknowledgement(@RequestBody @Valid Acknowledgement acknowledgement, @PathVariable Long uid) {
    return ResponseEntity.ok(acknowledgementService.createAcknowledgement(acknowledgement, uid));
  }

  @PutMapping("/{id}")
  public ResponseEntity<AcknowledgementDTO> customizeAcknowledgement(@PathVariable Long id,
                                                                  @RequestParam Map<String, String> parameters) {
   return ResponseEntity.ok(acknowledgementService.customizeUserAcknowledgement(id,parameters));
  }

  @GetMapping
  public ResponseEntity<List<AcknowledgementDTO>> getAllAcknowledgement() {
    return ResponseEntity.ok(acknowledgementService.getAllAcknowledgements());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AcknowledgementDTO> getAcknowledgementById(@PathVariable Long id) {
    return ResponseEntity.ok(acknowledgementService.getAcknowledgementById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Acknowledgement> deleteAcknowledgementById(@PathVariable Long id) {
    return ResponseEntity.ok(acknowledgementService.deleteAcknowledgementById(id));
  }
}
