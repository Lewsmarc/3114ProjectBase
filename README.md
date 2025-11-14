#Purpose: This is a sparse matrix/orthogonal list which can track movie reviews by both the reviewer and movie(indexed by ints not strings in the backend)

#Functionality:
Storing a SLL of header nodes which store nodes for a DLL structure to keep moview reviwers
Search- simple
Deletion- Access by row first to node, then update the non null pointers
Print to console that can be ordered by both reviewer and movie which gives movie, reviewer and score
Smilarity which is based on the closest average of another reveiwer's or movie's other scores

#Phase 0: Doubly Linked List implementation- open source available
#Phase 1: Insert and search completed
-3 or 4 days
#Phase 2: Delete, print, similiarity functionality
-5 days
#Phase 3: Debugging, likely one refactor, and junit tests + mutation coverage
-5 days

Sparse matrix: Stores a 2d matrix by using two different types of nodes with the axis being a SLL structure and the internal storage being doubly linked. 

#Design consideration:
 Insertion: The axis will point to the same nodes. Creating a helper function to handle insertion into the doubly linked list, first access will done on columns. Order both lists are ordered, which is handled directly in insert.
 Search- Composition of a simple linear search in a linked list twice to first find the correct column and then the correct row