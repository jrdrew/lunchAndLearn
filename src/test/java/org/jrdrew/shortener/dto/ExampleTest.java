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

import org.jrdrew.shortener.util.BoTeztByReflection;

public class ExampleTest extends BoTeztByReflection<Example> {

    private static final int EXPECTED_HASH_CODE = 97614677;
    private static final int EXPECTED_HASH_CODE_ALL_NULL = 0;
    private static final String EXPECTED_TO_STRING = "Example{exampleString='fooA0'}";

    public ExampleTest() {
        super(Example.class, EXPECTED_HASH_CODE, EXPECTED_HASH_CODE_ALL_NULL, EXPECTED_TO_STRING, null);
    }
}
