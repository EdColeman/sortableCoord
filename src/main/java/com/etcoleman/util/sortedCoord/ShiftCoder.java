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
 * 
 */
public class ShiftCoder {

	public static int interleave(byte even, byte odd) {

		int result = 0;

		for (int i = 0; i < 8; i++) {
			result |= (odd & (0x01 << i)) << i
					| (even & (0x01 << i)) << (i + 1);
		}

		return result;
	}
	
	public static byte odd(final int value){
		return decode(value >> 1);
	}

	public static byte even(final int value){
		return decode(value);
	}
	
	private static byte decode(final int value){
		
		int m = 0x0000ffff;
		
		int masked = value & m;
		
		int mask = 0x01;
		
		byte r = 0;
		int s = masked;
		
		for(int i = 0; i < 8; i++){
			
			r |= s & mask;
			
			mask = mask << 1;
			s = s >> 1;
			
		}
		
		return r;
	}
	
}
