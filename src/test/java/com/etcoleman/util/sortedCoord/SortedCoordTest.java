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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 */
public class SortedCoordTest {

	@Test
	public void inLimitsLat() {

		String coded = SortedCoord.coordToString(45.0, -10.0);

		System.out.println("Coded:  " + coded);

		double[] result = SortedCoord.fromString(coded);

		System.out.println("Latitude:  " + result[SortedCoord.LATITUDE]);
		System.out.println("Longitude: " + result[SortedCoord.LONGITUDE]);

	}

	@Test
	public void inLimitsLat2() {

		String coded = SortedCoord.coordToString(-89.999999, -179.999999);

		System.out.println("Coded:  " + coded);

		double[] result = SortedCoord.fromString(coded);

		System.out.println("Latitude:  " + result[SortedCoord.LATITUDE]);
		System.out.println("Longitude: " + result[SortedCoord.LONGITUDE]);

	}

	@Test
	public void inLimitsLat3() {

		String coded = SortedCoord.coordToString(89.999999, 179.999999);

		System.out.println("Coded:  " + coded);

		double[] result = SortedCoord.fromString(coded);

		System.out.println("Latitude:  " + result[SortedCoord.LATITUDE]);
		System.out.println("Longitude: " + result[SortedCoord.LONGITUDE]);
	}

	@Test
	public void sort1() {

		String lowerLeft = SortedCoord.coordToString(45.0, 45.0);
		String upperRight = SortedCoord.coordToString(46.0, 46.0);

		String inBox = SortedCoord.coordToString(45.5, 45.5);
		
		System.out.println("Coded LL:  " + lowerLeft);
		System.out.println("Coded IB:  " + inBox);
		System.out.println("Coded UR:  " + upperRight);
	
		assertTrue(lowerLeft.compareTo(inBox) < 0);
		assertTrue(upperRight.compareTo(inBox) > 0);
		
		inBox = SortedCoord.coordToString(45.9, 45.9);

		System.out.println("Coded IB:  " + inBox);

		assertTrue(lowerLeft.compareTo(inBox) < 0);
		assertTrue(upperRight.compareTo(inBox) > 0);
		
		String outBox = SortedCoord.coordToString(0.0, 1.0);
		
		System.out.println("Coded LonOBx:  " + SortedCoord.coordToString(0.0, 1.0));
		System.out.println("Coded LonOBx:  " + SortedCoord.coordToString(0.0, 2.0));
		System.out.println("Coded LonOBx:  " + SortedCoord.coordToString(0.0, -1.0));
		System.out.println("Coded LonOBx:  " + SortedCoord.coordToString(0.0, -2.0));

		System.out.println("Coded LatOBx:  " + SortedCoord.coordToString(1.0, 0));
		System.out.println("Coded LatOBx:  " + SortedCoord.coordToString(2.0, 0));
		System.out.println("Coded LatOBx:  " + SortedCoord.coordToString(-1.0, 0));
		System.out.println("Coded LatOBx:  " + SortedCoord.coordToString(-2.0, 0));

		System.out.println("Coded:  " + SortedCoord.coordToString(45.0, 45.0));
		System.out.println("Coded:  " + SortedCoord.coordToString(45.0001, 45.0));
		System.out.println("Coded:  " + SortedCoord.coordToString(45.0, 46.0));
		System.out.println("Coded:  " + SortedCoord.coordToString(45.0, 47.0));

		assertTrue(lowerLeft.compareTo(outBox) > 0);
		assertTrue(upperRight.compareTo(outBox) > 0);


	}

	@Test
	public void latitude(){
		
		String prev = SortedCoord.coordToString(-89.99999, 0.0);
		
		for(int latitude = -89; latitude < 90; latitude++){
			
			String curr = SortedCoord.coordToString(latitude, 0.0);

			System.out.println("Lat: " + SortedCoord.coordToString(latitude, 0.0));
			
			assertTrue(prev.compareTo(curr) < 0);
			
			prev = curr;
		}
	}
	
	@Test
	public void longitude(){
		
		String prev = SortedCoord.coordToString(0.0, -179.9999);
		
		for(int longitude = -179; longitude < 179; longitude++){
			
			String curr = SortedCoord.coordToString(0.0, longitude);

			System.out.println("Lon: " + SortedCoord.coordToString(0.0, longitude));
			
			assertTrue(prev.compareTo(curr) < 0);
			
			prev = curr;
		}
	}
	
