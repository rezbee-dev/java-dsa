# Java DSA

Notes from _Data Structures and Algorithms in Java 6th Edition_

## Misc
- How to compile directory and output to folder: `javac -d ./out/ ./src/ch3/*.java`
- How to run program in output directory: `java -cp .\out\ src.ch3.InsertionSort`

## CH 3 - Fundamental Data Structures

**GameEntry & ScoreBoard Classes**
- Shows basic add and remove array operations
- For remove operations, elements are shifted upwards

**Insertion-Sort Algorithm**
- CH 3.1.2
- It works by comparing each element with the previous elements and then moving the element to its correct position by shifting the larger elements to the right
  - Ex: if element is smaller than 1st prev element, then swap. 
    - Continue comparing and swapping until reached first element
- Ordered, low to high
- Nested for loop, so O(n^2)
  - best performance is O(n), if already sorted
- AKA in-place comparison sorting algorithm

**Singly Linked List**

Characteristics:
- Collection of nodes that collectively form a linear sequence
- No fixed size

Components:
- Node:
  - Contains object reference of an element of the sequence
  - Contains objects reference to the next node of the list
- Head:
  - Object reference to the first node of the list
- Tail:
  - Reference to the last node of the list
  - Distinguished from other nodes by having a null reference for its next node
- Size:
  - total count of number of nodes in the sequence
- Traversal
  - where you move from one node to the next, but following the node's `next` reference

Pseudocode:
- addFirst
  - Replaces head with new head
    - Inserts at beginning of the list
  - If list is empty, then `head`'s next pointer would be null
  ```
  addFirst(e):
    newHead = Node(e)
    newHead.next(currentHead) // reference current head as next
    currentHead = newHead     // override Head
    size++ 
  ```
- addLast
  - Replaces tail with new tail
    - Inserts at end of list
  - If list is empty, the following code wouldn't work (`currentTail.next = newTail`)
  ```
  addLast(e):
    newTail = Node(e)
    newTail.next = null
    currentTail.next = newTail
    currentTail = newTail
    size++
  ```

- removeFirst
  - Removal of an element at the head

  ```
  removeFirst():
    if currHead == null
      return // list is empty
    currHead = currHead.next
    size--
  ```

- removeLast
  - requires pointer to `prev` on node for easy removal (see doubly linkedlist)
    - otherwise, requires traversing from head to tail

**Doubly Linked List**

Components:
- Header & Trailer Sentinels
  - AKA dummy nodes, guards, etc
  - Specials nodes located at both ends of the list
  - Header = beginning of list, Trailer = ending of list
  - Does not store elements
  - Takes up extra space, but greatly simplifies logic of operations (especially for edge cases)
  - All insertions/ Deletions occur between sentinels

## CH 6 - Stacks, Queues, And Deques

### Stack
- Last-in, Last Out (LIFO)
- Can only access most recently inserted object (top of stack)

Code examples using Stack
- Reverse an array

  ```java
  // Push arrays into stack, then pop off the elements
  public static<E> void reverse(E[] a){
    Stack<E> buffer = new StackArray<>(a.length);

    for (int i=0; i<a.length; i++>) buffer.push(a[i]);
    for (int i=0; i<a.length; i++>) a[i] = buffer.pop();
  }
  ```

- Matching delimiters

```java
// Take string input
// When delimiter opening symbol is encountered, push to stack
// When delimiter closing symbol is encountered, pop off stack
// If end of string and stack is empty, then string had matching delimiters
// O(n)

public static boolean isMatched(String expression) {
  final String opening = "({["; // opening delimiters
  final String closing = "]})"; // closing delimiters
  Stack<Character> buffer = new StackSinglyLinkedList<>();
  for (char c: expression.toCharArray()){
    if(opening.indexOf(c) != -1) buffer.push(c); //opening delimter matched
    else if(closing.indexOf(c) != -1){ // closing delimiter matched
      if(buffer.isEmpty()) return false;
      // mismatched delimiter
      // matching via position of the characters in opening and closing
      if(closing.indexOf(c) != opening.indexOf(buffer.pop())) return false; 
    }
  }
  return buffer.isEmpty();
}
```

