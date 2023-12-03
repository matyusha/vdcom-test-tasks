package org.example;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Semaphore;

@RequiredArgsConstructor
public class RewriteFileTask implements Runnable {

    private final Semaphore semaphore;
    private final CountFileWriter writer;

    @Override
    public void run() {
        try {
            boolean isEnded = false;
            while (!isEnded) {
                semaphore.acquire();
                isEnded = writer.incrementCount();
                semaphore.release();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
