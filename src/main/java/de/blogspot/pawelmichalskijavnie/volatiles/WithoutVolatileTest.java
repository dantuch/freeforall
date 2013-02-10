package de.blogspot.pawelmichalskijavnie.volatiles;

// import org.apache.log4j.Logger;

/**
 * @author michalsk
 */
public class WithoutVolatileTest {

    // private static final Logger LOGGER = Logger.getLogger(WithoutVolatileTest.class);

    private static int MY_INT = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {

        @Override
        public void run() {
            int local_value = MY_INT;
            while (local_value < 5) {
                if (local_value != MY_INT) {
                    String string = "Got Change for MY_INT : " + MY_INT;
                    // LOGGER.info(string);
                    System.out.println(string);
                    local_value = MY_INT;
                }

            }
        }
    }

    static class ChangeMaker extends Thread {

        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT < 5) {
                String string = "Incrementing MY_INT to " + (local_value + 1);
                // LOGGER.info(string);
                System.out.println(string);
                MY_INT = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}