```java
// Tests if every opening tag has a matching closing tag, in a HTML string
public static boolean isHTMLMatched(String html){
  Stack<String> buffer = new StackSingledLinkedList<>();
  int j = html.indexOf('<'); // find first '<' if any

  while(j != -1) {
    // indexOf(int ch, int fromIndex) - starting from index, so after index of j
    int k = html.indexOf('>', j+1);
    if (k == -1) return false; // invalid html tag

    String tag = html.substring(j+1, k); // extract contents inside <>

    if(!tag.startsWith("/")) // opening tag
      buffer.push(tag);
    else { // closing tag
      if(buffer.isEmpty()) return false; // no opening tag present
      if(!tag.substring(1).equals(buffer.pop())) return false; // different, mismatched tag
    }
    j = html.indexOf('<', k+1); // find next '<' if any
  }
  return buffer.isEmpty();
}
```

### Queues
- FIFO - first in, first out
- Oldest elements removed first
- Elements enter at the back and are removed from front
  - AKA lines at register (enter from back, and those at front removed first)

Array-based Implementation Considerations
- Loops based operations
  - Enqueue : Elements can be stored in an array, such that first element is at index 0, second at index 1, etc
  - Dequeue Operation: remove element stored at index 0 and execute a loop to shift all other elements of the queue oe cell to the left (towards index 0, to replace the removal)
    - Results in O(n) runtime
- No loops
  - Enqueue: insert element and use variable `frontIndex` to represent index of element at front of queue
  - Dequeue: Replace removed element with null and avoid shifting elements 
  - Issue is that as you remove and shift `frontIndex`, you may reach the end of the array, even when there are fewer than N elements in the queue
- Circular based operations
  - `frontIndex` is shifted right towards end of array with each dequeue operation
  - wraps around the end of the array via modulo operation
  - Operation: `frontIndex = (frontIndex + 1) % N`
  - If `N = 10`, and `frontIndex = 9`, then the next dequeue operation will be `(9+1) % 10`, resulting in the `frontIndex` to wrap around to index 0
  - For Enqueue operations, you need to add element to the "end" of the array
    - Since we are using circular array, to get to the end, you add `frontIndex` and `size` (total number of elements) and modulo by N to get the index of the end of the queue

Note: skipped Circular Queue
Note: skipped Josephus problem exercise

### Dequeue

- AKA double-ended queue
- Supports insertion and deletion at both the front and back of the queue

Circular Array Implementation
- Where head and size are stored as fields
  - tail is calculated via modular arithmetic
- Negative values with modulo operator
  - When element inserted at front, head index is decremeted circularly
  - Usually, modulo operation looks like this: `head = (head - 1) % size`
  - In order to avoid negative values, modulo operation should look like: `f = (f-1+size) % size`

Doubly Linked List Implementation
- DoublyLinkedList already imlpements all Deque oeprations

Running times
- O(1) runtime
- O(N) spacetime for array based, and O(n) spacetime for doubly linked list, where n < N

## CH 7 - List and Iterators

- Stack, queue, deque, and linked lists data structures don't offer straightforward way to do arbitrary element access without having to traverse through the data structure

### Positional Lists
- Numerical indexes are not the best choice to represent the concept of "position" of an element
  - ex: index of an entry changes over time due to insertions and deletions, changing the index of the element
  - ex: numerical position of element in an linked list is not useful, as you still need to traverse the list to get to it
- Need an abstraction to be able to provide a way to refer to elements anywhere in the sequence and to perform arbitrary insertions and deletions
  - this abstraction would denote the position of an element without it changing even if the index of the element changes
