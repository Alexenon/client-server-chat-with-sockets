package chat.utils;

import java.util.List;
import java.util.stream.IntStream;

public class BlockPrinter {

    private static final String SPACE = " ";
    private static final String NEW_LINE = "\n";

    private static final int SPACE_BLOCK_LENGHT = 1;
    private static final int CHARACTER_BLOCK_HEIGHT = 6;
    private static final int SPACE_LENGHT_BETWEEN_WORDS = 4;

    private static final String REGEX_ALLOWED_CHARACTERS = "^[a-zA-Z0-9?!.,_\s\\-=()\\[\\]]+$";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890?!.,_-=()[]";
    /*
     * https://www.asciiart.eu/text-to-ascii-art -> Font name: ANSI Shadow
     * */
    private static final List<String> CHARACTER_BLOCKS = List.of(
            "█████╗   ██████╗   ██████╗ ██████╗  ███████╗ ███████╗  ██████╗  ██╗  ██╗ ██╗      ██╗ ██╗  ██╗ ██╗      ███╗   ███╗ ███╗   ██╗  ██████╗  ██████╗   ██████╗  ██████╗  ███████╗ █████████╗ ██╗   ██╗ ██╗   ██╗ ██╗    ██╗ ██╗  ██╗ ██╗   ██╗ ███████╗  ██╗ ██████╗  ██████╗  ██╗  ██╗ ███████╗  ██████╗ ███████╗  █████╗   █████╗   ██████╗  ██████╗  ██╗                                     ██╗ ██╗  ███╗ ███╗",
            "██╔══██╗ ██╔══██╗ ██╔════╝ ██╔══██╗ ██╔════╝ ██╔════╝ ██╔════╝  ██║  ██║ ██║      ██║ ██║ ██╔╝ ██║      ████╗ ████║ ████╗  ██║ ██╔═══██╗ ██╔══██╗ ██╔═══██╗ ██╔══██╗ ██╔════╝ ╚══ ██╔══╝ ██║   ██║ ██║   ██║ ██║    ██║ ╚██╗██╔╝ ╚██╗ ██╔╝ ╚══███╔╝ ███║ ╚════██╗ ╚════██╗ ██║  ██║ ██╔════╝ ██╔════╝ ╚════██║ ██╔══██╗ ██╔══██╗ ██╔═████╗ ╚════██╗ ██║                           ▄▄▄▄▄▄▄╗ ██╔╝ ╚██╗ ██╔╝ ╚██║",
            "███████║ ██████╔╝ ██║      ██║  ██║ █████╗   █████╗   ██║  ███╗ ███████║ ██║      ██║ █████╔╝  ██║      ██╔████╔██║ ██╔██╗ ██║ ██║   ██║ ██████╔╝ ██║   ██║ ██████╔╝ ███████╗     ██║    ██║   ██║ ██║   ██║ ██║ █╗ ██║  ╚███╔╝   ╚████╔╝    ███╔╝  ╚██║  █████╔╝  █████╔╝ ███████║ ███████╗ ███████╗     ██╔╝ ╚█████╔╝ ╚██████║ ██║██╔██║   ▄███╔╝ ██║                  ███████╗ ╚══════╝ ██║   ██║ ██║   ██║",
            "██╔══██║ ██╔══██╗ ██║      ██║  ██║ ██╔══╝   ██╔══╝   ██║   ██║ ██╔══██║ ██║ ██   ██║ ██╔═██╗  ██║      ██║╚██╔╝██║ ██║╚██╗██║ ██║   ██║ ██╔═══╝  ██║   ██║ ██╔══██╗ ╚════██║     ██║    ██║   ██║ ╚██╗ ██╔╝ ██║███╗██║  ██╔██╗    ╚██╔╝    ███╔╝    ██║ ██╔═══╝   ╚═══██╗ ╚════██║ ╚════██║ ██╔═══██╗   ██╔╝  ██╔══██╗  ╚═══██║ ████╔╝██║   ██══╝  ╚═╝                  ╚══════╝ ▄▄▄▄▄▄▄╗ ██║   ██║ ██║   ██║",
            "██║  ██║ ██████╔╝ ╚██████╗ ██████╔╝ ███████╗ ██║      ╚██████╔╝ ██║  ██║ ██║ ╚█████╔╝ ██║  ██╗ ███████╗ ██║ ╚═╝ ██║ ██║ ╚████║ ╚██████╔╝ ██║      ╚██████╔╝ ██║  ██║ ███████║     ██║    ╚██████╔╝  ╚████╔╝  ╚███╔███╔╝ ██╔╝ ██╗    ██║    ███████╗  ██║ ███████╗ ██████╔╝      ██║ ███████║ ╚██████╔╝   ██║   ╚█████╔╝  █████╔╝ ╚██████╔╝   ▄▄╗    ██╗ ██╗ ▄█╗ ███████╗          ╚══════╝ ╚██╗ ██╔╝ ███╗ ███║",
            "╚═╝  ╚═╝ ╚═════╝   ╚═════╝ ╚═════╝  ╚══════╝ ╚═╝       ╚═════╝  ╚═╝  ╚═╝ ╚═╝  ╚════╝  ╚═╝  ╚═╝ ╚══════╝ ╚═╝     ╚═╝ ╚═╝  ╚═══╝  ╚═════╝  ╚═╝       ╚══▀▀═╝  ╚═╝  ╚═╝ ╚══════╝     ╚═╝     ╚═════╝    ╚═══╝    ╚══╝╚══╝  ╚═╝  ╚═╝    ╚═╝    ╚══════╝  ╚═╝ ╚══════╝ ╚═════╝       ╚═╝ ╚══════╝  ╚═════╝    ╚═╝    ╚════╝   ╚════╝   ╚═════╝    ╚═╝    ╚═╝ ╚═╝ ╚═╝ ╚══════╝                    ╚═╝ ╚═╝  ╚══╝ ╚══╝"
    );

