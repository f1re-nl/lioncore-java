package io.lionweb.lioncore.java.serialization;

import io.lionweb.lioncore.java.language.Annotation;
import io.lionweb.lioncore.java.language.Classifier;
import io.lionweb.lioncore.java.language.Concept;
import io.lionweb.lioncore.java.language.Language;
import io.lionweb.lioncore.java.serialization.data.MetaPointer;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible to resolve the Concept associate with a given Concept ID.
 *
 * <p>While initially just know concepts which have been explicitly registered, in the future it
 * could adopt more advanced resolution strategies.
 */
public class ClassifierResolver {
  private Map<MetaPointer, Concept> registeredConcepts = new HashMap<>();
  private Map<MetaPointer, Annotation> registeredAnnotations = new HashMap<>();

  public Classifier<?> resolveClassifier(MetaPointer conceptMetaPointer) {
    if (registeredConcepts.containsKey(conceptMetaPointer)) {
      return registeredConcepts.get(conceptMetaPointer);
    } else if (registeredAnnotations.containsKey(conceptMetaPointer)) {
      return registeredAnnotations.get(conceptMetaPointer);
    } else {
      throw new RuntimeException(
          "Unable to resolve classifier with metaPointer " + conceptMetaPointer);
    }
  }

  public Concept resolveConcept(MetaPointer conceptMetaPointer) {
    if (registeredConcepts.containsKey(conceptMetaPointer)) {
      return registeredConcepts.get(conceptMetaPointer);
    } else {
      throw new RuntimeException(
          "Unable to resolve concept with metaPointer " + conceptMetaPointer);
    }
  }

  public Annotation resolveAnnotation(MetaPointer metaPointer) {
    if (registeredAnnotations.containsKey(metaPointer)) {
      return registeredAnnotations.get(metaPointer);
    } else {
      throw new RuntimeException("Unable to resolve annotation with metaPointer " + metaPointer);
    }
  }

  public ClassifierResolver registerLanguage(Language language) {
    language
        .getElements()
        .forEach(
            e -> {
              if (e instanceof Concept) {
                registerConcept((Concept) e);
              } else if (e instanceof Annotation) {
                registeredAnnotation((Annotation) e);
              }
            });
    return this;
  }

  private void registerConcept(Concept concept) {
    registeredConcepts.put(MetaPointer.from(concept), concept);
  }

  private void registeredAnnotation(Annotation annotation) {
    registeredAnnotations.put(MetaPointer.from(annotation), annotation);
  }
}
