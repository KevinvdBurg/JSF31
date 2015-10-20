/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Eric
 */
public class RW {

    private int readersActive = 0;
    private int writersActive = 0;
    private int readersWaiting = 0;
    private int writersWaiting = 0;

    Lock monLock = new ReentrantLock();
    Condition okToRead = monLock.newCondition();
    Condition okToWrite = monLock.newCondition();

    public RW() {

    }

    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive != 0) {
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
            }
            readersActive++;
        } finally {
            monLock.unlock();
        }
    }

    public void exitReader() {
        monLock.lock();
        try {
            readersActive--;
            if (readersActive == 0) {
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }

    public void interruptedReader(Ball ball) {
        monLock.lock();
        try {
            if (ball.isEnteringCs()) {
                readersWaiting--;
            } else if (ball.isInCs()) {
                readersActive--;
            }
        } finally {
            monLock.unlock();
        }
    }

    public void enterWriter() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0 || readersActive > 0) {
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
            writersActive++;
        } finally {
            monLock.unlock();
        }
    }

    public void exitWriter() {
        monLock.lock();
        try {
            writersActive--;
            if (writersWaiting > 0 && writersActive == 0) {
                okToWrite.signal();
            } else {
                okToRead.signalAll();
            }
        } finally {
            monLock.unlock();
        }
    }

    public void interruptedWriter(Ball ball) {
        monLock.lock();
        try {
            if (ball.isEnteringCs()) {
                writersWaiting--;
            } else if (ball.isInCs()) {
                writersActive--;
            }
            if (writersWaiting == 0 && writersActive == 0){
                okToRead.signalAll();
            }
            else if(writersWaiting > 0 ){
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }
}
