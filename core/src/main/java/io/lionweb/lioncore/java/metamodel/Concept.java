package io.lionweb.lioncore.java.metamodel;

import io.lionweb.lioncore.java.model.ReferenceValue;
import io.lionweb.lioncore.java.self.LionCore;
import io.lionweb.lioncore.java.serialization.data.MetaPointer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A Concept represents a category of entities sharing the same structure.
 *
 * <p>For example, Invoice would be a Concept. Single entities could be Concept instances, such as
 * Invoice #1/2022.
 *
 * @see org.eclipse.emf.ecore.EClass Ecore equivalent <i>EClass</i> (with the <code>isInterface
 *     </code> flag set to <code>false</code>)
 * @see <a href="https://www.jetbrains.com/help/mps/structure.html#conceptsandconceptinterfaces">MPS
 *     equivalent <i>Concept</i> in documentation</a>
 * @see <a
 *     href="http://127.0.0.1:63320/node?ref=r%3A00000000-0000-4000-0000-011c89590292%28jetbrains.mps.lang.structure.structure%29%2F1071489090640">MPS
 *     equivalent <i>ConceptDeclaration</i> in local MPS</a>
 * @see org.jetbrains.mps.openapi.language.SConcept MPS equivalent <i>SConcept</i> in SModel
 */
public class Concept extends FeaturesContainer<Concept> {
  // DOUBT: would extended be null only for BaseConcept? Would this be null for all Concept that do
  // not explicitly extend
  //        another concept?

  public Concept() {
    super();
    setAbstract(false);
  }

  public Concept(@Nullable Language language, @Nullable String name, @Nonnull String id) {
    super(language, name, id);
    setAbstract(false);
  }

  public Concept(@Nullable Language language, @Nullable String name) {
    super(language, name);
    setAbstract(false);
  }

  @Nonnull
  @Override
  public List<FeaturesContainer<?>> directAncestors() {
    List<FeaturesContainer<?>> directAncestors = new ArrayList<>();
    // TODO add base ancestor common to all Concepts
    if (this.getExtendedConcept() != null) {
      directAncestors.add(this.getExtendedConcept());
    }
    directAncestors.addAll(this.getImplemented());
    return directAncestors;
  }

  public Concept(@Nullable String name) {
    super(null, name);
  }

  public boolean isAbstract() {
    return this.getPropertyValue("abstract", Boolean.class, false);
  }

  public void setAbstract(boolean value) {
    this.setPropertyValue("abstract", value);
  }

  // TODO should this return BaseConcept when extended is equal null?
  public @Nullable Concept getExtendedConcept() {
    return this.getReferenceSingleValue("extends");
  }

  public @Nonnull List<ConceptInterface> getImplemented() {
    return this.getReferenceMultipleValue("implements");
  }

  public void addImplementedInterface(@Nonnull ConceptInterface conceptInterface) {
    Objects.requireNonNull(conceptInterface, "conceptInterface should not be null");
    this.addReferenceMultipleValue(
        "implements", new ReferenceValue(conceptInterface, conceptInterface.getName()));
  }

  // TODO should we verify the Concept does not extend itself, even indirectly?
  public void setExtendedConcept(@Nullable Concept extended) {
    if (extended == null) {
      this.setReferenceSingleValue("extends", null);
    } else {
      this.setReferenceSingleValue("extends", new ReferenceValue(extended, extended.getName()));
    }
  }

  @Override
  public @Nonnull List<Feature> allFeatures() {
    // TODO Should this return features which are overriden?
    // TODO Should features be returned in a particular order?
    List<Feature> result = new LinkedList<>();
    result.addAll(this.getFeatures());
    if (this.getExtendedConcept() != null) {
      result.addAll(this.getExtendedConcept().allFeatures());
    }
    for (ConceptInterface superInterface : this.getImplemented()) {
      result.addAll(superInterface.allFeatures());
    }
    return result;
  }

  @Override
  public Concept getConcept() {
    return LionCore.getConcept();
  }

  public @Nullable Property getPropertyByID(@Nonnull String propertyId) {
    if (propertyId == null) {
      throw new IllegalArgumentException("propertyId cannot be null");
    }
    return allFeatures().stream()
        .filter(f -> f instanceof Property)
        .map(f -> (Property) f)
        .filter(p -> p.getID().equals(propertyId))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Property getPropertyByName(@Nonnull String propertyName) {
    Objects.requireNonNull(propertyName, "propertyName should not be null");
    return allFeatures().stream()
        .filter(f -> f instanceof Property)
        .map(f -> (Property) f)
        .filter(p -> Objects.equals(p.getName(), propertyName))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Containment getContainmentByName(@Nonnull String containmentName) {
    Objects.requireNonNull(containmentName, "containmentName should not be null");
    return allFeatures().stream()
        .filter(f -> f instanceof Containment)
        .map(f -> (Containment) f)
        .filter(c -> Objects.equals(c.getName(), containmentName))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Reference getReferenceByName(@Nonnull String referenceName) {
    Objects.requireNonNull(referenceName, "referenceName should not be null");
    return allFeatures().stream()
        .filter(f -> f instanceof Reference)
        .map(f -> (Reference) f)
        .filter(c -> Objects.equals(c.getName(), referenceName))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Containment getContainmentByID(@Nonnull String containmentID) {
    Objects.requireNonNull(containmentID, "containmentID should not be null");
    return allFeatures().stream()
        .filter(f -> f instanceof Containment)
        .map(f -> (Containment) f)
        .filter(c -> Objects.equals(c.getID(), containmentID))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Reference getReferenceByID(@Nonnull String referenceID) {
    Objects.requireNonNull(referenceID, "referenceID should not be null");
    return allFeatures().stream()
        .filter(f -> f instanceof Reference)
        .map(f -> (Reference) f)
        .filter(c -> c.getID().equals(referenceID))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Link getLinkByName(@Nonnull String linkName) {
    Objects.requireNonNull(linkName, "linkName should not be null");
    return allFeatures().stream()
        .filter(f -> f instanceof Link)
        .map(f -> (Link) f)
        .filter(c -> Objects.equals(c.getName(), linkName))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Property getPropertyByMetaPointer(MetaPointer metaPointer) {
    return this.allProperties().stream()
        .filter(p -> MetaPointer.from(p, this.getLanguage()).equals(metaPointer))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Containment getContainmentByMetaPointer(MetaPointer metaPointer) {
    return this.allContainments().stream()
        .filter(p -> MetaPointer.from(p, this.getLanguage()).equals(metaPointer))
        .findFirst()
        .orElse(null);
  }

  public @Nullable Reference getReferenceByMetaPointer(MetaPointer metaPointer) {
    return this.allReferences().stream()
        .filter(p -> MetaPointer.from(p, this.getLanguage()).equals(metaPointer))
        .findFirst()
        .orElse(null);
  }
}
