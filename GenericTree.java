import java.util.*;

public class GenericTree {

    public TreeNode root = null;
    private int size = 0;
    private int minValueNode = Integer.MAX_VALUE;
    private int maxValueNode = Integer.MIN_VALUE;
    private int height = 0;

    private TreeNode predecessor = null;
    private TreeNode successor = null;
    private int state = 0;

    private int ceil = Integer.MAX_VALUE;
    private int floor = Integer.MIN_VALUE;

    private int maxSumOfSubTree = Integer.MIN_VALUE;
    private int maxNodeOfSubTree;

    private int Diameter;

    public GenericTree(int[] arr){
        Stack<TreeNode> st = new Stack<>();

        for (int element : arr) {
            if (element == -1) {
                st.pop();
            } else {
                TreeNode node = new TreeNode(element);
                if (st.size() > 0) {
                    st.peek().children.add(node);
                } else {
                    root = node;
                }

                st.push(node);
            }
        }
    }
    //display Tree
    public void display(TreeNode node){
        StringBuilder parentChildren = new StringBuilder(node.value + " -> ");

        for(TreeNode Node: node.children){
            parentChildren.append(Node.value).append(",");
        }
        parentChildren.append(".");
        System.out.println(parentChildren);

        for(TreeNode Node: node.children){
            display(Node);
        }
    }

    public int size(TreeNode node) {
        int size = 0;

        for(TreeNode child: node.children){
            size += size(child);
        }

        size += 1;

        return size;
    }

    public int maxValueNode(TreeNode node){

        int max = Integer.MIN_VALUE;

        for(TreeNode child: node.children){
            int maxValue = maxValueNode(child);
            max = Math.max(max, maxValue);
        }

        max = Math.max(max, node.value);

        return max;
    }

    public void traversals(TreeNode node){
        System.out.println("Node Pre: " + node.value);

        for(TreeNode child: node.children){
            System.out.println("Edge Pre: " + node.value + ".." + child.value);
            traversals(child);
            System.out.println("Edge Post: " + node.value + ".." + child.value);
        }

        System.out.println("Node Post: " + node.value);
    }

    public int height(TreeNode node){
        int depth = -1;

        for(TreeNode Node: node.children){
            depth = Math.max(depth,height(Node));
        }
        depth++;

        return depth;
    }

    public void levelOrder(TreeNode rootNode){
        Queue<TreeNode> queue  = new ArrayDeque<>();
        queue.add(rootNode);
        
        while(queue.size() > 0) {
            TreeNode Node = queue.remove();
            System.out.print(Node.value + " ");

            queue.addAll(Node.children);
        }
        System.out.print(".");
    }

    public void levelOrderLinewise(TreeNode rootNode){
        Queue<TreeNode> queue = new ArrayDeque<>();
        Queue<TreeNode> childQueue = new ArrayDeque<>();
        queue.add(rootNode);
        while(queue.size() > 0){
            TreeNode Node = queue.remove();
            System.out.print(Node.value + " ");
            childQueue.addAll(Node.children);

            if(queue.size() == 0){
                Queue<TreeNode> temp = queue;
                queue = childQueue;
                childQueue = temp;
                System.out.println();
            }
        }
    }

    public void levelOrderLinewiseZigzag(TreeNode rootNode){
        Boolean right = true;

        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> childStack = new Stack<>();

        stack.push(rootNode);

        while(stack.size() > 0){

            TreeNode Node = stack.pop();
            System.out.print(Node.value + " ");

            if(right){
                for(int i = 0;i < Node.children.size();++i){
                    childStack.push(Node.children.get(i));
                }
            }else{
                for(int i = Node.children.size() - 1;i >= 0;--i){
                    childStack.push(Node.children.get(i));
                }
            }

            if(stack.size() == 0){

                Stack<TreeNode> temp = stack;
                stack = childStack;
                childStack = temp;

                right = !right;
                System.out.println();
            }
        }
    }