- The Positional List ADT is like a java list, except it does not use index-based methods
  - its methods return element's _position_ rather than elements themselves
  - purpose of returning position is that it allows you to use it to further traverse the list
  - Uses doublylinkedlist for implementation
  - index-based array implementation is possible
    - Problem is that index changes when elements are inserted or deleted 
    - instead of storing elements directly into the underlying array, store a "position" object containing the element as well as the current index of the element in the list (Ex: `(0, BWI)`)
    - Allows you to determine the index associated with a position, and position associated with index
    - When insertions or deletions occur, can loop through the array to update the index variable
    - Runtime will be O(n) for insertions and removals, and O(1) for all other operations

### Iterators

Iterator
- Software design pattern that abstracts the process of scanning through a sequence of elements, one element at a time
- independent from storage mechanism
- typically features the following operations
  - hasNext()
  - next()
  - remove()
- a single iterator instance supports only one pass through a collection, and there is no way to reset the iterator back to the beginning of the sequence
  - to allow repeated iterations, data structure should support a method that returns a new iterator each time its invoked
  - in java, it allows the for-each loops to work
    - for-each is shorthand for `while(i.hasNext()){...}`

snapshot iterator
- maintains its own private copy of the sequence of elements, which is constructed at the time the iterator object is created
- is therefore unaffected by any subsequent changes to the primary collection that may occur after snapshot iterator is created
- O(N) runtime and O(n) spacetime upon construction

lazy iterator
- does not make a copy of the elements, and performs piecewise traversal of the primary structure only when `next()` is invoked
- O(1) for runtime and space when constructed
- is affected by changes to the primary structure before iteration is completed
  - can include "fail-fast" behavior that immediately invalidates the iterator if underlying collection is modified unexpectedly


### Java Collection Framework

Collection Interface
- Root interface for many java data structures that represents a collection of elements
- Includes general methods such as `size(), isEmpty(), iterator()`

Concurrency
- Data structure with concurrency support that allow multiple processes to share use of a data structure in a thread-safe manner

Blocking
- data structures that are blocking have operations where it will wait until some other operation completes, either to free up space or insert an element

Java List Iterators
- Java LinkedList class does not expose `position` like our `LinkedList` class does
- Instead, it uses a `ListIterator` that is returned by LinkedList's `listIterator()` method
- This iterator uses a list cursor  and provides methods for traversing list and getting index and modifying content
- To avoid issues, iterators have "fail-fast" feature that invalidates iterator if underlying collection is modified unexpectedly
  - there can be many iterators traversing through a list, but if one of them modifies the list, then all other iterators become invalid
  - iterators can also become invalid if the list modifies itself

Sorting a Positional List

```java
public static void insertSort(PositionalLinkedList<Integer> list){
  // last position known to be sorted
  Position<Integer> marker = list.first();

  while(marker != list.last()){
    Position<Integer> pivot = list.after(marker);
    // element to be sorted
    int value = pivot.getElement();
    if (value > marker.getElement())
      marker = pivot;
    else {
      // to find leftmost item greater than value
      Position<Integer> walk = marker;
      while (walk != list.first() && list.before(walk).getElement() > value){
        walk = list.before(walk);
      }
      list.remove(pivot);
      list.addBefore(walk, value);
    }
  }
}
```

### Case Study: Maintaining Access Frequencies
- Purpose: create a datastructure that maintains a collection of elements while keeping track of the number of times each element is accessed
  - To know which elements are among the most popular
- Example: User's most accessed pages; Playlist of most frequently played songs
- ADT
  - `access(e)`: Accesses element and adds to favorites list if not already present, and increments access count
  - `remove(e)` removes element from favorites list if present
  - `getFavorites(k)`: returns an iterable collection of the `k` most accessed elements


## CH 8 - Trees

Tree
- ADT that stores elements hierarchically
- Except for top element (root), each element has a parent element and 0 or more children elements

Leaf nodes
- aka External nodes
- nodes that have no children

Edge
- Pair of nodes such that one node is the parent of the other

Path
- Sequence of edges

Ordered
- where there is a meaningful linear order among the children of each node, such that children nodes are ordered from left to right

Depth
- assuming `p` is a position within Tree `T`, depth of `p` is number of ancestors (parents) of `p`, other than `p` itself
- depth of tree root would be 0 (because it has no parents)
- Recursive definition
  - If p is root, then depth of p is 0
  - otherwise, the depth of p is 1 + depth of parent of p

