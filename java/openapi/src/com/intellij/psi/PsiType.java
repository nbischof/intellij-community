/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.psi;

import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.TypeConversionUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.Function;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Representation of Java type (primitive type, array or class type).
 */
public abstract class PsiType implements PsiAnnotationOwner {
  public static final PsiType BYTE = new PsiPrimitiveType("byte", "java.lang.Byte");
  public static final PsiType CHAR = new PsiPrimitiveType("char", "java.lang.Character");
  public static final PsiType DOUBLE = new PsiPrimitiveType("double", "java.lang.Double");
  public static final PsiType FLOAT = new PsiPrimitiveType("float", "java.lang.Float");
  public static final PsiType INT = new PsiPrimitiveType("int", "java.lang.Integer");
  public static final PsiType LONG = new PsiPrimitiveType("long", "java.lang.Long");
  public static final PsiType SHORT = new PsiPrimitiveType("short", "java.lang.Short");
  public static final PsiType BOOLEAN = new PsiPrimitiveType("boolean", "java.lang.Boolean");
  public static final PsiType VOID = new PsiPrimitiveType("void", "java.lang.Void");
  public static final PsiType NULL = new PsiPrimitiveType("null", (String)null);
  public static final PsiType[] EMPTY_ARRAY = new PsiType[0];

  private final PsiAnnotation[] myAnnotations;
  protected PsiType(@NotNull PsiAnnotation[] annotations) {
    myAnnotations = annotations;
  }

  /**
   * Creates array type with this type as a component.
   */
  @NotNull
  public PsiArrayType createArrayType() {
    return new PsiArrayType(this);
  }
  /**
   * Creates array type with this type as a component.
   */
  @NotNull
  public PsiArrayType createArrayType(PsiAnnotation... annotations) {
    return new PsiArrayType(this,annotations);
  }

  /**
   * Returns text of this type that can be presented to user.
   */
  public abstract String getPresentableText();

  @NonNls
  public abstract String getCanonicalText();

  public abstract String getInternalCanonicalText();

  /**
   * Checks if the type is currently valid.
   *
   * @return true if the type is valid, false otherwise.
   * @see PsiElement#isValid()
   */
  public abstract boolean isValid();

  /**
   * Checks whether values of type <code>type</code> can be assigned to rvalues of this type.
   */
  public boolean isAssignableFrom(@NotNull PsiType type) {
    return TypeConversionUtil.isAssignable(this, type);
  }

  /**
   * Checks whether values of type <code>type</code> can be casted to this type.
   */
  public boolean isConvertibleFrom(@NotNull PsiType type) {
    return TypeConversionUtil.areTypesConvertible(type, this);
  }

  /**
   * Checks if the specified string is equivalent to the canonical text of the type.
   *
   * @param text the text to compare with.
   * @return true if the string is equivalent to the type, false otherwise
   */
  public abstract boolean equalsToText(@NonNls String text);

  /**
   * Returns the class type for the java.lang.Object class.
   *
   * @param manager      the PSI manager used to create the class type.
   * @param resolveScope the scope in which the class is searched.
   * @return the class instance.
   */
  @NotNull
  public static PsiClassType getJavaLangObject(PsiManager manager, GlobalSearchScope resolveScope) {
    return JavaPsiFacade.getInstance(manager.getProject()).getElementFactory().createTypeByFQClassName(CommonClassNames.JAVA_LANG_OBJECT, resolveScope);
  }

  /**
   * Returns the class type for the java.lang.Class class.
   *
   * @param manager      the PSI manager used to create the class type.
   * @param resolveScope the scope in which the class is searched.
   * @return the class instance.
   */
  public static PsiClassType getJavaLangClass(PsiManager manager, GlobalSearchScope resolveScope) {
    return JavaPsiFacade.getInstance(manager.getProject()).getElementFactory().createTypeByFQClassName(CommonClassNames.JAVA_LANG_CLASS, resolveScope);
  }

