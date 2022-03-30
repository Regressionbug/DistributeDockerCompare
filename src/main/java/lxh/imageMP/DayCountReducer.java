package lxh.imageMP;


import lxh.Image.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.hash.Hash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DayCountReducer extends Reducer<IntWritable, NewFileTreeNode, IntWritable, NewFileTreeNode> {
    @Override
    protected void reduce(IntWritable key, Iterable<NewFileTreeNode> values, Context context) throws IOException, InterruptedException {
        ArrayList<ArrayList<TranFileTreeNode>> totalList = new ArrayList<>();
//        int index = 0, size = 0;
//        //将传递过来的节点进行转换（转换子节点和获取相应的节点）
//        for(TranFileTreeNode node : values){
//            TreeNodeUtils.tranformTreeNode(node);
//            int[] subNodes = node.getDirectSubNodeId();
//            ArrayList<TranFileTreeNode> subNodeList = new ArrayList<>();
//            int treeId = node.getTreeId();
//            //从存储树中获取相应的树节点
//            HashMap<Integer,ArrayList<TranFileTreeNode>> treeStore = TreeNodeStore.getTreeStore();
//            ArrayList<TranFileTreeNode> theTree = treeStore.get(treeId);
//            for(Integer a : subNodes) {
//                //获取对应treeId下标的元素
//                subNodeList.add(theTree.get(a));
//            }
//            node.setSubList(subNodeList);

        }





    }

