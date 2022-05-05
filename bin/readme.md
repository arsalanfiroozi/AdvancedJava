Q1

We have this hierarchy of collection in java:
![Hierarchy](img.png) 

List: The list maintains insertion order. The list provides get() method to get the element at a specified index. If you need to access the elements frequently by using the index then we can use the list.

Set: 
Duplicate item will be ignored in Set. Set do not maintain any insertion order. Set does not provide get method to get the elements at a specified index. If you want to create a collection of unique elements then we can use set

Map: 
 Represents a mapping between a key and a value. More specifically, a Java Map can store pairs of keys and values. Each key is linked to a specific value. Once stored in a Map, you can later look up the value using just the key.
 The map does not allow duplicate elements.
 The map also does not maintain any insertion order. The map does not  provide get method to get the elements at a specified index. If you want to store the data in the form of key/value pair then we can use the map.

Queue: It is used to hold the elements about to be processed in FIFO(First In First Out) order. It is an ordered list of objects with its use limited to insert elements at the end of the list and deleting elements from the start of the list. 