  /**
   * Returns the class type for the java.lang.Throwable class.
   *
   * @param manager      the PSI manager used to create the class type.
   * @param resolveScope the scope in which the class is searched.
   * @return the class instance.
   */
  public static PsiClassType getJavaLangTrowable(PsiManager manager, GlobalSearchScope resolveScope) {
    return JavaPsiFacade.getInstance(manager.getProject()).getElementFactory().createTypeByFQClassName(CommonClassNames.JAVA_LANG_THROWABLE, resolveScope);
  }

  /**
   * Returns the class type for the java.lang.String class.
   *
   * @param manager      the PSI manager used to create the class type.
   * @param resolveScope the scope in which the class is searched.
   * @return the class instance.
   */
  @NotNull
  public static PsiClassType getJavaLangString(PsiManager manager, GlobalSearchScope resolveScope) {
    return JavaPsiFacade.getInstance(manager.getProject()).getElementFactory().createTypeByFQClassName(CommonClassNames.JAVA_LANG_STRING, resolveScope);
  }

  /**
   * Returns the class type for the java.lang.Error class.
   *
   * @param manager      the PSI manager used to create the class type.
   * @param resolveScope the scope in which the class is searched.
   * @return the class instance.
   */
  public static PsiClassType getJavaLangError(PsiManager manager, GlobalSearchScope resolveScope) {
    return JavaPsiFacade.getInstance(manager.getProject()).getElementFactory().createTypeByFQClassName(CommonClassNames.JAVA_LANG_ERROR, resolveScope);
  }

  /**
   * Returns the class type for the java.lang.RuntimeException class.
   *
   * @param manager      the PSI manager used to create the class type.
   * @param resolveScope the scope in which the class is searched.
   * @return the class instance.
   */
  public static PsiClassType getJavaLangRuntimeException(PsiManager manager, GlobalSearchScope resolveScope) {
    return JavaPsiFacade.getInstance(manager.getProject()).getElementFactory().createTypeByFQClassName(CommonClassNames.JAVA_LANG_RUNTIME_EXCEPTION, resolveScope);
  }

  /**
   * Passes the type to the specified visitor.
   *
   * @param visitor the visitor to accept the type.
   * @return the value returned by the visitor.
   */
  public abstract <A> A accept(PsiTypeVisitor<A> visitor);

  /**
   * Returns the number of array dimensions for the type.
   *
   * @return the number of dimensions, or 0 if the type is not an array type.
   */
  public final int getArrayDimensions() {
    PsiType type = this;
    int dims = 0;
    while (type instanceof PsiArrayType) {
      dims++;
      type = ((PsiArrayType)type).getComponentType();
    }
    return dims;
  }

  /**
   * Returns the innermost component type for an array type.
   *
   * @return the innermost (non-array) component of the type, or <code>this</code> if the type is not
   * an array type.
   */
  @NotNull
  public final PsiType getDeepComponentType() {
    PsiType type = this;
    while (type instanceof PsiArrayType) {
      type = ((PsiArrayType)type).getComponentType();
    }
    return type;
  }

  /**
   * Returns the scope in which the reference to the underlying class of a class type is searched.
   *
   * @return the resolve scope instance, or null if the type is a primitive or an array of primitives.
   */
  @Nullable
  public abstract GlobalSearchScope getResolveScope();

  public String toString() {
    //noinspection HardCodedStringLiteral
    return "PsiType:" + getPresentableText();
  }

  /**
   * Returns the list of superclass types for a class type.
   *
   * @return the array of superclass types, or an empty array if the type is not a class type.
   */
  @NotNull
  public abstract PsiType[] getSuperTypes();

  @NotNull
  public PsiAnnotation[] getAnnotations() {
    return myAnnotations;
  }

  public PsiAnnotation findAnnotation(@NotNull @NonNls String qualifiedName) {
    return null; //todo
  }

  @NotNull
  public PsiAnnotation addAnnotation(@NotNull @NonNls String qualifiedName) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  public PsiAnnotation[] getApplicableAnnotations() {
    return getAnnotations();
  }

  protected String getAnnotationsTextPrefix() {
    PsiAnnotation[] annotations = getAnnotations();
    return StringUtil.join(annotations, new Function<PsiAnnotation, String>() {
      public String fun(PsiAnnotation annotation) {
        return "@"+annotation.getQualifiedName();
      }
    }, " ") + (annotations.length == 0 ? "" : " ");
  }

}
