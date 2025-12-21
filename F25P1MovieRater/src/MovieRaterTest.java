import java.io.IOException;
import student.TestCase;

/**
 * @author AH
 * @version 1.0
 */
public class MovieRaterTest extends TestCase {

    private MovieRaterDB it;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        it = new MovieRaterDB();
    }

    /**
     * Test clearing on initial
     * @throws IOException
     */
    public void testClearInit()
        throws IOException
    {
        assertTrue(it.clear());
    }

    /**
     * Test empty print movie or reviewer
     * @throws IOException
     */
    public void testRefMissing()
        throws IOException
    {
        assertNull(it.listMovie(2));
        assertNull(it.listReviewer(3));
        assertFalse(it.deleteScore(5, 1));
        assertFalse(it.deleteReviewer(2));
        assertFalse(it.deleteMovie(2));
    }


    /**
     * Test insert two items and print
     * @throws IOException
     */
    public void testRefinsertTwo()
        throws IOException
    {
        assertTrue(it.addReview(2, 3, 7));
        assertTrue(it.addReview(2, 5, 5));
        it.addReview(2, 5, 5);
        assertFuzzyEquals(it.printRatings(), "2: (3, 7) (5, 5)");
        assertFuzzyEquals(it.listMovie(3), "3: 7");
        assertFuzzyEquals(it.listReviewer(2), "2: 7 5");
    }

    /**
     * Test bad review values
     * @throws IOException
     */
    public void testRefBadRatings()
        throws IOException
    {
        assertFalse(it.addReview(2, 3, -1));
        assertFalse(it.addReview(2, 4, 0));
        assertFalse(it.addReview(2, 5, 20));
        assertFuzzyEquals(it.printRatings(), "");
    }


    /**
     * Test insert 5 items and print
     * @throws IOException
     */
    public void testRefinsertFive()
        throws IOException
    {
        assertTrue(it.addReview(7, 3, 10));
        assertTrue(it.addReview(2, 3, 7));
        assertTrue(it.addReview(3, 5, 8));
        assertTrue(it.addReview(5, 7, 9));
        assertTrue(it.addReview(7, 7, 1));
        assertFuzzyEquals(
            multiline(
                "2: (3, 7)",
                "3: (5, 8)",
                "5: (7, 9)",
                "7: (3, 10) (7, 1)"),
            it.printRatings());
    }

    // =========================================================================
    // SIMILARITY ALGORITHM TESTS
    // Tests for similarReviewer() and similarMovie() methods
    // Algorithm: similarity = sum(|score_X - score_Y|) / count(common items)
    // =========================================================================

    // -------------------------------------------------------------------------
    // Category 1: Empty/Missing Data → Return -1
    // -------------------------------------------------------------------------

    /**
     * Test similarReviewer on empty database - should return -1
     * @throws IOException
     */
    public void testSimilarReviewerEmptyDatabase() throws IOException {
        assertEquals(-1, it.similarReviewer(1));
        assertEquals(-1, it.similarReviewer(0));
        assertEquals(-1, it.similarReviewer(999));
    }

    /**
     * Test similarMovie on empty database - should return -1
     * @throws IOException
     */
    public void testSimilarMovieEmptyDatabase() throws IOException {
        assertEquals(-1, it.similarMovie(1));
        assertEquals(-1, it.similarMovie(0));
        assertEquals(-1, it.similarMovie(999));
    }

    /**
     * Test similarReviewer when reviewer doesn't exist - should return -1
     * @throws IOException
     */
    public void testSimilarReviewerNotFound() throws IOException {
        it.addReview(1, 1, 5);
        it.addReview(2, 1, 5);
        assertEquals(-1, it.similarReviewer(99));
    }

    /**
     * Test similarMovie when movie doesn't exist - should return -1
     * @throws IOException
     */
    public void testSimilarMovieNotFound() throws IOException {
        it.addReview(1, 1, 5);
        it.addReview(1, 2, 5);
        assertEquals(-1, it.similarMovie(99));
    }

    // -------------------------------------------------------------------------
    // Category 2: Single Entity → Return -1
    // -------------------------------------------------------------------------

    /**
     * Test similarReviewer when only one reviewer exists - no one to compare
     * @throws IOException
     */
    public void testSimilarReviewerOnlyOne() throws IOException {
        it.addReview(1, 1, 5);
        it.addReview(1, 2, 8);
        it.addReview(1, 3, 3);
        assertEquals(-1, it.similarReviewer(1));
    }

    /**
     * Test similarMovie when only one movie exists - nothing to compare
     * @throws IOException
     */
    public void testSimilarMovieOnlyOne() throws IOException {
        it.addReview(1, 1, 5);
        it.addReview(2, 1, 8);
        it.addReview(3, 1, 3);
        assertEquals(-1, it.similarMovie(1));
    }

    /**
     * Test similarReviewer when reviewers have NO common movies
     * Multiple reviewers exist but rated completely different movies
     * @throws IOException
     */
    public void testSimilarReviewerNoCommonMovies() throws IOException {
        // Reviewer 1 rates movies 1, 2
        it.addReview(1, 1, 5);
        it.addReview(1, 2, 5);
        // Reviewer 2 rates movies 3, 4 (no overlap with reviewer 1)
        it.addReview(2, 3, 5);
        it.addReview(2, 4, 5);
        // Reviewer 3 rates movies 5, 6 (no overlap with reviewer 1)
        it.addReview(3, 5, 5);
        it.addReview(3, 6, 5);
        it.similarReviewer(1);

        assertEquals(-1, it.similarReviewer(1));
    }

    /**
     * Test similarMovie when movies have NO common reviewers
     * Multiple movies exist but rated by completely different reviewers
     * @throws IOException
     */
    public void testSimilarMovieNoCommonReviewers() throws IOException {
        // Movie 1 rated by reviewers 1, 2
        it.addReview(1, 1, 5);
        it.addReview(2, 1, 5);
        // Movie 2 rated by reviewers 3, 4 (no overlap with movie 1)
        it.addReview(3, 2, 5);
        it.addReview(4, 2, 5);
        // Movie 3 rated by reviewers 5, 6 (no overlap with movie 1)
        it.addReview(5, 3, 5);
        it.addReview(6, 3, 5);

        assertEquals(-1, it.similarMovie(1));
    }

    // -------------------------------------------------------------------------
    // Category 3: Basic Similarity - Clear Winner
    // -------------------------------------------------------------------------

    /**
     * Test similarReviewer with identical ratings - similarity = 0
     * Two reviewers rate same movies with exact same scores
     * @throws IOException
     */
    public void testSimilarReviewerIdenticalRatings() throws IOException {
        // Reviewer 1: movies 1,2,3 with scores 5,8,3
        it.addReview(1, 1, 5);
        it.addReview(1, 2, 8);
        it.addReview(1, 3, 3);

        // Reviewer 2: same movies, IDENTICAL scores
        it.addReview(2, 1, 5);
        it.addReview(2, 2, 8);
        it.addReview(2, 3, 3);

        // similarity = (|5-5| + |8-8| + |3-3|) / 3 = 0/3 = 0
        assertEquals(2, it.similarReviewer(1));
        assertEquals(1, it.similarReviewer(2));
    }

    /**
     * Test similarMovie with identical ratings - similarity = 0
     * Two movies rated by same reviewers with exact same scores
     * @throws IOException
     */
    public void testSimilarMovieIdenticalRatings() throws IOException {
        // Movie 1: reviewers 1,2,3 with scores 5,8,3
        it.addReview(1, 1, 5);
        it.addReview(2, 1, 8);
        it.addReview(3, 1, 3);

        // Movie 2: same reviewers, IDENTICAL scores
        it.addReview(1, 2, 5);
        it.addReview(2, 2, 8);
        it.addReview(3, 2, 3);

        // similarity = 0 (identical)
        assertEquals(2, it.similarMovie(1));
        assertEquals(1, it.similarMovie(2));
    }

    /**
     * Test similarReviewer with clear winner among three reviewers
     * @throws IOException
     */
    public void testSimilarReviewerClearWinner() throws IOException {
        // Reviewer 1: movie1=5, movie2=5
        it.addReview(1, 1, 5);
        it.addReview(1, 2, 5);

        // Reviewer 2: movie1=5, movie2=6 → similarity = (0+1)/2 = 0.5 (CLOSER)
        it.addReview(2, 1, 5);
        it.addReview(2, 2, 6);

        // Reviewer 3: movie1=5, movie2=10 → similarity = (0+5)/2 = 2.5 (FARTHER)
        it.addReview(3, 1, 5);
        it.addReview(3, 2, 10);

        assertEquals(2, it.similarReviewer(1));
    }

    /**
     * Test similarMovie with clear winner among three movies
     * @throws IOException
     */
    public void testSimilarMovieClearWinner() throws IOException {
        // Movie 1: reviewer1=5, reviewer2=5
        it.addReview(1, 1, 5);
        it.addReview(2, 1, 5);

        // Movie 2: reviewer1=5, reviewer2=6 → similarity = 0.5 (CLOSER)
        it.addReview(1, 2, 5);
        it.addReview(2, 2, 6);

        // Movie 3: reviewer1=5, reviewer2=10 → similarity = 2.5 (FARTHER)
        it.addReview(1, 3, 5);
        it.addReview(2, 3, 10);

        assertEquals(2, it.similarMovie(1));
    }

    // -------------------------------------------------------------------------
    // Category 4: Algorithm Calculation Verification
    // Hand-calculated expected results to verify formula
    // -------------------------------------------------------------------------

    /**
     * Test similarReviewer calculation with known expected result
     * Verifies formula: similarity = sum(|score_X - score_Y|) / count(common)
     * @throws IOException
     */
    public void testSimilarReviewerCalculation() throws IOException {
        // Reviewer 1: m1=2, m2=4, m3=6, m4=8
        it.addReview(1, 1, 2);
        it.addReview(1, 2, 4);
        it.addReview(1, 3, 6);
        it.addReview(1, 4, 8);

        // Reviewer 2: m1=3, m2=5, m3=7, m4=9
        // similarity = (|2-3| + |4-5| + |6-7| + |8-9|) / 4 = 4/4 = 1.0
        it.addReview(2, 1, 3);
        it.addReview(2, 2, 5);
        it.addReview(2, 3, 7);
        it.addReview(2, 4, 9);

        // Reviewer 3: m1=1, m2=1, m3=1, m4=1
        // similarity = (|2-1| + |4-1| + |6-1| + |8-1|) / 4 = (1+3+5+7)/4 = 4.0
        it.addReview(3, 1, 1);
        it.addReview(3, 2, 1);
        it.addReview(3, 3, 1);
        it.addReview(3, 4, 1);

        // Reviewer 2 has lower similarity (1.0 < 4.0)
        assertEquals(2, it.similarReviewer(1));
    }

    /**
     * Test similarMovie calculation with known expected result
     * @throws IOException
     */
    public void testSimilarMovieCalculation() throws IOException {
        // Movie 1: r1=2, r2=4, r3=6, r4=8
        it.addReview(1, 1, 2);
        it.addReview(2, 1, 4);
        it.addReview(3, 1, 6);
        it.addReview(4, 1, 8);

        // Movie 2: r1=3, r2=5, r3=7, r4=9 → similarity = 1.0
        it.addReview(1, 2, 3);
        it.addReview(2, 2, 5);
        it.addReview(3, 2, 7);
        it.addReview(4, 2, 9);

        // Movie 3: r1=1, r2=1, r3=1, r4=1 → similarity = 4.0
        it.addReview(1, 3, 1);
        it.addReview(2, 3, 1);
        it.addReview(3, 3, 1);
        it.addReview(4, 3, 1);

        assertEquals(2, it.similarMovie(1));
    }

    /**
     * Test similarReviewer with partial movie overlap
     * Only some movies in common between reviewers
     * @throws IOException
     */
    public void testSimilarReviewerPartialOverlap() throws IOException {
        // Reviewer 1: m1=5, m2=5, m3=5
        it.addReview(1, 1, 5);
        it.addReview(1, 2, 5);
        it.addReview(1, 3, 5);

        // Reviewer 2: m1=5, m2=5 (only 2 common movies, m3 missing)
        // similarity = (0 + 0) / 2 = 0
        it.addReview(2, 1, 5);
        it.addReview(2, 2, 5);

        // Reviewer 3: m2=6, m3=6 (only 2 common movies, m1 missing)
        // similarity = (|5-6| + |5-6|) / 2 = 2/2 = 1.0
        it.addReview(3, 2, 6);
        it.addReview(3, 3, 6);

        // Reviewer 2 wins (similarity 0 < 1.0)
        assertEquals(2, it.similarReviewer(1));
    }

    /**
     * Test similarMovie with partial reviewer overlap
     * Only some reviewers rated both movies
     * @throws IOException
     */
    public void testSimilarMoviePartialOverlap() throws IOException {
        // Movie 1: r1=5, r2=5, r3=5
        it.addReview(1, 1, 5);
        it.addReview(2, 1, 5);
        it.addReview(3, 1, 5);

        // Movie 2: r1=5, r2=5 (only 2 common reviewers)
        // similarity = 0
        it.addReview(1, 2, 5);
        it.addReview(2, 2, 5);

        // Movie 3: r2=6, r3=6 (only 2 common reviewers)
        // similarity = 1.0
        it.addReview(2, 3, 6);
        it.addReview(3, 3, 6);

        assertEquals(2, it.similarMovie(1));
    }

    // -------------------------------------------------------------------------
    // Category 5: Tie-Breaking Scenarios
    // -------------------------------------------------------------------------

    /**
     * Test similarReviewer when multiple reviewers have equal similarity
     * Should return a consistent result
     * @throws IOException
     */
    public void testSimilarReviewerTieBreaking() throws IOException {
        // Reviewer 1: m1=5
        it.addReview(1, 1, 5);

        // Reviewer 2: m1=6 → similarity = 1
        it.addReview(2, 1, 6);

        // Reviewer 3: m1=4 → similarity = 1 (TIE!)
        it.addReview(3, 1, 4);

        // Both 2 and 3 have same similarity - test accepts either
        int result = it.similarReviewer(1);
        assertTrue(result == 2 || result == 3);

        // Verify consistency - same call returns same result
        assertEquals(result, it.similarReviewer(1));
    }

    /**
     * Test similarMovie when multiple movies have equal similarity
     * @throws IOException
     */
    public void testSimilarMovieTieBreaking() throws IOException {
        // Movie 1: r1=5
        it.addReview(1, 1, 5);

        // Movie 2: r1=6 → similarity = 1
        it.addReview(1, 2, 6);

        // Movie 3: r1=4 → similarity = 1 (TIE!)
        it.addReview(1, 3, 4);

        int result = it.similarMovie(1);
        assertTrue(result == 2 || result == 3);
        assertEquals(result, it.similarMovie(1));
    }

    // -------------------------------------------------------------------------
    // Category 6: Boundary Conditions
    // -------------------------------------------------------------------------

    /**
     * Test similarReviewer when all scores are minimum (1)
     * All similarities should be 0
     * @throws IOException
     */
    public void testSimilarReviewerAllMinScores() throws IOException {
        // All reviewers rate all movies as 1 (minimum)
        it.addReview(1, 1, 1);
        it.addReview(1, 2, 1);
        it.addReview(2, 1, 1);
        it.addReview(2, 2, 1);
        it.addReview(3, 1, 1);
        it.addReview(3, 2, 1);

        // All similarities are 0 - should return something (not -1)
        int result = it.similarReviewer(1);
        assertTrue(result == 2 || result == 3);
    }

    /**
     * Test similarReviewer when all scores are maximum (10)
     * @throws IOException
     */
    public void testSimilarReviewerAllMaxScores() throws IOException {
        // All reviewers rate all movies as 10 (maximum)
        it.addReview(1, 1, 10);
        it.addReview(1, 2, 10);
        it.addReview(2, 1, 10);
        it.addReview(2, 2, 10);
        it.addReview(3, 1, 10);
        it.addReview(3, 2, 10);

        int result = it.similarReviewer(1);
        assertTrue(result == 2 || result == 3);
    }

    /**
     * Test similarReviewer with maximum score difference (1 vs 10)
     * @throws IOException
     */
    public void testSimilarReviewerMaxDifference() throws IOException {
        // Reviewer 1 rates everything 1
        it.addReview(1, 1, 1);
        it.addReview(1, 2, 1);

        // Reviewer 2 rates everything 10 (max difference = 9)
        it.addReview(2, 1, 10);
        it.addReview(2, 2, 10);

        // Reviewer 3 rates everything 5 (difference = 4, closer!)
        it.addReview(3, 1, 5);
        it.addReview(3, 2, 5);

        assertEquals(3, it.similarReviewer(1));
    }

    /**
     * Test similarReviewer with zero similarity (exact match)
     * @throws IOException
     */
    public void testSimilarReviewerZeroSimilarity() throws IOException {
        // Reviewer 1 and 2 have IDENTICAL ratings
        it.addReview(1, 1, 7);
        it.addReview(1, 2, 3);
        it.addReview(1, 3, 9);
        it.addReview(2, 1, 7);
        it.addReview(2, 2, 3);
        it.addReview(2, 3, 9);

        // Reviewer 3 has different ratings
        it.addReview(3, 1, 1);
        it.addReview(3, 2, 1);
        it.addReview(3, 3, 1);

        // Reviewer 2 has similarity 0, should win
        assertEquals(2, it.similarReviewer(1));
    }

    // -------------------------------------------------------------------------
    // Category 7: Scale/Stress Tests
    // -------------------------------------------------------------------------

    /**
     * Test similarReviewer with many reviewers (10+)
     * Verify correct selection among many candidates
     * @throws IOException
     */
    public void testSimilarReviewerManyReviewers() throws IOException {
        // Target: Reviewer 0 rates m1=5, m2=5
        it.addReview(0, 1, 5);
        it.addReview(0, 2, 5);

        // Create 10 other reviewers with varying similarities
        for (int i = 1; i <= 10; i++) {
            it.addReview(i, 1, 5);
            it.addReview(i, 2, (5 + i) % 10);
            // Similarity for reviewer i = (0 + i) / 2 = i/2
        }
        it.addReview(5, 2, 10);
        it.addReview(10, 2, 6);
        
        it.similarReviewer(0);
        // Reviewer 1 has lowest similarity (0.5)
        assertEquals(1, it.similarReviewer(0));
    }

    /**
     * Test similarMovie with many movies (10+)
     * @throws IOException
     */
    public void testSimilarMovieManyMovies() throws IOException {
        // Target: Movie 0 rated by r1=5, r2=5
        it.addReview(1, 0, 5);
        it.addReview(2, 0, 5);

        // Create 10 other movies with varying similarities
        for (int i = 1; i <= 10; i++) {
            it.addReview(1, i, 5);
            it.addReview(2, i, (5 + i) % 10);
        }
        it.addReview(2, 5, 10);
        it.addReview(2, 10, 6);

        // Movie 1 has lowest similarity
        assertEquals(1, it.similarMovie(0));
    }

    /**
     * Test similarity on large dense matrix (10x10)
     * @throws IOException
     */
    public void testSimilarityLargeMatrix() throws IOException {
        // Target reviewer 0 rates all 10 movies as 5
        for (int m = 0; m < 10; m++) {
            it.addReview(0, m, 5);
        }

        // Reviewer 1 rates all 10 movies as 5 (identical = similarity 0)
        for (int m = 0; m < 10; m++) {
            it.addReview(1, m, 5);
        }

        // Reviewers 2-9 rate with increasing differences
        for (int r = 2; r < 10; r++) {
            for (int m = 0; m < 10; m++) {
                int score = Math.min(5 + r, 10);
                it.addReview(r, m, score);
            }
        }

        // Reviewer 1 should be most similar (exact match)
        assertEquals(1, it.similarReviewer(0));
    }

    // -------------------------------------------------------------------------
    // Category 8: Complex Scenarios
    // -------------------------------------------------------------------------

    /**
     * Test similarity after deletions
     * Verify algorithm works correctly after removing entities
     * @throws IOException
     */
    public void testSimilarityAfterDeletions() throws IOException {
        // Setup: 3 reviewers
        it.addReview(1, 1, 5);
        it.addReview(1, 2, 5);
        it.addReview(2, 1, 5);
        it.addReview(2, 2, 5);
        it.addReview(3, 1, 10);
        it.addReview(3, 2, 10);

        // Initially, reviewer 2 is most similar to 1 (similarity 0)
        assertEquals(2, it.similarReviewer(1));

        // Delete reviewer 2
        it.deleteReviewer(2);

        // Now reviewer 3 is only option (even though less similar)
        assertEquals(3, it.similarReviewer(1));
    }

    /**
     * Test multiple similarity calls return consistent results
     * @throws IOException
     */
    public void testSimilarityMultipleCalls() throws IOException {
        it.addReview(1, 1, 5);
        it.addReview(2, 1, 6);
        it.addReview(3, 1, 10);

        // Multiple calls should return consistent results
        assertEquals(2, it.similarReviewer(1));
        assertEquals(2, it.similarReviewer(1));
        assertEquals(2, it.similarReviewer(1));

        // Different reviewer queries
        assertEquals(1, it.similarReviewer(2));
        assertEquals(2, it.similarReviewer(3));
    }

    /**
     * Test similarity symmetry - if A is most similar to B,
     * then B should be most similar to A (when only 2 exist)
     * @throws IOException
     */
    public void testSimilarReviewerSymmetry() throws IOException {
        // Only 2 reviewers - similarity should be symmetric
        it.addReview(1, 1, 5);
        it.addReview(1, 2, 8);
        it.addReview(2, 1, 7);
        it.addReview(2, 2, 6);

        // If 2 is most similar to 1, then 1 should be most similar to 2
        assertEquals(2, it.similarReviewer(1));
        assertEquals(1, it.similarReviewer(2));
    }

    /**
     * Test similarMovie symmetry
     * @throws IOException
     */
    public void testSimilarMovieSymmetry() throws IOException {
        // Only 2 movies
        it.addReview(1, 1, 5);
        it.addReview(2, 1, 8);
        it.addReview(1, 2, 7);
        it.addReview(2, 2, 6);

        // Symmetry check
        assertEquals(2, it.similarMovie(1));
        assertEquals(1, it.similarMovie(2));
    }
}
