package org.jetbrains.plugins.scala.lang.lexer;

import com.intellij.psi.PsiModifier;
import org.jetbrains.annotations.Nullable;

public enum ScalaModifier {
    Private(PsiModifier.PRIVATE),
    Protected(PsiModifier.PROTECTED),
    Final(PsiModifier.FINAL),
    Abstract(PsiModifier.ABSTRACT),
    Override(ScalaModifier.OVERRIDE),
    Implicit(ScalaModifier.IMPLICIT),
    Sealed(ScalaModifier.SEALED),
    Lazy(ScalaModifier.LAZY),
    Case(ScalaModifier.CASE),
    Inline(ScalaModifier.INLINE);

    private final String text;

    ScalaModifier(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }

    @Nullable
    public static ScalaModifier byText(String text) {
        for (ScalaModifier modifier: ScalaModifier.values()) {
            if (modifier.text.equals(text))
                return modifier;
        }
        return null;
    }

    public static final String PRIVATE = PsiModifier.PRIVATE;
    public static final String PROTECTED = PsiModifier.PROTECTED;
    public static final String FINAL = PsiModifier.FINAL;
    public static final String ABSTRACT = PsiModifier.ABSTRACT;
    public static final String CASE = "case";
    public static final String IMPLICIT = "implicit";
    public static final String LAZY = "lazy";
    public static final String OVERRIDE = "override";
    public static final String SEALED = "sealed";
    public static final String INLINE = "inline";
}
