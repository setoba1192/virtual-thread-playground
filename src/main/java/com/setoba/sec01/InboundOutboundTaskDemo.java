package com.setoba.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {

  private static final int MAX_PLATFORM = 10;
  private static final int MAX_VIRTUAL = 250_000;


  public static void main(String[] args) throws InterruptedException {
    virtualThreadDemo();
  }

  /**
   * To create a simple java platform thread
   */
  private static void platformThreadDemo1() {
    for (int i = 0; i < MAX_PLATFORM; i++) {
      int finalI = i;
      Thread thread = new Thread(() -> Task.ioIntensive(finalI));
      thread.start();
    }
  }

  /**
   * To create a platform thread using Thread.Builder
   */
  private static void platformThreadDemo2() {
    var builder = Thread.ofPlatform().name("seto", 1);
    for (int i = 0; i < MAX_PLATFORM; i++) {
      int finalI = i;
      Thread thread = builder.unstarted(() -> Task.ioIntensive(finalI));
      thread.start();
    }
  }

  /**
   * To create platform daemon thread using Thread.Builder
   *
   * @throws InterruptedException
   */
  private static void platformThreadDemo3() throws InterruptedException {
    var latch = new CountDownLatch(MAX_PLATFORM);
    var builder = Thread.ofPlatform().daemon().name("daemon", 1);
    for (int i = 0; i < MAX_PLATFORM; i++) {
      int finalI = i;
      Thread thread = builder.unstarted(() -> {
        Task.ioIntensive(finalI);
        latch.countDown();
      });
      thread.start();
    }
    latch.await();
  }

  /**
   * To create a virtual thread using Thread.Builder
   */
  private static void virtualThreadDemo() throws InterruptedException {
    var latch = new CountDownLatch(MAX_VIRTUAL);
    var builder = Thread.ofVirtual().name("seto-virtual", 1);
    for (int i = 0; i < MAX_VIRTUAL; i++) {
      int finalI = i;
      Thread thread = builder.unstarted(() -> {
        Task.ioIntensive(finalI);
        latch.countDown();
      });
      thread.start();
    }
    latch.await();
  }
}
