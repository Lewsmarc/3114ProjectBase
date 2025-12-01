
public class SparseMatrixTest extends student.TestCase {
    private SparseMatrix test;

    public void setUp() {
        test = new SparseMatrix();
    }


    public void testInsert() {
        test.insertScore(8, 0, 0);
        test.insertScore(9, 2, 0);
        test.insertScore(1, 0, 1);
        test.insertScore(9, 4, 11);        

        assertFalse(test.search(0, 0, 0));
        assertTrue(test.search(8, 0, 0));
        assertTrue(test.search(9, 2, 0));
        assertTrue(test.search(1, 0, 1));
        assertFalse(test.search(1, 1, 1));
    }


    public void testDelete() {
        test.insertScore(8, 0, 0);
        test.insertScore(9, 2, 0);
        test.insertScore(1, 0, 1);
        test.insertScore(9, 4, 11);

        test.search(9, 4, 11);
        
        assertFalse(test.search(0, 0, 0));
        assertTrue(test.search(8, 0, 0));
        assertTrue(test.search(9, 2, 0));
        assertTrue(test.search(1, 0, 1));
        assertTrue(test.search(9, 4, 11));
        assertFalse(test.search(1, 1, 1));

        test.delete(1, 0, 1);
        test.delete(8, 0, 0);
        test.delete(9, 4, 11);
        
        test.search(0, 0, 0);

        assertFalse(test.search(8, 0, 0));
        assertTrue(test.search(9, 2, 0));
        assertFalse(test.search(1, 0, 1));
        assertFalse(test.search(9, 4, 11));
    }
    
    public void testDeleteRowOrColumn() {
        test.insertScore(8, 0, 0);
        test.insertScore(9, 2, 0);
        test.insertScore(1, 0, 1);
        test.insertScore(9, 4, 11);
        test.insertScore(2, 0, 11);
        test.insertScore(4, 0, 3);
        test.insertScore(7, 0, 6);
        test.insertScore(4, 0, 4);
        test.insertScore(2, 1, 5);
        test.insertScore(5, 2, 5);
        test.insertScore(3, 8, 5);
        test.insertScore(1, 9, 5);
        test.insertScore(9, 11, 5);
        test.insertScore(9, 15, 5);
        
        test.removeRow(0);
        test.removeColumn(5);
        
        assertFalse(test.search(8, 0, 0));
        assertFalse(test.search(1, 0, 1));
        assertFalse(test.search(2, 0, 11));
        assertFalse(test.search(4, 0, 3));
        assertFalse(test.search(7, 0, 6));
        assertFalse(test.search(4, 0, 4));
        assertFalse(test.search(2, 1, 5));
        assertFalse(test.search(9, 15, 5));
        assertFalse(test.search(5, 2, 5));
        assertFalse(test.search(3, 8, 5));
        assertFalse(test.search(1, 9, 5));
        assertFalse(test.search(9, 11, 5));
        
    }
}
