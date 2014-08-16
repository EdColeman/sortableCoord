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

/**
 * Class provides static methods to encode and decode interleaved bytes using shift operations instead of lookup (Morton tables)
 */
public class ShiftCoder {

    /**
     * Static only methods in class, hide constructor.
     */
    private ShiftCoder() {

    }

    /**
     * Interleave the input bytes with so the the 16 bit word is Odd-bit-7 Even-bit-7, ... Odd-bit-0, Even-bit-0.
     *
     * @param even the byte that will occupy the even bit positions in a 16 bit word.
     * @param odd  the byte that will occupy the odd bit positions in a 16 bit word.
     * @return a packed 16 bit word.
     */
    public static int interleave(byte even, byte odd) {

        int result = 0;

        for (int i = 0; i < 8; i++) {
            result |= (odd & (0x01 << i)) << i
                    | (even & (0x01 << i)) << (i + 1);
        }

        return result;
    }

    /**
     * De-interleaves a packed 16 bit word with interleaved odd, even bits and returns the odd bits as a byte.
     *
     * @param value the interleaved 16 bit word.
     * @return odd bit values in input as byte.
     */
    public static byte odd(final int value) {
        return decode(value >> 1);
    }

    /**
     * De-interleaves a packed 16 bit word with interleaved odd, even bits and returns the even bits as a byte.
     *
     * @param value the interleaved 16 bit word.
     * @return even bit values in input as byte.
     */
    public static byte even(final int value) {
        return decode(value);
    }

    /**
     * De-interleaves 16 bit word, returning the even (0,2,..) bits as a byte.  To get the odd bits (1,3,..) shift the input before calling this method.
     *
     * @param value 16 bit interleaved word
     * @return a byte with the even bits of the input.
     */
    private static byte decode(final int value) {

        int inputWord = value & 0x0000ffff;

        int mask = 0x01;

        byte r = 0;

        int s = inputWord;

        for (int i = 0; i < 8; i++) {

            r |= s & mask;

            mask <<= 1;
            s >>= 1;

        }

        return r;
    }

}
