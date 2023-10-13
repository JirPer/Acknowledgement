package com.Acknowledgement.Service;


import com.Acknowledgement.Entity.Acknowledgement;
import com.Acknowledgement.Exceptions.AcknowledgementExistsException;
import com.Acknowledgement.Exceptions.AcknowledgementNotFoundException;
import com.Acknowledgement.Repository.AcknowledgementRepository;
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
      throw new AcknowledgementExistsException(
          "Acknowledgement with uuid:" + acknowledgement.getUuid() + " is already present");
    }
    return acknowledgementRepository.save(acknowledgement);
  }

  @Transactional
  public Acknowledgement customizeUserAcknowledgement(Long id, Map<String, String> parameters) {
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findById(id);
    acknowledgementOptional.orElseThrow(() -> new AcknowledgementNotFoundException(
        "acknowledgement with id:" + id + " does not exist"));

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
    acknowledgementOptional.orElseThrow(() -> new AcknowledgementNotFoundException("acknowledgement with id:" + id + " does not exist"));

    return acknowledgementOptional.get();
  }

  @Transactional
  public void deleteAcknowledgementById(Long id) {
    Optional<Acknowledgement> acknowledgementOptional = acknowledgementRepository.findById(id);
    acknowledgementOptional.orElseThrow(() -> new AcknowledgementNotFoundException("acknowledgement with id:" + id + " does not exist"));
    acknowledgementRepository.delete(acknowledgementOptional.get());
  }
}