Height
- Height of tree equal to the maximum depth of its leaf elements, or 0 if tree is empty
  - don't count the root

Level
- all nodes of tree T at the same depth d

### Binary Trees

Binary Tree
- Ordered tree
- Properties
  - Every node has at most two children
  - Each child node is labeled as being either a left child or right child
  - Left child precedes a right child in the order of children of a node

Subtree
- left and right subtrees are rooted at a left or right child of an internal node

Proper Binary Trees
- aka full binary trees
- Where each node has either zero or two children
- every internal node would have to have exactly two children

Improper Binary Tree
- tree that is not proper

Binary Tree Use Cases
- Can represent complex arithmetic expressions
- decision trees with yes or no answers

Binary Tree Levels
- level 0 has 1 node, level 1 has 2 nodes, level 2 has 4 nodes, level 3 has 8 nodes, and so on
- level d has at most 2^d nodes

### Implementing Trees

**Linked Structure Implementation for Binary Tree**

Node
- Would contain reference for element stored at a position `p` to the nodes associated  with the children and parent of p
- if p is root, then parent of p would be null
- if p does not have left or right child, that field would be null

Fields
- reference to root
- counter for number of nodes in tree

**Linked Structure Implementation for General Tree**
- Unlike binary tree, there is no limit on the number of children for node in a general tree
- thusly, the node for linked implementation of a tree consists of parent, element, and children container, where container contains variable references to children
- still O(1) runtime except for calculating height

**Array Based Representation of Binary Tree**

Level numbering Formula
- Can use function for numbering the positions of tree
  - this function can in turn be used to calculate array index
- function produces numbering where numbers increase from left to right, top to bottom
- the numbering applies to the potential positions of the tree, not the actual shape
  - so nodes with missing children will result in skips in numbering
- f(p) (position of p)
  - if p is root, then f(p) = 0
  - if p is left child of q, then f(p) = 2f(q) + 1
  - if p is right child of q, then f(p) = 2f(q) + 2
- the book does not cover implementing array based adt for binary trees
- may result in high-space requirements as there may be empty cells that nevertheless has to be defined due to the formula
- update operations are not efficient, and may take O(n) (ex: removal is O(n) since you shift all the descendants)

### Tree Traversal Algorithms

Traversal (of tree T)
- systematic way accessing/ visiting all positions of T
- O(n) runtime

Preorder Traversals
- where root of T is visited first, and then subtrees of root's children are traversed recursively
- if tree is ordered, then subtrees are traversed according to order of children
  - from left, towards right and bottom

Postorder Traversals
- Opposite of preorder traversal
- recursively traverses subtrees of root children first, and then visits root last

Breadth-First Tree
- where all nodes are visited at depth d, before moving on to depth d+1
- Ex of application use: game trees
  - represents the possible chocies of moves that might be made by player or computer during game
- O(n)
- Requires use of FIFO

Inorder traversal
- where nodes are visited from left to right
  - where node is visited after all nodes in its left and right subtrees are visited
- only applies to binary search trees

Binary Search Trees (BST)
- Where nodes are stored in such a way that parent's left child is smaller than itself, and parent's right child is greater than itself
- See CH11 for more

_skipped applications of Tree traversal algos__

## CH 9 - Priority Queues

### Priority Queue ADT

- FIFO is not always sufficient

Priority Queue
- collection of prioritized elements that allows 
  - arbitrary element insertion
  - removal of element that has first priority
- when element is added, elements are assigned a key that denotes priority
  - lower the value, the higher the priority (for removal)
  - ex: element of key value 1 will be removed first before element of key value 2

Priority Queue ADT Operations
- insert(k,v)
  - creates an entry with key k and value v in priority queue (pq)
- min()
  - returns (but not removes) PQ entry (k,v) that has least key
  - returns null if empty
- removeMin()
  - same as min(), but actually removes it
- size()
- isEmpty()