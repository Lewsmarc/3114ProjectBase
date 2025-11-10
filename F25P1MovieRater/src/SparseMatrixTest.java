
public class SparseMatrixTest extends student.TestCase{
    private SparseMatrix test;
    
    public void setUp() {
        test = new SparseMatrix();
    }
    
    public void testInsert() {
        test.insert(8,0,0);
        test.insert(9,2,0);
        test.insert(1,0,1);
        test.insert(9,4,11);
        
        assertFalse(test.search(0, 0, 0));
        assertTrue(test.search(8, 0, 0));
        assertTrue(test.search(9, 2, 0));
        assertTrue(test.search(1, 0, 1));
        assertFalse(test.search(1, 1, 1));
    }
}
