package org.kartishev.voltage.domain.enumeration;

import com.google.common.base.Preconditions;

public enum Language {
    RUSSIAN("ru"), ENGLISH("en"), LATVIAN("lv");

    private String shortName;

    Language(String shortName) {
        this.shortName = shortName;
    }

    public static Language getByShortName(String shortName) {
        Preconditions.checkNotNull(shortName);

        for (Language lang : values()) {
            if (lang.shortName.equals(shortName)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("No language for passed value: " + shortName);
    }
}
