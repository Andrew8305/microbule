package org.microbule.spi;

import java.util.function.Function;

public interface JaxrsServerProperties {
//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    default Double getDoubleProperty(String key) {
        return getProperty(key, Double::parseDouble);
    }

    default Integer getIntProperty(String key) {
        return getProperty(key, Integer::parseInt);
    }

    String getProperty(String key);

    default String getProperty(String key, String defaultValue) {
        final String value = getProperty(key);
        return value == null ? defaultValue : value;
    }

    <T> T getProperty(String key, Function<String, T> xform);

    default <T> T getProperty(String key, Function<String,T> xform, T defaultValue) {
        final T value = getProperty(key, xform);
        return value == null ? defaultValue : value;
    }

    /**
     * Returns the value of a boolean property
     * @param key the property key
     * @return whether or not the value of the property is 'true' (case insensitive)
     */
    default boolean isTrue(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
}
