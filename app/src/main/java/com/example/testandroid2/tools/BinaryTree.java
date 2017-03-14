package com.example.testandroid2.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：16/10/12 11:56
 */
public class BinaryTree {
    private int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    private static List<Node> nodeList=null;

    private static class Node{
        Node leftChild;
        Node rightChild;
        int data;

        Node(int data){
            leftChild=null;
            rightChild=null;
            this.data=data;
        }
    }

    public void createBinary(){
        nodeList=new ArrayList<>();
        // 将一个数组的值依次转换为Node节点
        for (int value:array) {
            nodeList.add(new Node(value));
        }

        for(int parentIndex=0;parentIndex<nodeList.size()/2-1;parentIndex++){
            nodeList.get(parentIndex).leftChild=nodeList.get(parentIndex*2+1);
            nodeList.get(parentIndex).rightChild=nodeList.get(parentIndex*2+2);
        }

        int lastIndext=nodeList.size()/2-1;
        nodeList.get(lastIndext).leftChild=nodeList.get(lastIndext*2+1);

        if(nodeList.size()%2==1){
            nodeList.get(lastIndext).rightChild=nodeList.get(lastIndext*2+2);
        }

    }

    /**
     * 先序遍历
     *
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node
     *            遍历的节点
     */
    public static void preOrderTraverse(Node node) {
        if (node == null)
            return;
        System.out.print(node.data + " ");
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }


    /**
     * 中序遍历
     *
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node
     *            遍历的节点
     */
    public static void inOrderTraverse(Node node) {
        if (node == null)
            return;
        inOrderTraverse(node.leftChild);
        System.out.print(node.data + " ");
        inOrderTraverse(node.rightChild);
    }

    /**
     * 后序遍历
     *
     * 这三种不同的遍历结构都是一样的，只是先后顺序不一样而已
     *
     * @param node
     *            遍历的节点
     */
    public static void postOrderTraverse(Node node) {
        if (node == null)
            return;
        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        System.out.print(node.data + " ");
    }


    public static void main(String[] args) {
        BinaryTree binaryTree=new BinaryTree();
        binaryTree.createBinary();

        Node root=nodeList.get(0);

        System.out.println("先序遍历：");
        preOrderTraverse(root);
        System.out.println();


        System.out.println("中序遍历：");
        inOrderTraverse(root);
        System.out.println();

        System.out.println("后序遍历：");
        postOrderTraverse(root);
    }
}
