package com.investingchannel.uat.webservice.util;

public final class StringBuilderUtil {
    private StringBuilderUtil() {
    }

    public static void capitalize(final StringBuilder pStringBuilder) {
        toCharacterCase(pStringBuilder, (byte) 3, 0);
    }

    public static void uncapitalize(final StringBuilder pStringBuilder) {
        toCharacterCase(pStringBuilder, (byte) 1, 0);
    }

    public static void clear(final StringBuilder pStringBuilder) {
        pStringBuilder.delete(0, pStringBuilder.length());
    }

    public static boolean contains(
            final StringBuilder pStringBuilder,
            final char pCharacter) {
        boolean contains = false;

        for (int i = 0; !contains && i < pStringBuilder.length(); i++) {
            final char character = pStringBuilder.charAt(i);

            contains = character == pCharacter;
        }

        return contains;
    }

    public static void replace(
            final StringBuilder pStringBuilder,
            final char pOldCharacter,
            final char pNewCharacter) {
        replace(pStringBuilder, pOldCharacter, pNewCharacter, (byte) 2);
    }

    public static void replace(
            final StringBuilder pStringBuilder,
            final char pOldCharacter,
            final char pNewCharacter,
            final byte pCharacterCase) {
        for (int i = 0; i < pStringBuilder.length(); i++) {
            final char character = pStringBuilder.charAt(i);

            if (character == pOldCharacter) {
                pStringBuilder.setCharAt(i, pNewCharacter);
            }
            else {
                final char caseCharacter;

                switch (pCharacterCase) {
                    case 1:
                        caseCharacter = Character.toLowerCase(character);
                        break;

                    case 3:
                        caseCharacter = Character.toUpperCase(character);
                        break;

                    default:
                        caseCharacter = character;
                        break;
                }

                pStringBuilder.setCharAt(i, caseCharacter);
            }
        }
    }

    public static void toLowerCase(final StringBuilder pStringBuilder) {
        toCharacterCase(pStringBuilder, (byte) 1);
    }

    public static void toUpperCase(final StringBuilder pStringBuilder) {
        toCharacterCase(pStringBuilder, (byte) 3);
    }

    public static void toCharacterCase(
            final StringBuilder pStringBuilder,
            final byte pCharacterCase) {
        for (int i = 0; i < pStringBuilder.length(); i++) {
           toCharacterCase(pStringBuilder, pCharacterCase, i);
        }
    }

    public static void toCharacterCase(
            final StringBuilder pStringBuilder,
            final byte pCharacterCase,
            final int pIndex) {
        final char character = pStringBuilder.charAt(pIndex);
        final char caseCharacter;

        switch (pCharacterCase) {
            case 1:
                caseCharacter = Character.toLowerCase(character);
                break;

            case 3:
                caseCharacter = Character.toUpperCase(character);
                break;

            default:
                caseCharacter = character;
                break;
        }

        pStringBuilder.setCharAt(pIndex, caseCharacter);
    }
}
