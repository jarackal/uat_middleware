package com.investingchannel.uat.webservice.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;


public final class PropertiesUtil {
    private final static Log LOG = LogFactory.getLog(PropertiesUtil.class);

    private PropertiesUtil() {
    }

    public static void setSystemProperties(final Map<String, String> pSystemPropertyMap) {
        for (final Entry<String, String> systemPropertyEntry : pSystemPropertyMap.entrySet()) {
            final String key = systemPropertyEntry.getKey();
            final String value = systemPropertyEntry.getValue();

            System.setProperty(key, value);
        }
    }

    public static Properties createProperties(final Resource[] pResources) {
        final Properties resolvedProperties = new Properties();

        new PropertyPlaceholderConfigurer() {
            private void populateProperties() {
                setLocations(pResources);

                try {
                    final Properties mergedProperties = mergeProperties();
                    final Set<?> hashSet = new HashSet<Object>();

                    for (final Object key : mergedProperties.keySet()) {
                        final String propertyKey = key.toString();
                        final String propertyValue = mergedProperties.getProperty(propertyKey);
                        @SuppressWarnings("deprecation")
						final String resolvedPropertyValue = parseStringValue(propertyValue, mergedProperties, hashSet);

                        resolvedProperties.put(propertyKey, resolvedPropertyValue);
                    }
                }
                catch (final IOException e) {
                    LOG.error("Error merging properties", e);
                }
            }
        }.populateProperties();

        return resolvedProperties;
    }

    public static void resolveProperties(
                final String pString,
                final Properties pProperties,
                final StringBuilder pPropertyStringBuilder,
                final StringBuilder pTempStringBuilder) {
        StringBuilderUtil.clear(pPropertyStringBuilder);
        StringBuilderUtil.clear(pTempStringBuilder);

        boolean hasPropertyStart1 = false;
        boolean hasPropertyStart2 = false;

        for (int i = 0; i <  pString.length(); i++) {
            final char character = pString.charAt(i);

            if (hasPropertyStart2) {
                if (character == '}') {
                    final String propertyName = pTempStringBuilder.toString();
                    final String propertyValue = pProperties.getProperty(propertyName);

                    if (propertyValue != null) {
                        pPropertyStringBuilder.append(propertyValue);
                    }

                    StringBuilderUtil.clear(pTempStringBuilder);
                    hasPropertyStart1 = false;
                    hasPropertyStart2 = false;
                }
                else {
                    pTempStringBuilder.append(character);
                }
            }
            else if (character == '{' && hasPropertyStart1) {
                hasPropertyStart2 = true;
            }
            else if (character == '$') {
                if (hasPropertyStart1) {
                    pPropertyStringBuilder.append('$');
                }

                hasPropertyStart1 = true;
            }
            else {
                if (hasPropertyStart1) {
                    pPropertyStringBuilder.append('$');
                }

                pPropertyStringBuilder.append(character);

                hasPropertyStart1 = false;
                hasPropertyStart2 = false;
            }
        }
    }
}
