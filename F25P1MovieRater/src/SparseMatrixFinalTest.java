import student.TestCase;

/**
 * Test class for SparseMatrix implementation.
 * Tests all functionality of the orthogonal linked list structure.
 *
 * @author AH
 * @version 1.0
 */
public class SparseMatrixFinalTest extends TestCase {

    private SparseMatrix matrix;  // Matrix instance for testing

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    public void setUp() {
        matrix = new SparseMatrix();
    }

    /**
     * Test basic instantiation and initial state.
     */
    public void testInstantiation() {
        assertNotNull(matrix);
        assertEquals(0, matrix.size());
    }

    /**
     * Test clear on empty matrix.
     */
    public void testClearEmpty() {
        matrix.clear();
        assertEquals(0, matrix.size());
    }

    // === Milestone Tests: Insert and Get ===

    /**
     * Test inserting a single value and retrieving it.
     */
    public void testInsertSingle() {
        assertTrue(matrix.insert(1, 1, 5));
        assertEquals(5, matrix.get(1, 1));
        assertEquals(1, matrix.size());
    }

    /**
     * Test inserting multiple values in the same row.
     */
    public void testInsertMultipleSameRow() {
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);
        matrix.insert(1, 3, 7);

        assertEquals(5, matrix.get(1, 1));
        assertEquals(6, matrix.get(1, 2));
        assertEquals(7, matrix.get(1, 3));
        assertEquals(3, matrix.size());
    }

    /**
     * Test inserting values out of order and verify sorted.
     */
    public void testInsertOutOfOrder() {
        matrix.insert(1, 3, 7);
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);

        // All should be retrievable
        assertEquals(5, matrix.get(1, 1));
        assertEquals(6, matrix.get(1, 2));
        assertEquals(7, matrix.get(1, 3));
        assertEquals(3, matrix.size());
    }

    /**
     * Test updating an existing value.
     */
    public void testInsertUpdate() {
        matrix.insert(1, 1, 5);
        matrix.insert(1, 1, 10);  // Update

        assertEquals(10, matrix.get(1, 1));
        assertEquals(1, matrix.size());  // Size shouldn't change
    }

    /**
     * Test getting a non-existent value returns 0.
     */
    public void testGetMissing() {
        assertEquals(0, matrix.get(999, 999));
    }

    /**
     * Test multiple rows and columns.
     */
    public void testMultipleRowsAndColumns() {
        matrix.insert(1, 1, 5);
        matrix.insert(2, 2, 6);
        matrix.insert(3, 3, 7);

        assertEquals(5, matrix.get(1, 1));
        assertEquals(6, matrix.get(2, 2));
        assertEquals(7, matrix.get(3, 3));
        assertEquals(0, matrix.get(1, 2));  // Empty cell
        assertEquals(3, matrix.size());
    }

    // === Milestone Tests: Traversal Methods ===

    /**
     * Test hasRow method.
     */
    public void testHasRow() {
        assertFalse(matrix.hasRow(1));

        matrix.insert(1, 1, 5);
        assertTrue(matrix.hasRow(1));
        assertFalse(matrix.hasRow(2));
    }

    /**
     * Test hasColumn method.
     */
    public void testHasColumn() {
        assertFalse(matrix.hasColumn(1));

        matrix.insert(1, 1, 5);
        assertTrue(matrix.hasColumn(1));
        assertFalse(matrix.hasColumn(2));
    }

    /**
     * Test getRow returns sorted output.
     */
    public void testGetRowSorted() {
        matrix.insert(1, 3, 7);
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);

        int[][] row = matrix.getRow(1);
        assertEquals(3, row.length);

        // Verify sorted by column
        assertEquals(1, row[0][0]);  // Column
        assertEquals(5, row[0][1]);  // Value
        assertEquals(2, row[1][0]);
        assertEquals(6, row[1][1]);
        assertEquals(3, row[2][0]);
        assertEquals(7, row[2][1]);
    }

    /**
     * Test getRow on empty row.
     */
    public void testGetRowEmpty() {
        int[][] row = matrix.getRow(999);
        assertEquals(0, row.length);
    }

    /**
     * Test getColumn returns sorted output.
     */
    public void testGetColumnSorted() {
        matrix.insert(3, 1, 7);
        matrix.insert(1, 1, 5);
        matrix.insert(2, 1, 6);

        int[][] col = matrix.getColumn(1);
        assertEquals(3, col.length);

        // Verify sorted by row
        assertEquals(1, col[0][0]);  // Row
        assertEquals(5, col[0][1]);  // Value
        assertEquals(2, col[1][0]);
        assertEquals(6, col[1][1]);
        assertEquals(3, col[2][0]);
        assertEquals(7, col[2][1]);
    }

    /**
     * Test getColumn on empty column.
     */
    public void testGetColumnEmpty() {
        int[][] col = matrix.getColumn(999);
        assertEquals(0, col.length);
    }

    /**
     * Test getAllRowIDs returns sorted IDs.
     */
    public void testGetAllRowIDs() {
        matrix.insert(3, 1, 7);
        matrix.insert(1, 1, 5);
        matrix.insert(2, 1, 6);

        int[] rows = matrix.getAllRowIDs();
        assertEquals(3, rows.length);
        assertEquals(1, rows[0]);
        assertEquals(2, rows[1]);
        assertEquals(3, rows[2]);
    }

    /**
     * Test getAllColumnIDs returns sorted IDs.
     */
    public void testGetAllColumnIDs() {
        matrix.insert(1, 3, 7);
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);

        int[] cols = matrix.getAllColumnIDs();
        assertEquals(3, cols.length);
        assertEquals(1, cols[0]);
        assertEquals(2, cols[1]);
        assertEquals(3, cols[2]);
    }

    /**
     * Test traversal on empty matrix.
     */
    public void testTraversalEmpty() {
        int[][] row = matrix.getRow(1);
        assertEquals(0, row.length);

        int[][] col = matrix.getColumn(1);
        assertEquals(0, col.length);

        int[] rows = matrix.getAllRowIDs();
        assertEquals(0, rows.length);

        int[] cols = matrix.getAllColumnIDs();
        assertEquals(0, cols.length);
    }

    /**
     * Test comprehensive example.
     */
    public void testComprehensiveExample() {
        // Insert data from roadmap visualization
        matrix.insert(2, 3, 7);
        matrix.insert(2, 5, 5);
        matrix.insert(3, 5, 8);
        matrix.insert(7, 3, 10);
        matrix.insert(7, 7, 1);

        // Verify size
        assertEquals(5, matrix.size());

        // Verify row 2
        int[][] row2 = matrix.getRow(2);
        assertEquals(2, row2.length);
        assertEquals(3, row2[0][0]);
        assertEquals(7, row2[0][1]);
        assertEquals(5, row2[1][0]);
        assertEquals(5, row2[1][1]);

        // Verify column 3
        int[][] col3 = matrix.getColumn(3);
        assertEquals(2, col3.length);
        assertEquals(2, col3[0][0]);
        assertEquals(7, col3[0][1]);
        assertEquals(7, col3[1][0]);
        assertEquals(10, col3[1][1]);

        // Verify all row IDs
        int[] rows = matrix.getAllRowIDs();
        assertEquals(3, rows.length);
        assertEquals(2, rows[0]);
        assertEquals(3, rows[1]);
        assertEquals(7, rows[2]);

        // Verify all column IDs
        int[] cols = matrix.getAllColumnIDs();
        assertEquals(3, cols.length);
        assertEquals(3, cols[0]);
        assertEquals(5, cols[1]);
        assertEquals(7, cols[2]);
    }

    // === Phase 2 Tests: Deletion Methods ===

    /**
     * Test removing a single cell (middle position).
     */
    public void testRemoveSingleCell() {
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);
        matrix.insert(1, 3, 7);

        // Remove middle cell
        assertTrue(matrix.remove(1, 2));
        assertEquals(2, matrix.size());

        // Verify cell is gone
        assertEquals(0, matrix.get(1, 2));

        // Verify other cells remain
        assertEquals(5, matrix.get(1, 1));
        assertEquals(7, matrix.get(1, 3));
    }

    /**
     * Test removing first cell in row.
     */
    public void testRemoveFirstInRow() {
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);
        matrix.insert(1, 3, 7);

        assertTrue(matrix.remove(1, 1));
        assertEquals(2, matrix.size());

        // Verify removal
        assertEquals(0, matrix.get(1, 1));
        assertEquals(6, matrix.get(1, 2));
        assertEquals(7, matrix.get(1, 3));
    }

    /**
     * Test removing last cell in row.
     */
    public void testRemoveLastInRow() {
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);
        matrix.insert(1, 3, 7);

        assertTrue(matrix.remove(1, 3));
        assertEquals(2, matrix.size());

        assertEquals(5, matrix.get(1, 1));
        assertEquals(6, matrix.get(1, 2));
        assertEquals(0, matrix.get(1, 3));
    }

    /**
     * Test removing the only cell (should clean up headers).
     */
    public void testRemoveOnlyCell() {
        matrix.insert(5, 10, 42);
        assertEquals(1, matrix.size());

        assertTrue(matrix.remove(5, 10));
        assertEquals(0, matrix.size());

        // Verify headers are cleaned up
        assertFalse(matrix.hasRow(5));
        assertFalse(matrix.hasColumn(10));
    }

    /**
     * Test removing from empty matrix.
     */
    public void testRemoveFromEmpty() {
        assertFalse(matrix.remove(1, 1));
        assertEquals(0, matrix.size());
    }

    /**
     * Test removing non-existent cell.
     */
    public void testRemoveNonExistent() {
        matrix.insert(1, 1, 5);
        assertFalse(matrix.remove(999, 999));
        assertEquals(1, matrix.size());
    }

    /**
     * Test removing cell with multiple rows and columns.
     */
    public void testRemoveFromMultipleRowsAndColumns() {
        // Create cross pattern
        matrix.insert(2, 3, 7);
        matrix.insert(2, 5, 5);
        matrix.insert(7, 3, 10);

        // Remove center-ish cell
        assertTrue(matrix.remove(2, 3));
        assertEquals(2, matrix.size());

        // Verify removal
        assertEquals(0, matrix.get(2, 3));

        // Verify others remain
        assertEquals(5, matrix.get(2, 5));
        assertEquals(10, matrix.get(7, 3));

        // Verify structure integrity
        int[][] row2 = matrix.getRow(2);
        assertEquals(1, row2.length);
        assertEquals(5, row2[0][0]);  // Only column 5 left

        int[][] col3 = matrix.getColumn(3);
        assertEquals(1, col3.length);
        assertEquals(7, col3[0][0]);  // Only row 7 left
    }

    /**
     * Test removeRow basic functionality.
     */
    public void testRemoveRowBasic() {
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);
        matrix.insert(1, 3, 7);
        matrix.insert(2, 1, 8);

        assertTrue(matrix.removeRow(1));
        assertEquals(1, matrix.size());

        // Verify row 1 is gone
        assertFalse(matrix.hasRow(1));
        assertEquals(0, matrix.get(1, 1));
        assertEquals(0, matrix.get(1, 2));
        assertEquals(0, matrix.get(1, 3));

        // Verify row 2 remains
        assertTrue(matrix.hasRow(2));
        assertEquals(8, matrix.get(2, 1));
    }

    /**
     * Test removeRow cleans up column headers.
     */
    public void testRemoveRowCleansUpColumns() {
        matrix.insert(5, 10, 42);
        matrix.insert(5, 20, 84);

        assertTrue(matrix.removeRow(5));
        assertEquals(0, matrix.size());

        // All headers should be cleaned up
        assertFalse(matrix.hasRow(5));
        assertFalse(matrix.hasColumn(10));
        assertFalse(matrix.hasColumn(20));

        int[] cols = matrix.getAllColumnIDs();
        assertEquals(0, cols.length);
    }

    /**
     * Test removeRow with shared columns.
     */
    public void testRemoveRowSharedColumns() {
        // Row 1 and row 2 share columns 1 and 2
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);
        matrix.insert(2, 1, 7);
        matrix.insert(2, 2, 8);

        assertTrue(matrix.removeRow(1));
        assertEquals(2, matrix.size());

        // Columns should still exist (because row 2 uses them)
        assertTrue(matrix.hasColumn(1));
        assertTrue(matrix.hasColumn(2));

        // Row 2 data intact
        assertEquals(7, matrix.get(2, 1));
        assertEquals(8, matrix.get(2, 2));
    }

    /**
     * Test removeRow on non-existent row.
     */
    public void testRemoveRowNonExistent() {
        matrix.insert(1, 1, 5);
        assertFalse(matrix.removeRow(999));
        assertEquals(1, matrix.size());
    }

    /**
     * Test removeRow on empty matrix.
     */
    public void testRemoveRowEmpty() {
        assertFalse(matrix.removeRow(1));
        assertEquals(0, matrix.size());
    }

    /**
     * Test removeColumn basic functionality.
     */
    public void testRemoveColumnBasic() {
        matrix.insert(1, 1, 5);
        matrix.insert(2, 1, 6);
        matrix.insert(3, 1, 7);
        matrix.insert(1, 2, 8);

        assertTrue(matrix.removeColumn(1));
        assertEquals(1, matrix.size());

        // Verify column 1 is gone
        assertFalse(matrix.hasColumn(1));
        assertEquals(0, matrix.get(1, 1));
        assertEquals(0, matrix.get(2, 1));
        assertEquals(0, matrix.get(3, 1));

        // Verify column 2 remains
        assertTrue(matrix.hasColumn(2));
        assertEquals(8, matrix.get(1, 2));
    }

    /**
     * Test removeColumn cleans up row headers.
     */
    public void testRemoveColumnCleansUpRows() {
        matrix.insert(10, 5, 42);
        matrix.insert(20, 5, 84);

        assertTrue(matrix.removeColumn(5));
        assertEquals(0, matrix.size());

        // All headers should be cleaned up
        assertFalse(matrix.hasColumn(5));
        assertFalse(matrix.hasRow(10));
        assertFalse(matrix.hasRow(20));

        int[] rows = matrix.getAllRowIDs();
        assertEquals(0, rows.length);
    }

    /**
     * Test removeColumn with shared rows.
     */
    public void testRemoveColumnSharedRows() {
        // Column 1 and column 2 share rows 1 and 2
        matrix.insert(1, 1, 5);
        matrix.insert(1, 2, 6);
        matrix.insert(2, 1, 7);
        matrix.insert(2, 2, 8);

        assertTrue(matrix.removeColumn(1));
        assertEquals(2, matrix.size());

        // Rows should still exist (because column 2 uses them)
        assertTrue(matrix.hasRow(1));
        assertTrue(matrix.hasRow(2));

        // Column 2 data intact
        assertEquals(6, matrix.get(1, 2));
        assertEquals(8, matrix.get(2, 2));
    }

    /**
     * Test removeColumn on non-existent column.
     */
    public void testRemoveColumnNonExistent() {
        matrix.insert(1, 1, 5);
        assertFalse(matrix.removeColumn(999));
        assertEquals(1, matrix.size());
    }

    /**
     * Test removeColumn on empty matrix.
     */
    public void testRemoveColumnEmpty() {
        assertFalse(matrix.removeColumn(1));
        assertEquals(0, matrix.size());
    }

    /**
     * Test complex deletion scenario with roadmap data.
     */
    public void testComplexDeletions() {
        // Build roadmap example
        matrix.insert(2, 3, 7);
        matrix.insert(2, 5, 5);
        matrix.insert(3, 5, 8);
        matrix.insert(7, 3, 10);
        matrix.insert(7, 7, 1);
        assertEquals(5, matrix.size());

        // Remove row 2
        assertTrue(matrix.removeRow(2));
        assertEquals(3, matrix.size());
        assertFalse(matrix.hasRow(2));

        // Column 3 should still exist (row 7 uses it)
        assertTrue(matrix.hasColumn(3));

        // Column 5 should still exist (row 3 uses it)
        assertTrue(matrix.hasColumn(5));

        // Remove column 3
        assertTrue(matrix.removeColumn(3));
        assertEquals(2, matrix.size());
        assertFalse(matrix.hasColumn(3));

        // Row 7 should still exist (has entry in column 7)
        assertTrue(matrix.hasRow(7));

        // Final state: row 3, col 5 and row 7, col 7
        assertEquals(8, matrix.get(3, 5));
        assertEquals(1, matrix.get(7, 7));

        int[] rows = matrix.getAllRowIDs();
        assertEquals(2, rows.length);
        assertEquals(3, rows[0]);
        assertEquals(7, rows[1]);

        int[] cols = matrix.getAllColumnIDs();
        assertEquals(2, cols.length);
        assertEquals(5, cols[0]);
        assertEquals(7, cols[1]);
    }

    /**
     * Test matrix integrity after clear following deletions.
     */
    public void testClearAfterDeletions() {
        matrix.insert(1, 1, 5);
        matrix.insert(2, 2, 6);
        matrix.remove(1, 1);
        matrix.removeRow(2);

        matrix.clear();
        assertEquals(0, matrix.size());

        // Should be able to reuse
        matrix.insert(10, 10, 100);
        assertEquals(1, matrix.size());
        assertEquals(100, matrix.get(10, 10));
    }
}
