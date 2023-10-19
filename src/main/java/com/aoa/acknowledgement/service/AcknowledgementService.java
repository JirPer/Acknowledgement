package com.aoa.acknowledgement.service;


import com.aoa.acknowledgement.entity.Acknowledgement;
import com.aoa.acknowledgement.repository.AcknowledgementRepository;
import com.aoa.exception.ApiException400;
import com.aoa.exception.ApiException404;
import com.aoa.exception.ErrorCause;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcknowledgementService {

  @Autowired
  private AcknowledgementRepository acknowledgementRepository;
  private final String sourcePath = "C:/Users/jirip/IdeaProjects/Acknowledgement/oliveOrig2.docx";

  @Transactional
  public Acknowledgement createAcknowledgement(Acknowledgement acknowledgement) {
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findByUuid(
        acknowledgement.getUuid());
    if (acknowledgementOptional.isPresent()) {
      throw new ApiException400(
      String.format("Acknowledgement with id %s is already present.", acknowledgement.getId()),
          ErrorCause.ENTITY_ALREADY_EXISTS);
    }
    return acknowledgementRepository.save(acknowledgement);
  }

  @Transactional
  public Acknowledgement customizeUserAcknowledgement(Long id, Map<String, String> parameters) {
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findById(id);
    acknowledgementOptional.orElseThrow(() -> new ApiException404
        (String.format("Acknowledgement with id %s already was not found", id), ErrorCause.ENTITY_NOT_FOUND));

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    String actDate = dateFormat.format(date);

    final Document document = new Document(sourcePath);

    parameters.forEach((name, value) -> {
      TextSelection nameSelection = document.findString("${" + name + "}", false, true);
      String fontName = nameSelection.getAsOneRange().getCharacterFormat().getFontName();
      nameSelection.getAsOneRange().getCharacterFormat().setFontName(fontName);
      nameSelection.getAsOneRange().getCharacterFormat().setBold(false);
      document.replace("${" + name + "}", value, true, true);
    });

    document.saveToFile("C:/Users/jirip/IdeaProjects/Acknowledgement/oliveOrig.pdf",
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
  public void deleteAcknowledgementById(Long id) {
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findById(id);
    acknowledgementOptional.orElseThrow(() -> new ApiException404(String.format("Acknowledgement with id %s was not found", id),ErrorCause.ENTITY_NOT_FOUND));
    acknowledgementRepository.delete(acknowledgementOptional.get());
  }
}
