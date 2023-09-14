package io.lionweb.lioncore.java.serialization;

import io.lionweb.lioncore.java.language.*;
import io.lionweb.lioncore.java.model.ClassifierInstance;
import io.lionweb.lioncore.java.model.Node;
import io.lionweb.lioncore.java.model.impl.DynamicAnnotationInstance;
import io.lionweb.lioncore.java.model.impl.DynamicNode;
import io.lionweb.lioncore.java.self.LionCore;
import io.lionweb.lioncore.java.serialization.data.SerializedClassifierInstance;
import java.util.HashMap;
import java.util.Map;

/**
 * This knows how to instantiate a Node, given the information provided by the unserialization
 * mechanism.
 */
public class Instantiator {

  public interface ConceptSpecificNodeInstantiator<T extends ClassifierInstance<?>> {
    T instantiate(
        Classifier<?> classifier,
        SerializedClassifierInstance serializedClassifierInstance,
        Map<String, ClassifierInstance<?>> unserializedNodesByID,
        Map<Property, Object> propertiesValues);
  }

  private Map<String, ConceptSpecificNodeInstantiator<?>> customUnserializers = new HashMap<>();
  private ConceptSpecificNodeInstantiator<?> defaultNodeUnserializer =
      (ConceptSpecificNodeInstantiator<Node>)
          (classifier, serializedNode, unserializedNodesByID, propertiesValues) -> {
            throw new IllegalArgumentException(
                "Unable to instantiate node with classifier " + classifier);
          };

  public Instantiator enableDynamicNodes() {
    defaultNodeUnserializer =
        (classifier, serializedNode, unserializedNodesByID, propertiesValues) -> {
          if (classifier instanceof Concept) {
            return new DynamicNode(serializedNode.getID(), (Concept) classifier);
          } else if (classifier instanceof Annotation) {
            return new DynamicAnnotationInstance(serializedNode.getID(), (Annotation) classifier);
          } else {
            throw new IllegalStateException();
          }
        };
    return this;
  }

  public ClassifierInstance<?> instantiate(
      Classifier<?> concept,
      SerializedClassifierInstance serializedClassifierInstance,
      Map<String, ClassifierInstance<?>> unserializedNodesByID,
      Map<Property, Object> propertiesValues) {
    if (customUnserializers.containsKey(concept.getID())) {
      return customUnserializers
          .get(concept.getID())
          .instantiate(
              concept, serializedClassifierInstance, unserializedNodesByID, propertiesValues);
    } else {
      return defaultNodeUnserializer.instantiate(
          concept, serializedClassifierInstance, unserializedNodesByID, propertiesValues);
    }
  }

  public Instantiator registerCustomUnserializer(
      String conceptID, ConceptSpecificNodeInstantiator<?> conceptSpecificNodeInstantiator) {
    customUnserializers.put(conceptID, conceptSpecificNodeInstantiator);
    return this;
  }

  public void registerLionCoreCustomUnserializers() {
    customUnserializers.put(
        LionCore.getLanguage().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new Language().setID(serializedNode.getID()));
    customUnserializers.put(
        LionCore.getConcept().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new Concept((String) null).setID(serializedNode.getID()));
    customUnserializers.put(
        LionCore.getConceptInterface().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new ConceptInterface((String) null).setID(serializedNode.getID()));
    customUnserializers.put(
        LionCore.getProperty().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new Property(null, null, serializedNode.getID()));
    customUnserializers.put(
        LionCore.getReference().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new Reference(null, serializedNode.getID()));
    customUnserializers.put(
        LionCore.getContainment().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new Containment(null, serializedNode.getID()));
    customUnserializers.put(
        LionCore.getPrimitiveType().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new PrimitiveType(serializedNode.getID()));
    customUnserializers.put(
        LionCore.getEnumeration().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new Enumeration().setID(serializedNode.getID()));
    customUnserializers.put(
        LionCore.getEnumerationLiteral().getID(),
        (concept, serializedNode, unserializedNodesByID, propertiesValues) ->
            new EnumerationLiteral().setID(serializedNode.getID()));
  }
}
