import BST.BSTNode;

public class KDTree {
    private class KDNode {
        CityRecord city;
        int[] point;
        KDNode left, right;

        public KDNode(CityRecord newCity) {
            city = newCity;
            point = newCity.getLocation();
        }


        public KDNode(String name, int x, int y) {
            city = city = new CityRecord(name, x, y);;
            point = city.getLocation();
        }

        
        public CityRecord getCityRecord() {
            return city;
        }

        public int[] getPoint() {
            return point;
        }


        public KDNode getLeft() {
            return left;
        }


        public KDNode getRight() {
            return right;
        }


        public boolean equals(Object obj) {
            if (obj instanceof KDNode) {
                KDNode node = (KDNode)obj;
                return city.equals(node.getCityRecord());
            }
            return false;
        }
    }

    private KDnode root;
    private int dim;

    public KDTree() {
        root = null;
        dim = 2;
    }


    public KDTree(int k) {
        root = null;
        dim = k;
    }


    public void insert(String name, int x, int y) {
        KDNode insertNode = new KDNode(name, x, y);
        insert(insertNode, root, 0);
    }


    public void insert(KDNode insert, KDNode root, int depth) {
        if (root == null) {
            root = insert;
            return;
        }
        int cd = depth % dim;

        if (root.getPoint()[cd] >= insert.getPoint()[cd]) {
            insert(insert, root.getLeft(), depth + 1);
        }
        else {
            insert(insert, root.getRight(), depth + 1);
        }
    }


    public KDNode search(String name, int x, int y) {
        KDNode searchNode = new KDNode(name, x, y);
        return search(searchNode, root, 0);
    }


    public KDNode search(KDNode search, KDNode root, int depth) {
        if (root.equals(search)) {
            return root;
        }
        if(root == null) {
            return null;
        }
        
        int cd = depth % dim;

        if (root.getPoint()[cd] >= search.getPoint()[cd]) {
            search(search, root.getLeft(), depth + 1);
        }
        else {
            search(search, root.getRight(), depth + 1);
        }
    }
}
