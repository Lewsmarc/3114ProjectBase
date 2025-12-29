2 New Class Files, one for a BST and one for a KD tree. This code is meant to store location data for cities, by geopgrahic coordinate. Our BST insert equal values to the left and upon deletion of a parent node with two children the maximum value from the left is taken. The tree nodes will not keep pointers to the parent. Out BST starts at a root node, and keep larger children to the right child and smaller children to the left child, and the data will be of the basic int type or a string for the city name. Our KD tree will store 2 int that represent a geographic coordinate, in a unordered manner. The KD will also support range queries- returning possible cities within a certain distance of a point.

The City record is the main encapsulation for all the city data we will be tracking, so it will store together the name of a city and its location, the location is represented by a geogrpahic coordinate of 2 integer values. These are accessed by two trees, one the KD will access the records by the x,y coordinates and the BST will access the records by city name.

Research how to compare string data

Milestone 1: Done
Test first little edge cases, basic functionality
City records: create a record, erase a record, edit, support range queries, and access the x,y coordinates as one data structure, and city names. Validation for x,y coordinate ie two cities cannot share the same coordinate, but two cities can share the same name, no negative coordinates, no city name can be an empty string.
1-2 days expected time
Note: expect more to be added to city record as functionality needs increase
Milestone 2: Started, so far insert, and search, delete WIP
Write tests first to understand how insert, search, and delete will handle edge cases and to determine the complexity of the logic
BST insert, search, and delete- considerations for keeping the tree balanced, 
2-3 days for basic insert, search, delete functionality, maybe 2 extra days to write tree balancing logic
Milestone 3: 
Time for researching this new data structure. Write tests from the research to understand the traversal and position logic like the SparseMatrix
KD insert, search, and delete- considerations for keeping the tree balanced, and to do range queries. 
4-5 days for basic insert, search, delete functionality, maybe 3-4 extra days to write tree balancing logic, and range queries