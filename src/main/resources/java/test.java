package be.facetit.rest.nu.open.strategydecorator;

if (true) {} else if (true) {} else {}
import be.facetit.rest.nu.open.strategydecorator.model.Order;
import be.facetit.rest.nu.open.strategydecorator.spring.SpringConfig;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        List<Order> orders = new LinkedList<>();

        Order mangos = context.getBean("mangoOrder", Order.class);
        Order mix = context.getBean("mixedOrder", Order.class);
        Order ananas = context.getBean("pineappleOrder", Order.class);

        orders.add(mangos);
        orders.add(mix);
        orders.add(ananas);

        orders.stream().map(order -> order.price())
                .forEach(System.out::println);

        if (true) {
        } else if (true) {
        } else {
        }
    }

}