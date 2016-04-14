package org.jrdrew.shortener.configuration;

import org.jrdrew.shortener.AbstractSpringConfigTester;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

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
public class WebInitializerTest extends AbstractSpringConfigTester {

    private WebInitializer webInitializer;

    @Before
    public void setup() {
        this.webInitializer = new WebInitializer();
    }

    @Test
    public void testGetServletMappings() throws Exception {
        assertArrayEquals(this.webInitializer.getServletConfigClasses(), new Class[] {WebMvcConfig.class});
    }

    @Test
    public void testGetRootConfigClasses() throws Exception {
        assertArrayEquals(this.webInitializer.getRootConfigClasses(), new Class[] {ShortenerConfiguration.class});
    }

    @Test
    public void testGetServletConfigClasses() throws Exception {
        assertArrayEquals(this.webInitializer.getServletMappings(), new String[] {"/"});
    }
}