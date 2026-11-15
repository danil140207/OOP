package substring;

import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubstringFinderTest {

    @Test
    void findsSimple() throws Exception {
        assertEquals(List.of(1, 8),
                SubstringFinder.findAll(new StringReader("абракадабра"), "бра"));
    }

    @Test
    void findsAtStart() throws Exception {
        assertEquals(List.of(0),
                SubstringFinder.findAll(new StringReader("abc"), "abc"));
    }

    @Test
    void noMatches() throws Exception {
        assertEquals(List.of(),
                SubstringFinder.findAll(new StringReader("abc"), "xyz"));
    }

    @Test
    void emptyNeedleThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> SubstringFinder.findAll(new StringReader("abc"), ""));
    }

    @Test
    void emptyText() throws Exception {
        assertEquals(List.of(),
                SubstringFinder.findAll(new StringReader(""), "abc"));
    }

    @Test
    void overlapping() throws Exception {
        assertEquals(List.of(0, 1, 2),
                SubstringFinder.findAll(new StringReader("aaaa"), "aa"));
    }
}