/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.etcoleman.util.sortedCoord;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class LexiSortCoordTest {

    private static final Logger log = LoggerFactory.getLogger(LexiSortCoordTest.class);

    @Test
    public void inLimitsLat() {

        String coded = LexiSortCoord.coordToString(45.0, -10.0);

        log.trace("Coded:  {}", coded);

        double[] result = LexiSortCoord.fromString(coded);

        log.trace("Latitude:  {}", result[LexiSortCoord.LATITUDE]);
        log.trace("Longitude:  {}", result[LexiSortCoord.LONGITUDE]);

    }

    @Test
    public void inLimitsLat2() {

        String coded = LexiSortCoord.coordToString(-89.999999, -179.999999);

        log.trace("Coded:  {}", coded);

        double[] result = LexiSortCoord.fromString(coded);

        log.trace("Latitude:  {}", result[LexiSortCoord.LATITUDE]);
        log.trace("Longitude: {}", result[LexiSortCoord.LONGITUDE]);

    }

    @Test
    public void inLimitsLat3() {

        String coded = LexiSortCoord.coordToString(89.999999, 179.999999);

        log.trace("Coded:  {}", coded);

        double[] result = LexiSortCoord.fromString(coded);

        log.trace("Latitude:  {}", result[LexiSortCoord.LATITUDE]);
        log.trace("Longitude: {}", result[LexiSortCoord.LONGITUDE]);
    }

    @Test
    public void sort1() {

        String lowerLeft = LexiSortCoord.coordToString(45.0, 45.0);
        String upperRight = LexiSortCoord.coordToString(46.0, 46.0);

        String inBox = LexiSortCoord.coordToString(45.5, 45.5);

        log.trace("Coded LL:  " + lowerLeft);
        log.trace("Coded IB:  " + inBox);
        log.trace("Coded UR:  " + upperRight);

        assertTrue(lowerLeft.compareTo(inBox) < 0);
        assertTrue(upperRight.compareTo(inBox) > 0);

        inBox = LexiSortCoord.coordToString(45.9, 45.9);

        log.trace("Coded IB:  " + inBox);

        assertTrue(lowerLeft.compareTo(inBox) < 0);
        assertTrue(upperRight.compareTo(inBox) > 0);

        String outBox = LexiSortCoord.coordToString(0.0, 1.0);

        log.trace("Coded LonOBx:  " + LexiSortCoord.coordToString(0.0, 1.0));
        log.trace("Coded LonOBx:  " + LexiSortCoord.coordToString(0.0, 2.0));
        log.trace("Coded LonOBx:  " + LexiSortCoord.coordToString(0.0, -1.0));
        log.trace("Coded LonOBx:  " + LexiSortCoord.coordToString(0.0, -2.0));

        log.trace("Coded LatOBx:  " + LexiSortCoord.coordToString(1.0, 0));
        log.trace("Coded LatOBx:  " + LexiSortCoord.coordToString(2.0, 0));
        log.trace("Coded LatOBx:  " + LexiSortCoord.coordToString(-1.0, 0));
        log.trace("Coded LatOBx:  " + LexiSortCoord.coordToString(-2.0, 0));

        log.trace("Coded:  " + LexiSortCoord.coordToString(45.0, 45.0));
        log.trace("Coded:  " + LexiSortCoord.coordToString(45.0001, 45.0));
        log.trace("Coded:  " + LexiSortCoord.coordToString(45.0, 46.0));
        log.trace("Coded:  " + LexiSortCoord.coordToString(45.0, 47.0));

        assertTrue(lowerLeft.compareTo(outBox) > 0);
        assertTrue(upperRight.compareTo(outBox) > 0);


    }

    @Test
    public void latitude() {

        String prev = LexiSortCoord.coordToString(-89.99999, 0.0);

        for (int latitude = -89; latitude < 90; latitude++) {

            String curr = LexiSortCoord.coordToString(latitude, 0.0);

            log.trace("Lat: " + LexiSortCoord.coordToString(latitude, 0.0));

            assertTrue(prev.compareTo(curr) < 0);

            prev = curr;
        }
    }

    @Test
    public void longitude() {

        String prev = LexiSortCoord.coordToString(0.0, -179.9999);

        for (int longitude = -179; longitude < 179; longitude++) {

            String curr = LexiSortCoord.coordToString(0.0, longitude);

            log.trace("Lon: " + LexiSortCoord.coordToString(0.0, longitude));

            assertTrue(prev.compareTo(curr) < 0);

            prev = curr;
        }
    }

    @Test
    public void diag() {

        String prev = LexiSortCoord.coordToString(-89.9, -179.9999);

        for (int latitude = -89; latitude < 90; latitude++) {

            String curr = LexiSortCoord.coordToString(latitude, latitude * 2);

            log.trace("Lon: " + LexiSortCoord.coordToString(latitude, latitude * 2));

            assertTrue(prev.compareTo(curr) < 0);

            prev = curr;
        }
    }

    @Test
    public void diag2() {

        String prev = LexiSortCoord.coordToString(89.9, 179.9999);

        for (int latitude = 89; latitude > -90; latitude--) {

            String curr = LexiSortCoord.coordToString(latitude, latitude * 2);

            log.trace("Lon: " + LexiSortCoord.coordToString(latitude, latitude * 2));

            assertTrue(prev.compareTo(curr) > 0);

            prev = curr;
        }
    }

    @Test
    public void inLimitsLong() {
        LexiSortCoord.coordToString(0.00, -76.861245);
    }

    @Test
    public void inLimitsSwp() {
        LexiSortCoord.coordToString(39.209856, -76.861245);
    }

    @Test(expected = IllegalArgumentException.class)
    public void latTooSmall() {
        LexiSortCoord.coordToString(-90, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void latToBig() {
        LexiSortCoord.coordToString(90, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void longToSmall() {
        LexiSortCoord.coordToString(0, -180);
    }

    @Test(expected = IllegalArgumentException.class)
    public void longToBig() {
        LexiSortCoord.coordToString(0, 180);
    }

    @Test
    public void normalLatitude() {

        log.trace("-90             " + LexiSortCoord.normalize(-90, 90));

        log.trace("-89.9           "
                + LexiSortCoord.normalize(-89.999, 90));

        log.trace("0               " + LexiSortCoord.normalize(0, 90));

        log.trace("1.23456789      "
                + LexiSortCoord.normalize(1.23456789, 90));

        log.trace("1.234567891     "
                + LexiSortCoord.normalize(1.234567891, 90));

        log.trace("89.9            " + LexiSortCoord.normalize(89.9, 90));

        log.trace("89.99999        "
                + LexiSortCoord.normalize(89.99999, 90));

        log.trace("89.9999999      "
                + LexiSortCoord.normalize(89.9999999, 90));

        log.trace("90              " + LexiSortCoord.normalize(90, 90));


        log.trace("-180 " + LexiSortCoord.normalize(-180, 180));

        log.trace("0    " + LexiSortCoord.normalize(-0, 180));

        log.trace("179.999998  "
                + LexiSortCoord.normalize(179.999998, 180));

        log.trace("179.999999  "
                + LexiSortCoord.normalize(179.999999, 180));

        log.trace("180  " + LexiSortCoord.normalize(180, 180));

    }

    @Test
    public void rebase() {

        double maxLatitude = 90;

        double delta = 0.000001;

        assertEquals(-90, LexiSortCoord.rebase(
                LexiSortCoord.normalize(-90, maxLatitude), maxLatitude), delta);
        assertEquals(0, LexiSortCoord.rebase(
                LexiSortCoord.normalize(0, maxLatitude), maxLatitude), delta);
        assertEquals(90, LexiSortCoord.rebase(
                LexiSortCoord.normalize(90, maxLatitude), maxLatitude), delta);

        double maxLongitude = 180;

        assertEquals(-180, LexiSortCoord.rebase(
                LexiSortCoord.normalize(-180, maxLongitude), maxLongitude), delta);
        assertEquals(0, LexiSortCoord.rebase(
                LexiSortCoord.normalize(0, maxLongitude), maxLongitude), delta);
        assertEquals(180, LexiSortCoord.rebase(
                LexiSortCoord.normalize(180, maxLongitude), maxLongitude), delta);

        log.trace("{}", LexiSortCoord.rebase(
                LexiSortCoord.normalize(-90, maxLatitude), maxLatitude));
        log.trace("{}", LexiSortCoord.rebase(
                LexiSortCoord.normalize(0, maxLatitude), maxLatitude));
        log.trace("{}", LexiSortCoord.rebase(
                LexiSortCoord.normalize(45, maxLatitude), maxLatitude));
        log.trace("{}", LexiSortCoord.rebase(
                LexiSortCoord.normalize(90, maxLatitude), maxLatitude));

        log.trace("{}", LexiSortCoord.rebase(
                LexiSortCoord.normalize(180, maxLongitude), maxLongitude));

    }
}
