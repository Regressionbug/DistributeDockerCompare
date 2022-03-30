package lxh.imageMP;


import lxh.Image.NewFileTreeNode;
import lxh.Image.TranFileTreeNode;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

//方法
public class DayCountDriver {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, InterruptedException {

//        args = new String[]{"E:/hadoopTestFile/country.csv","E:/hadoopTestFile/output"};
        //执行前清空表
//        Connection conn = JDBCUtils.getConnection();
//        String sql = "TRUNCATE TABLE daycount";
//        PreparedStatement preparedStatement = conn.prepareStatement(sql);
//        preparedStatement.executeUpdate();
//        JDBCUtils.release(preparedStatement,conn);
        File dir = new File("D:\\Learn-Thing\\大学课程\\毕设\\环境\\测试\\output");
        if(dir.isDirectory()){
            System.out.println("isd");
        }
        File[] files = dir.listFiles();
        for(File file : files){
            file.delete();
        }
        dir.delete();
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(DayCountDriver.class);
        job.setMapperClass(DayCountMapper.class);
        job.setReducerClass(DayCountReducer.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(TranFileTreeNode.class);
        //不需要变化按照key排序，因为默认升序
//        job.setSortComparatorClass(SortComparator.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(TranFileTreeNode.class);

//        job.setOutputFormatClass(DayCountOutputFormat.class);


        FileInputFormat.setInputPaths(job,new Path("D:\\Learn-Thing\\大学课程\\毕设\\程序\\mapreduce\\src\\main\\resources\\a.txt"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\Learn-Thing\\大学课程\\毕设\\环境\\测试\\output"));

        boolean result = job.waitForCompletion(true);
    }
}
