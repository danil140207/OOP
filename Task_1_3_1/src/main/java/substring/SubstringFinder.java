package substring;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Поиск подстроки в потоке символов.
 */
public final class SubstringFinder {

    private SubstringFinder() {
    }

    /**
     * Находит все вхождения подстроки в потоке.
     *
     * @param reader поток чтения
     * @param needle искомая подстрока
     * @return индексы начала вхождений
     * @throws IOException при ошибке чтения
     */
    public static List<Integer> findAll(Reader reader, String needle) throws IOException {
        if (needle == null || needle.isEmpty()) {
            throw new IllegalArgumentException("Подстрока не может быть пустой");
        }
        List<Integer> result = new ArrayList<>();
        int len = needle.length();
        char[] window = new char[len];
        int filled = 0;
        long position = 0;

        char[] buf = new char[8192];
        int read;
        while ((read = reader.read(buf)) != -1) {
            for (int i = 0; i < read; i++) {
                char c = buf[i];
                if (filled < len) {
                    window[filled++] = c;
                } else {
                    System.arraycopy(window, 1, window, 0, len - 1);
                    window[len - 1] = c;
                }
                if (filled == len && matches(window, needle)) {
                    result.add((int) (position - len + 1));
                }
                position++;
            }
        }
        return result;
    }

    private static boolean matches(char[] window, String needle) {
        for (int i = 0; i < needle.length(); i++) {
            if (window[i] != needle.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
