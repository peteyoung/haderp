package job;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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

/**
 * Created by peteyoung on 2/12/15.
 * 
 * This is called MyJob instead of PatentCiters in Hadoop in Action 2nd Ed.
 */
public class PatentCiters extends Configured implements Tool {

    public static class MapClass extends Mapper<Text, Text, Text, Text> {

        public void map(Text key, Text value,
                        Context context) throws IOException, InterruptedException {

            context.write(value, key);
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterator<Text> values,
                           Context context) throws IOException, InterruptedException {

//            String csv = "";
//            while (values.hasNext()) {
//                if (csv.length() > 0) csv += ",";
//                csv += values.next().toString();
//            }
//            context.write(key, new Text(csv));

            int count = 0;
            while (values.hasNext()) {
                count += 1;
            }
            if (count > 1) {
                context.write(key, new Text(String.format("has %d citers", count)));
            }

//            context.write(key, new Text(values.toString()));
        }
    }

    public int run(String[] args) throws Exception {
        Configuration conf = getConf();

        Job job = Job.getInstance(conf, "patent");

        Path in = new Path(args[0]);
        Path out = new Path(args[1]);

        FileInputFormat.addInputPath(job, in);
        FileOutputFormat.setOutputPath(job, out);

        job.setJarByClass(PatentCiters.class);

        job.setJobName("PatentCiters");
        job.setMapperClass(MapClass.class);
        job.setReducerClass(Reduce.class);

        job.setInputFormatClass(KeyValueTextInputFormat.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //job.getConfiguration().set("key.value.separator.in.input.line", ",");
        job.getConfiguration().set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ",");

        System.exit(job.waitForCompletion(true) ? 0 : 1);

        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new PatentCiters(), args);

        System.exit(res);
    }
}

