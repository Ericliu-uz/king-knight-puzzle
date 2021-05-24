package kingKnight.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingKnightStateTest {

    private KingKnightState state;


    @BeforeEach
    private void setUp() {
        state = new KingKnightState(KingKnightState.INITIAL);
    }

    @Test
    void testOneArgConstructor_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new KingKnightState(null));
        assertThrows(IllegalArgumentException.class, () -> new KingKnightState(new int[][] {
                {1, 1},
                {1, 0}})
        );
        assertThrows(IllegalArgumentException.class, () -> new KingKnightState(new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}})
        );

    }

    @Test
    void testOneArgConstructor() {
        int[][] a = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        KingKnightState state_1 = new KingKnightState(a);
        assertArrayEquals(new Cell[][] {
                {Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
                {Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
                {Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
                {Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
                {Cell.EMPTY,Cell.KNIGHT,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
                {Cell.EMPTY,Cell.EMPTY,Cell.KING,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
                {Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
                {Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY,Cell.EMPTY},
        }, state_1.getMatrix());
    }

    @Test
    void testIsSolved() {
        assertFalse(state.isSolved());
        assertTrue(new KingKnightState(new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 2}}).isSolved());

    }

    @Test
    void testCanMoveToNext() {

        assertFalse(state.canMoveToNext(4,3 ));

        assertTrue(state.canMoveToNext(state.getKnightCol(),state.getKnightRow()));
    }

    @Test
    void testMoveToNext() {
        Cell[][] m = state.getMatrix();
        state.moveToNext(6,1, m[state.getKnightCol()][state.getKnightRow()]);
        assertEquals(Cell.KNIGHT,state.getMatrix()[state.getKnightCol()][state.getKnightRow()]);

    }

    @Test
    void testToString() {
        assertEquals("0 0 0 0 0 0 0 0 \n"
               + "0 0 0 0 0 0 0 0 \n"
               + "0 0 0 0 0 0 0 0 \n"
               + "0 0 0 0 0 0 0 0 \n"
               + "0 0 0 0 0 0 0 0 \n"
               + "0 0 1 2 0 0 0 0 \n"
               + "0 0 0 0 0 0 0 0 \n"
               + "0 0 0 0 0 0 0 0 \n", state.toString());
    }


}