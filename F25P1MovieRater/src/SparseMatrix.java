public class SparseMatrix {
    private HeaderNode reviewers;
    private HeaderNode movies;
    private class MatrixNode {
         private int score;
         private int reviewer;
         private int movie;
         
         private MatrixNode next, prev, left, right;
    }
    private class HeaderNode{
        
        //Single linked list
        private int id;
        private MatrixNode first;
        private HeaderNode next;
        
        public HeaderNode(int name) {
            id = name;
        }
    }
    
    public SparseMatrix() {
        reviewers = new HeaderNode(-1);
        movies = new HeaderNode(-1);
    }


    public void insert(int score, int reviewer, int movie) {
        reviewers.insert(score, reviewer, movie);
        movies.insert(score, reviewer, movie);
    }


    public boolean search(int score, int reviewer, int movie) {
        return reviewers.search(score, reviewer, movie);
    }


    public void delete(int score, int reviewer, int movie) {
        reviewers.delete(score, reviewer, movie);
        movies.delete(score, reviewer, movie);
    }
}
