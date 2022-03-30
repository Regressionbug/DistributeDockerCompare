package lxh.imageMP;


import lxh.Image.NewFileTreeNode;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DayCountRecordWriter extends RecordWriter<IntWritable, NewFileTreeNode> {

    private Connection conn;
    private PreparedStatement preparedStatement;

    public DayCountRecordWriter(TaskAttemptContext taskAttemptContext) {

    }

    @Override
    public void write(IntWritable intWritable, NewFileTreeNode dayCountBean) throws IOException, InterruptedException {
        File file = new File("D:\\Learn-Thing\\大学课程\\毕设\\程序\\newMapReduce\\src\\main\\resources\\result.txt");
        FileWriter fileWriter = new FileWriter(file,true);
        fileWriter.write(intWritable.toString()+"-"+dayCountBean.getNodeId()+"\n");
        fileWriter.close();
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {

    }
}
