#!/bin/bash -e

# used to join columns between the year_month_day and hour_minute_sec files
paste $1 $2 | awk '{print $1,$2}' > $3

### Comparison
#AWK
#awk -F" *, *" 'NR==FNR{lines[$2]=$0} NR!=FNR{if(lines[$1])lines[$1]!=lines[$1] " , " $2} END{for(line in lines)print lines[line]}' $1 $2

#SED
##join -t, -11 -22 -o2.1,0,2.3,2.4,1.2 <(sed 's/ *, */,/g' file1.csv | sort -t,) <(sed 's/ *, */,/g' file2.csv | sort -t, -k2) | sed 's/,/ , /g' > output-file
#
## Remove whitespace around the `,`s, then sort using `,` to separate fields (-t,). 
## -k2 tells sort to use the second field.
#sed 's/ *, */,/g' t1 | sort -t, >temp-left
#sed 's/ *, */,/g' t2 | sort -t, -k2 >temp-right
#
## Join the files. -t, means break fields at ,, 
## -11 says use the first field in the first file,  -22 is the second field in the second file.
## -o... controls the output format, 2.1=second file, first field; 0 is the join field.
#join -t, -11 -22 -o2.1,0,2.3,2.4,1.2 temp-left temp-right > temp-joined
#
## Add whitespace back in around the ,s so it looks better.
#sed 's/,/ , /g' temp-joined >output-file
#
## Clean up temporary files
#rm temp-{left,right,joined}
