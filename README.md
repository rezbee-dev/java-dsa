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