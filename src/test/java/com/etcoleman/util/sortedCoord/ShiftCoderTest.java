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

import static org.junit.Assert.*;

/**
 * 
 */
public class ShiftCoderTest {

	@Test
	public void interleave() {
		
		System.out.println(Integer.toHexString(ShiftCoder.interleave((byte)0x01,(byte)0x00)));
		System.out.println(Integer.toHexString(ShiftCoder.interleave((byte)0x80,(byte)0x01)));
		System.out.println(Integer.toHexString(ShiftCoder.interleave((byte)0x80,(byte)0x81)));
		
		System.out.println("16: " + Integer.toHexString(MortonTable.mortonTable16(0x81, 0x80)));
		System.out.println(" 8: " + Integer.toHexString(MortonTable.mortonTable8((byte)0x81, (byte)0x80)));

	}
	
	
	@Test
	public void genDecodeEven() {
		
		for(int i = 0; i < 256; i++){
			//System.out.println("0x0" + Integer.toHexString(even(i)) + ",");
			System.out.println(i + " " + Integer.toBinaryString(i) + " : 0x0" + Integer.toHexString(even(i)));
		}
	}

	
	private int odd(final int value){
		
		int m = 0x0000ffff;
		
		int masked = value & m;
		
		int mask = 0x01;
		
		int r = 0;
		int s = masked;
		
		for(int i = 0; i < 8; i++){
			
			r |= s & mask;
			
			mask = mask << 1;
			s = s >> 1;
			
			// System.out.println("shift: " + mask + " " + s);
		}
		
		System.out.println("N: " + r + " = " + Integer.toHexString(r));
		
		// int v = (value & 0x0001);
		
		int shifted = (value >> 1) & 0x7fff;
		
		int v = (value & 0x0001) |
			((shifted >> 1) & 0x0002) |
			(shifted & 0x0004) |
			(shifted & 0x0008) |
			(shifted & 0x0010) |
			(shifted & 0x0020) |
			(shifted & 0x0040) |
			(shifted & 0x0080); 
			
		
		return v;
	}
	
	private int even(int value){
		
		int m = (0xffff & value) >> 1;
		
		return(odd(m));

	}
}
