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
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileTemplateBuilderTest {

	private FileTemplateBuilder builder = FileTemplateBuilder.builder();
	private Invoice invoice;
	private InvoiceLine invoiceLine;
	private String randomName;
	private String randomId;
	private String randomProduct;
	private double randomPrice;

	@BeforeEach
	public void setup() {
		invoiceLine = new InvoiceLine();
		invoiceLine.setProduct(randomProduct = getRandomString());
		invoiceLine.setPrice(randomPrice = getRandomDouble());

		invoice = new Invoice();
		invoice.setName(randomName = getRandomName());
		invoice.setId(randomId = getRandomID());
		invoice.setInvoiceLines(Arrays.asList(invoiceLine));
	}

	@Test
	public void createFileTemplate() {
		String result = builder.file("resources/xml/xml-invoice.xml").inject("invoice", invoice).build();
		assertThat(result).contains("<name>" + randomName + "</name>");
		assertThat(result).contains("<id>" + randomId + "</id>");
		assertThat(result).contains("<product name=\"" + randomProduct + "\" price=\"" + randomPrice + "\"/>");
	}

	public class Invoice {
		private String name;
		private String id;
		private List<InvoiceLine> invoiceLines;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public List<InvoiceLine> getInvoiceLines() {
			return invoiceLines;
		}

		public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
			this.invoiceLines = invoiceLines;
		}
	}

	public class InvoiceLine {
		private String product;
		private double price;

		public String getProduct() {
			return product;
		}

		public void setProduct(String product) {
			this.product = product;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}
	}
}
