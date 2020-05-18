public class Main {

  private static void withTime(Time t){
    var A = t.freshThread(() -> {
      t.sleep(2000);
      System.out.println("  A");
    });
    var B = t.freshThread(() -> {
      for(int i = 0; i < 4; i++){
        t.sleep(499);
        System.out.println("    B");
      }
    });

    A.start();
    B.start();

    try {
      A.join();
      B.join();
    } catch (Exception e) {}
  }

  private static void withRendevouz(Time t){
    var semA = t.freshSemaphore(0);
    var semB = t.freshSemaphore(0);
    var A = t.freshThread(() -> {
      for (int i = 0; i < 4; i++) {
        t.sleep(1000);
        System.out.println("  A");
        semB.release();
        semA.acquire();
      }
    });
    var B = t.freshThread(() -> {
      for (int i = 0; i < 4; i++) {
        t.sleep(499);
        System.out.println("    B");
        semA.release();
        semB.acquire();
      }
    });

    A.start();
    B.start();

    try {
      A.join();
      B.join();
    } catch (Exception e) {}
  }


  public static void main(String[] args) {

    System.out.println("Running in real time: ");
    withTime(new Concrete());

    System.out.println("Running in virtual time no jitter: ");
    withTime(new Virtual(0));

    System.out.println("Running in virtual time 5ms jitter: ");
    withTime(new Virtual(5));

    System.out.println("Rendezvous in real time: ");
    withRendevouz(new Concrete());

    System.out.println("Rendezvous in virtual time no jitter: ");
    withRendevouz(new Virtual(0));

  }

}