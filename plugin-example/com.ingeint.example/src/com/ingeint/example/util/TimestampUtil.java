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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

/**
 * Helper for Timestamp
 */
public class TimestampUtil {

	/**
	 * Get the current Timestamp
	 * 
	 * @return Current timestamp
	 */
	public static Timestamp now() {
		return Timestamp.valueOf(LocalDateTime.now());
	}

	/**
	 * Get current timestamp with 00:00:00 time
	 * 
	 * @return Current day timestamp
	 */
	public static Timestamp today() {
		return Timestamp.valueOf(LocalDate.now().atStartOfDay());
	}

	/**
	 * Get timestamp for tomorrow
	 * 
	 * @return Tomorrow timestamp
	 */
	public static Timestamp tomorrow() {
		return Timestamp.valueOf(LocalDate.now().plusDays(1).atStartOfDay());
	}

	/**
	 * Get timestamp for yesterday
	 * 
	 * @return Yesterday timestamp
	 */
	public static Timestamp yesterday() {
		return Timestamp.valueOf(LocalDate.now().minusDays(1).atStartOfDay());
	}

	/**
	 * Convert timestamp
	 * 
	 * @param timestamp    Date to convert
	 * @param targetFormat Format for string @see <a href=
	 *                     "https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html">DateTimeFormatter</a>
	 * @return String date
	 */
	public static String toString(Timestamp timestamp, String targetFormat) {
		return timestamp == null ? null : timestamp.toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(targetFormat));
	}

	/**
	 * Converts string to date
	 * 
	 * @param dateTime String date time
	 * @param format   Current string format @see <a href=
	 *                 "https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html">DateTimeFormatter</a>
	 * @return Timestamp date
	 */
	public static Timestamp parse(String dateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		TemporalAccessor temporalAccessor = formatter.parse(dateTime);

		if (temporalAccessor.query(TemporalQueries.zone()) != null) {
			return Timestamp.valueOf(ZonedDateTime.parse(dateTime, formatter).toLocalDateTime());
		} else if (temporalAccessor.query(TemporalQueries.localTime()) != null) {
			return Timestamp.valueOf(LocalDateTime.parse(dateTime, formatter));
		}

		return Timestamp.valueOf(LocalDate.parse(dateTime, formatter).atStartOfDay());
	}

	/**
	 * Checks if timestamp is today
	 * 
	 * @param date to check
	 * @return True if is today
	 */
	public static boolean isToday(Timestamp date) {
		if (date == null) {
			return false;
		}
		return LocalDate.now().isEqual(date.toLocalDateTime().toLocalDate());
	}
}
