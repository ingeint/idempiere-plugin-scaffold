/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Copyright (C) 2024 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.example.test.util;

import java.util.UUID;

import com.github.javafaker.Faker;

/**
 * Collection of random utils
 */
public final class RandomTestUtil {

	private static Faker faker = new Faker();

	private RandomTestUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String getRandomString() {
		return faker.regexify("[a-zA-Z]{5}");
	}

	public static int getRandomInt() {
		return faker.number().randomDigit();
	}

	public static double getRandomDouble() {
		return faker.number().randomDouble(3, 0, 10);
	}

	public static long getRandomLong() {
		return faker.number().randomNumber();
	}

	public static boolean getRandomBoolean() {
		return faker.bool().bool();
	}

	public static byte getRandomByte() {
		return (byte) faker.number().randomDigit();
	}

	public static short getRandomShort() {
		return (short) faker.number().randomDigit();
	}

	public static float getRandomFloat() {
		return (float) faker.random().nextDouble();
	}

	public static char getRandomChar() {
		return faker.regexify("[a-zA-Z]{1}").charAt(0);
	}

	public static UUID getRandomUUID() {
		return UUID.randomUUID();
	}

	public static String getRandomID() {
		return faker.idNumber().valid();
	}

	public static String getRandomName() {
		return faker.name().fullName();
	}
}
