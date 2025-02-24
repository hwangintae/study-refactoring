package org.intaehwang.chapter06.encapsulateVariable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerTest {

    @Test
    @DisplayName("owner2의 lastName을 수정했는데 owner1의 lastName도 수정되었다.")
    public void setterTest() {
        Person person = new Person("마틴", "파울러");
        Owner owner = new Owner(person);

        Person owner1 = owner.getDefaultOwner();
        assertThat(owner1.getLastName()).isEqualTo("파울러");

        Person owner2 = owner.getDefaultOwner();
        owner2.setLastName("파슨스");

        assertThat(owner1.getLastName()).isEqualTo("파슨스");
    }

}