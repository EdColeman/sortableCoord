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

import static junit.framework.TestCase.assertEquals;

/**
 *
 */
public class ShiftCoderTest {

    @Test
    public void interleave() {

        assertEquals(0x0002, ShiftCoder.interleave((byte) 0x01, (byte) 0x00));
        assertEquals(0x8001, ShiftCoder.interleave((byte) 0x80, (byte) 0x01));
        assertEquals(0xc001, ShiftCoder.interleave((byte) 0x80, (byte) 0x81));

        assertEquals(0x0003, ShiftCoder.interleave((byte) 0x01, (byte) 0x01));
        assertEquals(0x0001, ShiftCoder.interleave((byte) 0x00, (byte) 0x01));

    }

    @Test
    public void decode() {

        assertEquals(0x01, ShiftCoder.odd(0x0002));
        assertEquals(0x00, ShiftCoder.even(0x0002));

        assertEquals((byte) 0x80, ShiftCoder.odd(0x8001));
        assertEquals(0x01, ShiftCoder.even(0x8001));

        assertEquals((byte) 0x80, ShiftCoder.odd(0xc001));
        assertEquals((byte) 0x81, ShiftCoder.even(0xc001));

        assertEquals(0x01, ShiftCoder.odd(0x0003));
        assertEquals(0x01, ShiftCoder.even(0x0001));

        assertEquals(0x00, ShiftCoder.odd(0x0001));
        assertEquals(0x01, ShiftCoder.even(0x0001));


    }
}
