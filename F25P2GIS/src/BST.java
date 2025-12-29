public class BST {
    private class BSTNode {
        private CityRecord city;
        // private String cityName
        private BSTNode leftChild, rightChild;

        // Always construct a leaf node
        public BSTNode(String name, int x, int y) {
            city = new CityRecord(name, x, y);
        }


//        public void setLeft(BSTNode newChild) {
//            leftChild = newChild;
//        }
//
//
//        public void setRight(BSTNode newChild) {
//            rightChild = newChild;
//        }


        public BSTNode getLeft() {
            return leftChild;
        }


        public BSTNode getRight() {
            return rightChild;
        }


        public String getCityName() {
            return city.getName();
        }


        public CityRecord getCityRecord() {
            return city;
        }


        public boolean equals(Object obj) {
            if (obj instanceof BSTNode) {
                BSTNode node = (BSTNode)obj;
                return city.equals(node.getCityRecord());
            }
            return false;
        }
    }

    private BSTNode root;

    public BST() {
        root = null;
    }


    public void insert(String name, int x, int y) {
        BSTNode treeInsert = new BSTNode(name, x, y);
        insert(treeInsert, root);
    }


    private void insert(BSTNode insert, BSTNode root) {
        if (root == null) {
            root = insert;
            return;
        }
        
//        if(root.equals(insert)) {
//            root = insert;
//            return;
//        }

        if (insert.getCityName().compareTo(root.getCityName()) <= 0) {
            insert(insert, root.getLeft());
        }
        else {
            insert(insert, root.getRight());
        }
    }


    public BSTNode search(String name, int x, int y) {
        BSTNode treeSearch = new BSTNode(name, x, y);
        return search(treeSearch, root);
    }


    public BSTNode search(BSTNode search, BSTNode root) {
        if (root == null || root.equals(search)) {
            return root;
        }

        if (search.getCityName().compareTo(root.getCityName()) <= 0) {
            return search(search, root.getLeft());
        }
        else {
            return search(search, root.getRight());
        }
    }


    public void delete(String name, int x, int y) {
        BSTNode treeDelete = search(name, x, y);
        if (treeDelete != null) {
            delete(treeDelete, root);
        }
    }


    private void delete(BSTNode delete, BSTNode root) {
        if (root.equals(delete)) {
         // If the node has 0 or only right child
            if (root.getLeft() == null) {
                root = root.getRight();
                return;
            }
            //Node has 2 children
            else if (root.getRight() != null) {
                BSTNode max = searchMax(root.getLeft());
                delete(root.getRight(), max);
                root = max;
                return;
            }
            //Node only has left child
            else {
                root = root.getLeft();
                return;
            }
        }

        if (delete.getCityName().compareTo(root.getCityName()) <= 0) {
            delete(delete, root.getLeft());
        }
        else {
            delete(delete, root.getRight());
        }
    }
    
    private BSTNode searchMax(BSTNode root) {
        BSTNode curr = root;
        while(curr.getRight() != null) {
            curr = curr.getRight();
        }
        //logic after loop to restructure the tree
        return curr;
    }
}
