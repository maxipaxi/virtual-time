= A Minimal Virtual Time Library for Java

This library is an experiment, documented in my blog post.

To use it replace:

* `System.currentTimeMillis()` with `time.currentTimeMillis()`
* `new Thread(...)` with `time.freshThread(...)`
* `new Semaphore(...)` with `time.freshSemaphore(...)`
* `Thread.sleep(...)` with `time.sleep(...)`

Then instantiate `Concrete` in your `main` method and propagate it as a parameter throughout the program where you need `time`.
