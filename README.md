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