package org.jrdrew.shortener;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

/**
 * Copyright (c) 2014-2016 Cazena, Inc., as an unpublished work.
 * This notice does not imply unrestricted or public access to these
 * materials which are a trade secret of Cazena, Inc. or its
 * subsidiaries or affiliates (together referred to as "Cazena"), and
 * which may not be copied, reproduced, used, sold or transferred to any
 * third party without Cazena's prior written consent.
 * <p/>
 * All rights reserved.
 * Created by jondrew on 4/13/16.
 */
public class CanaryTest {

    @Test
    public void testIsThere() {
        assertNotNull(new Canary());
    }

}