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

import static java.util.stream.Collectors.joining;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.compiere.util.CLogger;

/**
 * This class abstracts the complexity of log management in key/value format,
 * used for monitoring applications. Use the builder pattern to build a log.
 * <p>
 * Example:
 * <p>
 * {@code KeyValueLogger keyValueLogger = KeyValueLogger.instance(App.class);}
 * <p>
 * Example using builder:
 * <p>
 * {@code keyValueLogger.message("Hello World!!").info();}
 * <p>
 * Log output:
 * <p>
 * {@code 08:07:26 [main] INFO App message="Hello World!!"}
 */
public class KeyValueLogger {

	private CLogger logger;
	private List<Pair> pairs;

	private KeyValueLogger(CLogger logger) {
		this.logger = logger;
		pairs = new ArrayList<>();
	}

	private KeyValueLogger(CLogger logger, List<Pair> pairs) {
		this.logger = logger;
		this.pairs = pairs;
	}

	/**
	 * Creates a object instance.
	 *
	 * @param origin Class
	 * @return Logger
	 */
	public static CLogger logger(Class<?> origin) {
		return CLogger.getCLogger(origin);
	}

	/**
	 * Creates a new instance
	 *
	 * @param origin Class
	 * @return Builder
	 */
	public static KeyValueLogger instance(Class<?> origin) {
		return new KeyValueLogger(CLogger.getCLogger(origin));
	}

	/**
	 * Creates a new instance
	 *
	 * @param logger Logger Example:
	 *               {@code private final static CLogger log = CLogger.getCLogger(CustomCalloutFactory.class);}.
	 * @return Builder
	 */
	public static KeyValueLogger instance(CLogger logger) {
		return new KeyValueLogger(logger);
	}

	/**
	 * Adds a new key value log
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.add("msg", "Hello World!!").info();}
	 * <p>
	 * Examaple output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App msg="Hello World!!"}
	 *
	 * @param key   Variable name
	 * @param value Variable value
	 * @return Builder
	 */
	public KeyValueLogger add(String key, Object value) {
		return add(key, null, value);
	}

	/**
	 * Adds a new key value log using a value string format.
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.add("message", "arg1 {}, arg2 {} y arg3 {}", 1, '2',
	 * "3").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 10:20:18 [main] INFO App message="arg1 1, arg2 2 y arg3 3"}
	 *
	 * @param key         Variable name
	 * @param valueFormat Value format
	 * @param values      Values to be format
	 * @return Builder O
	 */
	public KeyValueLogger add(String key, String valueFormat, Object... values) {
		return add(false, key, valueFormat, values);
	}

	private KeyValueLogger add(boolean internal, String key, String valueFormat, Object... values) {
		List<Pair> pairsCopy = new ArrayList<>(pairs);
		pairsCopy.add(new Pair(internal, key, valueFormat, values));
		KeyValueLogger keyValueLogger = new KeyValueLogger(logger, pairsCopy);
		return keyValueLogger;
	}

	private KeyValueLogger add(boolean internal, KeyValueLoggerKeys key, Object... values) {
		return add(internal, key.toString(), null, values);
	}

	private KeyValueLogger add(KeyValueLoggerKeys key, String valueFormat, Object... values) {
		return add(key.toString(), valueFormat, values);
	}

	private KeyValueLogger add(KeyValueLoggerKeys key, Object value) {
		return add(key.toString(), value);
	}

	/**
	 * Adds a new log using key "message".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.message("Hello World!!").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App message="Hello World!!"}
	 *
	 * @param value Value
	 * @return Builder
	 */
	public KeyValueLogger message(String value) {
		return add(KeyValueLoggerKeys.MESSAGE, value);
	}

