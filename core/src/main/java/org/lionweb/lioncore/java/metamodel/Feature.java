package org.lionweb.lioncore.java.metamodel;

import org.lionweb.lioncore.java.Experimental;
import org.lionweb.lioncore.java.utils.Naming;

import javax.annotation.Nullable;

/**
 * A Feature represents a characteristic or some form of data associated with a particular concept.
 *
 * For example, an Invoice can have an associated date, a number, a connection with a customer, and it can contain
 * InvoiceLines. All of this information is represented by features.
 *
 * @see org.eclipse.emf.ecore.EStructuralFeature Ecore equivalent <i>EStructuralFeature</i>
 * @see <a href="https://www.jetbrains.com/help/mps/structure.html#conceptmembers">MPS equivalent <i>Concept members</i> in documentation</a>
 * @see org.jetbrains.mps.openapi.language.SConceptFeature MPS equivalent <i>SConceptFeature</i> in SModel
 */
public abstract class Feature implements NamespacedEntity {
    private boolean optional;
    @Experimental
    private boolean derived;

    private String simpleName;
    private FeaturesContainer container;

    public Feature() {

    }

    public Feature(@Nullable String simpleName, @Nullable FeaturesContainer container) {
        // TODO verify that the container is also a NamespaceProvider
        // TODO enforce uniqueness of the name within the FeauturesContainer
        Naming.validateSimpleName(simpleName);
        this.simpleName = simpleName;
        this.container = container;
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean isRequired() {
        return !optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Experimental
    public boolean isDerived() {
        return derived;
    }

    @Experimental
    public void setDerived(boolean derived) {
        this.derived = derived;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public @Nullable NamespaceProvider getContainer() {
        return (NamespaceProvider) container;
    }

}
