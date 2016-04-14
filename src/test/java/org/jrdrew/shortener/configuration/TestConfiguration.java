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
package org.jrdrew.shortener.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("org.jrdrew.shortener")
@EnableWebMvc
public class TestConfiguration extends ShortenerConfiguration {

}
