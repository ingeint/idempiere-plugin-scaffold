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

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class TimestampUtilTest {

	@Test
	public void returnNotNullTimestamp() {
		assertThat(TimestampUtil.now()).isNotNull();
	}

	@Test
	public void trueIfTimestampIsTodayUsingUtil() {
		assertThat(TimestampUtil.isToday(TimestampUtil.today())).isTrue();
	}

	@Test
	public void trueIfTimestampIsNowUsingUtil() {
		assertThat(TimestampUtil.isToday(TimestampUtil.now())).isTrue();
	}

	@Test
	public void falseIfTimestampIsTomorrowUsingUtil() {
		assertThat(TimestampUtil.isToday(Timestamp.valueOf(LocalDateTime.now().plusDays(20)))).isFalse();
	}

	@Test
	public void trueIfTimestampIsToday() {
		LocalDate now = LocalDate.now();
		assertThat(now).isEqualTo(TimestampUtil.today().toLocalDateTime().toLocalDate());
	}

	@Test
	public void timestampToString() {
		String format = "yyyy-MM-dd HH:mm:ss";
		String expected = "2020-01-20 20:10:20";
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.parse(expected, DateTimeFormatter.ofPattern(format)));

		assertThat(TimestampUtil.toString(timestamp, format)).isEqualTo(expected);
	}

	@Test
	public void returnNullIfTimeIsNull() {
		assertThat(TimestampUtil.toString(null, "")).isNull();
	}

	@Test
	public void timestampToStringWithTimeZone() {
		ZonedDateTime now = ZonedDateTime.now();

		String format = "yyyy-MM-dd HH:mm:ss Z";
		String expected = now.format(DateTimeFormatter.ofPattern(format));
		Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());

		assertThat(TimestampUtil.toString(timestamp, format)).isEqualTo(expected);
	}

	@Test
	public void timestampToStringDate() {
		String format = "yyyy-MM-dd";
		String expected = "2020-01-20";
		Timestamp timestamp = Timestamp.valueOf(LocalDate.parse(expected, DateTimeFormatter.ofPattern(format)).atStartOfDay());

		assertThat(TimestampUtil.toString(timestamp, format)).isEqualTo(expected);
	}

	@Test
	public void stringToTimestamp() {
		String format = "yyyy-MM-dd HH:mm:ss";
		String dateTime = "2020-01-20 20:10:20";
		Timestamp expected = Timestamp.valueOf(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format)));

		assertThat(TimestampUtil.parse(dateTime, format)).isEqualTo(expected);
	}

	@Test
	public void stringTimeZoneToTimestamp() {
		String format = "yyyy-MM-dd HH:mm:ss Z";
		String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern(format));
		Timestamp expected = Timestamp.valueOf(ZonedDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format)).toLocalDateTime());

		assertThat(TimestampUtil.parse(dateTime, format)).isEqualTo(expected);
	}

	@Test
	public void stringDateToTimestamp() {
		String format = "yyyy-MM-dd";
		String dateTime = "2020-01-20";
		Timestamp expected = Timestamp.valueOf(LocalDate.parse(dateTime, DateTimeFormatter.ofPattern(format)).atStartOfDay());

		assertThat(TimestampUtil.parse(dateTime, format)).isEqualTo(expected);
	}
}
