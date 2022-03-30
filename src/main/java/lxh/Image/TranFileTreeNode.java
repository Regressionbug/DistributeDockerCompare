package lxh.Image;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Lixuhang
 * @date 2022/3/25
 * @whatItFor
 */
public class TranFileTreeNode implements Writable {
    private int treeId;
    private int nodeId;
    private int fatherNodeId;
    //是否成组
    private boolean isGroup = false;
    private int groupId = 0;
    private String nodeName;
    //用于序列化时传递子节点数组
    private String tranString;
    private int[] directSubNodeId;
    private ArrayList<TranFileTreeNode> subList;



    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(treeId);
        dataOutput.writeInt(nodeId);
        dataOutput.writeInt(fatherNodeId);
        dataOutput.writeBoolean(isGroup);
        dataOutput.writeInt(groupId);
        dataOutput.writeChars(nodeName);
        dataOutput.writeChars(tranString);

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.treeId = dataInput.readInt();
        this.nodeId = dataInput.readInt();
        this.fatherNodeId = dataInput.readInt();
        this.isGroup = dataInput.readBoolean();
        this.groupId = dataInput.readInt();
        this.nodeName = dataInput.readLine();
        this.tranString = dataInput.readLine();

    }

    public TranFileTreeNode(){};
    public TranFileTreeNode(FileTreeNode node){
        this.nodeId = node.getNodeId();
        this.nodeName = node.getNodeName();
        if(node.getFatherNode()!=null){
            this.fatherNodeId = node.getFatherNode().getNodeId();
        }
        int length = node.getDirctSubNodeNum();
        int index = 0;
        StringBuilder builder = new StringBuilder();
        for(;index<length;index++){
            FileTreeNode node1 = node.getSubNode().get(index);
            builder.append(node1.getNodeId());
            if(index!=length-1){
                builder.append("|");
            }
        }
        this.tranString = builder.toString();
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int[] getDirectSubNodeId() {
        return directSubNodeId;
    }

    public void setDirectSubNodeId(int[] directSubNodeId) {
        this.directSubNodeId = directSubNodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTranString() {
        return tranString;
    }

    public void setTranString(String tranString) {
        this.tranString = tranString;
    }

    public int getFatherNodeId() {
        return fatherNodeId;
    }

    public void setFatherNodeId(int fatherNodeId) {
        this.fatherNodeId = fatherNodeId;
    }

    public ArrayList<TranFileTreeNode> getSubList() {
        return subList;
    }

    public void setSubList(ArrayList<TranFileTreeNode> subList) {
        this.subList = subList;
    }
}
