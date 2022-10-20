package org.lionweb.lioncore.java;

/**
 * This represents a relation between an {@link FeaturesContainer} and referred {@link AbstractConcept}.
 *
 * A VariableReference may have a Reference to a VariableDeclaration.
 *
 * @see org.eclipse.emf.ecore.EReference Ecore equivalent <i>EReference</i> (with the <tt>containment</tt> flag set to <tt>false</tt>)
 * @see <a href="https://www.jetbrains.com/help/mps/structure.html#references">MPS equivalent <i>Reference</i> in documentation</a>
 * @see <a href="http://127.0.0.1:63320/node?ref=r%3A00000000-0000-4000-0000-011c89590292%28jetbrains.mps.lang.structure.structure%29%2F1071489288298">MPS equivalent <i>LinkDeclaration</i> in local MPS (with <tt>metaClass</tt> having value <tt>reference</tt>)</a>
 * @see org.jetbrains.mps.openapi.language.SReferenceLink MPS equivalent <i>SReferenceLink</i> in SModel
 */
public class Reference extends Link {
    private Reference specialized;

    public Reference(String simpleName, FeaturesContainer container) {
        // TODO verify that the container is also a NamespaceProvider
        super(simpleName, container);
    }

    public Reference getSpecialized() {
        return specialized;
    }

    public void setSpecialized(Reference specialized) {
        // TODO check which limitations there are: should have the same name? Should it belong
        //      to an ancestor of the FeaturesContainer holding this Containment?
        this.specialized = specialized;
    }

    @Override
    public void setMultiplicity(Multiplicity multiplicity) {
        // TODO check constraint on multiplicity
        super.setMultiplicity(multiplicity);
    }
}
