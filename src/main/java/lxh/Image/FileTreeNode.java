package lxh.Image;

import java.util.ArrayList;

/**
 * @author Lixuhang
 * @date 2021/12/18
 * @whatItFor
 */
public class
FileTreeNode {
    int nodeId;
    ArrayList<FileTreeNode> subNode = new ArrayList<>();
    String nodeName;
    int level;
    int subNodeNum = 0;
    FileTreeNode fatherNode;
    int treeId;
    int blockId;
    boolean inGroup = false;
    int groupId = 0;



    public boolean isInGroup() {
        return inGroup;
    }

    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }


    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }


    //用来记载所有树节点




    public int getDirctSubNodeNum() {
        setDirctSubNodeNum(subNode.size());
        return dirctSubNodeNum;
    }

    public void setDirctSubNodeNum(int dirctSubNodeNum) {
        this.dirctSubNodeNum = dirctSubNodeNum;
    }

    int dirctSubNodeNum = 0;

    public FileTreeNode getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(FileTreeNode fatherNode) {
        this.fatherNode = fatherNode;
    }

    int subTreeHigh = 1;


    public FileTreeNode() {}

    public FileTreeNode(int nodeId) {
        this.nodeId = nodeId;
    }

    public FileTreeNode(String nodeName, int level) {
        this.nodeName = nodeName;
        this.level = level;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public ArrayList<FileTreeNode> getSubNode() {
        return subNode;
    }

    public void setSubNode(ArrayList<FileTreeNode> subNode) {
        this.subNode = subNode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSubNodeNum() {
        return subNodeNum;
    }

    public void setSubNodeNum(int subNodeNum) {
        this.subNodeNum = subNodeNum;
    }

    public int getSubTreeHigh() {
        return subTreeHigh;
    }

    public void setSubTreeHigh(int subTreeHigh) {
        this.subTreeHigh = subTreeHigh;
    }

    public void incSubTreeNum(){
        subNodeNum++;
    }

    public void incSubTreeHigh(){ subTreeHigh++;}
}
