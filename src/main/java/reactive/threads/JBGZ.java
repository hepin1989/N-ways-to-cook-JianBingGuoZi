package reactive.threads;

import reactive.shared.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * kerr
 **/
public class JBGZ {
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        TimeElasped.run(() -> {
            Future<煎饼> 煎饼一张Future = executorService.submit(煎饼工厂::做煎饼);
            Future<鸡蛋煎饼> 鸡蛋煎饼一张Future = executorService.submit(() -> {
                try {
                    煎饼 煎饼一张 = 煎饼一张Future.get();
                    return 鸡蛋工厂.鸡蛋打在煎饼上(煎饼一张);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return null;
                }
            });
            Future<生菜> 生菜若干Future = executorService.submit(生菜工厂::切生菜);
            Future<火腿肠> 火腿肠一根Future = executorService.submit(火腿肠工厂::撕火腿肠);
            煎饼果子 好吃的煎饼果子 = 煎饼果子工厂.做煎饼果子(
                    鸡蛋煎饼一张Future.get(),
                    生菜若干Future.get(),
                    火腿肠一根Future.get());
            System.out.println(好吃的煎饼果子);
        });
        executorService.shutdown();
    }
}
