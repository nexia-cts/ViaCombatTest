package com.nexia.viacombattest;

import com.nexia.viacombattest.platform.ViaCombatTestPlatform;

public class ViaCombatTest {

	public static final String VERSION = "${version}";

	private static ViaCombatTestPlatform platform;

	private ViaCombatTest() {
	}

	public static void init(final ViaCombatTestPlatform platform) {
		if (ViaCombatTest.platform != null) throw new IllegalStateException("ViaCombatTest is already initialized!");

		ViaCombatTest.platform = platform;
	}

	public static ViaCombatTestPlatform getPlatform() {
		return ViaCombatTest.platform;
	}

}
