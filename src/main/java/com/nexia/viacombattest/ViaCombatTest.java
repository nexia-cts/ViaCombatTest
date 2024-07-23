package com.nexia.viacombattest;

import com.nexia.viacombattest.platform.ViaCombatTestPlatform;

/**
 * Addon's main class
 */
public class ViaCombatTest {

	/**
	 * Version of addon
	 */
	public static final String VERSION = "${version}";

	/**
	 * Addon's platform
	 */
	private static ViaCombatTestPlatform platform;

	/**
	 * Addon's config file (empty)
	 */
	private static ViaCombatTestConfig config;

	private ViaCombatTest() {}

	/**
	 * Initializes the addon
	 * @param platform platform variable
	 * @param config config variable
	 */
	public static void init(final ViaCombatTestPlatform platform, final ViaCombatTestConfig config) {
		if (ViaCombatTest.platform != null) throw new IllegalStateException("ViaCombatTest is already initialized!");

		ViaCombatTest.platform = platform;
		ViaCombatTest.config = config;
	}

	/**
	 * Platform
	 * @return Returns the addon's platform
	 */
	public static ViaCombatTestPlatform getPlatform() {
		return ViaCombatTest.platform;
	}

	/**
	 * Config
	 * @return Returns the addon's config
	 */
	public static ViaCombatTestConfig getConfig() {
		return ViaCombatTest.config;
	}

}