    public void mirror(TreeNode node){

        Collections.reverse(node.children);

        for(TreeNode child: node.children){
            mirror(child);
        }
    }

    public void mirrorUsingSwap(TreeNode node){

        int size = node.children.size() - 1;

        for(int i = 0;i < size;++i){
            swapNodes(node, i, size - i);
        }

        for(TreeNode child: node.children){
            mirrorUsingSwap(child);
        }
    }

    public void swapNodes(TreeNode node, int i, int j){
        TreeNode temp = node.children.get(i);
        node.children.set(i, node.children.get(j));
        node.children.set(j, temp);
    }

    public void removeLeaves(TreeNode node){

        for(int i = node.children.size() - 1; i >= 0; --i){
            TreeNode Node = node.children.get(i);
            if(Node.children.size() == 0){
                node.children.remove(Node);
            }
        }

        for(TreeNode child: node.children){
            removeLeaves(child);
        }
    }

    public void linearize(TreeNode rootNode){

        for(TreeNode child: rootNode.children){
            linearize(child);
        }
        while(rootNode.children.size() > 1) {
            TreeNode lastChild = rootNode.children.remove(rootNode.children.size() - 1);
            TreeNode secondLastChild = rootNode.children.get(rootNode.children.size() - 1);
            TreeNode tail = getTail(secondLastChild);
            tail.children.add(lastChild);
        }
    }

    private TreeNode getTail(TreeNode node){

        while(node.children.size() == 1){
            node = node.children.get(0);
        }

        return node;
    }

    public TreeNode linearize2(TreeNode rootNode){

        if(rootNode.children.size() == 0){
            return rootNode;
        }

        TreeNode lastChildLeaf= linearize2(rootNode.children.get(rootNode.children.size() - 1));

        while(rootNode.children.size() > 1){
            TreeNode lastChild = rootNode.children.remove(rootNode.children.size() - 1);
            TreeNode secondLastChild = rootNode.children.get(rootNode.children.size() - 1);
            TreeNode tail = linearize2(secondLastChild);
            tail.children.add(lastChild);
        }

        return lastChildLeaf;

    }

    public Boolean findNode(TreeNode rootNode, int Key){

        if(rootNode.value == Key){
            return true;
        }

        for(TreeNode child: rootNode.children){
            Boolean isFound = findNode(child, Key);

            if(isFound){
                return true;
            }
        }

        return false;
    }

    public ArrayList<Integer> nodeToRootPath(TreeNode rootNode, int Key){

        if(rootNode.value == Key){
            ArrayList<Integer> list = new ArrayList<>();
            list.add(Key);
            return list;
        }

        for(TreeNode child: rootNode.children){
            ArrayList<Integer> pathToRoot = nodeToRootPath(child, Key);

            if(pathToRoot.size() > 0) {
                pathToRoot.add(rootNode.value);
                return pathToRoot;
            }
        }

        return new ArrayList<>();

    }

    public int lowestCommonAncestor(TreeNode rootNode, int node1, int node2){
        ArrayList<Integer> list = nodeToRootPath(rootNode, node1);
        ArrayList<Integer> list1 = nodeToRootPath(rootNode, node2);

        int i = list.size() - 1;
        int j = list1.size() - 1;
        while(i >= 0 && j >=0 && Objects.equals(list.get(i), list1.get(j))){
            --i;
            --j;
        }
        return list.get(i + 1);
    }

    public int distanceBetweenNodeEdges(TreeNode rootNode, int node1, int node2){

        ArrayList<Integer> list = nodeToRootPath(rootNode , node1);
        ArrayList<Integer> list1 = nodeToRootPath(rootNode, node2);

        int i = list.size() - 1;
        int j = list1.size() - 1;
        while(i >= 0 && j >=0 && Objects.equals(list.get(i), list1.get(j))){
            --i;
            --j;
        }

        return (i + j + 2);
    }

