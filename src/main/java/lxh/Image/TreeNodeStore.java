package lxh.Image;

import org.apache.hadoop.util.hash.Hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lixuhang
 * @date 2022/3/26
 * @whatItFor
 */
public class TreeNodeStore {

    private static HashMap<Integer, ArrayList<TranFileTreeNode>> treeStore = new HashMap<>();

    public static HashMap<Integer, ArrayList<TranFileTreeNode>> getTreeStore() {
        return treeStore;
    }

    public static void setTreeStore(HashMap<Integer, ArrayList<TranFileTreeNode>> treeStore) {
        TreeNodeStore.treeStore = treeStore;
    }
}
