package org.intaehwang.chapter06.extractVariable;


import org.intaehwang.chapter06.comm.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractVariableTest {
    
    @Test
    @DisplayName("quantity 가 특정 개수 이하일 경우 추가 할인을 받을 수 없다.")
    public void quantityIsThreshold() {
        // given
        ExtractVariable extractVariable = new ExtractVariable();
        Order order = Order.of(Order.getDiscountThreshold());

        // when
        double quantityDiscount = order.getQuantityDiscount();

        // then
        assertThat(quantityDiscount).isEqualTo(0);
    }

    @Test
    @DisplayName("특정 상품 개수 이상 구매시 추가 할인 적용")
    public void quantityIsOverThreshold() {
        // given
        ExtractVariable extractVariable = new ExtractVariable();

        int moreQuantity = 10;
        double itemPrice = 100;

        Order order = Order.of(Order.getDiscountThreshold() + moreQuantity, itemPrice);

        // when
        double quantityDiscount = order.getQuantityDiscount();

        // then
        assertThat(quantityDiscount).isEqualTo(moreQuantity * itemPrice * Order.getDiscountRate());
    }

    @Test
    @DisplayName("배송 비용은 최대 배송비 이하로 받는다.")
    public void maxShippingFee() {
        // given
        ExtractVariable extractVariable = new ExtractVariable();

        int quantity = 10;
        double itemPrice = 100;

        Order order = Order.of(quantity, itemPrice);

        // when
        double shipping = order.getShipping();

        // then
        assertThat(shipping)
                .isLessThanOrEqualTo(Order.getMaxShippingFee());
    }
}