    public Boolean areTreeSimilar(TreeNode rootNode, TreeNode rootNode1){

        if(rootNode.children.size() != rootNode1.children.size()){
            return false;
        }

        for(int child = 0;child < rootNode.children.size(); ++child){
            Boolean areSimilar = areTreeSimilar(rootNode.children.get(child), rootNode1.children.get(child));

            if(!areSimilar){
                return false;
            }
        }

        return true;
    }

    public Boolean areTreeMirror(TreeNode rootNode, TreeNode rootNode1){

        if(rootNode.children.size() != rootNode1.children.size()){
            return false;
        }

        for(int child = 0;child < (rootNode.children.size() + 1)/ 2; ++child){

            Boolean isMatch = areTreeMirror(rootNode.children.get(child), rootNode1.children.get(rootNode.children.size() - child - 1));
            if(!isMatch){
                return false;
            }
        }

        return true;
    }

    public Boolean isTreeSymmetric(TreeNode rootNode){
        //if tree is symmetric then they are mirror image of itself and it gets fold.
        return areTreeMirror(rootNode, rootNode);
    }

    public void multiSolver(TreeNode rootNode, int depth){

        size++;
        maxValueNode = Math.max(maxValueNode, rootNode.value);
        minValueNode = Math.min(minValueNode, rootNode.value);
        height = Math.max(height, depth);

        for(TreeNode child: rootNode.children){
            multiSolver(child, depth + 1);
        }
    }
    // display ->  Data members Changed with traversed
    public void display(){

        System.out.println("Size: " + size);
        System.out.println("MaxValueNode: " + maxValueNode);
        System.out.println("MinValueNode: " + minValueNode);
        System.out.println("Height: " + height);

        System.out.println("Predecessor: " + (predecessor != null ? predecessor.value: null));
        System.out.println("Successor: " + (successor != null ? successor.value: null));

        System.out.println("Ceil: " + ceil);
        System.out.println("Floor: " + floor);
    }

    public int getSize(){
        return size;
    }

    public int getMaxValueNode(){
        return maxValueNode;
    }

    public int getMinValueNode(){
        return minValueNode;
    }

    public int getHeight(){
        return height;
    }

    public void predecessorAndSuccessor(TreeNode rootNode, int value){

        if(state == 0){
            if(rootNode.value == value){
                state = 1;
            }else{
                predecessor = rootNode;
            }
        }else if (state == 1){
            successor = rootNode;
            state = 2;
        }

        for(TreeNode child: rootNode.children){
            predecessorAndSuccessor(child, value);
        }
    }

    public void ceilAndFloor(TreeNode rootNode, int value){

        if(rootNode.value > value){
            if(rootNode.value < ceil){
                ceil = rootNode.value;
            }
        }

        if(rootNode.value < value){
            if(rootNode.value > floor){
                floor = rootNode.value;
            }
        }


        for(TreeNode child: rootNode.children){
            ceilAndFloor(child, value);
        }
    }

    public int kthLargest(TreeNode rootNode, int k){
        floor = Integer.MIN_VALUE;
        int factor = Integer.MAX_VALUE;
        for(int i = 0; i < k; ++i){
            ceilAndFloor(rootNode, factor);
            factor = floor;
            floor = Integer.MIN_VALUE;
        }

        return factor;
    }

    public int NodewithMaxSubTree(TreeNode rootNode){

        int sum = 0;
        for(TreeNode child: rootNode.children){
            sum += NodewithMaxSubTree(child);
        }

        sum += rootNode.value;

        if(maxSumOfSubTree < sum){
            maxSumOfSubTree = sum;
            maxNodeOfSubTree = rootNode.value;
        }

        return sum;
    }

    public void displayMaxSubTreeNode(){
        System.out.print(maxNodeOfSubTree + "@" + maxSumOfSubTree);
    }

