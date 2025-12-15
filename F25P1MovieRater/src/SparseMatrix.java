public class SparseMatrix {
    private HeaderNode reviewers;
    private HeaderNode movies;
    private int size;
    private int numRows;
    private int numCols;

    /**
     * Represents the internal reviewer scores in the movie for our sparsematrix
     */
    private class MatrixNode {
        private int score;
        private int reviewer;
        private int movie;

        private MatrixNode down, right;

        /**
         * Constructor
         * 
         * @param rating
         * @param name
         * @param movieID
         */
        public MatrixNode(int rating, int name, int movieID) {
            score = rating;
            reviewer = name;
            movie = movieID;
        }


        /**
         * Returns down node, ie next reviewer that scored the movie
         * 
         * @return
         */
        public MatrixNode getDown() {
            return down;
        }


        /**
         * returns the right node, ie the next movie the reviewer scored
         * 
         * @return
         */
        public MatrixNode getRight() {
            return right;
        }


        /**
         * returns the score given
         * 
         * @return
         */
        public int getScore() {
            return score;
        }


        /**
         * Returns the reviewer index which is also the row index
         * 
         * @return
         */
        public int getReviewer() {
            return reviewer;
        }


        /**
         * Returns the movie index which is also the column index
         * 
         * @return
         */
        public int getMovie() {
            return movie;
        }


        /**
         * Setter for the down node
         * 
         * @param insert
         */
        public void setDown(MatrixNode insert) {
            down = insert;
        }


        /**
         * Setter for the right node
         * 
         * @param nextNode
         */
        public void setRight(MatrixNode nextNode) {
            right = nextNode;
        }


        /**
         * Setter for the score
         * 
         * @param newScore
         */
        public void setScore(int newScore) {
            score = newScore;
        }


        /**
         * Evaluates the int data fields equality to the passed ints
         * 
         * @param rating
         * @param review
         * @param pic
         * @return
         */
        public boolean equals(int rating, int review, int pic) {
            return score == rating && reviewer == review && movie == pic;
        }


        /**
         * Equals to check that the location indices are the same
         * 
         * @param review
         * @param pic
         * @return
         */
        public boolean equals(int review, int pic) {
            return reviewer == review && movie == pic;
        }


        /**
         * Equals method for a generic object
         */
        public boolean equals(Object obj) {
            if (obj instanceof MatrixNode) {
                MatrixNode check = (MatrixNode)obj;
                return equals(check.getScore(), check.getReviewer(), check
                    .getMovie());
            }
            return false;
        }
    }


    private class HeaderNode {

        // Single linked list
        private int id;
        private MatrixNode first;
        private HeaderNode next;

        /**
         * Constructor, the id field must always be instantiated
         * 
         * @param name
         */
        public HeaderNode(int name) {
            id = name;
        }


        /**
         * Constructor that includes an initial first review and score
         * 
         * @param name
         * @param review
         */
        public HeaderNode(int name, MatrixNode review) {
            id = name;
            first = review;
        }


        /**
         * Returns the next row or column
         * 
         * @return
         */
        public HeaderNode getNext() {
            return next;
        }


        /**
         * Returns the first stored review score
         * 
         * @return
         */
        public MatrixNode getFirst() {
            return first;
        }


        /**
         * Returns the index of the row or column
         * 
         * @return
         */
        public int getID() {
            return id;
        }


        /**
         * Setter for the next field
         * 
         * @param newID
         */
        public void setNext(HeaderNode newID) {
            next = newID;
        }


        /**
         * Setter for the first field
         * 
         * @param newFirst
         */
        public void setFirst(MatrixNode newFirst) {
            first = newFirst;
        }


        /**
         * Determines if a HeaderNode points to no more MatrixNodes
         * 
         * @return
         */
        public boolean isEmpty() {
            return first == null;
        }
    }

    /*
     * Constructor method, using -1 to signify the sentinel nodes as data should
     * always be positively indexed
     */
    public SparseMatrix() {
        reviewers = new HeaderNode(-1);
        movies = new HeaderNode(-1);
        size = 0;
    }


    /**
     * Inserts a single score, can handle creating new entries for the
     * HeaderNodes
     * 
     * @param score
     * @param reviewer
     * @param movie
     * @return
     */
    public boolean insert(int reviewer, int movie, int score) {
        // Out of bounds check
        if (score <= -1 || reviewer <= -1 || movie <= -1) {
            return false;
        }

        // Checks if a new score is being inserted to correctly update the
        // number of entries
        if (get(reviewer, movie) == -1) {
            size++;
        }

        // Delegates the insertion into memory into helper functions as the
        // singly linked structure demands
        MatrixNode newNode = new MatrixNode(score, reviewer, movie);
        insertByRow(newNode);
        insertByColumn(newNode);
        return true;
    }


    private void insertByColumn(MatrixNode newNode) {
        // control block to see if the column already exists
        if (hasCol(newNode.getMovie())) {
            HeaderNode curr = findCol(newNode.getMovie());

            if (newNode.getMovie() == curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();

                // iterates through list to find the right insertion spot
                while (matrixInsert != null) {

                    // End of list insertion
                    if (matrixInsert.getDown() == null) {
                        // score already exists and must be edited
                        if (matrixInsert.equals(newNode.getReviewer(), newNode
                            .getMovie())) {
                            matrixInsert.setScore(newNode.getScore());
                            return;
                        }

                        // edge case of if there is exactly one entry in the
                        // HeaderNode
                        else if (matrixInsert == curr.getFirst() && newNode
                            .getReviewer() < matrixInsert.getReviewer()) {
                            newNode.setDown(matrixInsert);
                            curr.setFirst(newNode);
                            return;
                        }

                        // New entry must be inserted at the end of the list
                        else {
                            matrixInsert.setDown(newNode);
                            return;
                        }
                    }
                    // Not end of list
                    // score already exists and must be edited
                    if (matrixInsert.getDown().equals(newNode.getReviewer(),
                        newNode.getMovie())) {
                        matrixInsert.getDown().setScore(newNode.getScore());
                        return;
                    }
                    // New score to be inserted after curr
                    else if (newNode.getReviewer() < matrixInsert.getDown()
                        .getReviewer()) {
                        newNode.setDown(matrixInsert.getDown());
                        matrixInsert.setDown(newNode);
                        return;
                    }
                    matrixInsert = matrixInsert.getDown();
                }
            }
            // reviewer doesn't exist and needs to be appended to the end of the
            // list
            else if (curr.getNext() == null) {
                HeaderNode newMovie = new HeaderNode(newNode.getMovie(),
                    newNode);
                curr.setNext(newMovie);
                return;
            }
            // case where the movie needs to be inserted in the middle of the
            // list
            else if (newNode.getMovie() < curr.getNext().getID()) {
                HeaderNode newMovie = new HeaderNode(newNode.getMovie(),
                    newNode);
                newMovie.setNext(curr.getNext());
                curr.setNext(newMovie);
                return;
            }
        }
        // This a new movie
        HeaderNode newMovie = new HeaderNode(newNode.getMovie(), newNode);
        HeaderNode curr = movies;
        while (curr != null) {
            if (curr.getNext() == null) {
                curr.setNext(newMovie);
                numCols++;
                return;
            }
            else if (newNode.getMovie() <= curr.getNext().getID()) {
                newMovie.setNext(curr.getNext());
                curr.setNext(newMovie);
                numCols++;
                return;
            }
            curr = curr.getNext();
        }
    }


    // needs refactoring
    private void insertByRow(MatrixNode newNode) {
        // control block to see if the row already exists
        if (hasRow(newNode.getReviewer())) {
            HeaderNode curr = findRow(newNode.getReviewer());

            if (newNode.getReviewer() == curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();

                // iterates through list to find the right insertion spot
                while (matrixInsert != null) {

                    // End of list insertion
                    if (matrixInsert.getRight() == null) {
                        // score already exists and must be edited
                        if (matrixInsert.equals(newNode.getReviewer(), newNode
                            .getMovie())) {
                            matrixInsert.setScore(newNode.getScore());
                            return;
                        }

                        // edge case of if there is exactly one entry in the
                        // HeaderNode
                        else if (matrixInsert == curr.getFirst() && newNode
                            .getMovie() < matrixInsert.getMovie()) {
                            newNode.setRight(matrixInsert);
                            curr.setFirst(newNode);
                            return;
                        }

                        // New entry must be inserted at the end of the list
                        else {
                            matrixInsert.setRight(newNode);
                            return;
                        }
                    }
                    // movie doesn't exist and needs to be appended to the
                    // end of the
                    if (matrixInsert.getRight().equals(newNode.getReviewer(),
                        newNode.getMovie())) {
                        matrixInsert.setScore(newNode.getScore());
                        return;
                    }
                    // case where the movie needs to be inserted in the middle
                    // of the list
                    else if (newNode.getMovie() < matrixInsert.getRight()
                        .getMovie()) {
                        newNode.setRight(matrixInsert.getRight());
                        matrixInsert.setRight(newNode);
                        return;
                    }
                    matrixInsert = matrixInsert.getRight();
                }
            }

            // movie doesn't exist and needs to be appended to the end of the
            // list
            else if (curr.getNext() == null) {
                HeaderNode newReviewer = new HeaderNode(newNode.getReviewer(),
                    newNode);
                curr.setNext(newReviewer);
                return;
            }
            // case where the movie needs to be inserted in the middle of the
            // list
            else if (newNode.getReviewer() < curr.getNext().getID()) {
                HeaderNode newReviewer = new HeaderNode(newNode.getReviewer(),
                    newNode);
                newReviewer.setNext(curr.getNext());
                curr.setNext(newReviewer);
                return;
            }
        }

        //
        HeaderNode newReviewer = new HeaderNode(newNode.getReviewer(), newNode);
        HeaderNode curr = reviewers;
        while (curr != null) {
            if (curr.getNext() == null) {
                curr.setNext(newReviewer);
                numRows++;
                return;
            }
            else if (newNode.getReviewer() <= curr.getNext().getID()) {
                newReviewer.setNext(curr.getNext());
                curr.setNext(newReviewer);
                numRows++;
                return;
            }
            curr = curr.getNext();
        }
    }


    /**
     * Direct call to edit a score
     * 
     * @param score
     * @param reviewer
     * @param movie
     */
    public void edit(int score, int reviewer, int movie) {
        if (hasRow(reviewer)) {
            MatrixNode matrixSearch = findRow(reviewer).getFirst();
            while (matrixSearch != null) {
                if (matrixSearch.equals(score, reviewer, movie)) {
                    matrixSearch.setScore(score);
                }
                matrixSearch = matrixSearch.getRight();
            }
        }
    }


    /**
     * Returns a truth value for the if the score exists
     * 
     * @param reviewer
     * @param movie
     * @return
     */
    public int get(int reviewer, int movie) {
        if (reviewers.getNext() == null) {
            return -1;
        }

        HeaderNode curr = reviewers.getNext();
        while (curr != null) {
            if (reviewer == curr.getID()) {
                MatrixNode matrixSearch = curr.getFirst();
                while (matrixSearch != null) {
                    if (matrixSearch.getReviewer() == reviewer && matrixSearch
                        .getMovie() == movie) {
                        return matrixSearch.getScore();
                    }
                    matrixSearch = matrixSearch.getRight();
                }
            }
            curr = curr.getNext();
        }
        return -1;
    }


    /**
     * Removes a score from the matrix by changing the pointers to no longer
     * reference the passed point
     * 
     * @param reviewer
     * @param movie
     */
    public boolean remove(int reviewer, int movie) {
        if (get(reviewer, movie) == -1) {
            return false;
        }
        deleteByRow(reviewer, movie);
        deleteByColumn(reviewer, movie);
        size--;
        if (reviewers.getNext() == null && movies.getNext() == null) {
            size = 0;
        }
        return true;
    }


    private void deleteByRow(int reviewer, int movie) {
        // Uses helper findRow to obtain the pointer to the correct row and then
        // iterates through matrix entries
        HeaderNode curr = findRow(reviewer);
        MatrixNode matrixDelete = curr.getFirst();
        if (matrixDelete.equals(reviewer, movie)) {
            curr.setFirst(matrixDelete.getRight());
        }
        else {
            while (matrixDelete.getRight() != null) {
                // Checks each node
                if (matrixDelete.getRight().equals(reviewer, movie)) {
                    matrixDelete.setRight(matrixDelete.getRight().getRight());
                    break;
                }
                matrixDelete = matrixDelete.getRight();
            }
        }
        // If we removed the last entry for the row then it should be removed
        if (curr.isEmpty()) {
            removeRow(reviewer);
        }
    }


    private void deleteByColumn(int reviewer, int movie) {
        // Uses helper findRow to obtain the pointer to the correct row and then
        // iterates through matrix entries
        if (!hasCol(movie)) {
            return;
        }
        HeaderNode curr = findCol(movie);
        MatrixNode matrixDelete = curr.getFirst();
        if (matrixDelete.equals(reviewer, movie)) {
            curr.setFirst(matrixDelete.getDown());
        }
        else {
            while (matrixDelete.getDown() != null) {
                // Checks each node
                if (matrixDelete.getDown().equals(reviewer, movie)) {
                    matrixDelete.setDown(matrixDelete.getDown().getDown());
                    break;
                }
                matrixDelete = matrixDelete.getDown();
            }
        }
        // If we removed the last entry for the column then it should be removed
        if (curr.isEmpty()) {
            removeColumn(movie);
        }
    }


    /**
     * Removes one whole row
     * 
     * @param index
     */
    public boolean removeRow(int index) {
        boolean removed = false;
        HeaderNode curr = reviewers.getNext();
        if(curr == null) {
            return false;
        }
        // If the row to remove is the first one
        if (curr.getID() == index) {
            removed = true;
            reviewers.setNext(curr.getNext());
            numRows--;
        }

        else {
            while (curr.getNext() != null) {
                if (curr.getNext().getID() == index) {
                    // while loop to go through the list to connect the
                    // remaining one
                    curr.setNext(curr.getNext().getNext());
                    removed = true;
                    numRows--;
                    break;
                }
                curr = curr.getNext();
            }
        }
        curr = movies.getNext();
        // Look to change the pointers to delete all entries in a row
        while (curr != null) {
            MatrixNode matrixDelete = curr.getFirst();
            // Checks if the first node needs to be delete
            if (matrixDelete.getReviewer() == index) {
                curr.setFirst(matrixDelete.getDown());
                size--;
            }
            else {
                while (matrixDelete.getDown() != null) {
                    if (matrixDelete.getDown().getReviewer() == index) {
                        matrixDelete.setDown(matrixDelete.getDown().getDown());
                        size--;
                        break;
                    }
                    matrixDelete = matrixDelete.getDown();
                }
            }
            // Necessary conditional if the column also had its last element
            // deleted
            if (curr.isEmpty()) {
                // do something to delete the a now empty column
                removeColumn(curr.getID());
            }
            curr = curr.getNext();
        }
        return removed;
    }


    /**
     * Removes one whole column
     * 
     * @param index
     */
    public boolean removeColumn(int index) {
        boolean removed = false;
        HeaderNode curr = movies.getNext();
        if(curr == null) {
            return false;
        }
        // If the row to remove is the first one
        if (curr.getID() == index) {
            removed = true;
            movies.setNext(curr.getNext());
            numCols--;
        }
        else {
            while (curr.getNext() != null) {
                // while loop to go through the list to connect the
                // remaining one
                if (curr.getNext().getID() == index) {
                    curr.setNext(curr.getNext().getNext());
                    removed = true;
                    numCols--;
                    break;
                }
                curr = curr.getNext();
            }
        }
        curr = reviewers.getNext();
        // Look to change the pointers to delete all entries in a row
        while (curr != null) {
            MatrixNode matrixDelete = curr.getFirst();
            // Checks if the first node needs to be delete
            if (matrixDelete.getMovie() == index) {
                curr.setFirst(matrixDelete.getRight());
                size--;
            }
            else {
                while (matrixDelete.getRight() != null) {
                    if (matrixDelete.getRight().getMovie() == index) {
                        matrixDelete.setRight(matrixDelete.getRight()
                            .getRight());
                        size--;
                        break;
                    }
                    matrixDelete = matrixDelete.getRight();
                }
            }
            // Necessary conditional if the column also had its last element
            // deleted
            if (curr.isEmpty()) {
                // do something to delete the a now empty row
                removeRow(curr.getID());
            }
            curr = curr.getNext();
        }
        return removed;
    }


    /**
     * Prints of the format review: (movie, score)
     */
    public void print() {
        HeaderNode curr = reviewers.getNext();
        while (curr != null) {
            MatrixNode matrixPrint = curr.getFirst();
            System.out.print(curr.getID() + ": ");
            while (matrixPrint != null) {
                if (matrixPrint.getRight() == null) {
                    System.out.print("(" + matrixPrint.getMovie() + ", "
                        + matrixPrint.getScore() + ")\n");
                }
                else {
                    System.out.print("(" + matrixPrint.getMovie() + ", "
                        + matrixPrint.getScore() + ") ");
                }
                matrixPrint = matrixPrint.getRight();
            }
            curr = curr.getNext();
        }
    }


    /**
     * Gives a string representation of the matrix in the format:
     * review: (movie, score)
     */
    public String toString() {
        HeaderNode curr = reviewers.getNext();
        String returnString = "";
        while (curr != null) {
            MatrixNode matrixPrint = curr.getFirst();
            returnString = returnString + curr.getID() + ": ";
            while (matrixPrint != null) {
                if (matrixPrint.getRight() == null) {
                    returnString = returnString + "(" + matrixPrint.getMovie()
                        + ", " + matrixPrint.getScore() + ")\n";
                }
                else {
                    returnString = returnString + "(" + matrixPrint.getMovie()
                        + ", " + matrixPrint.getScore() + ") ";
                }
                matrixPrint = matrixPrint.getRight();
            }
            curr = curr.getNext();
        }
        return returnString;
    }


    private double averageColumn(int index) {
        int scoreSum = 0;
        int count = 0;
        if (hasCol(index)) {
            MatrixNode average = findCol(index).getFirst();
            while (average != null) {
                scoreSum += average.getScore();
                count++;
                average = average.getDown();
            }
            return (double)scoreSum / count;
        }
        return -1;
    }


    public double averageRow(int index) {
        int scoreSum = 0;
        int count = 0;
        if (hasRow(index)) {
            MatrixNode average = findRow(index).getFirst();
            while (average != null) {
                scoreSum += average.getScore();
                count++;
                average = average.getRight();
            }
            return (double)scoreSum / count;
        }
        return -1;
    }


    public int similarMovie(int index) {
        HeaderNode curr = movies.getNext();
        double benchmark = averageColumn(index);
        double diff = 100;
        int mostSimilar = -1;
        while (curr != null) {
            if (index != curr.getID()) {
                double similarCheck = averageColumn(curr.getID());
                if (Math.abs(benchmark - similarCheck) < diff) {
                    diff = Math.abs(benchmark - similarCheck);
                    mostSimilar = curr.getID();
                }
            }
            curr = curr.getNext();
        }
        return mostSimilar;
    }


    public int similarReviewer(int index) {
        HeaderNode curr = reviewers.getNext();
        double benchmark = averageRow(index);
        double diff = 100;
        int mostSimilar = -1;
        while (curr != null) {
            if (index != curr.getID()) {
                double similarCheck = averageRow(curr.getID());
                if (Math.abs(benchmark - similarCheck) < diff) {
                    diff = Math.abs(benchmark - similarCheck);
                    mostSimilar = curr.getID();
                }
            }
            curr = curr.getNext();
        }
        return mostSimilar;
    }


    public String getRowString(int index) {
        HeaderNode curr = reviewers.getNext();
        String returnString = "";
        while (curr != null) {
            if (curr.getID() == index) {
                MatrixNode matrixPrint = curr.getFirst();
                returnString = returnString + curr.getID() + ": ";
                while (matrixPrint != null) {
                    if (matrixPrint.getRight() == null) {
                        returnString = returnString + matrixPrint.getScore();
                    }
                    else {
                        returnString = returnString + matrixPrint.getScore()
                            + " ";
                    }
                    matrixPrint = matrixPrint.getRight();
                }
            }
            curr = curr.getNext();
        }
        if (returnString.equals("")) {
            return null;
        }
        return returnString;
    }


    public String getColString(int index) {
        HeaderNode curr = movies.getNext();
        String returnString = "";
        while (curr != null) {
            if (curr.getID() == index) {
                MatrixNode matrixPrint = curr.getFirst();
                returnString = returnString + curr.getID() + ": ";
                while (matrixPrint != null) {
                    if (matrixPrint.getDown() == null) {
                        returnString = returnString + matrixPrint.getScore();
                    }
                    else {
                        returnString = returnString + matrixPrint.getScore()
                            + " ";
                    }
                    matrixPrint = matrixPrint.getDown();
                }
            }
            curr = curr.getNext();
        }
        if (returnString.equals("")) {
            return null;
        }
        return returnString;
    }


    public int size() {
        return size;
    }


    public void clear() {
        reviewers = new HeaderNode(-1);
        movies = new HeaderNode(-1);
        size = 0;
    }


    public HeaderNode findRow(int index) {
        HeaderNode curr = reviewers.getNext();
        while (curr != null) {
            if (curr.getID() == index) {
                return curr;
            }
            curr = curr.getNext();
        }
        return null;
    }


    public HeaderNode findCol(int index) {
        HeaderNode curr = movies.getNext();
        while (curr != null) {
            if (curr.getID() == index) {
                return curr;
            }
            curr = curr.getNext();
        }
        return null;
    }


    public boolean hasCol(int index) {
        if (findCol(index) != null) {
            return true;
        }
        else {
            return false;
        }
    }


    public boolean hasRow(int index) {
        if (findRow(index) != null) {
            return true;
        }
        else {
            return false;
        }
    }


    public int[] getAllRowIDs() {
        HeaderNode curr = reviewers.getNext();
        int[] arr = new int[numRows];
        int index = 0;
        while (curr != null) {
            arr[index] = curr.getID();
            curr = curr.getNext();
            index++;
        }
        return arr;
    }


    public int[] getAllColIDs() {
        HeaderNode curr = movies.getNext();
        int[] arr = new int[numCols];
        int index = 0;
        while (curr != null) {
            arr[index] = curr.getID();
            curr = curr.getNext();
            index++;
        }
        return arr;
    }


    public boolean rowSorted(int index) {
        if (hasRow(index)) {
            MatrixNode sortedHead = findRow(index).getFirst();
            while (sortedHead.getRight() != null) {
                if (sortedHead.getReviewer() > sortedHead.getRight()
                    .getReviewer()) {
                    return false;
                }
                sortedHead = sortedHead.getRight();
            }
            return true;
        }
        return false;
    }


    public boolean colSorted(int index) {
        if (hasCol(index)) {
            MatrixNode sortedHead = findCol(index).getFirst();
            while (sortedHead.getDown() != null) {
                if (sortedHead.getReviewer() > sortedHead.getDown()
                    .getReviewer()) {
                    return false;
                }
                sortedHead = sortedHead.getDown();
            }
            return true;
        }
        return false;
    }
}
