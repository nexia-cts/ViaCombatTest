package com.nexia.viacombattest;

import com.nexia.viacombattest.platform.ViaCombatTestPlatform;

public class ViaCombatTest {

	public static final String VERSION = "${version}";

	private static ViaCombatTestPlatform platform;
	private static ViaCombatTestConfig config;

	private ViaCombatTest() {
	}

	public static void init(final ViaCombatTestPlatform platform, final ViaCombatTestConfig config) {
		if (ViaCombatTest.platform != null) throw new IllegalStateException("ViaCombatTest is already initialized!");

		ViaCombatTest.platform = platform;
		ViaCombatTest.config = config;
	}

	public static ViaCombatTestPlatform getPlatform() {
		return ViaCombatTest.platform;
	}

	public static ViaCombatTestConfig getConfig() {
		return ViaCombatTest.config;
	}

}
