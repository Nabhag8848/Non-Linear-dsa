import java.util.*;

public class GenericTree {

    public TreeNode root = null;

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

     private static class TreeNode {
        private final int value;
        private final ArrayList<TreeNode> children = new ArrayList<>();

        public TreeNode(int val) {
            this.value = val;
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};

        GenericTree tree = new GenericTree(arr);
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
    }
}
