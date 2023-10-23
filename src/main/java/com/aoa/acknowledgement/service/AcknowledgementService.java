package com.aoa.acknowledgement.service;


import com.aoa.acknowledgement.entity.Acknowledgement;
import com.aoa.acknowledgement.repository.AcknowledgementRepository;
import com.aoa.exception.ApiException400;
import com.aoa.exception.ApiException404;
import com.aoa.exception.ErrorCause;
import com.aoa.user.entity.UserDetail;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.TextSelection;
import jakarta.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcknowledgementService {

  @Autowired
  private AcknowledgementRepository acknowledgementRepository;
  @Autowired
  private Acknowledgement acknowledgement;

  private static final Logger logger = LogManager.getLogger(AcknowledgementService.class);
  @Transactional
  public Acknowledgement createAcknowledgement(Acknowledgement acknowledgement, Long id) {
    Optional<UserDetail> userDetailOptional = acknowledgementRepository.findUserById(id);
    if(userDetailOptional.isEmpty()) {
      throw new ApiException404(String.format("user with id %s was not found",id),ErrorCause.ENTITY_NOT_FOUND);
    }
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findByUuid(
          acknowledgement.getUuid());
    if (acknowledgementOptional.isPresent()) {
      throw new ApiException400(
          String.format("Acknowledgement with id %s is already present.",
              acknowledgement.getId()),
          ErrorCause.ENTITY_ALREADY_EXISTS);
    }
      acknowledgement.setUser(userDetailOptional.get());
      acknowledgementRepository.save(acknowledgement);
      logger.info("acknowledgement successfully saved");

    return acknowledgement;
  }

  @Transactional
  public Acknowledgement customizeUserAcknowledgement(Long id, Map<String, String> parameters) {
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findById(id);
    acknowledgementOptional.orElseThrow(() -> new ApiException404
        (String.format("Acknowledgement with id %s already was not found", id), ErrorCause.ENTITY_NOT_FOUND));

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String actDate = dateFormat.format(date);

    final Document document = new Document(acknowledgement.getSourcePath());

    parameters.forEach((name, value) -> {
      TextSelection nameSelection = document.findString("${" + name + "}", false, true);
      String fontName = nameSelection.getAsOneRange().getCharacterFormat().getFontName();
      nameSelection.getAsOneRange().getCharacterFormat().setFontName(fontName);
      nameSelection.getAsOneRange().getCharacterFormat().setBold(false);
      document.replace("${" + name + "}", value, true, true);
    });

    document.saveToFile(acknowledgement.getSavePath(),
        FileFormat.PDF);
    document.dispose();

    return acknowledgementOptional.get();
  }

  public List<Acknowledgement> getAllAcknowledgements() {
    return acknowledgementRepository.findAll();
  }

  public Acknowledgement getAcknowledgementById(Long id) {
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findById(id);
    acknowledgementOptional.orElseThrow(() -> new ApiException404
        (String.format("Acknowledgement with id %s was not found", id), ErrorCause.ENTITY_NOT_FOUND));

    return acknowledgementOptional.get();
  }

  @Transactional
  public Acknowledgement deleteAcknowledgementById(Long id) {
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findById(id);
    acknowledgementOptional.orElseThrow(() -> new ApiException404(String.format("Acknowledgement with id %s was not found", id),ErrorCause.ENTITY_NOT_FOUND));
    acknowledgementRepository.delete(acknowledgementOptional.get());

    return acknowledgementOptional.get();
  }
}
