package lxh.imageMP;


import lxh.Image.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.hash.Hash;
import sun.java2d.loops.TransformBlit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DayCountReducer extends Reducer<IntWritable, TranFileTreeNode, IntWritable, TranFileTreeNode> {
    @Override
    protected void reduce(IntWritable key, Iterable<TranFileTreeNode> values, Context context) throws IOException, InterruptedException {
        ArrayList<TranFileTreeNode> list = new ArrayList<>();
        Iterator iterator = values.iterator();
        TranFileTreeNode node1 = null;
        boolean isLeaf = false;
        int subListLength = 0;
        while(iterator.hasNext()){
            TranFileTreeNode node = (TranFileTreeNode)iterator.next();
            TranFileTreeNode newNode = new TranFileTreeNode(node);
            list.add(newNode);
        }
        //target用来装所有的子节点list
        ArrayList<ArrayList<TranFileTreeNode>> target = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<TranFileTreeNode>>> result = new ArrayList<>();
        //将传递过来的节点进行转换（转换子节点和获取相应的节点）

        for (TranFileTreeNode node : list) {
            //写入store树节点
            TreeNodeStore.putTreeNode(node);

            //但传递的是有有效值才进行转换,就是说是有子节点的
            if(TreeNodeUtils.tranformTreeNode(node)) {
                int[] subNodes = node.getDirectSubNodeId();
                ArrayList<TranFileTreeNode> subNodeList = new ArrayList<>();
                int treeId = node.getTreeId();
                //从存储树中获取相应的树节点
                HashMap<Integer, ArrayList<TranFileTreeNode>> treeStore = TreeNodeStore.getTreeStore();
                ArrayList<TranFileTreeNode> theTree = treeStore.get(treeId);
                for (Integer a : subNodes) {
                    //获取对应treeId下标的元素
                    subNodeList.add(theTree.get(a));
                }
                //子节点数量都是一样的，这是由key来决定的
                subListLength = subNodeList.size();
                node.setSubList(subNodeList);
                target.add(subNodeList);
            }
            else {
                isLeaf = true;
            }
        }
        if(isLeaf){
            //由于算法是按照子节点的方式来写的，所以如果是叶子节点，则需要进行转换
            for(TranFileTreeNode node : list){
                ArrayList<TranFileTreeNode> tempList = new ArrayList<>();
                tempList.add(node);
                target.add(tempList);
                TreeNodeUtils.findSameSubNode(target,result,0,1);
            }
           for(ArrayList<ArrayList<TranFileTreeNode>> group : result){
               int groupId = TreeNodeUtils.getGroupId();
               for(ArrayList<TranFileTreeNode> nodeList : group){
                   //由于是叶子节点，所以不需要获取父节点,且只有一个节点
                   TranFileTreeNode node = nodeList.get(0);
                   node.setGroup(true);
                   node.setGroupId(groupId);
               }
           }
        }
        else{
            //非叶子节点，直接使用之前的target就行了
            TreeNodeUtils.findSameSubNode(target,result,0,subListLength);
            for(ArrayList<ArrayList<TranFileTreeNode>> group : result){
                int groupId = TreeNodeUtils.getGroupId();
                for(ArrayList<TranFileTreeNode> nodeList : group){
                    //非叶子节点，需要获取结果中的父节点，直接拿第一个节点即可
                    TranFileTreeNode node = nodeList.get(0);
                    int treeId = node.getTreeId();
                    int fatherNodeId = node.getFatherNodeId();
                    //这里需要获取父节点，所以不如把写入store树中
                    HashMap<Integer, ArrayList<TranFileTreeNode>> treeStore = TreeNodeStore.getTreeStore();
                    ArrayList<TranFileTreeNode> theTree = treeStore.get(treeId);
                    TranFileTreeNode fatherNode = theTree.get(fatherNodeId);
                    fatherNode.setGroup(true);
                    fatherNode.setGroupId(groupId);
                }
            }
        }









    }
}

