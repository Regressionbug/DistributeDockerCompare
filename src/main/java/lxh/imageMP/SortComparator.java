package lxh.imageMP;

import org.apache.hadoop.io.IntWritable;

/**
 * @author Lixuhang
 * @date 2022/3/25
 * @whatItFor
 */
public class SortComparator extends IntWritable.Comparator {
    @Override
    public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
        return -super.compare(b1, s1, l1, b2, s2, l2);
    }
}
