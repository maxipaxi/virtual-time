import java.util.*;
import java.util.concurrent.*; 
public class Virtual implements Time {

  private ScheduleMap<Semaphore> _queue;
  private int _threads;
  private Semaphore _lock;
  private long _clock;
  private int _jitter;
  private Random _rand;

  public Virtual(int jitter) {
    _jitter = jitter;
    _rand = new Random();
    _clock = System.currentTimeMillis();
    _lock = new Semaphore(1);
    _threads = 0;
    _queue = new ScheduleMap<>();
  }

  private void unregisterThread() {
    try {
      _lock.acquire();
    } catch (Exception e) {}
    _threads--;
    wakeUpNextIfNoneActive();
    _lock.release();
  }

  private void registerThread() {
    try {
      _lock.acquire();
    } catch (Exception e) {}
    _threads++;
    _lock.release();
  }

  public void sleep(long millis) {
    try {
      _lock.acquire();
    } catch (Exception e) {}
    var sem = new Semaphore(0);
    _queue.put(_clock + millis + (_jitter == 0 ? 0 : _rand.nextInt(_jitter)), sem);
    wakeUpNextIfNoneActive();
    _lock.release();
    try {
      sem.acquire();
    } catch (Exception e) {}
  }

  private void wakeUpNextIfNoneActive() {
    if (_threads > 0 && _queue.size() == _threads) {
      wakeUpNext();
    }
  }

  private void wakeUpNext() {
    ScheduleMap.LongEntry<Semaphore> v;
    do {
      v = _queue.pollFirstEntry();
      v.getValue().release();
    } while (_queue.size() > 0 && _queue.firstKey() == v.getKey());
    _clock = v.getKey();
  }

  public void releaseNext() {
    try {
      _lock.acquire();
    } catch (Exception e) {}
    var sem = new Semaphore(0);
    _queue.put(_queue.firstKey(), sem);
    wakeUpNextIfNoneActive();
    _lock.release();
    try {
      sem.acquire();
    } catch (Exception e) {}
  }

  public CustomSemaphore freshSemaphore(int size){
    return new VirtualSemaphore(this, size);
  }

  public Thread freshThread(Runnable r) {
    registerThread();
    return new Thread(() -> {
      r.run();
      unregisterThread();
    });
  }

  public long currentTimeMillis() {
    return _clock;
  }

}