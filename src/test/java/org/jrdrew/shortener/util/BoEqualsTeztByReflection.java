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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This is a helper class which I wrote with a co-worker to make testing POJOs a little easier.
 *
 * @author jdrew
 *
 */
@SuppressWarnings("rawtypes")
@RunWith(Parameterized.class)
public abstract class BoEqualsTeztByReflection<T> {

    static Constructor<?> getLargestConstructorWithTheMostArguments(
            Class<?> clazz) {

        Constructor[] constructors = clazz.getConstructors();
        Constructor constructor = null;
        int max = -1;
        for (Constructor tmp : constructors) {
            Class<?>[] tmpTypes = tmp.getParameterTypes();
            if (tmpTypes != null) {
                if (tmpTypes.length > max) {
                    max = tmpTypes.length;
                    constructor = tmp;
                }
            }
        }
        return constructor;
    }

    private static String A_STRING_PREFIX = "fooA";
    private static int A_INT_PREFIX = 10;
    private static long A_LONG_PREFIX = 1001;
    private static int A_ENUM_POSITION = 0;
    private static Date A_DATE_VALUE = new Date(54321L);
    private static String B_STRING_PREFIX = "fooB";
    private static int B_INT_PREFIX = 11;
    private static long B_LONG_PREFIX = 2012;
    private static int B_ENUM_POSITION = 1;
    private static Date B_DATE_VALUE = new Date(12345L);

    static Object getObjectOfTypeB(Class<?> type, Class<?> listType, int index)
            throws Exception {
        return getObjectOfType(type, listType, index, B_STRING_PREFIX,
                B_INT_PREFIX, B_LONG_PREFIX, Boolean.TRUE, B_ENUM_POSITION, B_DATE_VALUE);
    }

    private static Object getObjectOfType(Class<?> type, Class<?> listType,
                                          int index, String stringPrefix, int intPrefix, long longPrefix,
                                          Boolean value, int enumPosition, Date dateValue) throws Exception {
        if (type.equals(String.class)) {
            return stringPrefix + index;
        } else if (type.equals(Integer.class)) {
            return Integer.valueOf(intPrefix + index);
        } else if (type.equals(Long.class)) {
            return Long.valueOf(longPrefix + index);
        } else if (type.equals(Double.class)) {
            return Double.valueOf(longPrefix + index);
        } else if (type.equals(Boolean.class)) {
            return value;
        } else if (type.isEnum()) {
            return type.getEnumConstants()[enumPosition];
        } else if (type.equals(Date.class)) {
            return dateValue;
        } else if (type.equals(List.class)) {
            ArrayList<Object> list = null;
            if (listType != null) {
                Constructor<?> constructor = getLargestConstructorWithTheMostArguments(listType);
                Class<?>[] types = constructor.getParameterTypes();
                Object[] objects = getInitialObjectValues(!value, types, null);
                list = new ArrayList<Object>();
                list.add(constructor.newInstance(objects));
            }
            return list;
        } else if (Object.class.isAssignableFrom(type)) {
            Constructor<?> constructor = getLargestConstructorWithTheMostArguments(type);
            Class<?>[] types = constructor.getParameterTypes();
            Object[] objects = getInitialObjectValues(!value, types, null);
            return constructor.newInstance(objects);
        } else {

            throw new RuntimeException("unsupported type " + type +
                    ", please add support in " +
                    BoEqualsTeztByReflection.class);
        }
    }

    private static Object getObjectOfTypeA(Class<?> type, Class<?> listType,
                                           int index) throws Exception {
        return getObjectOfType(type, listType, index, A_STRING_PREFIX,
                A_INT_PREFIX, A_LONG_PREFIX, Boolean.FALSE, A_ENUM_POSITION, A_DATE_VALUE);
    }

    static Object[] getInitialObjectValuesA(Class<?>[] types, Class<?> listType)
            throws Exception {
        return getInitialObjectValues(true, types, listType);
    }

    private static Object[] getInitialObjectValuesB(Class<?>[] types,
                                                    Class<?> listType) throws Exception {
        return getInitialObjectValues(false, types, listType);
    }

    private static Object[] getInitialObjectValues(boolean isA,
                                                   Class<?>[] types, Class<?> listType) throws Exception {
        Object[] objects = new Object[types.length];
        for (int counter = 0; counter < types.length; counter++) {
            Class<?> type = types[counter];
            if (isA) {
                objects[counter] = getObjectOfTypeA(type, listType, counter);
            } else {
                objects[counter] = getObjectOfTypeB(type, listType, counter);
            }
        }
        return objects;
    }

