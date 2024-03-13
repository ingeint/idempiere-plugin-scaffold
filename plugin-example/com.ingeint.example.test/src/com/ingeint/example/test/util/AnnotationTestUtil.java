/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Copyright (C) 2024 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.example.test.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Annotations assertions
 */
public final class AnnotationTestUtil {

	private AnnotationTestUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static void assertAnnotations(String fieldOrClass, List<Annotation> actualAnnotations, List<Class<?>> expectedAnnotation, String parameterName, Object parameterValue) {
        if (expectedAnnotation.size() != actualAnnotations.size()) {
            throw new AssertionError(String.format("%s: Expected %d annotations, but found %d", fieldOrClass, expectedAnnotation.size(), actualAnnotations.size()));
        }

        expectedAnnotation.forEach(clazz -> {
            List<Annotation> annotations = actualAnnotations.stream().filter(annotation -> annotation.annotationType().isAssignableFrom(clazz)).toList();

            if (annotations.isEmpty()) {
                throw new AssertionError(String.format("No annotation %s found", clazz.getName()));
            }

            if (parameterName != null) {

                if (parameterName.isEmpty()) {
                    throw new AssertionError(String.format("Parameter %s not allowed", parameterName));
                }

                annotations.forEach(annotation -> {

                    List<Method> methods = Arrays.stream(annotation.annotationType().getDeclaredMethods()).filter(method -> method.getName().equals(parameterName))
                            .toList();

                    if (methods.isEmpty()) {
                        throw new AssertionError(String.format("No parameter %s found", parameterName));
                    }

                    methods.forEach(method -> {

                        Object result;

                        try {
                            result = method.invoke(annotation, (Object[]) null);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new AssertionError(e);
                        }

                        if(result instanceof Object[]){
                            if (!((Object[]) result)[0].equals(parameterValue)) {
                                throw new AssertionError(String.format("Expected: %s and was %s for parameter %s", parameterValue.toString(), result, parameterName));
                            }
                        }else {
                            if (!result.equals(parameterValue)) {
                                throw new AssertionError(String.format("Expected: %s and was %s for parameter %s", parameterValue.toString(), result, parameterName));
                            }
                        }

                    });
                });
            }

        });
    }

    /**
     * Asserts if a class has one or more annotations.
     *
     * @param classUnderTest     Class under test
     * @param expectedAnnotation Annotations that should be present in the class
     */
    public static void assertClassAnnotation(Class<?> classUnderTest, Class<?>... expectedAnnotation) {
        assertAnnotations(classUnderTest.toString(), Arrays.asList(classUnderTest.getAnnotations()), Arrays.asList(expectedAnnotation), null, null);
    }

    /**
     * Asserts if a field has one or more annotations.
     *
     * @param classUnderTest     Class under test
     * @param fieldUnderTest     Filed under test
     * @param expectedAnnotation Annotations that should be present in the filed
     */
    public static void assertFieldAnnotation(Class<?> classUnderTest, String fieldUnderTest, Class<?>... expectedAnnotation) {
        try {
            assertAnnotations(fieldUnderTest, Arrays.asList(classUnderTest.getDeclaredField(fieldUnderTest).getDeclaredAnnotations()), Arrays.asList(expectedAnnotation), null, null);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Asserts if a method
     *
     * @param classUnderTest     Class under test
     * @param methodUnderTest    Method under test
     * @param expectedAnnotation Expected annotations
     */
    public static void assertMethodAnnotation(Class<?> classUnderTest, String methodUnderTest, Class<?>... expectedAnnotation) {
        try {
            assertAnnotations(methodUnderTest, Arrays.asList(classUnderTest.getDeclaredMethod(methodUnderTest).getAnnotations()), Arrays.asList(expectedAnnotation), null, null);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Asserts if a class has an annotation and if this annotation has a parameter
     *
     * @param classUnderTest         Class under test
     * @param expectedAnnotation     Expected annotation
     * @param parameterName          Parameter name
     * @param expectedParameterValue Expected parameter value
     */
    public static void assertClassAnnotationParameter(Class<?> classUnderTest, Class<?> expectedAnnotation, String parameterName, Object expectedParameterValue) {
        assertAnnotations(classUnderTest.toString(), Arrays.asList(classUnderTest.getAnnotations()), Collections.singletonList(expectedAnnotation), parameterName, expectedParameterValue);
    }

    /**
     * Asserts if a method has an annotation and if this annotation has a parameter
     *
     * @param classUnderTest         Class under test
     * @param methodUnderTest        Method under test
     * @param expectedAnnotation     Expected annotation
     * @param parameterName          Parameter name
     * @param expectedParameterValue Expected parameter value
     */
    public static void assertMethodAnnotationParameter(Class<?> classUnderTest, String methodUnderTest, Class<?> expectedAnnotation, String parameterName, Object expectedParameterValue) {
        try {
            assertAnnotations(methodUnderTest, Arrays.asList(classUnderTest.getDeclaredMethod(methodUnderTest).getAnnotations()), Collections.singletonList(expectedAnnotation), parameterName,
                    expectedParameterValue);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Asserts if a field has an annotation and if this annotation has a parameter
     *
     * @param classUnderTest         Class under test
     * @param fieldUnderTest         Field under test
     * @param expectedAnnotation     Expected annotation
     * @param parameterName          Parameter name
     * @param expectedParameterValue Expected parameter value
     */
    public static void assertFieldAnnotationParameter(Class<?> classUnderTest, String fieldUnderTest, Class<?> expectedAnnotation, String parameterName, Object expectedParameterValue) {
        try {
            assertAnnotations(fieldUnderTest, Arrays.asList(classUnderTest.getDeclaredField(fieldUnderTest).getAnnotations()), Collections.singletonList(expectedAnnotation), parameterName,
                    expectedParameterValue);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }
}