	/**
	 * Agrega un mensaje al log con clave "message" using a format.
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.message("arg1 {}, arg2 {} y arg3 {}", 1, '2',
	 * "3").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 10:20:18 [main] INFO App message="arg1 1, arg2 2 y arg3 3"}
	 *
	 * @param format Format
	 * @param values Parameters
	 * @return Builder
	 * @see #add(String, String, Object...)
	 */
	public KeyValueLogger message(String format, Object... values) {
		return add(KeyValueLoggerKeys.MESSAGE, format, values);
	}

	/**
	 * Adds a new log using the key "exception".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.exception("Error!!!").error();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] ERROR App exception="Error!!!"}
	 *
	 * @param exception Exception as a String
	 * @return Builder
	 */
	public KeyValueLogger exception(String exception) {
		return add(KeyValueLoggerKeys.EXCEPTION, exception);
	}

	/**
	 * Adds a new log using the key "exception".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.exception(new RuntimeException("Error!!!")).error();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 10:20:18 [main] ERROR App exception="java.lang.RuntimeException: Error!!!"}
	 *
	 * @param exception Exception
	 * @return Builder
	 */
	public KeyValueLogger exception(Throwable exception) {
		return add(KeyValueLoggerKeys.EXCEPTION, exception);
	}

	/**
	 * Adds a new log using the key "exception" and prints the error stacktrace.
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.exceptionWithStackTrace("Custom Message", new RuntimeException("Error!!!")).error();}
	 * <p>
	 * Example output: {@code
	 * 10:20:18 [main] ERROR App exception="Custom Message"
	 * java.lang.RuntimeException: Error!!!
	 *     at App.main(App.java:24)
	 * }
	 *
	 * @param message   Custom Message
	 * @param exception Exception
	 * @return Builder
	 */
	public KeyValueLogger exceptionWithStackTrace(String message, Throwable exception) {
		return add(true, KeyValueLoggerKeys.EXCEPTION, exception).add(KeyValueLoggerKeys.EXCEPTION, message);
	}

	/**
	 * Adds a new log using the key "exception" and prints the error stacktrace.
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.exceptionWithStackTrace(new RuntimeException("Error!!!")).error();}
	 * <p>
	 * Example output: {@code
	 * 10:20:18 [main] ERROR App exception="java.lang.RuntimeException: Error!!!"
	 * java.lang.RuntimeException: Error!!!
	 *     at App.main(App.java:24)
	 * }
	 *
	 * @param exception Exception
	 * @return Builder
	 */
	public KeyValueLogger exceptionWithStackTrace(Throwable exception) {
		return add(true, KeyValueLoggerKeys.EXCEPTION, exception).add(KeyValueLoggerKeys.EXCEPTION, exception);
	}

	/**
	 * Adds a new log using the key "endpoint".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.endpoint("/info").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App endpoint="/info"}
	 *
	 * @param endpoint Value to log
	 * @return Builder
	 */
	public KeyValueLogger endpoint(String endpoint) {
		return add(KeyValueLoggerKeys.ENDPOINT, endpoint);
	}

	/**
	 * Adds a new log using the key "service".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.service("userService").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App service="userService"}
	 *
	 * @param service Value to log
	 * @return Builder
	 */
	public KeyValueLogger service(String service) {
		return add(KeyValueLoggerKeys.SERVICE, service);
	}

	/**
	 * Adds a new log using the key "name".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.name("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App name="value"}
	 *
	 * @param name Value to log
	 * @return Builder
	 */
	public KeyValueLogger name(String name) {
		return add(KeyValueLoggerKeys.NAME, name);
	}

	/**
	 * Adds a new log using the key "duration".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.duration(0.2).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App duration="0.2"}
	 *
	 * @param duration Value to log
	 * @return Builder
	 */
	public KeyValueLogger duration(double duration) {
		return add(KeyValueLoggerKeys.DURATION, duration);
	}

	/**
	 * Adds a new log using the key "duration".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.duration(10000).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App duration="10000"}
	 *
	 * @param duration Value to log
	 * @return Builder
	 */
	public KeyValueLogger duration(long duration) {
		return add(KeyValueLoggerKeys.DURATION, duration);
	}

