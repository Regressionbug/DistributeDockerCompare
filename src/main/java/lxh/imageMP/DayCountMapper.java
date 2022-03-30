package lxh.imageMP;


import lxh.Image.FileTreeNode;
import lxh.Image.NewFileTreeNode;
import lxh.Image.TranFileTreeNode;
import lxh.Image.TreeNodeUtils;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static lxh.Image.ImageUtils.analyseTarAndBuildTree;


public class DayCountMapper extends Mapper<LongWritable, Text, IntWritable, TranFileTreeNode> {

    private TranFileTreeNode v = new TranFileTreeNode();
    private IntWritable k = new IntWritable();

    private static int index=0;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //删除文件夹

        // 获取地址
        String str = value.toString();
//        context.write(new IntWritable(1),new TranFileTreeNode());
//        构建树以及树节点
        InputStream in = new BufferedInputStream(new FileInputStream(str));
        TarArchiveInputStream tarIn = new TarArchiveInputStream(in);
        ArchiveEntry tea = null;
        ArrayList<FileTreeNode> layerTrees = new ArrayList<>();
        while ((tea = tarIn.getNextEntry())!=null){
            if(tea.isDirectory()){
//                System.out.println(ze.getName()+" directory");
//                zin.skip(ze.getSize());
            }
            else {
//                if(ze.getName().equals("manifest.json")) {
//                    findManifestResult(zin);
//                }
                if(tea.getName().contains("layer.tar")){
                    layerTrees.add(analyseTarAndBuildTree(tarIn));
                }
            }
        }
        int treeId = TreeNodeUtils.getNowTreeId();
        FileTreeNode tempNode;
        //层序遍历节点,并构建新节点
        for(FileTreeNode node : layerTrees){
            LinkedList<FileTreeNode> list = new LinkedList<>();
            list.add(node);
            while(!list.isEmpty()){
                tempNode =  list.remove();
                if(tempNode.getDirctSubNodeNum()!=0){
                    list.addAll(tempNode.getSubNode());
                }
                TranFileTreeNode tranFileTreeNode = new TranFileTreeNode(tempNode);
                tranFileTreeNode.setTreeId(treeId);
                int blockId = TreeNodeUtils.genBlockId(tempNode.getSubNodeNum(),tempNode.getLevel(),tempNode.getDirctSubNodeNum());
                context.write(new IntWritable(blockId),tranFileTreeNode);
            }
        }
        //获取对象信息
//        v.setNodeId(TreeNodeUtils.getTheTreeNodeId());
//        k.set(1);
        // 切割字段
//        String[] fields = line.trim().split(",");
        // 封装对象
        //date,province_code,province,city_code,city,confirmed,suspected,cured,dead
        //20191201,420000,湖北省,420100,武汉市,1,0,0,0
//        int day = Integer.parseInt(fields[0]);
//        int sum = Integer.parseInt(fields[5]);
//        int cured = Integer.parseInt(fields[7]);
//        int dead = Integer.parseInt(fields[8]);

//        k.set(day);
//        v.setDay(day);
//        v.setSum(sum);
//        v.setCured(cured);
//        v.setDead(dead);

//        context.write(k,v);
//        v.setNodeId(TreeNodeUtils.getTheTreeNodeId());
//        context.write(k,v);
    }
}
