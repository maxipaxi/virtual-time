import java.util.concurrent.*; 
public class VirtualSemaphore implements CustomSemaphore {

  private Virtual _time;
  private Semaphore _sem;
  private Semaphore _lock;
  public VirtualSemaphore(Virtual t, int capacity) {
    _lock = new Semaphore(1);
    _sem = new Semaphore(capacity);
    _time = t;
  }

  public void acquire() {
    try {
      _lock.acquire();
    } catch (Exception e) {}
    if (_sem.availablePermits() == 0)
      _time.releaseNext();
    _lock.release();
    try {
      _sem.acquire();
    } catch (Exception e) {}
  }

  public void release() {
    try {
      _lock.acquire();
    } catch (Exception e) {}
    _sem.release();    
    _lock.release();
  }

}