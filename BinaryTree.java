import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {

    public TreeNode rootNode;

    BinaryTree(Integer[] Nodes){

        rootNode = new TreeNode(Nodes[0], null, null);

        Pair rootPair = new Pair(rootNode, 1);
        int index = 0;
        Stack<Pair> stack = new Stack<>();
        stack.push(rootPair);

        while(stack.size() > 0){

            Pair top = stack.peek();
            int currentState = top.state;
            switch (currentState) {
                case 1 -> {
                    ++index;
                    if (Nodes[index] != null) {
                        top.node.left = new TreeNode(Nodes[index], null, null);
                        Pair leftPair = new Pair(top.node.left, 1);
                        stack.push(leftPair);
                    }
                    top.state++;
                }
                case 2 -> {
                    ++index;
                    if (Nodes[index] != null) {
                        top.node.right = new TreeNode(Nodes[index], null, null);
                        Pair rightPair = new Pair(top.node.right, 1);
                        stack.push(rightPair);
                    }
                    top.state++;
                }
                default -> stack.pop();
            }
        }

    }

    public void display(TreeNode Node){

        if(Node == null){
            return;
        }

        if(Node.left == null)
            System.out.print("null");
        else
            System.out.print(Node.left.value);

        System.out.print(" <- " + Node.value + " -> ");

        if(Node.right == null)
            System.out.println("null");
        else
            System.out.println(Node.right.value);

        display(Node.left);
        display(Node.right);

    }

    public int size(TreeNode Node){

        if(Node == null){
            return 0;
        }

        int size = 1;
        size += size(Node.left);
        size += size(Node.right);

        return size;
    }

    public int sum(TreeNode Node){

        if(Node == null){
            return 0;
        }

        int sum = Node.value;
        sum += sum(Node.left);
        sum += sum(Node.right);

        return sum;
    }

    public int max(TreeNode Node){

        if(Node == null){
            return Integer.MIN_VALUE;
        }

        int max = Node.value;
        max = Math.max(max, max(Node.left));
        max = Math.max(max, max(Node.right));

        return max;
    }

    public int height(TreeNode Node){

        if(Node == null){
            return -1;
        }

        int leftHeight = height(Node.left);
        int rightHeight = height(Node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public void preOrder(TreeNode Node){

        if(Node == null){
            return;
        }

        System.out.print(Node.value + " ");
        preOrder(Node.left);
        preOrder(Node.right);
    }

    public void inOrder(TreeNode Node){
        if(Node == null){
            return;
        }

        inOrder(Node.left);
        System.out.print(Node.value + " ");
        inOrder(Node.right);
    }

      public void postOrder(TreeNode Node){
        if(Node == null){
            return;
        }

        postOrder(Node.left);
        postOrder(Node.right);
        System.out.print(Node.value + " ");

    }

    public void levelOrder(TreeNode Node){

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(Node);

        while(queue.size() > 0){
            int count = queue.size();
            for(int i = 0;i < count; ++i){

                TreeNode node = queue.remove();
                System.out.print(node.value + " ");

                if(node.left != null){
                    queue.add(node.left);
                }

                if(node.right != null){
                    queue.add(node.right);
                }

            }

            System.out.println();
        }
    }

    public void IterativePrePostInOrder(TreeNode rootNode){

        Stack<Pair> stack = new Stack<>();
        Pair rootPair = new Pair(rootNode, 1);
        stack.push(rootPair);

        StringBuilder pre = new StringBuilder();
        StringBuilder post = new StringBuilder();
        StringBuilder in = new StringBuilder();

        while(stack.size() > 0){

            Pair top = stack.peek();
            if(top.state == 1){
                top.state++;
                pre.append(top.node.value). append(" ");

                if(top.node.left != null){
                    stack.push(new Pair(top.node.left, 1));
                }

            }else if(top.state == 2){
                top.state++;
                in.append(top.node.value). append(" ");
                if(top.node.right != null){
                    stack.push(new Pair(top.node.right, 1));
                }
            }else {
                post.append(top.node.value). append(" ");
                stack.pop();
            }
        }

        System.out.println("Pre: " + pre);
        System.out.println("In: " + in);
        System.out.println("Post: " + post);
    }

    private static class Pair {
        private final TreeNode node;
        private int state;

        Pair(TreeNode node, int state){
            this.node = node;
            this.state = state;
        }
    }

    private static class TreeNode{

        private final int value;
        private TreeNode left;
        private TreeNode right;

        TreeNode(int value, TreeNode left, TreeNode right){
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        Integer[] Nodes = {50, 25, 12, null, null, 37, 30, null, null, null, 75, 62, null, 70, null, null, 87,  null, null};
        BinaryTree tree = new BinaryTree(Nodes);
        tree.display(tree.rootNode);

        System.out.println(tree.size(tree.rootNode));
        System.out.println(tree.sum(tree.rootNode));
        System.out.println(tree.max(tree.rootNode));
        System.out.println(tree.height(tree.rootNode));

        tree.preOrder(tree.rootNode);
        System.out.println();
        tree.postOrder(tree.rootNode);
        System.out.println();
        tree.inOrder(tree.rootNode);
        System.out.println();
        tree.levelOrder(tree.rootNode);
        tree.IterativePrePostInOrder(tree.rootNode);

    }
}