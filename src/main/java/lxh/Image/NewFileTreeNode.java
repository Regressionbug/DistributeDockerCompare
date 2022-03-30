package lxh.Image;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Lixuhang
 * @date 2022/3/25
 * @whatItFor
 */
public class NewFileTreeNode implements Writable {

    private int nodeId;

    public NewFileTreeNode(){super();};
    public NewFileTreeNode(int nodeId) {
        super();
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return nodeId+"";
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(nodeId);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.nodeId=dataInput.readInt();
    }
}
