public class SparseMatrix{
    private DoublyLinkedList reviewers;
    private DoublyLinkedList movies;

    public SparseMatrix(){
        reviewers = new DoublyLinkedList();
        movies = new DoublyLinkedList();
    }

    public void insert(int score, int reviewer, int movie){
        reviewers.insert(score, reviewer, movie);
        movies.insert(score, reviewer, movie);
    }

    public boolean search(int score, int reviewer, int movie){
        return reviewers.search(score, reviewer, movie);
    }
}