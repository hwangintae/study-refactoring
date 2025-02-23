package org.intaehwang.chapter06.inlineFunction;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InlineFunctionTest {

    @Test
    public void reportLinesTest() {
        // given
        String name = "대흥동 타이거 우직";
        String location = "마포구";

        InlineFunction inlineFunction = new InlineFunction();
        Customer customer = new Customer(name, location);

        // when
        List<List<String>> results = inlineFunction.reportLines(customer);

        // then
        assertThat(results)
                .containsExactly(
                        List.of("name", name),
                        List.of("location", location)
                );
    }

}