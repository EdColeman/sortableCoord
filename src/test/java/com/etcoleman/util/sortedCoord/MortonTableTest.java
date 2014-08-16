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

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class MortonTableTest {

    @Test
    public void encodeLsbMortonTable16() {

        // 0 no bits set
        assertEquals(0, MortonTable.mortonTable16(0, 0));

        // even bit 0 set
        assertEquals(1, MortonTable.mortonTable16(0, 1));

        // odd bit 1 set
        assertEquals(2, MortonTable.mortonTable16(1, 0));

        // both lsb set
        assertEquals(3, MortonTable.mortonTable16(1, 1));

    }

    @Test
    public void decode() {

        assertEquals(0, MortonTable.decodeEven(MortonTable.mortonTable16(0, 0)));
        assertEquals(0, MortonTable.decodeOdd(MortonTable.mortonTable16(0, 0)));

        assertEquals(1, MortonTable.decodeEven(MortonTable.mortonTable16(0, 1)));
        assertEquals(0, MortonTable.decodeOdd(MortonTable.mortonTable16(0, 1)));

        assertEquals(0, MortonTable.decodeEven(MortonTable.mortonTable16(1, 0)));
        assertEquals(1, MortonTable.decodeOdd(MortonTable.mortonTable16(1, 0)));

        assertEquals(0x80, MortonTable.decodeEven(MortonTable.mortonTable16(0, 0x80)));
        assertEquals(0, MortonTable.decodeOdd(MortonTable.mortonTable16(0, 0x80)));

        assertEquals(0, MortonTable.decodeEven(MortonTable.mortonTable16(0x80, 0)));
        assertEquals(0x80, MortonTable.decodeOdd(MortonTable.mortonTable16(0x80, 0)));

        assertEquals(0x80, MortonTable.decodeEven(MortonTable.mortonTable16(0x80, 0x80)));
        assertEquals(0x80, MortonTable.decodeOdd(MortonTable.mortonTable16(0x80, 0x80)));

        assertEquals(0x82, MortonTable.decodeEven(MortonTable.mortonTable16(0x81, 0x82)));
        assertEquals(0x81, MortonTable.decodeOdd(MortonTable.mortonTable16(0x81, 0x82)));

    }

    @Test
    public void encodeMsbMortonTable16() {

        // 0 no bits set
        assertEquals(0, MortonTable.mortonTable16(0, 0));

        // even bit 0x4000 0000 set
        assertEquals(0x40000000, MortonTable.mortonTable16(0, 0x8000));

        // odd bit 1 set
        assertEquals(0x80000000, MortonTable.mortonTable16(0x8000, 0));

        // both lsb set
        assertEquals(0xc0000000, MortonTable.mortonTable16(0x8000, 0x8000));

    }

    @Test
    public void interleaveMortonTable16() {

        // all even set
        assertEquals(0x55555555, MortonTable.mortonTable16(0, 0xffff));

        // all odd set
        assertEquals(0xaaaaaaaa, MortonTable.mortonTable16(0xffff, 0));

        // all set
        assertEquals(0xffffffff, MortonTable.mortonTable16(0xffff, 0xffff));

    }

    @Test
    public void encodeLsbMortonTable8() {

        // 0 no bits set
        int value = MortonTable.mortonTable8((byte) 0, (byte) 0);
        assertEquals(0, value);

        assertEquals(0, ShiftCoder.odd(value));
        assertEquals(0, MortonTable.decodeOdd(value));

        assertEquals(0, ShiftCoder.even(value));
        assertEquals(0, MortonTable.decodeEven(value));

        // even bit 0 set
        value = MortonTable.mortonTable8((byte) 0, (byte) 1);
        assertEquals(1, value);

        assertEquals(0, ShiftCoder.odd(value));
        assertEquals(0, MortonTable.decodeOdd(value));

        assertEquals(1, ShiftCoder.even(value));
        assertEquals(1, MortonTable.decodeEven(value));

        // odd bit 1 set
        value = MortonTable.mortonTable8((byte) 1, (byte) 0);
        assertEquals(2, value);

        assertEquals(1, ShiftCoder.odd(value));
        assertEquals(1, MortonTable.decodeOdd(value));

        assertEquals(0, ShiftCoder.even(value));
        assertEquals(0, MortonTable.decodeEven(value));

        // both lsb set
        value = MortonTable.mortonTable8((byte) 1, (byte) 1);
        assertEquals(3, value);

        assertEquals(1, ShiftCoder.odd(value));
        assertEquals(1, MortonTable.decodeOdd(value));

        assertEquals(1, ShiftCoder.even(value));
        assertEquals(1, MortonTable.decodeEven(value));

    }

    @Test
    public void encodeMsbMortonTable8() {

        // 0 no bits set
        int value = MortonTable.mortonTable8((byte) 0, (byte) 0);
        assertEquals(0, value);

        // even bit 0x4000 0000 set
        value = MortonTable.mortonTable8((byte) 0, (byte) 0x80);
        assertEquals(0x4000, value);

        // odd bit 1 set
        value = MortonTable.mortonTable8((byte) 0x80, (byte) 0);
        assertEquals(0x8000, value);

        // both lsb set
        value = MortonTable.mortonTable8((byte) 0x80, (byte) 0x80);
        assertEquals(0xc000, value);

    }

    @Test
    public void interleaveMortonTable8() {

        // all even set
        int value = MortonTable.mortonTable8((byte) 0, (byte) 0xff);
        assertEquals(0x5555, value);

        // all odd set
        value = MortonTable.mortonTable8((byte) 0xff, (byte) 0);
        assertEquals(0xaaaa, value);

        // all set
        value = MortonTable.mortonTable8((byte) 0xff, (byte) 0xff);
        assertEquals(0xffff, value);

    }

}
