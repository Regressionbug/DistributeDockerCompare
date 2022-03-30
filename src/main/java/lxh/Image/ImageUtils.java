package lxh.Image;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static lxh.Image.TreeNodeUtils.*;


/**
 * @author Lixuhang
 * @date 2022/3/21
 * @whatItFor
 */
public class ImageUtils {
    public static FileTreeNode analyseTarAndBuildTree(TarArchiveInputStream fileInputStream) throws IOException {
        TarArchiveInputStream inputStream = new TarArchiveInputStream(fileInputStream);
        ArchiveEntry tae = null;
        int tempLevel = 0;
        //由于是深度优先，所以这个treeStruct相当于从根节点到目前访问点的通路记录，对于整体的
        LinkedList<FileTreeNode> treeStruct = new LinkedList<>();
        FileTreeNode node = new FileTreeNode("layer1",0);
        treeStruct.add(node);
        while ((tae = inputStream.getNextEntry())!= null) {
            if(tae.isDirectory()){
                String dirStr = tae.getName();
                System.out.println(dirStr);
                FileTreeNode tempNode = new FileTreeNode();
                //通过除号的数量来标识级别
                getDirLevelAndName(tae.getName(),tempNode);
                tempLevel = tempNode.getLevel();
                //但是新的级别出现（就是大于当前treeStruct的数量）
                if(tempLevel > treeStruct.size()-1){
                    incTreeStructNum(treeStruct);
                }
                //一旦没有新的级别出现，就说明treeStruct有节点要出去了
                else {
                    int removeSize = treeStruct.size() - tempLevel;
                    for(int i = 0;i < removeSize;i++){
                        FileTreeNode removeNode = treeStruct.removeLast();
                        //调整每个节点的子节点的按照排序（之所以需要排序，是因为保证两个镜像是一致的）
                        adjustFileNode(removeNode);
                    }
                }
                //treeStruct暂时确定之后，就可以增加其中所有节点的子节点数量
                incTreeStructSubNodeNum(treeStruct);
                //调整完之后，将最新节点加入treeStruct
                treeStruct.add(tempNode);
                //设置父子节点的关系
                FileTreeNode fatherNode = treeStruct.get(tempLevel-1);
                tempNode.setFatherNode(fatherNode);
                ArrayList<FileTreeNode> subNode = fatherNode.getSubNode();
                subNode.add(tempNode);
            }
        }
        //对于剩下的文件夹进行调整
        if(!treeStruct.isEmpty()){
            for(FileTreeNode getNode : treeStruct){
                adjustFileNode(getNode);
            }
        }
        return node;
    }

    public static void getDirLevelAndName(String str,FileTreeNode node){
        int level = 0;
        String[] strLevels = str.split("/");
        if(strLevels.length == 0){
            return;
        }
        node.setLevel(strLevels.length);
        node.setNodeName(strLevels[strLevels.length-1]);
    }
}
