package chat.utils;

public class PrettyPrinter {

    public static String getTextWithBorders(String text) {
        String[] lines = text.split("\n");

        int maxLineLength = text.lines()
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(0);

        String header = "." + "_".repeat(maxLineLength + 2) + ".";
        StringBuilder result = new StringBuilder(header + "\n");
        for (String line : lines) {
            result.append("| ").append(line);
            int spaces = maxLineLength - line.length() + 1;
            result.append(" ".repeat(spaces)).append("|");
            result.append("\n");
        }
        String footer = "|" + "_".repeat(maxLineLength + 2) + "|";

        return result.append(footer).toString();
    }

    public static String getTextWithDoubleBorders(String text) {
        String textWithBorders = getTextWithBorders(text);
        String[] lines = textWithBorders.split("\n");
        int len = lines[0].length();

        String header = "." + "_".repeat(len + 2) + ".";
        StringBuilder sb = new StringBuilder(header + "\n");
        for (String line : lines) {
            sb.append("| ").append(line).append(" |").append("\n");
        }

        String footer = "|" + "_".repeat(len + 2) + "|";
        return sb.append(footer).toString();
    }

    public static void main(String[] args) {
        String text = """
                int main(void)
                {
                    printf( "| Greed    : %-10d |\\n", 6 );
                    printf( "| Gluttony : %-10d |\\n", 16 );
                    printf( "| Lust     : %-10d |\\n", 116 );
                    return 0;
                }
                """;
        System.out.println(getTextWithBorders(text));
        System.out.println("\n");
        System.out.println(getTextWithDoubleBorders(text));


        String s = """
                                     ,---.           ,---.
                                    / /"`.\\.--""\"--./,'"\\ \\
                                    \\ \\    _       _    / /
                                     `./  / __   __ \\  \\,'
                                      /    /_O)_(_O\\    \\
                                      |  .-'  ___  `-.  |
                                   .--|       \\_/       |--.
                                 ,'    \\   \\   |   /   /    `.
                                /       `.  `--^--'  ,'       \\
                             .-""\"""-.    `--.___.--'     .-""\"""-.
                .-----------/         \\------------------/         \\--------------.
                | .---------\\         /----------------- \\         /------------. |
                | |          `-`--`--'                    `--'--'-'             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |                                                             | |
                | |_____________________________________________________________| |
                |_________________________________________________________________|
                                   )__________|__|__________(
                                  |            ||            |
                                  |____________||____________|
                                    ),-----.(      ),-----.(
                                  ,'   ==.   \\    /  .==    `.
                                 /            )  (            \\
                                 `==========='    `==========='  hjw
                """;


        System.out.println(s);
    }




}
