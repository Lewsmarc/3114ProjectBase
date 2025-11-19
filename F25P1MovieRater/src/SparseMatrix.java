public class SparseMatrix {
    private HeaderNode reviewers;
    private HeaderNode movies;

    private class MatrixNode {
        private int score;
        private int reviewer;
        private int movie;

        private MatrixNode down, right;

        public MatrixNode(int rating, int name, int movieID) {
            score = rating;
            reviewer = name;
            movie = movieID;
        }


        public MatrixNode getDown() {
            return down;
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


        public void setDown(MatrixNode insert) {
            down = insert;
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
    }

    public SparseMatrix() {
        reviewers = new HeaderNode(-1);
        movies = new HeaderNode(-1);
    }


    public void insertScore(int score, int reviewer, int movie) {
        if (score <= -1 || reviewer <= -1 || movie <= -1) {
            return;
        }

        MatrixNode newNode = new MatrixNode(score, reviewer, movie);
        insertByRow(newNode);
        insertByColumn(newNode);
    }


    public void insertByColumn(MatrixNode newNode) {
        HeaderNode curr = movies;

        while (curr != null) {
            // Movie exists and we must insert into an already existing list
            if (newNode.getMovie() == curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert != null) {
                    // End of list
                    if (matrixInsert.getDown() == null) {
                        matrixInsert.setDown(newNode);
                        return;
                    }
                    // Not end of list
                    else if (newNode.getReviewer() < matrixInsert.getDown()
                        .getReviewer()) {
                        newNode.setDown(matrixInsert.getDown());
                        matrixInsert.setDown(newNode);
                        return;
                    }
                    matrixInsert = matrixInsert.getDown();
                }
            }
            else if (curr.getNext() == null) {
                HeaderNode newMovie = new HeaderNode(newNode.getMovie(),
                    newNode);
                curr.setNext(newMovie);
                return;
            }
            else if (newNode.getMovie() < curr.getNext().getID()) {
                HeaderNode newMovie = new HeaderNode(newNode.getMovie(),
                    newNode);
                newMovie.setNext(curr.getNext());
                curr.setNext(newMovie);
                return;
            }
            curr = curr.getNext();
        }

        // This a new movie
        HeaderNode newMovie = new HeaderNode(newNode.getMovie(), newNode);
        movies.setNext(newMovie);
    }


    public void insertByRow(MatrixNode newNode) {
        HeaderNode curr = reviewers;

        while (curr != null) {
            if (newNode.getReviewer() == curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert != null) {

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
            else if (curr.getNext() == null) {
                HeaderNode newReviewer = new HeaderNode(newNode.getReviewer(),
                    newNode);
                curr.setNext(newReviewer);
                return;
            }
            else if (newNode.getReviewer() < curr.getNext().getID()) {
                HeaderNode newReviewer = new HeaderNode(newNode.getReviewer(),
                    newNode);
                newReviewer.setNext(curr.getNext());
                curr.setNext(newReviewer);
                return;
            }
            curr = curr.getNext();
        }

        HeaderNode newReviewer = new HeaderNode(newNode.getReviewer(), newNode);
        reviewers.setNext(newReviewer);
    }


    public boolean search(int score, int reviewer, int movie) {
        if (reviewers.getNext() == null) {
            return false;
        }

        HeaderNode curr = reviewers.getNext();
        while (curr.getNext() != null) {
            if (reviewer == curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert != null) {
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
        if (reviewers.getNext() == null) {
            return;
        }

        HeaderNode curr = reviewers.getNext();
        while (curr.getNext() != null) {
            if (reviewer == curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert != null) {
                    if (matrixInsert.equals(score, reviewer, movie)) {
                        /**
                         * Cases: list still has entries so keep the current
                         * headernode, or list becomes empty so headernode must
                         * be deleted either with helper function or inline.
                         * When deleting a headernode, the references of its
                         * entries need to be rearranged of all the ups and
                         * lefts
                         */
                        ;
                    }
                    matrixInsert = matrixInsert.getRight();
                }
            }
            curr = curr.getNext();
        }
    }
}