	@Test
	public void diag(){
		
		String prev = SortedCoord.coordToString(-89.9, -179.9999);
		
		for(int latitude = -89; latitude < 90; latitude++){
			
			String curr = SortedCoord.coordToString(latitude, latitude*2);

			System.out.println("Lon: " + SortedCoord.coordToString(latitude, latitude*2));
			
			assertTrue(prev.compareTo(curr) < 0);
			
			prev = curr;
		}
	}
	
	@Test
	public void diag2(){
		
		String prev = SortedCoord.coordToString(89.9, 179.9999);
		
		for(int latitude = 89; latitude > -90; latitude--){
			
			String curr = SortedCoord.coordToString(latitude, latitude*2);

			System.out.println("Lon: " + SortedCoord.coordToString(latitude, latitude*2));
			
			assertTrue(prev.compareTo(curr) > 0);
			
			prev = curr;
		}
	}
	
	@Test
	public void inLimitsLong() {
		SortedCoord.coordToString(0.00, -76.861245);
	}

	@Test
	public void inLimitsSwp() {
		SortedCoord.coordToString(39.209856, -76.861245);
	}

	@Test(expected = IllegalArgumentException.class)
	public void latTooSmall() {
		SortedCoord.coordToString(-90, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void latToBig() {
		SortedCoord.coordToString(90, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void longToSmall() {
		SortedCoord.coordToString(0, -180);
	}

	@Test(expected = IllegalArgumentException.class)
	public void longToBig() {
		SortedCoord.coordToString(0, 180);
	}

	@Test
	public void normalLatitude() {

		System.out.println("-90             " + SortedCoord.normalize(-90, 90));
		System.out.println("-89.9           "
				+ SortedCoord.normalize(-89.999, 90));
		System.out.println("0               " + SortedCoord.normalize(0, 90));
		System.out.println("1.23456789      "
				+ SortedCoord.normalize(1.23456789, 90));
		System.out.println("1.234567891     "
				+ SortedCoord.normalize(1.234567891, 90));
		System.out
				.println("89.9            " + SortedCoord.normalize(89.9, 90));
		System.out.println("89.99999        "
				+ SortedCoord.normalize(89.99999, 90));
		System.out.println("89.9999999      "
				+ SortedCoord.normalize(89.9999999, 90));
		System.out.println("90              " + SortedCoord.normalize(90, 90));

		System.out.println("-180 " + SortedCoord.normalize(-180, 180));
		System.out.println("0    " + SortedCoord.normalize(-0, 180));
		System.out.println("179.999998  "
				+ SortedCoord.normalize(179.999998, 180));
		System.out.println("179.999999  "
				+ SortedCoord.normalize(179.999999, 180));
		System.out.println("180  " + SortedCoord.normalize(180, 180));

	}

	@Test
	public void rebase() {

		double maxLatitude = 90;

		double delta = 0.000001;

		assertEquals(-90, SortedCoord.rebase(
				SortedCoord.normalize(-90, maxLatitude), maxLatitude), delta);
		assertEquals(0, SortedCoord.rebase(
				SortedCoord.normalize(0, maxLatitude), maxLatitude), delta);
		assertEquals(90, SortedCoord.rebase(
				SortedCoord.normalize(90, maxLatitude), maxLatitude), delta);

		double maxLongitude = 180;

		assertEquals(-180, SortedCoord.rebase(
				SortedCoord.normalize(-180, maxLongitude), maxLongitude), delta);
		assertEquals(0, SortedCoord.rebase(
				SortedCoord.normalize(0, maxLongitude), maxLongitude), delta);
		assertEquals(180, SortedCoord.rebase(
				SortedCoord.normalize(180, maxLongitude), maxLongitude), delta);

		System.out.println(SortedCoord.rebase(
				SortedCoord.normalize(-90, maxLatitude), maxLatitude));
		System.out.println(SortedCoord.rebase(
				SortedCoord.normalize(0, maxLatitude), maxLatitude));
		System.out.println(SortedCoord.rebase(
				SortedCoord.normalize(45, maxLatitude), maxLatitude));
		System.out.println(SortedCoord.rebase(
				SortedCoord.normalize(90, maxLatitude), maxLatitude));

		System.out.println(SortedCoord.rebase(
				SortedCoord.normalize(180, maxLongitude), maxLongitude));

	}
}