	/**
	 * Adds a new log using the key "status".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.status("fail").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App status="fail"}
	 *
	 * @param status Value to log
	 * @return Builder
	 */
	public KeyValueLogger status(String status) {
		return add(KeyValueLoggerKeys.STATUS, status);
	}

	/**
	 * Adds a new log using the key "status" and value "fail".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.fail().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App status="fail"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger fail() {
		return add(KeyValueLoggerKeys.STATUS, KeyValueLoggerKeys.FAIL);
	}

	/**
	 * Adds a new log using the key "status" and value "success".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.success().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App status="success"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger success() {
		return add(KeyValueLoggerKeys.STATUS, KeyValueLoggerKeys.SUCCESS);
	}

	/**
	 * Adds a new log using the key "environment".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.environment("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App environment="value"}
	 *
	 * @param environment Value to log
	 * @return Builder
	 */
	public KeyValueLogger environment(String environment) {
		return add(KeyValueLoggerKeys.ENVIRONMENT, environment);
	}

	/**
	 * Adds a new log using the key "method".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.javaMethod("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App method="value"}
	 *
	 * @param javaMethod Value to log
	 * @return Builder
	 */
	public KeyValueLogger javaMethod(String javaMethod) {
		return add(KeyValueLoggerKeys.METHOD, javaMethod);
	}

	/**
	 * Adds a new log using the key "method".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.javaMethod(Dummy.class.getDeclaredMethod("toString")).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App method="toString"}
	 *
	 * @param javaMethod Value to log
	 * @return Builder
	 */
	public KeyValueLogger javaMethod(Method javaMethod) {
		return add(KeyValueLoggerKeys.METHOD, javaMethod == null ? null : javaMethod.getName());
	}

	/**
	 * Adds a new log using the key "class".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.javaClass("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App class="value"}
	 *
	 * @param javaClass Value to log
	 * @return Builder
	 */
	public KeyValueLogger javaClass(String javaClass) {
		return add(KeyValueLoggerKeys.CLASS, javaClass);
	}

	/**
	 * Adds a new log using the key "class".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.javaClass(Dummy.class).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App class="app.Dummy"}
	 *
	 * @param javaClass Value to log
	 * @return Builder
	 */
	public KeyValueLogger javaClass(Class<?> javaClass) {
		return add(KeyValueLoggerKeys.CLASS, javaClass == null ? null : javaClass.getName());
	}

	/**
	 * Adds a new log using the key "package".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.javaPackage("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App package="value"}
	 *
	 * @param javaPackage Value to log
	 * @return Builder
	 */
	public KeyValueLogger javaPackage(String javaPackage) {
		return add(KeyValueLoggerKeys.PACKAGE, javaPackage);
	}

	/**
	 * Adds a new log using the key "package".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.javaPackage(Dummy.class).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App package="app"}
	 *
	 * @param javaClass Value to log
	 * @return Builder
	 */
	public KeyValueLogger javaPackage(Class<?> javaClass) {
		return add(KeyValueLoggerKeys.PACKAGE, javaClass == null ? null : javaClass.getPackage().getName());
	}

	/**
	 * Adds a new log using the key "package".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.javaPackage(Dummy.class.getPackage()).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App package="app"}
	 *
	 * @param javaPackage Value to log
	 * @return Builder
	 */
	public KeyValueLogger javaPackage(Package javaPackage) {
		return add(KeyValueLoggerKeys.PACKAGE, javaPackage == null ? null : javaPackage.getName());
	}

	/**
	 * Adds a new log using the key "code".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.code("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App code="value"}
	 *
	 * @param code Value to log
	 * @return Builder
	 */
	public KeyValueLogger code(String code) {
		return add(KeyValueLoggerKeys.CODE, code);
	}

