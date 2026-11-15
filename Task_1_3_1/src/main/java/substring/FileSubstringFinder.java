package substring;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Поиск подстроки в файле UTF-8.
 */
public final class FileSubstringFinder {

    private FileSubstringFinder() {
    }

    /**
     * Находит все вхождения подстроки в файле.
     *
     * @param filename путь к файлу
     * @param needle   подстрока
     * @return индексы вхождений
     * @throws IOException при ошибке чтения
     */
    public static List<Integer> find(String filename, String needle) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {
            return SubstringFinder.findAll(reader, needle);
        }
    }
}