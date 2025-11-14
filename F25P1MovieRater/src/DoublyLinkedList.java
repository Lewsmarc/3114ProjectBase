public class DoublyLinkedList {
    private class DLLNode {
        /*
         * private int score;
         * private int reviewer;
         * private int movie;
         */
        private E data;
        private DLLNode prev;
        private DLLNode next;

        public DLLNode(int rating, int review, int pic) {
            score = rating;
            reviewer = review;
            movie = pic;
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


        public DLLNode getNext() {
            return next;
        }


        public DLLNode getPrev() {
            return prev;
        }


        public void setScore(int rating) {
            score = rating;
        }


        public void setNext(DLLNode newNext) {
            next = newNext;
        }


        public void setPrev(DLLNode newPrev) {
            prev = newPrev;
        }


        public boolean equals(int rating, int review, int pic) {
            return score == rating && reviewer == review && movie == pic;
        }
    }

    private DLLNode head;
    private DLLNode tail;
    //private E data;

    public DoublyLinkedList() {
        head = new DLLNode(-1, -1, -1);
        tail = new DLLNode(-1, -1, -1);
        head.setNext(tail);
        tail.setPrev(head);
    }


    public void insert(int rating, int review, int pic) {
        DLLNode curr = new DLLNode(rating, review, pic);

        if (!search(rating, review, pic)) {
            if (head == null) {
                head = curr;
                tail = curr;
            }
            else {
                curr.setNext(head);
                head.setPrev(curr);
                head = curr;
            }
        }
        else {
            while (curr.getNext() != null) {
                if (curr.equals(rating, review, pic)) {
                    curr.setScore(rating);
                }
                curr = curr.getNext();
            }
        }
    }


    public boolean search(int rating, int review, int pic) {
        DLLNode curr = head;
        while (curr.getNext() != null) {
            if (curr.equals(rating, review, pic)) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }


    public void delete(int rating, int review, int pic) {
        DLLNode curr = head;
        while (curr.getNext() != null) {
            if (curr.equals(rating, review, pic)) {
                if (curr == tail) {
                    DLLNode buff = curr.getPrev();
                    buff.setNext(null);
                }
                else if (curr == head) {
                    head = head.getNext();
                }
                else {
                    DLLNode buff = curr.getPrev();
                    buff.setNext(curr.getNext());
                    curr.getNext().setPrev(buff);
                }
            }
            curr = curr.getNext();
        }
    }


    public int average() {
        DLLNode curr = head;
        int sum = 0;
        int count = 0;
        while (curr.getNext() != null) {
            sum += curr.getScore();
            count++;
        }
        return sum / count;
    }
}