	/**
	 * Adds a new log using the key "action".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.action("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App action="value"}
	 *
	 * @param action Value to log
	 * @return Builder
	 */
	public KeyValueLogger action(String action) {
		return add(KeyValueLoggerKeys.ACTION, action);
	}

	/**
	 * Adds a new log using the key "code".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.code("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App code="value"}
	 *
	 * @param code Value to log
	 * @return Builder
	 */
	public KeyValueLogger code(int code) {
		return add(KeyValueLoggerKeys.CODE, code);
	}

	/**
	 * Adds a new log using the key "track".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.track(UUID.randomUUID()).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App track="123e4567-e89b-42d3-a456-556642440000"}
	 *
	 * @param track Value to log
	 * @return Builder
	 */
	public KeyValueLogger track(UUID track) {
		return add(KeyValueLoggerKeys.TRACK, track);
	}

	/**
	 * Adds a new log using the key "track".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.track("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App track="value"}
	 *
	 * @param track Value to log
	 * @return Builder
	 */
	public KeyValueLogger track(String track) {
		return add(KeyValueLoggerKeys.TRACK, track);
	}

	/**
	 * Adds a new log using the key "request".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.request(UUID.randomUUID()).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App request="123e4567-e89b-42d3-a456-556642440000"}
	 *
	 * @param request Value to log
	 * @return Builder
	 */
	public KeyValueLogger request(UUID request) {
		return add(KeyValueLoggerKeys.REQUEST, request);
	}

	/**
	 * Adds a new log using the key "request".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.request("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App request="value"}
	 *
	 * @param request Value to log
	 * @return Builder
	 */
	public KeyValueLogger request(String request) {
		return add(KeyValueLoggerKeys.REQUEST, request);
	}

	/**
	 * Adds a new log using the key "session".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.session(UUID.randomUUID()).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App session="123e4567-e89b-42d3-a456-556642440000"}
	 *
	 * @param session Value to log
	 * @return Builder
	 */
	public KeyValueLogger session(UUID session) {
		return add(KeyValueLoggerKeys.SESSION, session);
	}

	/**
	 * Adds a new log using the key "session".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.session("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App session="value"}
	 *
	 * @param session Value to log
	 * @return Builder
	 */
	public KeyValueLogger session(String session) {
		return add(KeyValueLoggerKeys.SESSION, session);
	}

	/**
	 * Adds a new log using the key "id".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.id(UUID.randomUUID()).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App id="123e4567-e89b-42d3-a456-556642440000"}
	 *
	 * @param id Value to log
	 * @return Builder
	 */
	public KeyValueLogger id(UUID id) {
		return add(KeyValueLoggerKeys.ID, id);
	}

	/**
	 * Adds a new log using the key "id".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.id("3").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App id="3"}
	 *
	 * @param id Value to log
	 * @return Builder
	 */
	public KeyValueLogger id(String id) {
		return add(KeyValueLoggerKeys.ID, id);
	}

	/**
	 * Adds a new log using the key "type".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.type("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App type="value"}
	 *
	 * @param type Value to log
	 * @return Builder
	 */
	public KeyValueLogger type(String type) {
		return add(KeyValueLoggerKeys.TYPE, type);
	}

	/**
	 * Adds a new log using the key "value".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.value("value").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App value="value"}
	 *
	 * @param value Value to log
	 * @return Builder
	 */
	public KeyValueLogger value(String value) {
		return add(KeyValueLoggerKeys.VALUE, value);
	}

	/**
	 * Adds a new log using the key "transaction".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.transaction(UUID.randomUUID()).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App transaction="123e4567-e89b-42d3-a456-556642440000"}
	 *
	 * @param transaction Value to log
	 * @return Builder
	 */
	public KeyValueLogger transaction(UUID transaction) {
		return add(KeyValueLoggerKeys.TRANSACTION, transaction);
	}

