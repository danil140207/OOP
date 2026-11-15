package substring;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileSubstringFinderTest {

    private String resourcePath(String name) {
        URL url = getClass().getClassLoader().getResource(name);
        assertNotNull(url, "Ресурс не найден: " + name);
        return url.getPath();
    }

    @Test
    void findsInResourceFile() throws Exception {
        assertEquals(List.of(1, 8),
                FileSubstringFinder.find(resourcePath("input.txt"), "бра"));
    }

    @Test
    void missingFileThrows() {
        assertThrows(Exception.class,
                () -> FileSubstringFinder.find("src/test/resources/nonexistent.txt", "abc"));
    }
}