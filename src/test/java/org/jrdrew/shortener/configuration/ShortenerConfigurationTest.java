package org.jrdrew.shortener.configuration;

import org.jrdrew.shortener.AbstractSpringConfigTester;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
 * Created by jondrew on 4/14/16.
 */
public class ShortenerConfigurationTest extends AbstractSpringConfigTester {

    @Autowired
    private ShortenerConfiguration shortenerConfiguration;

    @Test
    public void testGetPropertyPlaceholderConfigurer() throws Exception {
        assertNotNull(shortenerConfiguration.getRestTemplate());
    }

    @Test
    public void testDataSource() throws Exception {
        assertNotNull(shortenerConfiguration.getDataSource());
    }

    @Test
    public void testGetRestTemplate() throws Exception {
        assertNotNull(shortenerConfiguration.getRestTemplate());
    }
}