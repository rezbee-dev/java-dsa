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