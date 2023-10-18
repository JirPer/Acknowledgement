package com.Acknowledgement.controller;

import com.Acknowledgement.entity.Acknowledgement;
import com.Acknowledgement.service.AcknowledgementService;
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
@RequestMapping("/acknowledgement")
public class AcknowledgementController {

  @Autowired
  private AcknowledgementService acknowledgementService;

  @PostMapping
  public ResponseEntity<Acknowledgement> createNewAcknowledgement(@RequestBody Acknowledgement acknowledgement) {
    return ResponseEntity.ok(acknowledgementService.createAcknowledgement(acknowledgement));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Acknowledgement> customizeAcknowledgement(@PathVariable Long id,
                                                                  @RequestParam Map<String, String> parameters) {
   return ResponseEntity.ok(acknowledgementService.customizeUserAcknowledgement(id,parameters));
  }

  @GetMapping
  public ResponseEntity<List<Acknowledgement>> getAllAcknowledgement() {
    return ResponseEntity.ok(acknowledgementService.getAllAcknowledgements());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Acknowledgement> getAcknowledgementById(@PathVariable Long id) {
    return ResponseEntity.ok(acknowledgementService.getAcknowledgementById(id));
  }

  @DeleteMapping("/{id}")
  public void deleteAcknowledgementById(@PathVariable Long id) {
    acknowledgementService.deleteAcknowledgementById(id);
  }
}
