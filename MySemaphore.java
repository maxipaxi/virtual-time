import java.util.concurrent.*; 
public class MySemaphore implements CustomSemaphore {

  private Semaphore _sem;
  public MySemaphore(int capacity) {
    _sem = new Semaphore(capacity);
  }

  public void acquire() {
    try {
      _sem.acquire();
    } catch (Exception e) {}
  }

  public void release() {
    _sem.release();    
  }

}