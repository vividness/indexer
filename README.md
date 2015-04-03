Indexer
==
Indexer for CSV files. Faster than ElasticSearch although less flexible.

Usage
-- 
Indexes and searches the contents of a CSV file.

The first column in the input file is the ID field for each indexed document. 

 * indexer create [ index/dir ] from [ input.csv ]
 * indexer append [ index/dir ] from [ input.csv ]
 * indexer update [ index/dir ] from [ input.csv ]
 * indexer drop [ index/dir ]
 * indexer find [ all | n ] return [ "field1,field2 ..." ] from [ index/dir ] where [ "criteria [[AND | OR] criteria]"]
