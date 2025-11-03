#Purpose: This is a sparse matrix/orthogonal list which can track movie reviews by both the reviewer and movie(indexed by ints not strings in the backend)

#Functionality:
Ordered insertion into the matrix and the underlying DLL
Search- simple
Deletion- simple
Print to console that can be ordered by both reviewer and movie which gives movie, reviewer and score
Smilarity which is based on the closest average of another reveiwer's or movie's other scores

#Phase 0: Doubly Linked List implementation- open source available
#Phase 1: Insert and search completed
-3 or 4 days
#Phase 2: Delete, print, smiliarity functionality

Sparse matrix: Stores a 2d matrix with minimal space overhead by only storing filled in data nodes. The headers for each axis of the matrix is stored as a DLL of header node for the internal DLL which contain reviewer, movie, and score data as ints.

#Design consideration:
 Insertion: The two header lists are going to be independent of each other and will not have sight of where the current point is. Creating a helper function to handle insertion in one row or column and having the main insert call both on the axises. Order must be maintained within both rows and columns but rows and columns cannot communicate to syncronize idividual nodes in their lists so that logic must be hanlded pre internal list access.
 Search- Composition of a simple linear search in a linked list twice to first find the correct column and then the correct row