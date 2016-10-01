import java.io.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Page_Rank_Mapper extends Mapper<LongWritable, Text, Text, Text> {
	
	public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
	        
		    Text links = new Text();
		    Text page = new Text();
		    //Creating an array of lines from input file
			String str = value.toString();
			String[] lines = str.split(" ");
			int size = lines.length;
			String links_store = "";            //This empty string will store links without any PR values
			for(int i = 1; i < (size-1); i++)
			{
				/*Calculating PR value and converting it to String format*/
				//The current PR value will be at the end of the array.
				double temp1 = Double.parseDouble(lines[size-1]);
				//Count of the number of Outlinks (excluding PR value and Page itself)
				double temp2 = (size-2);
				//Calculating PR(Page with link)/(Number of Outlinks of that Page)
				double pr_temp = (temp1/temp2);
				//Covert Double value to String format
				String pr = Double.toString(pr_temp);
				//Order send to the Reducer will be "Outlink, Page, PR value of Page" 
				context.write(new Text(lines[i]), new Text(lines[0] + ", " + pr));
				links_store+= lines[i]+" "; //Store Page links
			}
			page.set(lines[0]);             
			links.set(links_store);         
			context.write(page, links);     //Send the page with all links to the Reducer
	}
}		