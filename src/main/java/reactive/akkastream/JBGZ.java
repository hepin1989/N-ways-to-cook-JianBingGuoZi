package reactive.akkastream;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.japi.function.Function;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import reactive.shared.*;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * kerr
 **/
public class JBGZ {
    private static final ActorSystem ACTOR_SYSTEM = ActorSystem.apply();

    public static void main(String[] args) {
        Source<String, NotUsed> reqSource = Source.repeat("来一个煎饼果子");
        Source<煎饼, NotUsed> 煎饼Source = Source.from(new AbstractList<煎饼>() {
            @Override
            public 煎饼 get(int index) {
                System.out.println("生产煎饼" );
                return 煎饼工厂.做煎饼();
            }

            @Override
            public int size() {
                return 1;
            }
        });
        Source<鸡蛋煎饼, NotUsed> 鸡蛋煎饼Source = 煎饼Source
                .map((Function<煎饼, 鸡蛋煎饼>) param -> {
                    System.out.println("鸡蛋打在煎饼上");
                    return 鸡蛋工厂.鸡蛋打在煎饼上(param);
                });
        Source<生菜, NotUsed> 生菜Source = Source.from(new AbstractList<生菜>() {
            @Override
            public 生菜 get(int index) {
                System.out.println("生产生菜");
                return 生菜工厂.切生菜();
            }

            @Override
            public int size() {
                return 1;
            }
        });
        Source<火腿肠, NotUsed> 火腿肠Source = Source.from(new AbstractList<火腿肠>() {
            @Override
            public 火腿肠 get(int index) {
                System.out.println("生产火腿肠");
                return 火腿肠工厂.撕火腿肠();
            }

            @Override
            public int size() {
                return 1;
            }
        });
        Source<煎饼果子, NotUsed> 煎饼果子Source = reqSource
                .flatMapConcat((Function<String, Source<煎饼果子, NotUsed>>) (String req) -> {
                    System.out.println("你有新的订单:" + req);
                    List<Source<食材, ?>> sources = new ArrayList<>();
                    sources.add(鸡蛋煎饼Source.map(食材.class::cast).async());
                    sources.add(生菜Source.map(食材.class::cast).async());
                    sources.add(火腿肠Source.map(食材.class::cast).async());
//                    sources.add(鸡蛋煎饼Source.map(r -> (食材) r));
//                    sources.add(生菜Source.map(r -> (食材) r));
//                    sources.add(火腿肠Source.map(r -> (食材) r));
                    return Source.zipWithN((Function<List<食材>, 煎饼果子>) 食材List -> {
                        鸡蛋煎饼 鸡蛋煎饼一张 = (鸡蛋煎饼) 食材List.get(0);
                        生菜 生菜若干 = (生菜) 食材List.get(1);
                        火腿肠 火腿肠一根 = (火腿肠) 食材List.get(2);
                        煎饼果子 好吃的煎饼果子 = 煎饼果子工厂.做煎饼果子(
                                鸡蛋煎饼一张,
                                生菜若干,
                                火腿肠一根);
                        return 好吃的煎饼果子;
                    }, sources);
                });

        TimeElasped.run(() -> {
            ActorMaterializer actorMaterializer = ActorMaterializer.create(ACTOR_SYSTEM);
//            CompletableFuture<煎饼果子> 煎饼果子CompletableFuture =
//                    煎饼果子Source
//                            .take(1)
//                            .runWith(Sink.head(), actorMaterializer)
//                            .toCompletableFuture();
//            System.out.println(煎饼果子CompletableFuture.join());
            煎饼果子Source.runForeach(System.out::println,actorMaterializer);
//            actorMaterializer.shutdown();
//            ACTOR_SYSTEM.terminate();
        });

    }
}
