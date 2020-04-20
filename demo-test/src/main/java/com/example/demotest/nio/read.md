>* java.io
>* java.nio

>* java.io中最为核心概念：Stream(流)
>* io编程就是面向流编程
>* java.io中，要么是输入流，要么是输出流，不可能既是输入流又是输出流。InputStream和OutputStream都是抽象类，一个类不可能同时继承两个类

>* java.nio中核心概念：Selector(选择器)，Channel(通道)和Buffer(缓冲区)。
>* java.nio中，我们是面向Block(块)或是Buffer(缓冲区)编程的。Buffer本身就是一块内存，底层实现是数组。数据的读写都是通过Buffer来实现的
>* 除了数组之外，Buffer还提供了对于数据的结构化访问方式，并且可以追踪到系统的读写过程
>* java中的8中原生数据类型都有对应的Buffer类型
>* 数据写入Channel要先经过Buffer，不可能出现直接将数据写入Channel的情况（data->buffer->channel）或（channel->buffer->data）
>* 因为Channel是双向的，所以能更好反映底层操作系统的真实情况。在Linux系统中，底层操作系统的通道就是双向的。

>* java.nio中三个重要属性含义：position，limit与capacity。
>* capacity:元素的个数或数量，不可能是负数且不可变化
>* limit:不能被读或不能被写的第一个元素的索引，不可能为负数且不会超过capacity
>* position:下一个将要去读或者写的元素的索引
>* position<=limit<=capacity

>* Stream是单向的，只能是输入流或者输出流，Channel是双向的，既可以读数据也可以写数据
