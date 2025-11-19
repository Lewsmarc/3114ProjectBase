public class SparseMatrix {
    private HeaderNode reviewers;
    private HeaderNode movies;

    private class MatrixNode {
        private int score;
        private int reviewer;
        private int movie;

        private MatrixNode next, right;

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


        public void setRight(MatrixNode nextNode) {
            right = nextNode;
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


        public void setNext(HeaderNode newID) {
            next = newID;
        }

// public void insertMatrixNode(MatrixNode insertNode) {
//
// }
    }

    public SparseMatrix() {
        reviewers = new HeaderNode(-1);
        movies = new HeaderNode(-1);
    }


    public void insertScore(int score, int reviewer, int movie) {
        MatrixNode newNode = new MatrixNode(score, reviewer, movie);
        insertByRow(newNode);
        insertByColumn(newNode);
    }


    public void insertByColumn(MatrixNode newNode) {
        HeaderNode curr = movies;

        while (curr.getNext() != null) {

            // Movie exists and we must insert into an already existing list
            if (newNode.getMovie() < curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert.getNext() != null) {
                    // End of list
                    if (matrixInsert.getNext() == null) {
                        matrixInsert.setNext(newNode);
                        return;
                    }
                    // Not end of list
                    else if (newNode.getReviewer() < matrixInsert.getNext()
                        .getReviewer()) {
                        newNode.setNext(matrixInsert.getNext());
                        matrixInsert.setNext(newNode);
                        return;
                    }
                    matrixInsert = matrixInsert.getNext();
                }
            }
            curr = curr.getNext();
        }

        // This a new movie
        HeaderNode newMovie = new HeaderNode(newNode.getMovie(), newNode);
        curr.setNext(newMovie);
    }


    public void insertByRow(MatrixNode newNode) {
        HeaderNode curr = reviewers;

        while (curr.getNext() != null) {
            if (newNode.getReviewer() < curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert.getRight() != null) {

                    // end of list
                    if (matrixInsert.getRight() == null) {
                        matrixInsert.setRight(newNode);
                        return;
                    }
                    else if (newNode.getMovie() < matrixInsert.getRight()
                        .getMovie()) {
                        newNode.setRight(matrixInsert.getRight());
                        matrixInsert.setRight(newNode);
                        return;
                    }
                    matrixInsert = matrixInsert.getRight();
                }
            }
            curr = curr.getNext();
        }

        HeaderNode newReviewer = new HeaderNode(newNode.getReviewer(), newNode);
        curr.setNext(newReviewer);
    }


    public boolean search(int score, int reviewer, int movie) {
        HeaderNode curr = reviewers;
        while (curr.getNext() != null) {
            if (reviewer < curr.getNext().getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert.getRight() != null) {
                    if (matrixInsert.equals(score, reviewer, movie)) {
                        return true;
                    }
                    matrixInsert = matrixInsert.getRight();
                }
            }
            curr = curr.getNext();
        }
        return false;
    }


    public void delete(int score, int reviewer, int movie) {
    }
}