	/**
	 * Adds a new log using the key "transaction".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.transaction("123e4567-e89b-42d3-a456-556642440000").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App transaction="123e4567-e89b-42d3-a456-556642440000"}
	 *
	 * @param transaction Value to log
	 * @return Builder
	 */
	public KeyValueLogger transaction(String transaction) {
		return add(KeyValueLoggerKeys.TRANSACTION, transaction);
	}

	/**
	 * Adds a new log using the key "httpMethod".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.httpMethod("GET").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App httpMethod="GET"}
	 *
	 * @param httpMethod Value to log
	 * @return Builder
	 */
	public KeyValueLogger httpMethod(String httpMethod) {
		return add(KeyValueLoggerKeys.HTTP_METHOD, httpMethod);
	}

	/**
	 * Adds a new log using the key "httpStatus".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.httpStatus("500").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App httpStatus="500"}
	 *
	 * @param httpStatus Value to log
	 * @return Builder
	 */
	public KeyValueLogger httpStatus(String httpStatus) {
		return add(KeyValueLoggerKeys.HTTP_STATUS, httpStatus);
	}

	/**
	 * Adds a new log using the key "httpStatus".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.httpStatus(500).info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App httpStatus="500"}
	 *
	 * @param httpStatus Value to log
	 * @return Builder
	 */
	public KeyValueLogger httpStatus(int httpStatus) {
		return add(KeyValueLoggerKeys.HTTP_STATUS, httpStatus);
	}

	/**
	 * Adds a new log using the key "language".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.language("es").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App language="es"}
	 *
	 * @param language Value to log
	 * @return Builder
	 */
	public KeyValueLogger language(String language) {
		return add(KeyValueLoggerKeys.LANGUAGE, language);
	}

	/**
	 * Adds log with key "arguments".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.arguments(new Object[]{"1", "2"}).info();}
	 * <p>
	 * Output example:
	 * <p>
	 * {@code 08:07:26 [main] INFO App arguments="[1, 2]"}
	 *
	 * @param arguments Values
	 * @return Builder
	 */
	public KeyValueLogger arguments(Object[] arguments) {
		return add(KeyValueLoggerKeys.ARGUMENTS, arguments);
	}

	/**
	 * Adds log with key "day".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.day().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App day="20"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger day() {
		return add(KeyValueLoggerKeys.DAY, LocalDate.now().getDayOfMonth());
	}

	/**
	 * Adds log with key "month".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.month().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App month="11"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger month() {
		return add(KeyValueLoggerKeys.MONTH, LocalDate.now().getMonthValue());
	}

	/**
	 * Adds log with key "date".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.date().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App month="2019-12-11"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger date() {
		return add(KeyValueLoggerKeys.DATE, LocalDate.now());
	}

	/**
	 * Adds log with key "year".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.year().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App year="2019"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger year() {
		return add(KeyValueLoggerKeys.YEAR, LocalDate.now().getYear());
	}

	/**
	 * Adds log with key "month".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.month().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App month="JULY"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger monthName() {
		return add(KeyValueLoggerKeys.MONTH, LocalDate.now().getMonth());
	}

	/**
	 * Adds log with key "day".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.day().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App day="MONDAY"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger dayName() {
		return add(KeyValueLoggerKeys.DAY, LocalDate.now().getDayOfWeek());
	}

	/**
	 * Adds log with key "time".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.time().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App time="22:20:12.523"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger time() {
		return dateTime(KeyValueLoggerKeys.TIME.toString(), "HH:mm:ss.SSS");
	}

	/**
	 * Adds log with key "dateTime".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.dateTime().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App dateTime="2019-11-20 22:20:12.523 +0000"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger dateTime() {
		return dateTime("yyyy-MM-dd HH:mm:ss.SSS Z");
	}

	/**
	 * Adds log with key "timeZone".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.timeZone().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App timeZone="-0500"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger timeZone() {
		return dateTime(KeyValueLoggerKeys.TIME_ZONE.toString(), "Z");
	}

	/**
	 * Adds log with key "timeZone".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.timeZoneName().info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App timeZone="America/Guayaquil"}
	 *
	 * @return Builder
	 */
	public KeyValueLogger timeZoneName() {
		return add(KeyValueLoggerKeys.TIME_ZONE, ZonedDateTime.now().getZone());
	}

