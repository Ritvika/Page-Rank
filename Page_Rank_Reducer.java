import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Page_Rank_Reducer extends Reducer<Text, Text, Text, Text> {
	
	public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
	
		Text pr_total = new Text();
		double pr = 0;
		String links = "";
		for (Text value : values) {		
			//Creating an array of String type from the input received from the Mapper
			String str = value.toString();
			String[] lines = str.split(", ");
			int size = lines.length;
			//The size of Value part received from the Mapper will be 2 (Format = Page, PR value of the Page).
			if (size == 1)
			{
				links=lines[0];
			}
			else if (size == 2)
			{
				pr+=Double.parseDouble(lines[1]);
			}
			String pr_val = Double.toString(pr);
			pr_total.set(links + " " + pr_val);
		}
		context.write(key, pr_total);
	}
}
		