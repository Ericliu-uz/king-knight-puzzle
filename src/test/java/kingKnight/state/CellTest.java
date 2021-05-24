package kingKnight.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void testOf() {
        assertEquals(Cell.EMPTY, Cell.of(0));
        assertEquals(Cell.KING, Cell.of(1));
        assertEquals(Cell.KNIGHT, Cell.of(2));
    }

        @Test
        void testOf_shouldThrowException () {
            assertThrows(IllegalArgumentException.class, () -> Cell.of(-1));
            assertThrows(IllegalArgumentException.class, () -> Cell.of(7));
        }

}