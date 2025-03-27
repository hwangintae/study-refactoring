package org.intaehwang.chapter06.encapsulateVariable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerChapter10Test {

    @Test
    @DisplayName("owner2의 lastName을 수정해도 owner1의 lastName은 수정되지 않는다.")
    public void setterTest() {
        Person person = new Person("마틴", "파울러");
        Owner owner = new Owner(person);

        Person owner1 = owner.getDefaultOwner();
        Person owner2 = owner.getDefaultOwner();
        owner2.setLastName("파슨스");

        assertThat(owner1.getLastName()).isEqualTo("파울러");
        assertThat(owner2.getLastName()).isEqualTo("파슨스");
    }

}