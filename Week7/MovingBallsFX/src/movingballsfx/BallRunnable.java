/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import javafx.scene.paint.Color;

/**
 *
 * @author Kevin van der Burg
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private RW readWrite;

    public BallRunnable(Ball ball, RW rw) {
        this.ball = ball;
        readWrite = rw;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (ball.getName().equals("reader")) {

                    if (ball.isEnteringCs()) {
                        readWrite.enterReader();
                    } else if (ball.isLeavingCs()) {
                        readWrite.exitReader();
                    }

                } else if (ball.getName().equals("writer")) {
                    if (ball.isEnteringCs()) {
                        readWrite.enterWriter();
                    } else if (ball.isLeavingCs()) {
                        readWrite.exitWriter();
                    }
                }
                ball.move();

                Thread.sleep(ball.getSpeed());

            } catch (InterruptedException ex) {
                if (ball.getName().equals("reader")) {
                    readWrite.interruptedReader(ball);
                } else if (ball.getName().equals("writer")) {
                    readWrite.interruptedWriter(ball);
                }
                Thread.currentThread().interrupt();
            }
        }
    }
}
