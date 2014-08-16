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

import java.math.BigInteger;

/**
 *
 */
public class LexiSortCoord {

    public static final int LATITUDE = 0;
    public static final int LONGITUDE = 1;

    private static final double minLatitude = -90.0;
    private static final double maxLatitude = 90.0;
    private static final double minLongitude = -180.0;
    private static final double maxLongitude = 180.0;

    // provides about 6 decimal places of accuracy or about 1/100 of a second.  One second (1/3600) is 0.000277778;
    private static final double scale = Integer.MAX_VALUE;

    /**
     * Takes decimal latitude and longitude values and turns them into a string
     * that is lexigraphically sortable. The values are interleaved with
     * longitude bit as msb.
     *
     * @param latitude  decimal latitude value between (-90 and 90) exclusive.
     * @param longitude decimal longitude value between (-180 and 180) exclusive.
     * @return a sortable string.
     */
    public static String coordToString(final double latitude,
                                       final double longitude) {

        if (latitude <= minLatitude || latitude >= maxLatitude) {
            throw new IllegalArgumentException(
                    "Latitude out of range (-90 < latitde < 90), received "
                            + latitude);
        }

        if (longitude <= minLongitude || longitude >= maxLongitude) {
            throw new IllegalArgumentException(
                    "Longitude out of range (-180 < longitude < 180), received "
                            + longitude);
        }

        long x = MortonTable.mortonTable(normalize(longitude, maxLongitude),
                normalize(latitude, maxLatitude));

        return String.format("%016x", x);

    }

    /**
     * Returns decimal values for latitude and longitude that have been
     * converted into a string that is lexicographically sortable.
     *
     * @param value encoded string coordinates
     * @return the decoded coordinates in array, index using {@code LexiSortCoord.LATITUDE}, and {@code LexiSortCoord.LONGITUDE}
     */
    public static double[] fromString(final String value) {

        double[] results = new double[2];

        long in = new BigInteger(value, 16).longValue();

        int[] deinterleaved = separate(in);

        results[LONGITUDE] = rebase(deinterleaved[LONGITUDE], maxLongitude);
        results[LATITUDE] = rebase(deinterleaved[LATITUDE], maxLatitude);

        return results;
    }

    /**
     * Converts a decimal longitude from -180 to 180 into 0 - 360 or a decimal
     * latitude -90 to 90 into 0 - 180 so that 90S (-90), 180W (-180) is 0,0.
     * The is the normalize from [0 to 1) multiplied by a scaling factor it to
     * fit into 32 bit integer.
     *
     * @param value    a latitude or longitude.
     * @param maxRange either 90 for latitude or 180 for longitude.
     * @return scaled 32 bit value representing 0 - .999999
     */
    public static int normalize(final double value, final double maxRange) {
        //return (int) ((value + maxRange) / (maxRange * 2.0) * scale);
        return (int) ((value + maxRange) / (180 * 2.0) * scale);
    }

    /**
     * Take a scaled, normalized integer longitude or latitude and returns a
     * value in the normal range (-90.0 to 90.0) for latitude or (-180.0 to
     * 180.0)
     *
     * @param value    a normalize value in range of 0.0 <= value < 1.0
     * @param maxRange the maximum value for the variable (90 for Latitude, 180 for Longitude)
     * @return a value in the range of -90 < result < 90 for latitude, -180 < result < 180 for longitude.
     */
    public static double rebase(final int value, final double maxRange) {
        // return (((double) value / scale) * (maxRange * 2.0)) - maxRange;
        return (((double) value / scale) * (180 * 2.0)) - maxRange;
    }

    /**
     * Takes 64 bit interleaved value and returns 2 32 bit values. The values
     * for longitude are stored in the even bits and the latitude values are
     * stored in the odd bits. This way the longitude bits are the MSB becuase
     * of its greater range, -180 to 180 vs -90 to 90.
     *
     * @param interleaved a 64 bit value of longitude, latitude with msb longitude bit
     *                    first and stored in odd bits.
     * @return an array with two 32 bits words, can be accessed with LATITUDE
     * and LONGITUDE constants.
     */
    private static int[] separate(long interleaved) {

        int[] results = new int[2];

        results[LONGITUDE] = (MortonTable.decodeOdd((int) (interleaved >> 48)) << 24)
                | (MortonTable.decodeOdd((int) (interleaved >> 32)) << 16)
                | (MortonTable.decodeOdd((int) (interleaved >> 16)) << 8)
                | (MortonTable.decodeOdd((int) interleaved));

        results[LATITUDE] = (MortonTable.decodeEven((int) (interleaved >> 48)) << 24)
                | (MortonTable.decodeEven((int) (interleaved >> 32)) << 16)
                | (MortonTable.decodeEven((int) (interleaved >> 16)) << 8)
                | (MortonTable.decodeEven((int) interleaved));

        return results;
    }
}
