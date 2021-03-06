package job;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class PatentCitationCount extends Configured implements Tool {

    public static class MapClass extends Mapper<Text, Text, Text, Text> {

        public void map(Text key, Text value,
                        Context context) throws IOException, InterruptedException {

            context.write(value, key);
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, IntWritable> {

        public void reduce(Text key, Iterator<Text> values,
                           Context context) throws IOException, InterruptedException {
            
            int count = 0;
            while (values.hasNext()) {
                values.next();
                count++;
            }
            context.write(key, new IntWritable(count));
        }
    }

    public int run(String[] args) throws Exception {
        Configuration conf = getConf();

        Job job = Job.getInstance(conf, "patent citation count");

        Path in = new Path(args[0]);
        Path out = new Path(args[1]);

        FileInputFormat.addInputPath(job, in);
        FileOutputFormat.setOutputPath(job, out);

        job.setJarByClass(PatentCitationCount.class);

        job.setJobName("PatentCitationCount");
        job.setMapperClass(MapClass.class);
        job.setReducerClass(Reduce.class);

        job.setInputFormatClass(KeyValueTextInputFormat.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.getConfiguration().set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ",");

        System.exit(job.waitForCompletion(true) ? 0 : 1);

        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new PatentCitationCount(), args);

        System.exit(res);
    }
}

