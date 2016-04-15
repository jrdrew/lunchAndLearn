package org.jrdrew.shortener.controller;

import org.jrdrew.shortener.AbstractSpringConfigTester;
import org.jrdrew.shortener.service.UrlService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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
public class ShortenerControllerTest extends AbstractSpringConfigTester {

    @Autowired
    protected WebApplicationContext wac;
    private UrlService urlService = mock(UrlService.class);

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(new ShortenerController(urlService)).build();
    }

    @Test
    public void testGet() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo("hello world"));
    }

    @Test
    public void testGetShortUrl() throws Exception {

        String google = "http://www.google.com";
        when(urlService.getLongUrl("1")).thenReturn(google);

        MvcResult mvcResult = this.mockMvc.perform(get("/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString(), equalTo(google));
    }

}