    public static Collection data(Class<?> clazz, Class<?> listType) {
        // search for the largest Constructor, the one that takes the most
        // arguments
        Constructor constructor = getLargestConstructorWithTheMostArguments(clazz);
        Class<?>[] types = constructor.getParameterTypes();

        // we have 4 test conditions for every parameter on the constructor.
        // matching, (A,B), (null, A), (A, null).
        try {
            Object[] objectAs = getInitialObjectValuesA(types, listType);
            Object[] objectBs = getInitialObjectValuesB(types, listType);

            List<Object[]> list = new ArrayList<Object[]>();
            Object[] testCase;

            // (A, A)
            testCase = new Object[3];
            testCase[0] = constructor.newInstance(objectAs);
            testCase[1] = constructor.newInstance(objectAs);
            testCase[2] = true;
            list.add(testCase);

            // all (A, B)
            testCase = new Object[3];
            testCase[0] = constructor.newInstance(objectAs);
            testCase[1] = constructor.newInstance(objectBs);
            testCase[2] = false;
            list.add(testCase);

            // all (null, A)
            Object[] allNulls = new Object[objectAs.length];
            for (int counter = 0; counter < allNulls.length; counter++) {
                allNulls[counter] = null;
            }
            testCase = new Object[3];
            testCase[0] = constructor.newInstance(allNulls);
            testCase[1] = constructor.newInstance(objectAs);
            testCase[2] = false;
            list.add(testCase);

            // all (A, null)
            testCase = new Object[3];
            testCase[0] = constructor.newInstance(objectAs);
            testCase[1] = constructor.newInstance(allNulls);
            testCase[2] = false;
            list.add(testCase);

            // compare null
            testCase = new Object[3];
            testCase[0] = constructor.newInstance(objectAs);
            testCase[1] = null;
            testCase[2] = false;
            list.add(testCase);

            // compare same object
            testCase = new Object[3];
            testCase[0] = constructor.newInstance(objectAs);
            testCase[1] = testCase[0];
            testCase[2] = true;
            list.add(testCase);

            Object[] tmp = new Object[objectAs.length];
            for (int counter = 0; counter < tmp.length; counter++) {
                // for exactly one field the only difference is (A, B)
                testCase = new Object[3];
                testCase[0] = constructor.newInstance(objectAs);
                System.arraycopy(objectAs, 0, tmp, 0, tmp.length);
                tmp[counter] = objectBs[counter];
                testCase[1] = constructor.newInstance(tmp);
                testCase[2] = false;
                list.add(testCase);

                // for exactly one field the only difference is (null, A)
                System.arraycopy(objectAs, 0, tmp, 0, tmp.length);
                tmp[counter] = null;
                testCase = new Object[3];
                testCase[0] = constructor.newInstance(tmp);
                testCase[1] = constructor.newInstance(objectAs);
                testCase[2] = false;
                list.add(testCase);

                // for exactly one field the only difference is (A, null)
                testCase = new Object[3];
                testCase[0] = constructor.newInstance(objectAs);
                testCase[1] = constructor.newInstance(tmp);
                testCase[2] = false;
                list.add(testCase);

                // for exactly one field the only difference is (null, null)
                testCase = new Object[3];
                testCase[0] = constructor.newInstance(tmp);
                testCase[1] = constructor.newInstance(tmp);
                testCase[2] = true;
                list.add(testCase);
            }

            return list;
        } catch (InvocationTargetException e) {
            throw new RuntimeException("failed: " + e, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("failed: " + e, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("failed: " + e, e);
        } catch (Exception e) {
            throw new RuntimeException("failed: " + e, e);
        }
    }

    private final T expected;
    private final T actual;
    private final boolean expectedResult;

    public BoEqualsTeztByReflection(T expected, T actual, boolean expectedResult) {
        this.expected = expected;
        this.actual = actual;
        this.expectedResult = expectedResult;
    }

    @Test
    public void testEqualsObject() {
        assertEquals("expected: " + expected.toString() + " but was " + actual,
                expectedResult, expected.equals(actual));
        if (actual != null) {
            assertFalse(expected.equals(actual.toString()));
        }
    }

}