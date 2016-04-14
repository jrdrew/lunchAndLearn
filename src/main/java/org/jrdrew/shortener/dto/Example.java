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

public class Example {
    private String exampleString;

    public Example() {
    }

    public Example(String exampleString) {
        this.exampleString = exampleString;
    }

    public String getExampleString() {
        return exampleString;
    }

    public void setExampleString(String exampleString) {
        this.exampleString = exampleString;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Example example = (Example) object;

        return exampleString != null ? exampleString.equals(example.exampleString) : example.exampleString == null;

    }

    @Override
    public int hashCode() {
        return exampleString != null ? exampleString.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Example{" +
                "exampleString='" + exampleString + '\'' +
                '}';
    }
}
