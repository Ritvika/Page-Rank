# Page-Rank

PageRank is an algorithm used by the Google web search engine to rank websites in their search engine results. 
It is named after Larry Page, although many people think it means "rank of pages". 

Basically, we need to distribute the page's own PR value to all of the linked pages iteratively, and finally get a stable state, 
which presents the theoretical PR values of all pages. As described on the wiki, we can transfer PR/outlinks of a page to all 
linked pages, and also add a damping factor. We will ignore the damping factor and focus on the formula:
PR(A) = PR(B)/2 + PR(C) + PR(D)/3

The task is to implement a simplified PageRank with MapReduce.

We will assume the following input file:
A C F 0.166667
B D E F 0.166667
C A B 0.166667
D A B C E F 0.166667
E F 0.166667
F B C 0.166667

The first line, for example, is interpreted as follows:
“A” means "Page A".
"C F" means "Page A" has outlinks to "Page C" and "Page F".
"0.166667" is the initial PR value of Page A. 

The output file should look like this, where PR is the pagerank value computed by your program:
A C F PR 
B D E F PR 
C A B PR 
D A B C E F PR 
E F PR 
F B C PR 

The Map jobs should output key-values as follows:
outlink target: source page, PR/number of outlinks

For the first line in the input, the Map job will output this:
key=C, value=A, PR/2
key=F, value=A, PR/2
key=A, value=C F

Therefore, the Reducer step will see the data formatted as follows coming in from the Map step for C:
key=C, value=A, PR1
key=C, value=F, PR2
key=C, value=D, PR3
key=C, value=A B

Also, we will output the original outlinks information. For the first line it would be:
A: C F

Finally, the Reducer should be able to compute the PR value of C, for example, by just summing each PR value. 

The final output will be formatted just like the input file (the input to the map phase). 
For example, one of your output lines will be formatted like this:      
A C F 0.123456

