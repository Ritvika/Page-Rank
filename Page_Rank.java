import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Page_Rank {
	public static void main(String[] args) throws Exception {			
	        Job job = new Job();
	        job.setJarByClass(Page_Rank.class);    
	        job.setJobName("Page_Rank");
	        
	        FileInputFormat.addInputPath(job, new Path(args[0]));
	        FileOutputFormat.setOutputPath(job, new Path(args[1]));
	       	       
	        job.setMapperClass(Page_Rank_Mapper.class);
	        job.setReducerClass(Page_Rank_Reducer.class);

	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(Text.class);
	            
	        System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
	        
		       
