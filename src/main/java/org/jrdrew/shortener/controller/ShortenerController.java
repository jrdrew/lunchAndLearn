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
package org.jrdrew.shortener.controller;

import org.jrdrew.shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ShortenerController {

    private UrlService urlService;

    @Autowired
    public ShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String get() {
        return "hello world";
    }

    @RequestMapping(path = "{shortUrl}", method = RequestMethod.GET)
    @ResponseBody
    public String getLongUrl(@PathVariable String shortUrl) {
        return urlService.getLongUrl(shortUrl);

    }


}
