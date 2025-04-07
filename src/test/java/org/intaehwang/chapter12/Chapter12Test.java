package org.intaehwang.chapter12;

import org.intaehwang.chapter12.pullUpMethod.Department;
import org.intaehwang.chapter12.pullUpMethod.Employee;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

}