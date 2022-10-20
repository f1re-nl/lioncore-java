package org.lionweb.lioncore.java;

import java.util.LinkedList;
import java.util.List;

/**
 * A ConceptInterface represents a category of entities sharing some similar characteristics.
 *
 * For example, Named would be a ConceptInterface.
 *
 * @see org.eclipse.emf.ecore.EClass Ecore equivalent <i>EClass</i> (with the <tt>isInterface</tt> flag set to <tt>true</tt>)
 * @see <a href="https://www.jetbrains.com/help/mps/structure.html#conceptsandconceptinterfaces">MPS equivalent <i>Concept Interface</i> in documentation</a>
 * @see <a href="http://127.0.0.1:63320/node?ref=r%3A00000000-0000-4000-0000-011c89590292%28jetbrains.mps.lang.structure.structure%29%2F1169125989551">MPS equivalent <i>InterfaceConceptDeclaration</i> in local MPS</a>
 * @see org.jetbrains.mps.openapi.language.SConceptInterface MPS equivalent <i>SConceptInterface</i> in SModel
 */
public class ConceptInterface extends AbstractConcept {
    private List<ConceptInterface> extended = new LinkedList<>();

    public ConceptInterface(Metamodel metamodel, String simpleName) {
        super(metamodel, simpleName);
    }

    public List<ConceptInterface> getExtendedInterface() {
        return this.extended;
    }
}