    public int Diameter(TreeNode rootNode){

        int deepestChild = -1;
        int secondDeepestChild = -1;

        for(TreeNode child: rootNode.children){
             int childHeight = Diameter(child);
             if(childHeight > deepestChild){
                 secondDeepestChild = deepestChild;
                 deepestChild = childHeight;
             }else if(childHeight > secondDeepestChild){
                 secondDeepestChild = childHeight;
             }
        }

        int candidate = deepestChild + secondDeepestChild + 2;
        if(candidate > Diameter){
            Diameter = candidate;
        }

        deepestChild += 1;
        return deepestChild;
    }
    public void displayDiameter() {
        System.out.println("Diameter: " + Diameter);
    }

    private static class TreeNode {
        private final int value;
        private final ArrayList<TreeNode> children = new ArrayList<>();

        public TreeNode(int val) {
            this.value = val;
        }

        public TreeNode(TreeNode node){
            this.value = node.value;
        }
    }

    public static void main(String[] args) {
        int[] arr  = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};
        int[] arr1 = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};
        int[] arr2 = {10, 40, 100, -1, -1, 30, 90, -1, 80, 120, -1, 110, -1, -1, 70, -1, -1, 20, 60, -1,50, -1, -1, -1};
        int[] arr3 = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, -1, 90, -1, -1, 40, 100, -1, 110, -1, -1, -1};
        int[] arr4 = {10, 20, -50, -1, 60, -1, -1, 30, -70, -1, 80, -1, 90, -1, -1, 40, -100, -1, -1, -1};
        
        GenericTree tree = new GenericTree(arr);
        GenericTree tree1 = new GenericTree(arr1);
        GenericTree tree2 = new GenericTree(arr2);
        GenericTree tree3 = new GenericTree(arr3);
        GenericTree tree4 = new GenericTree(arr4);

        tree.display(tree.root);

        System.out.println(tree.size(tree.root));
        System.out.println(tree.maxValueNode(tree.root));
        System.out.println(tree.height(tree.root));

        tree.traversals(tree.root);
        tree.levelOrder(tree.root);
        tree.levelOrderLinewise(tree.root);
        tree.levelOrderLinewiseZigzag(tree.root);

        tree.mirror(tree.root);
        tree.mirrorUsingSwap(tree.root);
        tree.removeLeaves(tree.root);

        tree.linearize(tree.root);
        tree.linearize2(tree.root);

        System.out.println(tree.findNode(tree.root, 110));

        ArrayList<Integer> pathToRoot = tree.nodeToRootPath(tree.root, 100);
        System.out.println(pathToRoot);

        int lowestCommonAncestor = tree.lowestCommonAncestor(tree.root, 50, 90);
        System.out.println(lowestCommonAncestor);

        int distanceBetweenTwoNodes = tree.distanceBetweenNodeEdges(tree.root, 50, 110);
        System.out.println(distanceBetweenTwoNodes);

        System.out.println(tree.areTreeSimilar(tree.root,tree1.root));
        System.out.println(tree.areTreeMirror(tree.root, tree1.root));
        System.out.println(tree.areTreeMirror(tree.root, tree2.root));
        System.out.println(tree.isTreeSymmetric(tree3.root));
        tree.multiSolver(tree.root, 0);
        tree.display();

        System.out.println(tree.getSize());
        System.out.println(tree.getMaxValueNode());
        System.out.println(tree.getMinValueNode());
        System.out.println(tree.getHeight());

        tree.predecessorAndSuccessor(tree.root, 100);
        tree.display();

        tree.ceilAndFloor(tree.root, 65);
        tree.display();

        System.out.println(tree.kthLargest(tree.root, 12));

        tree4.NodewithMaxSubTree(tree4.root);
        tree4.displayMaxSubTreeNode();

        tree4.Diameter(tree4.root);
        tree4.displayDiameter();
    }
}
