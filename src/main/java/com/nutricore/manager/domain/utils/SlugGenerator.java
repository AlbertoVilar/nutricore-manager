package com.nutricore.manager.domain.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public final class SlugGenerator {

    private static final Pattern NON_ASCII = Pattern.compile("[^\\p{ASCII}]");
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-z0-9]+");
    private static final Pattern DUPLICATED_DASH = Pattern.compile("-{2,}");

    private SlugGenerator() {
    }

    public static String slugify(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
        String withoutAccents = NON_ASCII.matcher(normalized).replaceAll("");
        String lowercase = withoutAccents.toLowerCase(Locale.ROOT).trim();
        String withDashes = NON_ALPHANUMERIC.matcher(lowercase).replaceAll("-");

        return DUPLICATED_DASH.matcher(withDashes)
                .replaceAll("-")
                .replaceAll("^-|-$", "");
    }
}
