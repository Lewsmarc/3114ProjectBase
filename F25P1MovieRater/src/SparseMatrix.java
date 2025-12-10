public class SparseMatrix {
    private HeaderNode reviewers;
    private HeaderNode movies;
    private int size;

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


        public void setScore(int newScore) {
            score = newScore;
        }


        public boolean equals(int rating, int review, int pic) {
            return score == rating && reviewer == review && movie == pic;
        }


        public boolean equals(int review, int pic) {
            return reviewer == review && movie == pic;
        }


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


        public void setFirst(MatrixNode newFirst) {
            first = newFirst;
        }
    }

    public SparseMatrix() {
        reviewers = new HeaderNode(-1);
        movies = new HeaderNode(-1);
        size = 0;
    }


    public boolean insert(int score, int reviewer, int movie) {
        if (score <= -1 || reviewer <= -1 || movie <= -1) {
            return false;
        }

        if (!get(reviewer, movie)) {
            size++;
        }

        MatrixNode newNode = new MatrixNode(score, reviewer, movie);
        insertByRow(newNode);
        insertByColumn(newNode);
        return true;
    }


    public void insertByColumn(MatrixNode newNode) {
        if (hasCol(newNode.getMovie())) {
            HeaderNode curr = findCol(newNode.getMovie());
            if (newNode.getMovie() == curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert != null) {
                    // End of list
                    if (matrixInsert.getDown() == null) {
                        if (matrixInsert.equals(newNode.getReviewer(), newNode
                            .getMovie())) {
                            matrixInsert.setScore(newNode.getScore());
                            return;
                        }
                        else if (matrixInsert == curr.getFirst() && newNode
                            .getReviewer() < matrixInsert.getReviewer()) {
                            newNode.setDown(matrixInsert);
                            curr.setFirst(newNode);
                            return;
                        }
                        else {
                            matrixInsert.setDown(newNode);
                            return;
                        }
                    }
                    // Not end of list
                    if (matrixInsert.getDown().equals(newNode.getReviewer(),
                        newNode.getMovie())) {
                        matrixInsert.setScore(newNode.getScore());
                        return;
                    }
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
        }
        // This a new movie
        HeaderNode newMovie = new HeaderNode(newNode.getMovie(), newNode);
        movies.setNext(newMovie);
    }


    public void insertByRow(MatrixNode newNode) {
        if (hasRow(newNode.getReviewer())) {
            HeaderNode curr = findRow(newNode.getMovie());
            if (newNode.getReviewer() == curr.getID()) {
                MatrixNode matrixInsert = curr.getFirst();
                while (matrixInsert != null) {

                    // end of list
                    if (matrixInsert.getRight() == null) {
                        if (matrixInsert.equals(newNode.getReviewer(), newNode
                            .getMovie())) {
                            matrixInsert.setScore(newNode.getScore());
                            return;
                        }
                        else if (matrixInsert == curr.getFirst() && newNode
                            .getMovie() < matrixInsert.getMovie()) {
                            newNode.setRight(matrixInsert);
                            curr.setFirst(newNode);
                            return;
                        }
                        else {
                            matrixInsert.setRight(newNode);
                            return;
                        }
                    }
                    // middle of list
                    if (matrixInsert.getRight().equals(newNode.getReviewer(),
                        newNode.getMovie())) {
                        matrixInsert.setScore(newNode.getScore());
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

            // Movie isn't there
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


    public void edit(int score, int reviewer, int movie) {
        if (reviewers.getNext() == null) {
            return;
        }

        HeaderNode curr = reviewers.getNext();
        while (curr != null) {
            if (reviewer == curr.getID()) {
                MatrixNode matrixSearch = curr.getFirst();
                while (matrixSearch != null) {
                    if (matrixSearch.equals(score, reviewer, movie)) {
                        matrixSearch.setScore(score);
                    }
                    matrixSearch = matrixSearch.getRight();
                }
            }
            curr = curr.getNext();
        }
    }


    public boolean get(int reviewer, int movie) {
        if (reviewers.getNext() == null) {
            return false;
        }

        HeaderNode curr = reviewers.getNext();
        while (curr != null) {
            if (reviewer == curr.getID()) {
                MatrixNode matrixSearch = curr.getFirst();
                while (matrixSearch != null) {
                    if (matrixSearch.getReviewer() == reviewer && matrixSearch
                        .getMovie() == movie) {
                        return true;
                    }
                    matrixSearch = matrixSearch.getRight();
                }
            }
            curr = curr.getNext();
        }
        return false;
    }


    public void delete(int reviewer, int movie) {
        if (reviewers.getNext() == null) {
            return;
        }
        if (!get(reviewer, movie)) {
            return;
        }
        deleteByRow(reviewer, movie);
        deleteByColumn(reviewer, movie);
        size--;
    }


    public void deleteByRow(int reviewer, int movie) {
        if (reviewers.getNext() == null) {
            return;
        }

        HeaderNode prev = reviewers;
        HeaderNode curr = reviewers.getNext();
        while (curr != null) {
            if (reviewer == curr.getID()) {
                MatrixNode matrixDelete = curr.getFirst();
                if (matrixDelete.equals(reviewer, movie)) {
                    curr.setFirst(matrixDelete.getRight());
                    if (curr.getFirst() == null) {
                        prev.setNext(curr);
                    }
                    return;
                }
                while (matrixDelete != null) {
                    if (matrixDelete.getRight().equals(reviewer, movie)) {
                        matrixDelete.setRight(matrixDelete.getRight()
                            .getRight());
                        return;
                    }
                    matrixDelete = matrixDelete.getRight();
                }
            }
            prev = curr;
            curr = curr.getNext();
        }
    }


    public void deleteByColumn(int reviewer, int movie) {
        if (movies.getNext() == null) {
            return;
        }
        HeaderNode prev = movies;
        HeaderNode curr = movies.getNext();
        while (curr != null) {
            if (movie == curr.getID()) {
                MatrixNode matrixDelete = curr.getFirst();
                if (matrixDelete.equals(reviewer, movie)) {
                    curr.setFirst(matrixDelete.getDown());
                    if (curr.getFirst() == null) {
                        prev.setNext(curr);
                    }
                    return;
                }
                while (matrixDelete != null) {
                    if (matrixDelete.getDown().equals(reviewer, movie)) {
                        matrixDelete.setDown(matrixDelete.getDown().getDown());
                        return;
                    }
                    matrixDelete = matrixDelete.getDown();
                }
            }
            prev = curr;
            curr = curr.getNext();
        }
    }


    public void removeRow(int index) {
        HeaderNode curr = reviewers.getNext();
        if (curr.getID() == index) {
            reviewers.setNext(curr.getNext());
        }
        else {
            while (curr.getNext() != null) {
                if (curr.getNext().getID() == index) {
                    // while loop to go through the list to connect the
                    // remaining
                    // one
                    curr.setNext(curr.getNext().getNext());
                    break;
                }
                curr = curr.getNext();
            }
        }
        curr = movies.getNext();
        while (curr != null) {
            MatrixNode matrixDelete = curr.getFirst();
            if (matrixDelete.getReviewer() == index) {
                curr.setFirst(matrixDelete.getDown());
                // Necessary conditional if the column also had its last element
                // deleted
                if (curr.getFirst() == null) {
                    // do something to delete the headernode
                }
            }
            else {
                while (matrixDelete.getDown() != null) {
                    if (matrixDelete.getDown().getReviewer() == index) {
                        matrixDelete.setDown(matrixDelete.getDown().getDown());
                        break;
                    }
                    matrixDelete = matrixDelete.getDown();
                }
            }
            curr = curr.getNext();
        }
    }


    public void removeColumn(int index) {
        HeaderNode curr = movies.getNext();
        if (curr.getID() == index) {
            reviewers.setNext(curr.getNext());
        }
        else {
            while (curr.getNext() != null) {
                if (curr.getNext().getID() == index) {
                    curr.setNext(curr.getNext().getNext());
                    break;
                }
                curr = curr.getNext();
            }
        }
        curr = reviewers.getNext();
        while (curr != null) {
            MatrixNode matrixDelete = curr.getFirst();
            if (matrixDelete.getMovie() == index) {
                curr.setFirst(matrixDelete.getRight());
            }
            else {
                while (matrixDelete.getRight() != null) {
                    if (matrixDelete.getRight().getMovie() == index) {
                        matrixDelete.setRight(matrixDelete.getRight()
                            .getRight());
                        break;
                    }
                    matrixDelete = matrixDelete.getRight();
                }
            }
            curr = curr.getNext();
        }
    }


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


    public double averageColumn(int index) {
        HeaderNode curr = movies.getNext();
        int scoreSum = 0;
        int count = 0;
        while (curr != null) {
            if (index == curr.getID()) {
                MatrixNode matrixPrint = curr.getFirst();
                while (matrixPrint != null) {
                    scoreSum += matrixPrint.getScore();
                    count++;
                    matrixPrint = matrixPrint.getDown();
                }
            }
            curr = curr.getNext();
        }
        if (count == 0) {
            return -1;
        }
        else {
            return (double)scoreSum / count;
        }
    }


    public double averageRow(int index) {
        HeaderNode curr = reviewers.getNext();
        int scoreSum = 0;
        int count = 0;
        while (curr != null) {
            if (index == curr.getID()) {
                MatrixNode matrixPrint = curr.getFirst();
                while (matrixPrint != null) {
                    scoreSum += matrixPrint.getScore();
                    count++;
                    matrixPrint = matrixPrint.getDown();
                }
            }
            curr = curr.getNext();
        }
        if (count == 0) {
            return -1;
        }
        else {
            return (double)scoreSum / count;
        }
    }


    public int similarMovie(int index) {
        HeaderNode curr = movies.getNext();
        double benchmark = averageColumn(index);
        double diff = 0;
        int mostSimilar = -1;
        while (curr != null) {
            if (index != curr.getID()) {
                double similarCheck = averageColumn(curr.getID());
                if (Math.abs(benchmark - similarCheck) < diff) {
                    diff = benchmark - similarCheck;
                    mostSimilar = curr.getID();
                }
            }
            curr = curr.getNext();
        }
        return mostSimilar;
    }


    public int similarReviewer(int index) {
        HeaderNode curr = movies.getNext();
        double benchmark = averageColumn(index);
        double diff = 0;
        int mostSimilar = -1;
        while (curr != null) {
            if (index != curr.getID()) {
                double similarCheck = averageColumn(curr.getID());
                if (Math.abs(benchmark - similarCheck) < diff) {
                    diff = benchmark - similarCheck;
                    mostSimilar = curr.getID();
                }
            }
            curr = curr.getNext();
        }
        return mostSimilar;
    }


    public boolean rowExists(int index) {
        HeaderNode curr = reviewers.getNext();
        while (curr != null) {
            if (curr.getID() == index) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }


    public boolean colExists(int index) {
        HeaderNode curr = movies.getNext();
        while (curr != null) {
            if (curr.getID() == index) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }


    public String getRow(int index) {
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


    public String getCol(int index) {
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


    private HeaderNode findRow(int index) {
        HeaderNode curr = reviewers.getNext();
        while (curr != null) {
            if (curr.getID() == index) {
                return curr;
            }
            curr = curr.getNext();
        }
        return null;
    }


    private HeaderNode findCol(int index) {
        HeaderNode curr = movies.getNext();
        while (curr != null) {
            if (curr.getID() == index) {
                return curr;
            }
            curr = curr.getNext();
        }
        return null;
    }


    private boolean hasCol(int index) {
        if (findCol(index) != null) {
            return true;
        }
        else {
            return false;
        }
    }


    private boolean hasRow(int index) {
        if (findRow(index) != null) {
            return true;
        }
        else {
            return false;
        }
    }
}