	/**
	 * Adds log with key "dateTime".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.dateTime("yyyy-MM-dd HH:mm:ss.SSS Z").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App dateTime="2019-11-20 22:20:12.523 +0000"}
	 *
	 * @param format Date format
	 * @return Builder
	 */
	public KeyValueLogger dateTime(String format) {
		return dateTime(KeyValueLoggerKeys.DATE_TIME.toString(), format);
	}

	/**
	 * Adds a log with the current datetime.
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.dateTime("dateTime", "yyyy-MM-dd HH:mm:ss.SSS Z").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App dateTime="2019-11-20 22:20:12.523 +0000"}
	 *
	 * @param format Date format
	 * @return Builder
	 */
	public KeyValueLogger dateTime(String key, String format) {
		return add(key, ZonedDateTime.now().format(DateTimeFormatter.ofPattern(format)));
	}

	/**
	 * Adds log with key "time".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.time("HH:mm:ss.SSS").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App time="22:20:12.523"}
	 *
	 * @param format Date format
	 * @return Builder
	 */
	public KeyValueLogger time(String format) {
		return dateTime(KeyValueLoggerKeys.TIME.toString(), format);
	}

	/**
	 * Adds log with key "date".
	 * <p>
	 * Example:
	 * <p>
	 * {@code keyValueLogger.date("yyyy-MM-dd").info();}
	 * <p>
	 * Example output:
	 * <p>
	 * {@code 08:07:26 [main] INFO App date="2019-11-20"}
	 *
	 * @param format Date format
	 * @return Builder
	 */
	public KeyValueLogger date(String format) {
		return dateTime(KeyValueLoggerKeys.DATE.toString(), format);
	}

	private void log(Level level) {
		Throwable exception = getInternalException();
		if (exception != null) {
			logger.log(level, createMessage(), exception);
		} else {
			logger.log(level, createMessage());
		}
	}

	private KeyValueLogger level(Level level) {
		return add(KeyValueLoggerKeys.LEVEL, level);
	}

	/**
	 * Logs level INFO.
	 */
	public void info() {
		level(Level.INFO).log(Level.INFO);
	}

	/**
	 * Logs level FINE.
	 */
	public void fine() {
		level(Level.FINE).log(Level.FINE);
	}

	/**
	 * Logs level SEVERE.
	 */
	public void severe() {
		level(Level.SEVERE).log(Level.SEVERE);
	}

	/**
	 * Logs level ALL.
	 */
	public void all() {
		level(Level.ALL).log(Level.ALL);
	}

	/**
	 * Logs level FINER.
	 */
	public void finer() {
		level(Level.FINER).log(Level.FINER);
	}

	/**
	 * Logs level CONFIG.
	 */
	public void config() {
		level(Level.CONFIG).log(Level.CONFIG);
	}

	/**
	 * Logs level FINEST.
	 */
	public void finest() {
		level(Level.FINEST).log(Level.FINEST);
	}

	/**
	 * Logs level WARNING.
	 */
	public void warning() {
		level(Level.WARNING).log(Level.WARNING);
	}

	private String createMessage() {
		return pairs.stream().filter(Pair::isValid).filter(Pair::isExternal).map(Pair::formatKeyValue).collect(joining(" "));
	}

	private Throwable getInternalException() {
		Pair internalPair = pairs.stream().filter(Pair::isInternal).filter(pair -> pair.equalsKey(KeyValueLoggerKeys.EXCEPTION.toString())).findFirst().orElse(null);
		if (internalPair == null) {
			return null;
		}

		return (Throwable) internalPair.getValue();
	}

