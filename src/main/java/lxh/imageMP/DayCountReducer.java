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
        while(iterator.hasNext()){
            TranFileTreeNode node = (TranFileTreeNode)iterator.next();
            TranFileTreeNode newNode = new TranFileTreeNode(node);
            list.add(newNode);
        }

        ArrayList<ArrayList<TranFileTreeNode>> totalList = new ArrayList<>();


        int index = 0, size = 0;
        //将传递过来的节点进行转换（转换子节点和获取相应的节点）
        for (TranFileTreeNode node : list) {
            //但传递的是有有效值才进行转换
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
                node.setSubList(subNodeList);
            }
        }
        //底下进行比较,并将比较后的结果加入store树当中


    }
}

