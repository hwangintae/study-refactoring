package org.intaehwang.chapter12;

import org.assertj.core.groups.Tuple;
import org.intaehwang.chapter12.pullUpMethod.Department;
import org.intaehwang.chapter12.pullUpMethod.Employee;
import org.intaehwang.chapter12.removeSubclass.People;
import org.intaehwang.chapter12.removeSubclass.Person;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.*;

class Chapter12Test {

    @Test
    public void employeeTest() {
        // given
        int monthlyCost = 1;
        Employee employee = new Employee(monthlyCost);

        // when
        int result = employee.annualCost();

        // then
        assertThat(result).isEqualTo(monthlyCost * 12);
    }

    @Test
    public void departmentTest() {
        // given
        int monthlyCost = 2;
        Department department = new Department(monthlyCost);

        // when
        int result = department.annualCost();

        // then
        assertThat(result).isEqualTo(monthlyCost * 12);
    }

    @Test
    public void employeeAndDepartmentTest() {
        // given
        int employeeMonthlyCost = 1;
        int departmentMonthlyCost = 2;
        Employee employee = new Employee(employeeMonthlyCost);
        Department department = new Department(departmentMonthlyCost);

        // when
        int result1 = employee.annualCost();
        int result2 = department.annualCost();

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    public void loadFromInputTest() {
        // given
        Chapter12 chapter12 = new Chapter12();

        List<People> people = List.of(
                new People("M", "우직이"),
                new People("M", "행복이"),
                new People("F", "뚜직이"),
                new People("M", "뿌직이"),
                new People("", "인직이"),
                new People("", "참직이")
        );

        // when
        List<Person> result = chapter12.loadFromInput(people);

        // then
        assertThat(result)
                .extracting(Person::genderCode, Person::getName)
                .containsExactly(
                        tuple("M", "우직이"),
                        tuple("M", "행복이"),
                        tuple("F", "뚜직이"),
                        tuple("M", "뿌직이"),
                        tuple("X", "인직이"),
                        tuple("X", "참직이")
                );
    }

    @Test
    public void employeeAnnualCostTest() {
        // given
        org.intaehwang.chapter12.extractSuperclass.Employee intae = new org.intaehwang.chapter12.extractSuperclass.Employee(1L, "intae", 100);

        // when
        int result = intae.annualCost();

        // then
        assertThat(result).isEqualTo(12 * 100);
    }

}