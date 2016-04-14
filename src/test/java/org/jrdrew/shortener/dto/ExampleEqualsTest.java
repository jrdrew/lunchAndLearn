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
package org.jrdrew.shortener.dto;

import org.jrdrew.shortener.util.BoEqualsTeztByReflection;
import org.junit.runners.Parameterized;

import java.util.Collection;

public class ExampleEqualsTest extends BoEqualsTeztByReflection<Example> {

    public ExampleEqualsTest(Example expected, Example actual, boolean expectedResult) {
        super(expected, actual, expectedResult);
    }

    @SuppressWarnings("rawtypes")
    @Parameterized.Parameters
    public static Collection data() {
        return data(Example.class, null);
    }


}