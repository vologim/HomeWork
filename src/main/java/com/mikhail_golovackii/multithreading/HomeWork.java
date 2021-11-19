
package com.mikhail_golovackii.multithreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class HomeWork {
    
    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();

        CompletableFuture<Void> printSecond = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                foo.second(this);
            }
        });

        CompletableFuture<Void> printThird = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                foo.third(this);
            }
        });
        
        CompletableFuture<Void> printFirst = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run(){
                foo.first(this);
            }
        });
        
        Thread.sleep(100);
    }
}

class Foo {

    private Semaphore firstSemaphore = new Semaphore(1);
    private Semaphore secondSemaphore = new Semaphore(0);
    private Semaphore thirdSemaphore = new Semaphore(0);

    public void first(Runnable r){
        try {
            firstSemaphore.acquire();
            System.out.println("first");
        } 
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        finally {
            secondSemaphore.release();
        }
    }

    public void second(Runnable r){
        try {
            secondSemaphore.acquire();
            System.out.println("second");
        } 
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        finally {
            thirdSemaphore.release();
        }
    }

    public void third(Runnable r){
        try {
            thirdSemaphore.acquire();
            System.out.println("third");
        } 
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        finally {
            firstSemaphore.release();
        }
    }
}