java-callgraph: Java Call Graph Utilities
=========================================

This is heavily based on Georgios Gousios's project for retrieving information to build a static call graph.

This program generates the following information from a JAR:

 - A list of every method
 	- A list of every method called from this method
 	- A list of all methods that call this method

It outputs this in the form of a text file that is easily parsed.

#### License

[2-clause BSD](http://www.opensource.org/licenses/bsd-license.php)
