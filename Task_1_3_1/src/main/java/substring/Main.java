package substring;

import java.io.IOException;
import java.util.List;

/**
 * Демонстрация поиска подстроки.
 */
public final class Main {

    private Main() {
    }

    /**
     * Точка входа.
     *
     * @param args аргументы командной строки
     * @throws IOException при ошибке чтения
     */
    public static void main(String[] args) throws IOException {
        List<Integer> result = FileSubstringFinder.find("input.txt", "бра");
        System.out.println(result);
    }
}