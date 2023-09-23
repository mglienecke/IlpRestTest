package uk.ac.ed.inf;

import uk.ac.ed.inf.ilp.constant.OrderStatus;
import uk.ac.ed.inf.ilp.constant.OrderValidationCode;
import uk.ac.ed.inf.ilp.constant.SystemConstants;
import uk.ac.ed.inf.ilp.data.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class TestIlpJar {
    public static void main(String[] args) {
        System.out.println("ILP Test Application using the IlpDataObjects.jar file");

        var order = new Order();
        order.setOrderNo(String.format("%08X", ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE)));
        order.setOrderDate(LocalDate.of(2023, 9, 1));

        order.setCreditCardInformation(
                new CreditCardInformation(
                        "1212",
                        String.format("%02d/%02d", ThreadLocalRandom.current().nextInt(1, 12), ThreadLocalRandom.current().nextInt(24, 29)),
                        "222"
                )
        );

        // every order has the defined outcome
        order.setOrderStatus(OrderStatus.UNDEFINED);
        order.setOrderValidationCode(OrderValidationCode.UNDEFINED);

        // get a random restaurant

        // and load the order items plus the price
        order.setPizzasInOrder(new Pizza[]{new Pizza("A", 1212)});
        order.setPriceTotalInPence(1212 + SystemConstants.ORDER_CHARGE_IN_PENCE);

        var validatedOrder =
                new OrderValidator().validateOrder(order,
                        new Restaurant[]{new Restaurant("myRestaurant",
                                new LngLat(55.945535152517735, -3.1912869215011597),
                                new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.FRIDAY},
                                new Pizza[]{new Pizza("Pizza A", 2300)})
                });

        System.out.println("order validation resulted in status: " +
                validatedOrder.getOrderStatus() +
                " and validation code: " +
                validatedOrder.getOrderValidationCode());
    }
}