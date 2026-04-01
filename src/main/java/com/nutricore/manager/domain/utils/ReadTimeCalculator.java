package com.nutricore.manager.domain.utils;

public final class ReadTimeCalculator {

    private static final int WORDS_PER_MINUTE = 220;

    private ReadTimeCalculator() {
    }

    public static int estimateMinutes(String content) {
        if (content == null || content.isBlank()) {
            return 1;
        }

        long words = content.trim().split("\\s+").length;
        return Math.max(1, (int) Math.ceil((double) words / WORDS_PER_MINUTE));
    }
}
