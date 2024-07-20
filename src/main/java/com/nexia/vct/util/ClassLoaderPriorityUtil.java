package com.nexia.vct.util;

import com.nexia.vct.ViaCombatTest;
import net.lenni0451.reflect.ClassLoaders;
import net.lenni0451.reflect.stream.RStream;

import java.io.File;


public class ClassLoaderPriorityUtil {
    /**
     * Loads all overriding jars
     */
    public static void loadOverridingJars(final File directory) {
        try {
            final File jarsDirectory = new File(directory, "jars");
            if (!jarsDirectory.exists()) {
                jarsDirectory.mkdir();
                return;
            }

            final File[] files = jarsDirectory.listFiles();
            if (files != null && files.length > 0) {
                final ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
                try {
                    final ClassLoader actualLoader = RStream.of(oldLoader).fields().by("urlLoader").get();
                    Thread.currentThread().setContextClassLoader(actualLoader);
                    for (File file : files) {
                        if (file.getName().endsWith(".jar")) {
                            ClassLoaders.loadToFront(file.toURI().toURL());
                            ViaCombatTest.LOGGER.warn("Loaded overriding jar {}", file.getName());
                        }
                    }
                } finally {
                    Thread.currentThread().setContextClassLoader(oldLoader);
                }
            }
        } catch (Throwable e) {
            ViaCombatTest.LOGGER.error("Failed to load overriding jars", e);
        }
    }
}
