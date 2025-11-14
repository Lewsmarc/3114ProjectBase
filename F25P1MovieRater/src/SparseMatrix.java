public class SparseMatrix {
    private HeaderNode reviewers;
    private HeaderNode movies;

    private class MatrixNode {
        private int score;
        private int reviewer;
        private int movie;

        private MatrixNode next, prev, left, right;

        public MatrixNode(int rating, int name, int movieID) {
            score = rating;
            reviewer = name;
            movie = movieID;
        }


        public MatrixNode getNext() {
            return next;
        }


        public MatrixNode getRight() {
            return right;
        }


        public int getScore() {
            return score;
        }


        public int getReviewer() {
            return reviewer;
        }


        public int getMovie() {
            return movie;
        }


        public void setNext(MatrixNode insert) {
            next = insert;
        }


        public void setPrev(MatrixNode insert) {
            prev = insert;
        }


        public boolean equals(int rating, int review, int pic) {
            return score == rating && reviewer == review && movie == pic;
        }
    }


    private class HeaderNode {

        // Single linked list
        private int id;
        private MatrixNode first;
        private HeaderNode next;

        public HeaderNode(int name) {
            id = name;
        }


        public HeaderNode(int name, MatrixNode review) {
            id = name;
            first = review;
        }


        public HeaderNode getNext() {
            return next;
        }


        public MatrixNode getFirst() {
            return first;
        }


        public int getID() {
            return id;
        }
    }

    public SparseMatrix() {
        reviewers = new HeaderNode(-1);
        movies = new HeaderNode(-1);
    }


    public void insertScore(int score, int reviewer, int movie) {
        MatrixNode newNode = new MatrixNode(score, reviewer, movie);
        HeaderNode curr = reviewers;
        while (curr.getNext() != null) {
            if (reviewer < curr.getNext().getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert.getNext() != null) {
                    if (matrixInsert.getNext() == null) {
                        matrixInsert.setNext(newNode);
                    }
                    else if (movie < matrixInsert.getNext().getMovie()) {
                        newNode.setNext(matrixInsert.getNext());
                        newNode.setPrev(matrixInsert);
                        matrixInsert.setNext(newNode);
                        matrixInsert.getNext().setPrev(newNode);
                    }
                    matrixInsert = matrixInsert.getNext();
                }
            }
            curr = curr.getNext();
        }
    }


    public boolean search(int score, int reviewer, int movie) {
        HeaderNode curr = reviewers;
        while (curr.getNext() != null) {
            if (reviewer < curr.getNext().getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert.getNext() != null) {
                    if (matrixInsert.equals(score, reviewer, movie)) {
                        return true;
                    }
                    matrixInsert = matrixInsert.getNext();
                }
            }
            curr = curr.getNext();
        }
        return false;
    }


    public void delete(int score, int reviewer, int movie) {
        reviewers.delete(score, reviewer, movie);
        movies.delete(score, reviewer, movie);
    }
}
