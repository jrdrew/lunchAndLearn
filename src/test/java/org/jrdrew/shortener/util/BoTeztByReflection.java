/**
 * Copyright (c) 2014-2016 Cazena, Inc., as an unpublished work.
 * This notice does not imply unrestricted or public access to these
 * materials which are a trade secret of Cazena, Inc. or its
 * subsidiaries or affiliates (together referred to as "Cazena"), and
 * which may not be copied, reproduced, used, sold or transferred to any
 * third party without Cazena's prior written consent.
 * <p/>
 * All rights reserved.
 * Created by jondrew on 4/14/16.
 */
package org.jrdrew.shortener.util;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This is a helper class which I wrote with a co-worker to make testing POJOs a little easier.
 *
 * @author jdrew
 *
 */
public abstract class BoTeztByReflection<T> {

    private final Class<T> clazz;
    private Object[] objects;
    private T instance;
    private Constructor<T> constructor;
    private List<Method> methodsToIgnore;
    private final Class<?> listType;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        constructor = (Constructor<T>) BoEqualsTeztByReflection
                .getLargestConstructorWithTheMostArguments(clazz);
        Class<?>[] types = constructor.getParameterTypes();
        objects = BoEqualsTeztByReflection.getInitialObjectValuesA(types,
                listType);
        instance = constructor.newInstance(objects);
        methodsToIgnore = new ArrayList<Method>();
    }

    private final int expectedHashCode;
    private final int expectedHashCodeAllNull;
    private final String expectedToString;

    public BoTeztByReflection(Class<T> clazz, int expectedHashCode,
                              int expectedHashCodeAllNull, String expectedToString,
                              Class<?> listType) {
        this.clazz = clazz;
        this.expectedHashCode = expectedHashCode;
        this.expectedHashCodeAllNull = expectedHashCodeAllNull;
        this.expectedToString = expectedToString;
        this.listType = listType;
    }

    @Test
    public void test_toString() throws Exception {
        String actualToString = instance.toString();
        assertEquals(expectedToString, actualToString);
    }

    @Test
    public void test_hashCode() throws Exception {
        int actualHashCode = instance.hashCode();
        assertEquals(expectedHashCode, actualHashCode);
    }

    @Test
    public void test_hashCode_allNull() throws Exception {
        Object[] objects = new Object[this.objects.length];
        Arrays.fill(objects, null);
        T newInstance = constructor.newInstance(objects);
        int actualHashCode = newInstance.hashCode();
        assertEquals(expectedHashCodeAllNull, actualHashCode);
    }

    private static final String GET_PREFIX = "get";

    @Test
    public void test_getters() throws Exception {
        List<Method> getters = getGetters();
        List<Object> expectedValueSet = new ArrayList<Object>();
        Collections.addAll(expectedValueSet, objects);

        for (Method getter : getters) {
            Object value = getter.invoke(instance);
            assertTrue("expecteds " + expectedValueSet + " didn't contain " +
                            value + " for method call: " + getter.getName(),
                    expectedValueSet.remove(value));
        }
        for (Method methodToIgnore : methodsToIgnore) {
            Object value = methodToIgnore.invoke(instance);
            expectedValueSet.remove(value);
        }

        assertTrue("expecteds " + expectedValueSet +
                        " contained values that were not accessible via getters",
                expectedValueSet.size() == 0);

    }

    @Test
    public void test_setters_against_getters() throws Exception {
        List<Method> getters = getGetters();
        List<Method> setters = getSetters();
        assertTrue("number of getters " + getters.size() +
                " not greater than or equal to the number of setters " +
                setters.size(), getters.size() >= setters.size());

        int index = 1043422;
        Object[] args = new Object[1];
        for (Method setter : setters) {
            Class<?>[] types = setter.getParameterTypes();
            Class<?> type = types[0];

            Object value = BoEqualsTeztByReflection.getObjectOfTypeB(type, listType, index++);
            args[0] = value;
            setter.invoke(instance, args);

            Method getter = getGetterForSetter(getters, setter);

            assert getter != null;
            Object actualValue = getter.invoke(instance);
            assertEquals(value, actualValue);
        }
    }

    private Method getGetterForSetter(List<Method> getters, Method setter)
            throws Exception {
        String setterName = setter.getName();
        String propertyPortion = setterName.substring(SET_PREFIX.length());
        String getterName = GET_PREFIX + propertyPortion;
        for (Method getter : getters) {
            String name = getter.getName();
            if (name != null && name.equals(getterName)) {
                return getter;
            }
        }
        fail("getter not found: " + getterName);
        return null;
    }

    private static final String SET_PREFIX = "set";

    private List<Method> getSetters() throws Exception {
        return getMethods(false);
    }

    private static final String GET_CLASS_METHOD_NAME = "getClass";

    private List<Method> getGetters() throws Exception {
        return getMethods(true);
    }

    private List<Method> getMethods(boolean trueForGettersFalseForSetters)
            throws Exception {
        Method[] methods = clazz.getMethods();
        List<Method> methodList = new ArrayList<Method>();
        final String methodPrefix = trueForGettersFalseForSetters ? GET_PREFIX : SET_PREFIX;
        for (Method method : methods) {
            String methodName = method.getName();
            if ((methodName != null && methodName.startsWith(methodPrefix)) && !GET_CLASS_METHOD_NAME.equals(methodName)) {
                Class<?>[] types = method.getParameterTypes();
                Class<?> returnType = method.getReturnType();
                if (method.getDeclaringClass() == clazz) {
                    if (trueForGettersFalseForSetters) {
                        // getters case
                        if (null == types || types.length == 0) {
                            if (null != returnType && !returnType.equals(Void.TYPE)) {
                                // we have a getter
                                methodList.add(method);
                            }
                        }
                    } else {
                        // setters case
                        if (null != types && types.length == 1) {
                            if (null != returnType && returnType.equals(Void.TYPE)) {
                                // we have a setter
                                methodList.add(method);
                            }
                        }
                    }
                } else {
                    methodsToIgnore.add(method);
                }
            }
        }
        return methodList;
    }
}