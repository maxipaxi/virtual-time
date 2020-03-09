public class Concrete implements Time {

  public void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception e) {}
  }

  public CustomSemaphore freshSemaphore(int size){
    return new MySemaphore(size);
  }

  public Thread freshThread(Runnable r) {
    return new Thread(r);
  }

  public long currentTimeMillis() {
    return System.currentTimeMillis();
  }

}