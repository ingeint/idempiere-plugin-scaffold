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

package com.ingeint.example.util;

import static com.ingeint.example.test.util.RandomTestUtil.*;
import static com.ingeint.example.test.util.ReflectionTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KeyValueLoggerTest {

	private KeyValueLogger keyValueLogger;
	private CLogger logger;
	private String randomValue;
	private String randomKey;
	private int randomIntValue;

	@BeforeEach
	void setUp() throws ReflectiveOperationException {
		keyValueLogger = KeyValueLogger.instance(KeyValueLoggerTest.class);
		logger = mock(CLogger.class);
		setFieldValue(keyValueLogger, "logger", logger);

		randomKey = getRandomString();
		randomValue = getRandomString();
		randomIntValue = getRandomInt();
	}

	@Test
	void shouldCreateLogger() {
		CLogger logger = KeyValueLogger.logger(KeyValueLoggerTest.class);
		assertThat(logger).isNotNull();
	}

	@Test
	void shouldCreateKeyValueLogger() throws ReflectiveOperationException {
		keyValueLogger = KeyValueLogger.instance(KeyValueLoggerTest.class);
		Object output = getFieldValue(keyValueLogger, "logger");
		assertThat(output).isNotNull();

		assertThat(output).isInstanceOf(CLogger.class);

		Object pairs = getFieldValue(keyValueLogger, "pairs");
		assertThat(pairs).isNotNull();
	}

	@Test
	void shouldSetLogger() throws ReflectiveOperationException {
		CLogger logger = CLogger.getCLogger(KeyValueLoggerTest.class);
		keyValueLogger = KeyValueLogger.instance(logger);
		Object output = getFieldValue(keyValueLogger, "logger");
		assertThat(output).isNotNull();

		assertThat(output).isSameAs(logger);

		Object pairs = getFieldValue(keyValueLogger, "pairs");
		assertThat(pairs).isNotNull();
	}

	@Test
	void shouldInvokeInfo() {
		keyValueLogger.info();

		verify(logger).log(eq(Level.INFO), anyString());
	}

	@Test
	void shouldInvokeFine() {
		keyValueLogger.fine();

		verify(logger).log(eq(Level.FINE), anyString());
	}

	@Test
	void shouldInvokeSevere() {
		keyValueLogger.severe();

		verify(logger).log(eq(Level.SEVERE), anyString());
	}

	@Test
	void shouldInvokeFiner() {
		keyValueLogger.finer();

		verify(logger).log(eq(Level.FINER), anyString());
	}

	@Test
	void shouldInvokeWarn() {
		keyValueLogger.warning();

		verify(logger).log(eq(Level.WARNING), anyString());
	}

	@Test
	void shouldInvokeConfig() {
		keyValueLogger.config();

		verify(logger).log(eq(Level.CONFIG), anyString());
	}

	@Test
	void shouldInvokeAll() {
		keyValueLogger.all();

		verify(logger).log(eq(Level.ALL), anyString());
	}

	@Test
	void shouldInvokeFinest() {
		keyValueLogger.finest();

		verify(logger).log(eq(Level.FINEST), anyString());
	}

	@Test
	void shouldCreateNewKeyValueLogger() {
		KeyValueLogger addKeyValueLogger = keyValueLogger.add(randomKey, randomValue);

		addKeyValueLogger.info();

		assertThat(addKeyValueLogger).isNotSameAs(keyValueLogger);
	}

	@Test
	void shouldInvokeLoggerWithLevelInfoAndReceiveKeyValueArguments() {
		keyValueLogger.add(randomKey, randomValue).info();

		verifyStringLog(Level.INFO, randomKey + "=\"" + randomValue + "\"");
	}

	@Test
	void shouldAddQuotesWhenStringIsEmpty() {
		keyValueLogger.add("message", "").info();

		verifyStringLog(Level.INFO, "message=\"\"");
	}

	@Test
	void shouldSkipKeyWhenKeyStringIsEmpty() {
		keyValueLogger.add(randomKey, randomValue).add("", "").info();

		verifyStringLog(Level.INFO, randomKey + "=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintNullWhenKeyIsNull() {
		String message = getRandomString();
		keyValueLogger.add(null, message).info();

		verifyStringLog(Level.INFO, "null=\"" + message + "\"");
	}

	@Test
	void shouldPrintNullWhenValue() {
		keyValueLogger.add("message", null).info();

		verifyStringLog(Level.INFO, "message=\"null\"");
	}

	@Test
	void shouldAddQuotesWhenStringHaveWhiteSpace() {
		String message = String.format("%s %s", getRandomString(), getRandomString());
		keyValueLogger.add("message", message).info();

		verifyStringLog(Level.INFO, "message=\"" + message + "\"");
	}

	@Test
	void shouldRemoveNewLine() {
		String randomString1 = getRandomString();
		String randomString2 = getRandomString();
		String message = String.format("\n%s\n%s", randomString1, randomString2);
		keyValueLogger.add("message", message).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("%s %s", randomString1, randomString2) + "\"");
	}

	@Test
	void shouldTrimText() {
		String message = String.format(" %s ", randomValue);
		keyValueLogger.add("message", message).info();

		verifyStringLog(Level.INFO, "message=\"" + randomValue + "\"");
	}

	@Test
	void shouldCleanKey() {
		keyValueLogger.add(" Me's$ sag e 1*\n# ", randomValue).info();

		verifyStringLog(Level.INFO, "Message1=\"" + randomValue + "\"");
	}

	@Test
	void shouldRemoveSingleQuotes() {
		String message = String.format("'%s'", randomValue);
		keyValueLogger.add("message", message).info();

		verifyStringLog(Level.INFO, "message=\"" + randomValue + "\"");
	}

	@Test
	void shouldRemoveDoubleQuotes() {
		String message = String.format("\"%s\"", randomValue);
		keyValueLogger.add("message", message).info();

		verifyStringLog(Level.INFO, "message=\"" + randomValue + "\"");
	}

	@Test
	void shouldRemoveDoubleQuotesAndKeepWrapperQuotes() {
		String randomString1 = getRandomString();
		String randomString2 = getRandomString();
		String randomString3 = getRandomString();
		String message = String.format("\"%s\" %s %s", randomString1, randomString2, randomString3);
		keyValueLogger.add("message", message).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("%s %s %s", randomString1, randomString2, randomString3) + "\"");
	}

	@Test
	void shouldInvokeLogWithMultiplePairs() {
		String message1 = getRandomString();
		String message2 = getRandomString();
		keyValueLogger.add("message1", message1).add("message2", message2).info();

		verifyStringLog(Level.INFO, "message1=\"" + message1 + "\" message2=\"" + message2 + "\"");
	}

	@Test
	void shouldPrintInteger() {
		int value = getRandomInt();

		keyValueLogger.add("int", value).info();

		verifyStringLog(Level.INFO, "int=\"" + Integer.toString(value) + "\"");
	}

	@Test
	void shouldPrintFloat() {
		float value = getRandomFloat();

		keyValueLogger.add("float", value).info();

		verifyStringLog(Level.INFO, "float=\"" + Float.toString(value) + "\"");
	}

	@Test
	void shouldPrintDouble() {
		double value = getRandomDouble();

		keyValueLogger.add("double", value).info();

		verifyStringLog(Level.INFO, "double=\"" + Double.toString(value) + "\"");
	}

	@Test
	void shouldPrintClassType() {
		keyValueLogger.add("value", new Dummy()).info();

		verifyStringLog(Level.INFO, "value=\"To String Dummy\"");
	}

	@Test
	void shouldPrintMessage() {
		keyValueLogger.message(randomValue).info();

		verifyStringLog(Level.INFO, "message=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintEndpoint() {
		keyValueLogger.endpoint(randomValue).info();

		verifyStringLog(Level.INFO, "endpoint=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintService() {
		keyValueLogger.service(randomValue).info();

		verifyStringLog(Level.INFO, "service=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintName() {
		keyValueLogger.name(randomValue).info();

		verifyStringLog(Level.INFO, "name=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintDurationDouble() {
		double duration = getRandomDouble();
		keyValueLogger.duration(duration).info();

		verifyStringLog(Level.INFO, "duration=\"" + Double.toString(duration) + "\"");
	}

	@Test
	void shouldPrintDurationLong() {
		long duration = getRandomLong();
		keyValueLogger.duration(duration).info();

		verifyStringLog(Level.INFO, "duration=\"" + Long.toString(duration) + "\"");

	}

	@Test
	void shouldPrintStatus() {
		keyValueLogger.status(randomValue).info();

		verifyStringLog(Level.INFO, "status=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintAction() {
		keyValueLogger.action(randomValue).info();

		verifyStringLog(Level.INFO, "action=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintFailStatus() {
		keyValueLogger.fail().info();

		verifyStringLog(Level.INFO, "status=\"fail\"");
	}

	@Test
	void shouldPrintSuccessStatus() {
		keyValueLogger.success().info();

		verifyStringLog(Level.INFO, "status=\"success\"");
	}

	@Test
	void shouldPrintEnvironment() {
		keyValueLogger.environment(randomValue).info();

		verifyStringLog(Level.INFO, "environment=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintMethodFromString() {
		keyValueLogger.javaMethod(randomValue).info();

		verifyStringLog(Level.INFO, "method=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintMethodFromMethod() throws NoSuchMethodException {
		keyValueLogger.javaMethod(Dummy.class.getDeclaredMethod("toString")).info();

		verifyStringLog(Level.INFO, "method=\"toString\"");
	}

	@Test
	void shouldPrintNullWhenMethodIsNull() throws NoSuchMethodException {
		keyValueLogger.javaMethod((Method) null).info();

		verifyStringLog(Level.INFO, "method=\"null\"");
	}

	private void verifyStringLog(Level level, String string) {
		String expected = string + " level=\"" + Level.INFO + "\"";
		verify(logger).log(level, expected.trim());
	}

	@Test
	void shouldPrintClassFromString() {
		keyValueLogger.javaClass(randomValue).info();

		verifyStringLog(Level.INFO, "class=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintClassFromClass() {
		keyValueLogger.javaClass(KeyValueLogger.class).info();

		verifyStringLog(Level.INFO, "class=\"com.ingeint.example.util.KeyValueLogger\"");
	}

	@Test
	void shouldPrintNullWhenClassIsNull() {
		keyValueLogger.javaClass((Class<?>) null).info();

		verifyStringLog(Level.INFO, "class=\"null\"");
	}

	@Test
	void shouldPrintPackageFromString() {
		keyValueLogger.javaPackage(randomValue).info();

		verifyStringLog(Level.INFO, "package=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintPackageFromClass() {
		keyValueLogger.javaPackage(KeyValueLogger.class).info();

		verifyStringLog(Level.INFO, "package=\"com.ingeint.example.util\"");
	}

	@Test
	void shouldPrintNullWhenClassIsNullInJavaPackageMethod() {
		keyValueLogger.javaPackage((Class<?>) null).info();

		verifyStringLog(Level.INFO, "package=\"null\"");
	}

	@Test
	void shouldPrintNullWhenPackageIsNullInJavaPackageMethod() {
		keyValueLogger.javaPackage((Package) null).info();

		verifyStringLog(Level.INFO, "package=\"null\"");
	}

	@Test
	void shouldPrintPackageFromPackage() {
		keyValueLogger.javaPackage(KeyValueLogger.class.getPackage()).info();

		verifyStringLog(Level.INFO, "package=\"com.ingeint.example.util\"");
	}

	@Test
	void shouldPrintCode() {
		keyValueLogger.code(randomValue).info();

		verifyStringLog(Level.INFO, "code=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintIntCode() {
		keyValueLogger.code(randomIntValue).info();

		verifyStringLog(Level.INFO, "code=\"" + randomIntValue + "\"");
	}

	@Test
	void shouldPrintType() {
		keyValueLogger.type(randomValue).info();

		verifyStringLog(Level.INFO, "type=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintValue() {
		keyValueLogger.value(randomValue).info();

		verifyStringLog(Level.INFO, "value=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintTrack() {
		UUID randomUUID = getRandomUUID();
		keyValueLogger.track(randomUUID).info();

		verifyStringLog(Level.INFO, "track=\"" + randomUUID.toString() + "\"");
	}

	@Test
	void shouldPrintTrackString() {
		keyValueLogger.track(randomValue).info();

		verifyStringLog(Level.INFO, "track=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintRequest() {
		UUID randomUUID = getRandomUUID();
		keyValueLogger.request(randomUUID).info();

		verifyStringLog(Level.INFO, "request=\"" + randomUUID.toString() + "\"");
	}

	@Test
	void shouldPrintRequestString() {
		keyValueLogger.request(randomValue).info();

		verifyStringLog(Level.INFO, "request=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintId() {
		UUID randomUUID = getRandomUUID();
		keyValueLogger.id(randomUUID).info();

		verifyStringLog(Level.INFO, "id=\"" + randomUUID.toString() + "\"");
	}

	@Test
	void shouldPrintIdString() {
		keyValueLogger.id(randomValue).info();

		verifyStringLog(Level.INFO, "id=\"" + randomValue + "\"");
	}

	@Test
	void shouldTransactionTrack() {
		UUID randomUUID = getRandomUUID();
		keyValueLogger.transaction(randomUUID).info();

		verifyStringLog(Level.INFO, "transaction=\"" + randomUUID.toString() + "\"");
	}

	@Test
	void shouldPrintTransactionString() {
		keyValueLogger.transaction(randomValue).info();

		verifyStringLog(Level.INFO, "transaction=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintSession() {
		UUID randomUUID = getRandomUUID();
		keyValueLogger.session(randomUUID).info();

		verifyStringLog(Level.INFO, "session=\"" + randomUUID.toString() + "\"");
	}

	@Test
	void shouldPrintLanguage() {
		keyValueLogger.language(randomValue).info();

		verifyStringLog(Level.INFO, "language=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintSessionString() {
		keyValueLogger.session(randomValue).info();

		verifyStringLog(Level.INFO, "session=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintHTTPMethodString() {
		keyValueLogger.httpMethod(randomValue).info();

		verifyStringLog(Level.INFO, "httpMethod=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintHTTPStatusString() {
		keyValueLogger.httpStatus(randomValue).info();

		verifyStringLog(Level.INFO, "httpStatus=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintHTTPStatusInt() {
		int status = getRandomInt();
		keyValueLogger.httpStatus(status).info();

		verifyStringLog(Level.INFO, "httpStatus=\"" + Integer.toString(status) + "\"");
	}

	@Test
	void shouldPrintDay() {
		keyValueLogger.day().info();

		String expectedDay = Integer.toString(LocalDate.now().getDayOfMonth());

		verifyStringLog(Level.INFO, "day=\"" + expectedDay + "\"");
	}

	@Test
	void shouldPrintDayName() {
		keyValueLogger.dayName().info();

		String expectedDay = LocalDate.now().getDayOfWeek().toString();

		verifyStringLog(Level.INFO, "day=\"" + expectedDay + "\"");
	}

	@Test
	void shouldPrintMonth() {
		keyValueLogger.month().info();

		String expectedMonth = Integer.toString(LocalDate.now().getMonthValue());

		verifyStringLog(Level.INFO, "month=\"" + expectedMonth + "\"");
	}

	@Test
	void shouldPrintMonthName() {
		keyValueLogger.monthName().info();

		String expectedMonth = LocalDate.now().getMonth().toString();

		verifyStringLog(Level.INFO, "month=\"" + expectedMonth + "\"");
	}

	@Test
	void shouldPrintYear() {
		keyValueLogger.year().info();

		String expectedYear = Integer.toString(LocalDate.now().getYear());

		verifyStringLog(Level.INFO, "year=\"" + expectedYear + "\"");
	}

	@Test
	void shouldPrintDate() {
		keyValueLogger.date().info();

		String expectedDate = LocalDate.now().toString();

		verifyStringLog(Level.INFO, "date=\"" + expectedDate + "\"");
	}

	@Test
	void shouldPrintTime() {
		keyValueLogger.time().info();

		verifyMatch(Level.INFO, "time=\"\\d{2}:\\d{2}:\\d{2}\\.\\d{3}\"");
	}

	@Test
	void shouldPrintDateTime() {
		keyValueLogger.dateTime().info();

		verifyMatch(Level.INFO, "dateTime=\"\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3} [-+]\\d{4}\"");
	}

	@Test
	void shouldPrintDateTimeFormat() {
		String expectedFormat = "yyyy-MM-dd HH:mm:ss.SSS Z";
		keyValueLogger.dateTime(expectedFormat).info();

		verifyMatch(Level.INFO, "dateTime=\"\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3} [-+]\\d{4}\"");
	}

	private void verifyMatch(Level level, String string) {
		verify(logger).log(eq(level), matches(string + " level=\"" + level + "\""));
	}

	@Test
	void shouldPrintDateTimeFormatAndKey() {
		String expectedFormat = "yyyy-MM-dd HH:mm:ss.SSS Z";
		String expectedKey = "newDate";
		keyValueLogger.dateTime(expectedKey, expectedFormat).info();

		verifyMatch(Level.INFO, expectedKey + "=\"\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3} [-+]\\d{4}\"");
	}

	@Test
	void shouldPrintTimeFormat() {
		keyValueLogger.time("HH:mm:ss.SSS").info();

		verifyMatch(Level.INFO, "time=\"\\d{2}:\\d{2}:\\d{2}\\.\\d{3}\"");
	}

	@Test
	void shouldPrintDateFormat() {
		keyValueLogger.date("yyyy-MM-dd").info();

		verifyMatch(Level.INFO, "date=\"\\d{4}-\\d{2}-\\d{2}\"");
	}

	@Test
	void shouldPrintTimeZone() {
		keyValueLogger.timeZone().info();

		verifyMatch(Level.INFO, "timeZone=\"[-+]\\d{4}\"");
	}

	@Test
	void shouldPrintTimeName() {
		keyValueLogger.timeZoneName().info();

		String expectedTimeZone = ZonedDateTime.now().getZone().toString();

		verifyMatch(Level.INFO, "timeZone=\"" + expectedTimeZone + "\"");
	}

	@Test
	void shouldProcessObjectList() {
		String randomString1 = getRandomString();
		String randomString2 = getRandomString();
		String randomString3 = getRandomString();

		keyValueLogger.add("message", Arrays.asList(randomString1, randomString2, randomString3)).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomString1, randomString2, randomString3) + "\"");
	}

	@Test
	void shouldProcessObjectArray() {
		String randomString1 = getRandomString();
		String randomString2 = getRandomString();
		String randomString3 = getRandomString();
		String[] objects = { randomString1, randomString2, randomString3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomString1, randomString2, randomString3) + "\"");
	}

	@Test
	void shouldProcessObjectArrayEmpty() {
		String[] objects = {};

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"[]\"");
	}

	@Test
	void shouldProcessObjectArrayNull() {
		keyValueLogger.add("message", null).info();

		verifyStringLog(Level.INFO, "message=\"null\"");
	}

	@Test
	void shouldProcessObjectArrayWithNull() {
		String randomString1 = getRandomString();
		String[] objects = { randomString1, null, null };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, null, null]", randomString1) + "\"");
	}

	@Test
	void shouldProcessDummyArray() {
		Dummy[] objects = { new Dummy(), new Dummy() };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"[To String Dummy, To String Dummy]\"");
	}

	@Test
	void shouldProcessIntArray() {
		int randomInt1 = getRandomInt();
		int randomInt2 = getRandomInt();
		int randomInt3 = getRandomInt();
		int[] objects = { randomInt1, randomInt2, randomInt3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomInt1, randomInt2, randomInt3) + "\"");
	}

	@Test
	void shouldProcessDoubleArray() {
		double randomDouble1 = getRandomDouble();
		double randomDouble2 = getRandomDouble();
		double randomDouble3 = getRandomDouble();
		double[] objects = { randomDouble1, randomDouble2, randomDouble3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomDouble1, randomDouble2, randomDouble3) + "\"");
	}

	@Test
	void shouldProcessLongArray() {
		long randomLong1 = getRandomLong();
		long randomLong2 = getRandomLong();
		long randomLong3 = getRandomLong();
		long[] objects = { randomLong1, randomLong2, randomLong3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomLong1, randomLong2, randomLong3) + "\"");
	}

	@Test
	void shouldProcessBooleanArray() {
		boolean randomBoolean1 = getRandomBoolean();
		boolean randomBoolean2 = getRandomBoolean();
		boolean randomBoolean3 = getRandomBoolean();
		boolean[] objects = { randomBoolean1, randomBoolean2, randomBoolean3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomBoolean1, randomBoolean2, randomBoolean3) + "\"");
	}

	@Test
	void shouldProcessByteArray() {
		byte randomByte1 = getRandomByte();
		byte randomByte2 = getRandomByte();
		byte randomByte3 = getRandomByte();
		byte[] objects = { randomByte1, randomByte2, randomByte3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomByte1, randomByte2, randomByte3) + "\"");
	}

	@Test
	void shouldProcessShortArray() {
		short randomShort1 = getRandomShort();
		short randomShort2 = getRandomShort();
		short randomShort3 = getRandomShort();
		short[] objects = { randomShort1, randomShort2, randomShort3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomShort1, randomShort2, randomShort3) + "\"");
	}

	@Test
	void shouldProcessFloatArray() {
		float randomFloat1 = getRandomFloat();
		float randomFloat2 = getRandomFloat();
		float randomFloat3 = getRandomFloat();
		float[] objects = { randomFloat1, randomFloat2, randomFloat3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomFloat1, randomFloat2, randomFloat3) + "\"");
	}

	@Test
	void shouldProcessCharArray() {
		char randomChar1 = getRandomChar();
		char randomChar2 = getRandomChar();
		char randomChar3 = getRandomChar();
		char[] objects = { randomChar1, randomChar2, randomChar3 };

		keyValueLogger.add("message", objects).info();

		verifyStringLog(Level.INFO, "message=\"" + String.format("[%s, %s, %s]", randomChar1, randomChar2, randomChar3) + "\"");
	}

	@Test
	void shouldPrintArguments() {
		String randomString1 = getRandomString();
		String randomString2 = getRandomString();
		String randomString3 = getRandomString();
		Object[] arguments = { randomString1, randomString2, randomString3 };

		keyValueLogger.arguments(arguments).info();

		verifyStringLog(Level.INFO, "arguments=\"" + String.format("[%s, %s, %s]", randomString1, randomString2, randomString3) + "\"");
	}

	@Test
	void shouldPrintEmptyArguments() {
		Object[] arguments = {};

		keyValueLogger.arguments(arguments).info();

		verifyStringLog(Level.INFO, "arguments=\"[]\"");
	}

	@Test
	void shouldPrintLogWithMultipleArgs() {
		String randomString1 = getRandomString();
		String randomString2 = getRandomString();
		keyValueLogger.add("message1", "arg1 %s, arg2 %s", randomString1, randomString2).add("message2", "arg1 %s, arg2 %s", randomString1, randomString2).info();

		verifyStringLog(Level.INFO, "message1=\"arg1 " + randomString1 + ", arg2 " + randomString2 + "\" message2=\"arg1 " + randomString1 + ", arg2 " + randomString2 + "\"");
	}

	@Test
	void shouldPrintDefaultFormatWhenIsNUll() {
		keyValueLogger.add("message1", null, randomValue).info();

		verifyStringLog(Level.INFO, "message1=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintMessageWithMultipleArgs() {
		String randomString1 = getRandomString();
		String randomString2 = getRandomString();
		keyValueLogger.message("arg1 %s, arg2 %s", randomString1, randomString2).info();

		verifyStringLog(Level.INFO, "message=\"arg1 " + randomString1 + ", arg2 " + randomString2 + "\"");
	}

	@Test
	void shouldPrintStringException() {
		keyValueLogger.exception(randomValue).info();

		verifyStringLog(Level.INFO, "exception=\"" + randomValue + "\"");
	}

	@Test
	void shouldPrintException() {
		keyValueLogger.exception(new RuntimeException(randomValue)).info();

		verifyStringLog(Level.INFO, "exception=\"" + String.format("java.lang.RuntimeException: %s", randomValue) + "\"");
	}

	@Test
	void shouldPrintStringExceptionAndStackTrace() {
		RuntimeException exception = new RuntimeException(randomValue);
		keyValueLogger.exceptionWithStackTrace(randomValue, exception).info();

		verify(logger).log(Level.INFO, "exception=\"" + randomValue + "\" level=\"INFO\"", exception);
		verify(logger, never()).log(eq(Level.INFO), anyString());
	}

	@Test
	void shouldPrintExceptionAndStackTrace() {
		RuntimeException exception = new RuntimeException(randomValue);
		keyValueLogger.exceptionWithStackTrace(exception).info();

		verify(logger).log(Level.INFO, "exception=\"" + String.format("java.lang.RuntimeException: %s", randomValue) + "\" level=\"INFO\"", exception);
		verify(logger, never()).log(eq(Level.INFO), anyString());
	}

	@Test
	void shouldSkipWhenKeyIsEmpty() {
		keyValueLogger.add("", randomValue).info();

		verifyStringLog(Level.INFO, "");
	}

	public class Dummy {

		@Override
		public String toString() {
			return "To String Dummy";
		}
	}

}
