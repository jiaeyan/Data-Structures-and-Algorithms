from collections import deque


class Node:

    def __init__(self, val=None, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


class BinarySearchTree:

    def __init__(self, val):
        self.root = Node(val)

    # a new node is always inserted as leaf
    # O(logN)
    def insert(self, val):
        self.insert_recursive(self.root, val)

    # pass a None object will lose the pointer, so check None first
    def insert_recursive(self, node, val):
        if node.val > val:
            if not node.left:
                node.left = Node(val)
            else:
                self.insert_recursive(node.left, val)
        elif node.val < val:
            if not node.right:
                node.right = Node(val)
            else:
                self.insert_recursive(node.right, val)

    # O(logN)
    def search(self, val):
        return self.search_help(self.root, val)

    def search_help(self, node, val):
        if not node or node.val == val:
            return node
        elif node.val > val:
            return self.search_help(node.left, val)
        else:
            return self.search_help(node.right, val)

    def preorder_traversal(self):
        self.preorder_recursive(self.root)
        print()
        self.preorder_iterative(self.root)

    def preorder_recursive(self, root):
        if root:
            print(root.val)
            self.preorder_recursive(root.left)
            self.preorder_recursive(root.right)

    # always deal with left child first
    def preorder_iterative(self, root):
        if root:
            stack = [root]
            while stack:
                node = stack.pop()
                print(node.val)
                if node.right:
                    stack.append(node.right)
                if node.left:
                    stack.append(node.left)

    def inorder_traversal(self):
        self.inorder_help(self.root)

    def inorder_help(self, root):
        if root:
            self.inorder_help(root.left)
            print(root.val)
            self.inorder_help(root.right)

    def postorder_traversal(self):
        self.postorder_help(self.root)

    def postorder_help(self, root):
        if root:
            self.postorder_help(root.left)
            self.postorder_help(root.right)
            print(root.val)

    def levelorder_traversal(self):
        self.levelorder_iterative(self.root)

    # nodes from the same level are adjacent (order in queue is from left to right)
    def levelorder_iterative(self, root):
        if root:
            queue = deque([root])
            while queue:
                node = queue.popleft()
                print(node.val)
                if node.left:
                    queue.append(node.left)
                if node.right:
                    queue.append(node.right)

    def DFS(self):
        self.preorder_traversal()

    def BFS(self):
        self.levelorder_traversal()

    # height of a node is the length of the longest downward path to a leaf from that node
    # leaf nodes have height zero
    # empty nodes have height -1
    def get_height(self):
        return self.height_help(self.root)

    # for BST, two children, get height of each, choose the bigger one
    def height_help(self, root):
        if not root:
            return -1
        # height is the number of edges, so when one level up, + 1
        lefth = self.height_help(root.left) + 1
        righth = self.height_help(root.right) + 1
        return max(lefth, righth)

    # the diameter of a tree (sometimes called the width) is the number of nodes
    # on the longest path between two leaves in the tree, it can be the largest of these:
    # 1. the diameter of T’s left subtree
    # 2. the diameter of T’s right subtree
    # 3. the longest path between leaves that goes through the root of T
    # (this can be computed from the heights of the subtrees of T)
    def get_diameter(self):
        return self.diameter_help(self.root)

    def diameter_help(self, root):
        if not root:
            return

    # levelorder traversal, get the length of the level that has the most nodes
    def get_width(self):
        return self.width_help(self.root)

    def width_help(self, root):
        maxw = 0  # track the widest level length
        if root:
            queue = deque([root])
            while queue:
                width = len(queue)  # the width of current level
                maxw = max(maxw, width)
                while width:
                    # remove current level nodes, add all their children for next level
                    node = queue.popleft()
                    if node.left:
                        queue.append(node.left)
                    if node.right:
                        queue.append(node.right)
                    width -= 1
        return maxw




bst = BinarySearchTree(7)
bst.insert(4)
bst.insert(1)
bst.insert(9)
bst.insert(6)
bst.insert(3)
bst.insert(5)
bst.insert(2)
bst.insert(8)

bst.preorder_traversal()
print()
# bst.inorder_traversal()
# print()
# bst.postorder_traversal()
# print()
# bst.levelorder_traversal()

# print(bst.search(7))
print(bst.get_width())
print(bst.get_height())
