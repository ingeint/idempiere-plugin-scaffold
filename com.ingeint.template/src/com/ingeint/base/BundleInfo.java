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
 * Copyright (C) 2019 INGEINT <https://www.ingeint.com> and contributors (see README.md file).
 */

package com.ingeint.base;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Singleton class for Bundle information
 */
public final class BundleInfo {

	private static String ATTRIBUTE_BUNDLE_NAME = "Bundle-Name";
	private static String ATTRIBUTE_BUNDLE_ID = "Bundle-SymbolicName";
	private static String ATTRIBUTE_BUNDLE_VERSION = "Bundle-Version";
	private static String ATTRIBUTE_BUNDLE_VENDOR = "Bundle-Vendor";
	private static String ATTRIBUTE_BUNDLE_CATEGORY = "Bundle-Category";
	private static String VALUE_BUNDLE_CATEGORY = "idempiere-plugin";

	private static BundleInfo instance = null;
	private Manifest manifest = null;

	/**
	 * Private constructor
	 * 
	 * @throws IOException If not found manifest file
	 */
	private BundleInfo() throws IOException {
		manifest = findPluginManifest();
	}

	/**
	 * Find the correct Manifest file for this bundle
	 * 
	 * @throws IOException
	 */
	private Manifest findPluginManifest() throws IOException {
		Enumeration<URL> resources = getClass().getClassLoader().getResources(JarFile.MANIFEST_NAME);
		while (resources.hasMoreElements()) {
			URL manifestPath = resources.nextElement();
			Manifest currentManifest = new Manifest(manifestPath.openStream());
			if (VALUE_BUNDLE_CATEGORY.equals(getBundleCategory(currentManifest))) {
				return currentManifest;
			}
		}
		return new Manifest();
	}

	/**
	 * Create a return a BundleInfo singleton object
	 * 
	 * @return Singleton object
	 * @throws IOException If not found manifest file
	 */
	public synchronized static BundleInfo getInstance() throws IOException {
		if (instance == null)
			instance = new BundleInfo();
		return instance;
	}

	/**
	 * Gets the actual Bundle Name
	 * 
	 * @return Bundle Name
	 */
	public String getBundleName() {
		return manifest.getMainAttributes().getValue(ATTRIBUTE_BUNDLE_NAME);
	}

	/**
	 * Gets the actual Bundle Vendor
	 * 
	 * @return Bundle Vendor
	 */
	public String getBundleVendor() {
		return manifest.getMainAttributes().getValue(ATTRIBUTE_BUNDLE_VENDOR);
	}

	/**
	 * Gets the actual Bundle Version
	 * 
	 * @return Bundle Version
	 */
	public String getBundleVersion() {
		return manifest.getMainAttributes().getValue(ATTRIBUTE_BUNDLE_VERSION);
	}

	/**
	 * Gets the actual Bundle ID
	 * 
	 * @return Bundle ID
	 */
	public String getBundleID() {
		try {
			return manifest.getMainAttributes().getValue(ATTRIBUTE_BUNDLE_ID).split(";")[0];
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets VALUE_BUNDLE_CATEGORY
	 * 
	 * @param manifest
	 * 
	 * @return Bundle Category
	 */
	private String getBundleCategory(Manifest manifest) {
		return manifest.getMainAttributes().getValue(ATTRIBUTE_BUNDLE_CATEGORY);
	}

	@Override
	public String toString() {
		return String.format("[BundleName=%s, BundleVendor=%s, BundleVersion=%s, BundleID=%s]", getBundleName(), getBundleVendor(), getBundleVersion(), getBundleID());
	}

}
