public interface Time {

  void sleep(long millis);
  Thread freshThread(Runnable r);
  CustomSemaphore freshSemaphore(int size);
  long currentTimeMillis();

}