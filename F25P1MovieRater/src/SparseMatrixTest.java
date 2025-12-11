
public class SparseMatrixTest extends student.TestCase {
    private SparseMatrix test;

    public void setUp() {
        test = new SparseMatrix();
    }


// public void testInsert() {
// test.insertScore(8, 0, 0);
// test.insertScore(9, 2, 0);
// test.insertScore(1, 0, 1);
// test.insertScore(9, 4, 11);
// test.insertScore(-1, 9, 8);
// test.insertScore(9, -2, 8);
// test.insertScore(1, 9, -4);
//
// assertTrue(test.search(0, 0));
// assertTrue(test.search(2, 0));
// assertTrue(test.search(0, 1));
// assertFalse(test.search(1, 1));
// assertEquals(test.getSize(), 4);
// }
//
//
// public void testDelete() {
// test.insertScore(8, 0, 0);
// test.insertScore(9, 2, 0);
// test.insertScore(1, 0, 1);
// test.insertScore(9, 4, 11);
//
// assertTrue(test.search(0, 0));
// assertTrue(test.search(2, 0));
// assertTrue(test.search(0, 1));
// assertTrue(test.search(4, 11));
// assertFalse(test.search(1, 1));
//
// test.delete(0, 1);
// test.delete(0, 0);
// test.delete(4, 11);
//
// assertFalse(test.search(0, 0));
// assertTrue(test.search(2, 0));
// assertFalse(test.search(0, 1));
// assertFalse(test.search(4, 11));
// assertEquals(test.getSize(), 1);
// }
//
//
// public void testDeleteRowOrColumn() {
// test.insertScore(8, 0, 0);
// test.insertScore(9, 2, 0);
// test.insertScore(1, 0, 1);
// test.insertScore(9, 4, 11);
// test.insertScore(2, 0, 11);
// test.insertScore(4, 0, 3);
// test.insertScore(7, 0, 6);
// test.insertScore(4, 0, 4);
// test.insertScore(2, 1, 5);
// test.insertScore(5, 2, 5);
// test.insertScore(3, 8, 5);
// test.insertScore(1, 9, 5);
// test.insertScore(9, 11, 5);
// test.insertScore(9, 15, 5);
//
// test.removeRow(0);
// test.removeColumn(5);
//
// assertFalse(test.search(0, 0));
// assertFalse(test.search(0, 1));
// assertFalse(test.search(0, 11));
// assertFalse(test.search(0, 3));
// assertFalse(test.search(0, 6));
// assertFalse(test.search(0, 4));
// assertFalse(test.search(1, 5));
// assertFalse(test.search(15, 5));
// assertFalse(test.search(2, 5));
// assertFalse(test.search(8, 5));
// assertFalse(test.search(9, 5));
// assertFalse(test.search(11, 5));
// }
//
//
// public void testPrint() {
// test.insertScore(8, 0, 0);
// test.insertScore(9, 2, 0);
// test.insertScore(1, 0, 1);
// test.insertScore(9, 4, 11);
// test.insertScore(2, 0, 11);
// test.insertScore(4, 0, 3);
// test.insertScore(7, 0, 6);
// test.insertScore(4, 0, 4);
// test.insertScore(2, 1, 5);
// test.insertScore(5, 2, 5);
// test.insertScore(3, 8, 5);
// test.insertScore(1, 9, 5);
// test.insertScore(9, 11, 5);
// test.insertScore(9, 15, 5);
//
// test.print();
// }
//
//
// public void testPrint2() {
// test.insertScore(7, 2, 3);
// test.insertScore(5, 2, 5);
//
// test.toString();
//
// assertFuzzyEquals(test.toString(), "2: (3, 7) (5, 5)");
//
// test.getCol(3);
//
// assertFuzzyEquals(test.getCol(3), "3: 7");
// }
    public void testGetAllRowIDs() {
        test.insert(7, 1, 3);
        test.insert(5, 6, 1);
        test.insert(6, 14, 2);

        int[] rows = test.getAllRowIDs();
        assertEquals(3, rows.length);
        assertEquals(1, rows[0]);
        assertEquals(6, rows[1]);
        assertEquals(14, rows[2]);
    }
}
