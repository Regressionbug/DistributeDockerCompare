package lxh.Image;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Lixuhang
 * @date 2022/3/21
 * @whatItFor
 */
public class TreeNodeUtils {


    //用来记录树的标号
    private static int nowTreeId = 0;
    private static int groupId = 0;
    private static ThreadLocal<Integer> nowNodeId = new ThreadLocal<>();

    private static int theTreeNodeId;

    public static int getTheTreeNodeId() {
        return ++theTreeNodeId;
    }
    public static void cleanTreeNodeId(){
        theTreeNodeId = 0;
    }

    public static int getNowNodeId(){
        int result = nowNodeId.get();
        nowNodeId.set(result+1);
        return result;
    }

    public static void initNowNodeId(){
        nowNodeId.set(0);
    }



    public synchronized static int getGroupId() {
        groupId++;
        return groupId;
    }

    //返回值表示传递是否是有效的转换值，只有是有效时才进行转换
    public static boolean tranformTreeNode(TranFileTreeNode node){
        String tranString = node.getTranString();
        String afterString = transFrom(tranString);
        node.setTranString(afterString);

        if(afterString.charAt(0)=='-'){
            return false;
        }
        String[] strings = afterString.split("|");
        ArrayList<Integer> list = new ArrayList<>();
        for(String str : strings){
            if(StringUtils.isNumeric(str)) {
                int a = Integer.parseInt(str);
                list.add(a);
            }
        }
        int index = 0;
        int[] subNodes = new int[list.size()];
        for(Integer b : list){
            subNodes[index++]=b;
        }
        node.setDirectSubNodeId(subNodes);
        return true;
    }




    public static int getNowTreeId() {
        nowTreeId++;
        return nowTreeId;
    }

    public static void incTreeStructNum(LinkedList<FileTreeNode> treeStruct){
        for(FileTreeNode node : treeStruct){
            node.incSubTreeHigh();
        }
    }

    public static void adjustFileNode(FileTreeNode node){
        ArrayList<FileTreeNode> subNode = node.getSubNode();

        Collections.sort(subNode, new Comparator<FileTreeNode>() {
            @Override
            public int compare(FileTreeNode o1, FileTreeNode o2) {
                //数量相对于深度更加有辨识度，所以作为判断的第一标准
                int subNum1 = o1.getSubNodeNum();
                int subNum2 = o2.getSubNodeNum();
                if(subNum1 != subNum2){
                    return subNum1-subNum2;
                }
                int subHigh1 = o1.getSubTreeHigh();
                int subHigt2 = o2.getSubTreeHigh();
                if(subHigh1 != subHigt2){
                    return subHigh1-subHigt2;
                }
                String str1 = o1.getNodeName();
                String str2 = o2.getNodeName();
                if(str1.length()!=str2.length()){
                    return str1.length()-str2.length();
                }
                for(int i = 0 ; i < str1.length() ; i++){
                    char char1 = str1.charAt(i);
                    char char2 = str2.charAt(i);
                    if(char1 != char2){
                        return char1-char2;
                    }
                }
                return 0;
            }
        });
    }

    public static void incTreeStructSubNodeNum(LinkedList<FileTreeNode> treeStruct){
        for(FileTreeNode node : treeStruct){
            node.incSubTreeNum();
        }
    }

    public static int genBlockId(int subNum,int level,int dirSubNum ){
        //下面算式基于认为层数不超过100，直接子节点不超过100
        return 100*100*subNum+100*level+dirSubNum;
    }

    public static void findSameSubNode(ArrayList<ArrayList<FileTreeNode>> targetList, ArrayList<ArrayList<ArrayList<FileTreeNode>>> result
                                          ,int index, int length){
        HashMap<String,ArrayList<ArrayList<FileTreeNode>>> compareResult = new HashMap<>();

        for(ArrayList<FileTreeNode> list: targetList){
            FileTreeNode node = list.get(index);
            if(node.getDirctSubNodeNum() == 0){
                //单节点（没有子节点）情况
                ArrayList<ArrayList<FileTreeNode>> templist = compareResult.getOrDefault(node.getNodeName(),new ArrayList<ArrayList<FileTreeNode>>());
                templist.add(list);
                compareResult.put(node.getNodeName(),templist);
            }
            else {
                //非单节点
                if(node.isInGroup()){
                    String theKey = node.getGroupId()+node.getNodeName();
                    ArrayList<ArrayList<FileTreeNode>> templist = compareResult.getOrDefault(theKey,new ArrayList<ArrayList<FileTreeNode>>());
                    templist.add(list);
                    compareResult.put(theKey,templist);
                }
            }
            for(ArrayList<ArrayList<FileTreeNode>> resultList : compareResult.values()){
                if(resultList.size() >= 2){
                    if(index==length-1){
                        result.add(resultList);
                    }
                    else{
                        findSameSubNode(resultList,result,index+1,length);
                    }
                }
            }
        }

    }

    //发现在传递中，其会自动在字符串中的字符周围加入0字符，需要去除得到原字符
    public static String transFrom(String str){
        int length = str.length();
        int index = 1;
        StringBuilder stringBuilder = new StringBuilder();
        while(index<length){
            stringBuilder.append(str.charAt(index));
            index+=2;
        }
        return stringBuilder.toString();
    }
}