    private static final List<Integer> listOfCharacterBlockLenghts = List.of(
            8, 8, 8, 8, 8, 8, 9, 8, 3, 8, 8, 8, 11,   // ABCDEFGHIJKLM
            10, 9, 8, 9, 8, 8, 10, 9, 9, 10, 8, 9, 8, // NOPQRSTUVWXYZ
            4, 8, 8, 8, 8, 8, 8, 8, 8, 9,             // 1234567890
            8, 3, 3, 3, 8, 8, 8, 4, 4, 4, 4           // ?!.,_-=()[]
    );

    public static String blockPrint(String text) {
        if (text.isBlank()) return "";
        if (!text.matches(REGEX_ALLOWED_CHARACTERS)) return "";

        text = text.stripLeading().stripTrailing().toUpperCase(); // Removes leading and trailing spaces

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CHARACTER_BLOCK_HEIGHT; i++) {
            String blockLine = CHARACTER_BLOCKS.get(i);
            for (int j = 0; j < text.length(); j++) {
                char c = text.charAt(j);

                if (Character.isSpaceChar(c)) {
                    sb.append(SPACE.repeat(SPACE_LENGHT_BETWEEN_WORDS));
                    continue;
                }

                int characterBlockStartIndex = getCharacterBlockStartIndex(c);
                int characterBlockLength = listOfCharacterBlockLenghts.get(ALPHABET.indexOf(c));
                int characterBlockEndIndex = characterBlockStartIndex + characterBlockLength;
                String line = blockLine.substring(characterBlockStartIndex, characterBlockEndIndex) + SPACE;

                // Removes the trailing spaces for last char character in the line
                if (j == text.length() - 1)
                    line = line.stripTrailing();

                sb.append(line);
            }
            sb.append(NEW_LINE);
        }

        return sb.substring(0, sb.length() - 1);
    }

    private static int getCharacterBlockStartIndex(char c) {
        int indexInAlphabet = ALPHABET.indexOf(c);
        return IntStream.range(0, indexInAlphabet)
                .map(i -> listOfCharacterBlockLenghts.get(i) + SPACE_BLOCK_LENGHT)
                .sum();
    }

    public static void main(String[] args) {
        System.out.println(blockPrint("Vladik - pidar =)"));
    }
}