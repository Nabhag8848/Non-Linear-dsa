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

     private class TreeNode {
        private final int value;
        private final ArrayList<TreeNode> children = new ArrayList<>();

        public TreeNode(int val) {
            this.value = val;
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, -1, 30, 50, -1, 60, -1, -1, 40, -1, -1};
        GenericTree tree = new GenericTree(arr);
        tree.display(tree.root);
        System.out.println(tree.size(tree.root));
        System.out.println(tree.maxValueNode(tree.root));
    }
}