	private class Pair {
		private static final String NULL = "null";
		private static final String KEY_VALUE_FORMAT = "%s=\"%s\"";
		private static final String DEFAULT_CUSTOM_VALUE_FORMAT = "%s";

		private String key;
		private Object[] values;
		private String valueFormat;
		private boolean internal;

		Pair(boolean internal, String key, String valueFormat, Object[] values) {
			this.internal = internal;
			this.key = key == null ? NULL : key;
			this.valueFormat = valueFormat == null ? DEFAULT_CUSTOM_VALUE_FORMAT : valueFormat;
			this.values = values == null ? new Object[] {} : values;
		}

		boolean isValid() {
			return !key.isEmpty();
		}

		boolean isExternal() {
			return !internal;
		}

		boolean isInternal() {
			return internal;
		}

		String formatKeyValue() {
			String cleanKey = getKey();

			return String.format(KEY_VALUE_FORMAT, cleanKey, formatValue());
		}

		boolean equalsKey(String key) {
			return getKey().equals(formatKey(key));
		}

		String getKey() {
			return formatKey(key);
		}

		String formatKey(String key) {
			String cleanKey = key.replaceAll("[^a-zA-Z0-9_.]", "");

			if (isInternal()) {
				cleanKey = String.format("__%s__", cleanKey);
			}

			return cleanKey;
		}

		Object getValue() {
			if (values.length > 0) {
				return values[0];
			} else {
				return null;
			}
		}

		String formatValue() {
			return String.format(valueFormat, getStringValues());
		}

		Object[] getStringValues() {
			return Arrays.stream(values).map(this::valueToString).map(this::cleanValue).toArray();
		}

		private String valueToString(Object value) {
			if (value == null) {
				return NULL;
			}

			if (value instanceof Object[]) {
				return Arrays.toString((Object[]) value);
			} else if (value instanceof int[]) {
				return Arrays.toString((int[]) value);
			} else if (value instanceof double[]) {
				return Arrays.toString((double[]) value);
			} else if (value instanceof long[]) {
				return Arrays.toString((long[]) value);
			} else if (value instanceof boolean[]) {
				return Arrays.toString((boolean[]) value);
			} else if (value instanceof byte[]) {
				return Arrays.toString((byte[]) value);
			} else if (value instanceof short[]) {
				return Arrays.toString((short[]) value);
			} else if (value instanceof float[]) {
				return Arrays.toString((float[]) value);
			} else if (value instanceof char[]) {
				return Arrays.toString((char[]) value);
			}

			return value.toString();
		}

		private String cleanValue(String value) {
			return value.replace("'", "").replace("\"", "").replace("\n", " ").trim();
		}
	}

	private enum KeyValueLoggerKeys {
		PACKAGE("package"), CLASS("class"), ENDPOINT("endpoint"), SERVICE("service"), EXCEPTION("exception"), HTTP_STATUS("httpStatus"), HTTP_METHOD("httpMethod"), TRANSACTION("transaction"), VALUE("value"), TYPE("type"), SESSION("session"),
		TRACK("track"), REQUEST("request"), CODE("code"), METHOD("method"), ENVIRONMENT("environment"), STATUS("status"), MESSAGE("message"), NAME("name"), DURATION("duration"), LANGUAGE("language"), ARGUMENTS("arguments"), ID("id"), FAIL("fail"),
		SUCCESS("success"), DAY("day"), MONTH("month"), DATE("date"), YEAR("year"), TIME("time"), DATE_TIME("dateTime"), TIME_ZONE("timeZone"), ACTION("action"), LEVEL("level");

		private final String toStringKey;

		KeyValueLoggerKeys(String toStringKey) {
			this.toStringKey = toStringKey;
		}

		@Override
		public String toString() {
			return toStringKey;
		}
	}

}
