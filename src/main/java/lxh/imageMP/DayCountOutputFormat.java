package lxh.imageMP;


import lxh.Image.NewFileTreeNode;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class DayCountOutputFormat extends FileOutputFormat<IntWritable, NewFileTreeNode> {
    @Override
    public RecordWriter<IntWritable, NewFileTreeNode> getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new DayCountRecordWriter(taskAttemptContext);
    }
}
