package com.setoba.sec01;

public class InboundOutboundTaskDemo {

  private static final int MAX_PLATFORM = 1000_000;

  public static void main(String[] args) {
    platformThreadDemo();
  }

  private static void platformThreadDemo() {
    for (int i = 0; i < MAX_PLATFORM; i++) {
      int finalI = i;
      Thread thread = new Thread(() -> Task.ioIntensive(finalI));
      thread.start();
    }
  }
}
