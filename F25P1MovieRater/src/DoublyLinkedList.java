public class DoublyLinkedList{
    private class DLLNode{
        private int score;
        private int reviewer;
        private int movie;
        private DLLNode prev;
        private DLLNode next;

        public DLLNode(int rating, int review, int pic){
            score = rating;
            reviewer = review;
            movie = pic;
        }

        public int getScore(){
            return score;
        }
        public int getReviewer(){
            return reviewer;
        }
        public int getMovie(){
            return movie;
        }
        public DLLNode getNext(){
            return next;
        }
        public DLLNode getPrev(){
            return prev;
        }

        public void setScore(int rating){
            score = rating;
        }
        public void setNext(DLL newNext){
            next = newNext;
        }
        public void setPrev(DLL newPrev){
            prev = newPrev;
        }

        public boolean equals(int rating, int review, int pic){
            return score == rating && reviewer == review && movie == pic;
        }
    }
        private DLLNode head;
        private DLLNode headSentinel;

        public DoublyLinkedList(){
            head = null;
            headSentinel = null;
            headSentinel.setNext(head);
            head.setPrev(null);
        }

        public void insert(int rating, int review, int pic){
            DLLNode curr = head;

            while(curr.getNext != null){
                if(curr.getMovie() > pic && curr.getNext().getMovie() <= pic){
                    break;
                }
                if(curr.getReviewer() > review && curr.getNext().getReviewer() <= review){
                    break;
                }
                curr.setNext(curr.getNext);
            }
            if(curr.equals(rating, review, pic)){
                curr.setScore(rating);
            }
            else{
                DLLNode insertNode = new DLLNode(rating, review, pic);
                DLLNode.setNext(curr.getNext());
                DLLNode.setPrev(curr.getPrev());
                curr.setNext(insertNode);
                curr.getNext().setPrev(insertNode);
            }
        }

        public boolean search(int rating, int review, int pic){
            DLLNode curr = head;
            while(curr.getNext != null){
                if(curr.equals(rating, review, pic)){
                    return true;
                }
            }
            return false;
        }

        public void delete(int rating, int review, int pic){
            DLLNode curr = head;
            while(curr.getNext != null){
                if(curr.equals(rating, review, pic)){
                    DLLNode buff = curr.getPrev();
                    buff.setNext(curr.getNext);
                    buff.getNext().setPrev(buff);
                }
            }
        }

        public int average(){
            DLLNode curr = head;
            int sum = 0;
            int count = 0;
            while(curr.getNext != null){
                sum += curr.getScore();
                count++;
            }
            return sum/count;
        }
}