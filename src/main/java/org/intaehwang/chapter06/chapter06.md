# chapter06 기본적인 리팩터링

> 마틴 파울러가 가장 많이 사용하는 리팩터링 방법
> 	- 함수 추출하기
> 	- 변수 추출하기
> 위와 반대되는 방법
> 	- 함수 인라인하기
> 	- 변수 인라인하기

- 코드 이해도가 높아지면 이름을바꿔야 할 때가 많다.
- 함수 구성과 이름 짓기는 가장 기본적인 저수준 리팩터링이다.
    - 다시 고수준 모듈로 묶을 때
        - 여 함수를 클래스로 묶기
        - 여러 함수를 변환 함수로 묶기

## 함수 추출하기
```java
public void printOwing(Invoice invoice) {
	printBanner();
	int outstanding = calculateOutstanding();

	log.info("고객명 : {}", invoice.getCustomer());
	log.info("채무액 : {}", outstanding);
}
```

```java
public void printOwing(Invoice invoice) {
	printBanner();
	int outstanding = calculateOutstanding();

	printDetails(invoice, outstanding);
}

public void printDetails(Invoice invoice, int outstanding) {
	log.info("고객명 : {}", invoice.getCustomer());
	log.info("채무액 : {}", outstanding);
}

```

#### 배경
1. 코드 조각을 찾아 무슨 일을 하는지 파악한 다음, 독립된 함수로 추출하고 목적에 맞는 이름을 붙이다.
2. ==**목적과 구현을 분리**하는 방식이 가장 합리적이다.==
    1. 코드를 보고 무슨 일인지 파악하기 오래걸림
    2. 그 부분을 함수로 추출한 뒤 **무슨 일**에 걸맞는 이름을 짓는다.
    3. 나중에 코드를 다시 읽을 때 함수의 목적이 눈에 들어오고, 그 함수의 구체적인 코드는 더 이상 신경 쓸 일이 거의 없다.
> 켄트 백의 코드 중 화면의 텍스트나 색상을 강조하기 위해 highligt() 메서드가 있는데 구현 코드를 보면 단순히 reverse()라는 메서드만 호출하고 있다. 코드의 목적(강조)과 구현(반전) 차이를 해소 했다.

#### 절차
1. 함수를 새로 만들고 목적과 의도가 잘 드러나도록 이름을 정한다.
2. 추출할 코드를 원본 함수에서 새 함수로 이동한다.
3. 원본 함수의 지역 변수를 참조하거나, 추출한 함수의 유효범위를 벗어나는 변수는 없는지 확인한다. 있으면 매개변수로 전달한다.
4. 컴파일한다.
5. 추출한 함수로 일을 위임한다.
6. 테스트
7. 다른 코드에 방금 추출한 함수를 호출할 수 있는지 검토한다.

#### 예시: 유효범위를 벗어나는 변수가 없을 때
> ⚠️
> Clock Wrapper를 만들어서 시스템 시간을 직접 호출하지 말자. 직접 호출을 하게 되면 테스트할 때 마다 결과가 달라져 재현하기 어렵다.

```java
@Slf4j  
public class ExtractFunction {  
    public void printOwing(Invoice invoice) {  
        int outstanding = 0;  
  
        printBanner(); // 배너 출력 로직을 함수로 추출
  
        for (Order o : invoice.getOrders()) {  
            outstanding += o.getAmount();  
        }  
  
        LocalDate today = new Clock().now();  
        invoice.setDueDate(today.plusDays(30));  
  
        printDetails(invoice, outstanding);  // 세부 사항 출력 로직을 함수로 추출
    }  
  
    public void printBanner() {  
        log.info("*******************");  
        log.info("**** 고객 채무 ****");  
        log.info("*******************");  
    }  
  
    public void printDetails(Invoice invoice, int outstanding) {  
        log.info("고객명: {}", invoice.getCustomer());  
        log.info("채무액: {}", outstanding);  
        log.info("마감일: {}", invoice.getDueDate());  
    }  
}
```

#### 예시: 지역 변수를 사용할 때
지역변수를 다른 값으로 대입하지 않을 때 매개변수로 넘겨라
```java
@Slf4j  
public class ExtractFunction {  
    public void printOwing(Invoice invoice) {  
        int outstanding = 0;  
  
        printBanner();  
  
        for (Order o : invoice.getOrders()) {  
            outstanding += o.getAmount();  
        }  
  
        recordDueDate(invoice);  
  
        printDetails(invoice, outstanding);  
    }  
  
    public void printBanner() {  
        log.info("*******************");  
        log.info("**** 고객 채무 ****");  
        log.info("*******************");  
    }  
  
    public void printDetails(Invoice invoice, int outstanding) {  
        log.info("고객명: {}", invoice.getCustomer());  
        log.info("채무액: {}", outstanding);  
        log.info("마감일: {}", invoice.getDueDate());  
    }  
  
    public void recordDueDate(Invoice invoice) {  
        LocalDate today = new Clock().now();  
        invoice.setDueDate(today.plusDays(30));  
    }  
}
```

#### 예시: 지역 변수의 값을 변경할 때
임시 변수는 크게 두 가지로 나눌 수 있다.
1. 변수가 추출된 코드 안에서만 사용될 때
    - 만약 변수가 초기화되는 지점과 실제로 사용되는 지점이 떨어져 있다면 **문장 슬라이드하기**를 활용하여 한곳에 처리하도록 모아둔다.
2. 변수가 추출한 함수 밖에서 사용될 때
    - 이럴 때는 변수에 대입된 새 값을 반환해야 한다.
```java
@Slf4j  
public class ExtractFunction {  
    public void printOwing(Invoice invoice) {  
  
        printBanner();  
  
        int outstanding = calculateOutstanding(invoice);  
  
        recordDueDate(invoice);  
  
        printDetails(invoice, outstanding);  
    }  
  
    public int calculateOutstanding(Invoice invoice) {  
        List<Order> orders = invoice.getOrders();  
  
        return orders.stream()  
                .map(Order::getAmount)  
                .reduce(0, Integer::sum);  
    }  
  
    public void printBanner() {  
        log.info("*******************");  
        log.info("**** 고객 채무 ****");  
        log.info("*******************");  
    }  
  
    public void printDetails(Invoice invoice, int outstanding) {  
        log.info("고객명: {}", invoice.getCustomer());  
        log.info("채무액: {}", outstanding);  
        log.info("마감일: {}", invoice.getDueDate());  
    }  
  
    public void recordDueDate(Invoice invoice) {  
        LocalDate today = new Clock().now();  
        invoice.setDueDate(today.plusDays(30));  
    }  
}
```

> 📌 반환할 변수가 여러 개라면?
>  - 함수가 값 하만 반환하는 방식을 선호. 각각을 반환하는 함수를 만든다.
>  - 여러 값을 반환해야 한다면. 레코드로 묶어서 반환
>  - 그러나 레코드로 묶어서 반환하는 것 보다 다ㄴ 방식으로 처리하는 것이 나을 때가 많다.
     > 	 - **임시 변수를 질의 함수로 바꾸기**, **변수 쪼개기** 로 처리하면 좋다.
>  - 추출함수를 최상위 수준의 다른 문맥으로 이동하려면?
     > 	 - 중첩 함수로 추출하고 나서 새로운 문맥으로 옮긴다.
     > 	 - 변수를 처리하기 까다로울 수 있는데, 실제로 옮겨기 전까지 알지 못한다.
     > 	 - 최소한 원본 함수와 같은 수준의 문맥으로 먼저 추출하자

## 함수 인라인 하기
```java
public class InlineFunction {  
    public int getRating(Driver driver) {  
        return moreThanFiveLateDeliveries(driver) ? 2 : 1;  
    }  
      
    public boolean moreThanFiveLateDeliveries(Driver driver) {  
        return driver.getNumberOfLateDeliveries() > 5;  
    }  
}
```

```java
public class InlineFunction {  
    public int getRating(Driver driver) {  
        return (driver.getNumberOfLateDeliveries() > 5) ? 2 : 1;  
    }  
}
```
#### 배경
==목적이 분명한 짧은 함수명을 이용해라==
코드가 명료해지고 이해하기 쉬워진다.
하지만 때로는 함수 본문이 이름만큼 명확한 경우도 있다. 그럴 때는 그 함수를 제거한다.

#### 절차
1. 다형 메서드인지 확인한다.
    - 서브클래스에서 오버라이드하는 메서드는 인라인하면 안된다.
2. 인라인할 함수를 호출하는 곳을 모두 찾는다.
3. 각 호출문을 함수 본문으로 교체한다.
4. 하나씩 교체할 때마다 테스트한다.
    - 인라인 작업을 한 번에 처리할 필요는 없다.
    - 인라인하기 까다로운 부분이 있다면 여유가 있을 때 틈틈이 처리한다.
5. 원래 함수를 삭제한다.

#### 예시
```java
public class InlineFunction {
    public List<List<String>> reportLines(Customer aCustomer) {  
        List<List<String>> lines = new ArrayList<>();  
  
        gatherCustomerData(lines, aCustomer);  
        return lines;  
    }  
  
    public void gatherCustomerData(List<List<String>> out, Customer aCustomer) {  
        out.add(List.of("name", aCustomer.getName()));  
        out.add(List.of("location", aCustomer.getLocation()));  
    }  
}
```

```java
public List<List<String>> reportLines(Customer aCustomer) {  
	List<List<String>> lines = new ArrayList<>();  
	  
	lines.add(List.of("name", aCustomer.getName()));  
	lines.add(List.of("location", aCustomer.getLocation()));  
	  
	return lines;  
}  
```

> 📌 핵심 사항
> - 항상 단계를 잘게 나눠서 처리한다.
> - 상황이 복잡해지면 다시 한 번에 한 문장씩 처리
> - 실패하면 가장 최근의 정상 코드로 돌아온다.

## 변수 추출하기
#### 배경
- 표현식이 너무 복잡해서 이해하기 어려울 때
- 지역 변수를 활용하면 표현식을 쪼개 관리하기 더 쉽게 만들 수 있다.
- 복잡한 로직의 단계마다 이름을 붙일 수 있어 코드의 목적이 명확하게 드러난다.
- ==변수 추출 시 그 이름이 들어갈 문맥도 확인해야한다.==
- 함수를 벗어난 넓은 문맥에서까지 의미가 된다면
    - ==넓은 범위에서 통용되는 이름을 생각해야한다.==
    - 단점으로는 할 일이 늘어난다는 것
#### 절차
1. 추출의 부작용은 없는지 확인
2. 불변 변수를 하나 선언하고 이름을 붙일 표현식의 복제본을 대입
3. 원본 표현식을 새로 만든 변수로 교체
4. 테스트
5. 여러 곳에서 사용한다면 각각 새로 만든 변수로 교체

#### 예시
```java
public class ExtractVariable {  
    public double price(Order order) {  
        return order.getQuantity() * order.getItemPrice() -  
                Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05 +  
                Math.min(order.getQuantity() * order.getItemPrice() * 0.1, 100);  
    }  
}
```

```java
// base price 추출
public double price(Order order) {  
    double basePrice = order.getQuantity() * order.getItemPrice();  
    
    return basePrice -  
            Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05 +  
            Math.min(basePrice * 0.1, 100);  
}
```

```java
// quantitiyDiscount 추출
public double price(Order order) {  
    double basePrice = order.getQuantity() * order.getItemPrice();
    double quantityDiscount = Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05;  

    return basePrice - quantityDiscount + Math.min(basePrice * 0.1, 100);  
}
```

```java
// shipping 추출
public double price(Order order) {  
    double basePrice = order.getQuantity() * order.getItemPrice();  
    double quantityDiscount = Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05;  
    double shipping = Math.min(basePrice * 0.1, 100);  
      
    return basePrice - quantityDiscount + shipping;  
}
```

```java
// Order 객체로 매서드 추출
public double price(Order order) {  
    return order.getPrice();  
}

@Getter  
public class Order {  
    private static final double SHIPPING_FEE_RATE = 0.1;  
    private static final int MAX_SHIPPING_FEE = 100;  
    private static final double DISCOUNT_RATE = 0.05;  
    private static final int DISCOUNT_THRESHOLD = 500;  
  
    private final int amount;  
    private final double quantity;  
    private final double itemPrice;  
  
    @Builder  
    public Order(int amount, double quantity, double itemPrice) {  
        this.amount = amount;  
        this.quantity = quantity;  
        this.itemPrice = itemPrice;  
    }  
  
    public static Order of(int amount) {  
        return Order.builder()  
                .amount(amount)  
                .build();  
    }  
  
    public static Order of(double quantity) {  
        return Order.builder()  
                .quantity(quantity)  
                .build();  
    }  
  
    public static Order of(double quantity, double itemPrice) {  
        return Order.builder()  
                .quantity(quantity)  
                .itemPrice(itemPrice)  
                .build();  
    }  
  
    public double getBasePrice() {  
        return this.quantity * this.itemPrice;  
    }  
    public double getQuantityDiscount() {  
        return Math.max(0, this.quantity - DISCOUNT_THRESHOLD)
		        * this.itemPrice * DISCOUNT_RATE;  
    }  
  
    public double getShipping() {  
        return Math.min(getBasePrice() * SHIPPING_FEE_RATE, MAX_SHIPPING_FEE);  
    }  
  
    public double getPrice() {  
        return getBasePrice() - getQuantityDiscount() + getShipping();  
    }  
  
    public static double getDiscountRate() {  
        return DISCOUNT_RATE;  
    }  
  
    public static int getDiscountThreshold() {  
        return DISCOUNT_THRESHOLD;  
    }  
  
    public static int getMaxShippingFee() {  
        return MAX_SHIPPING_FEE;  
    }  
}
```

#### 테스트 코드
```java
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
```

## 변수 인라인하기
```java
public boolean isOver1000() {  
    double basePrice = getBasePrice();  
      
    return basePrice > 1000;  
}

public boolean isOver1000() {      
    return getBasePrice() > 1000;  
}
```

#### 배경
- 변수는 함수 안에서 대체로 긍정적인 효과를 준다.
- 하지만 그 이름이 원래 표현식과 다를 바없을 때도 있다.
- 또 주변 코드를 리팩터링하는데 방해가 되기도 한다.
- 이럴 때 인라인하는게 좋다.
#### 절차
1. 표현식에 부작용이 생기지 않는지 확인하다.
2. 불변 변수로 만든 후 테스트 한다.
    - 변수에 값이 단 한번만 대입되는지 확인할 수 있다.
3. 이 변수를 가장 처음 사용하는 코드를 찾아서 바꾼다.
4. 테스트
5. 변수를 사용하는 부분을 모두 교체할 때까지 과정 반복
6. 변수 선언문과 대입문을 지운다.
7. 테스트

## 함수 선언 바꾸기
#### 배경
- 함수는 프로그래밍을 작은 부분으로 나누는 주된 수단이다.
- 함수는 소프트웨어 시스템의 연결부 역할을 한다.
- 연결부를 잘 정의하면 새로운 부분을 추가하기 쉽다.
- 연결부에서 가장 중요한 요소는 함수 명이다.
- ==함수 명이 좋으면 구현 코드를 살펴볼 필요가 없다.==
- 함수의 활용 범위를 넓히자. 그러면 모듀로가의 결합을 제거할 수도 있다.

> 🙋‍♂️
> 좋은 함수명이 떠오르지 않을 때 주석을 이용해서 함수의 목적을 설명해보자. 주석이 멋진 이름으로 되돌아올 때가 있다.

#### 절차
- 간단한 절차
    1. 매개변수를 제거할때 먼저 함수 본문에서 매개변수를 참조하는 곳은 없는지 확인
    2. 메서드 선언을 원하는 형태로 변경
    3. 기존 메서드 선언을 참조하는 부분을 모두 찾아서 바뀐 형태로 수정
    4. 테스트
- 마이그레이션 절차
    1. 함수 본문을 적절히 리팩터링
    2. 함수 본문을 새로운 함수로 추출
        - 새로 만들 함수 이름이 기존 함수와 같다면 일단 검색하기 쉬운 이름을 임시로 붙인다.
    3. 추출한 함수에 매개변수를 추가해야 한다면 '간단한 절차'를 따라 추가
    4. 테스트
    5. **기존 함수 인라인**
    6. 임시로 붙여뒀던 함수 선언 바꾸기를 한 번 더 적용
    7. 테스트

상속 구조 속에 있는 클래스의 메서드를 변경할 때는 다른 클래스들에도 변경이 반영되어야 한다.
이때, 상황이 복잡하기 때문에 간접 호출 방식으로 우회한다.

공개된 API를 리팩터링할 때는 새 함수를 추가한 다음 예전 함수를 폐기 대상으로 지정하고 모든 클라이언트가 새 함수로 이전할 때까지 기다린다. 모두 이전했다는 확신이 들면 예전 함수를 지운다.

#### 예시: 함수 이름 바꾸기 (간단한 절차)
```java
public double circum(double radius) {
	return 2 * Math.PI * radius;
}

public double circumference(double radius) {
	return 2 * Math.PI * radius;
}
```

정적 타입 연어와 뛰어난 IDE 조합일 경우 함수 이름 바꾸기를 자동으로 처리할 수 있다.
![](https://github.com/user-attachments/assets/b3cdab9d-4286-40da-9800-73a488566600)

#### 예시: 함수 이름바꾸기(마이그레이션 절차)
```java
/**  
 * @deprecated 이 method 는 곧 제거될 예정 입니다. 대신 {@link #circumference(double)}를 사용 하세요.  
 * @since 1.5  
 * @param radius 원의 반지름  
 * @return 원의 둘레 값 (circumference)  
 */
@Deprecated(forRemoval = true, since = "1.5")
public double circum(double radius) {
	return circumference(radius);
}

public double circumference(double radius) {
	return 2 * Math.PI * radius;
}
```
![](https://github.com/user-attachments/assets/118a2836-292c-4b5e-aa10-b62c14447d4c)

circum()을 삭제하지 못할 수 도 있지만 새로 작성되는 코드는 더 나은 이름으로 사용될 것이다.

#### 예시: 매개변수 추가하기
- 예약 시 우선순위 큐를 지원하라는 새로운 요구 추가
- 우선순위 큐에 넣을지 말지 지정하는 매개변수를 추가 해야함
- 모두 찾아 고치기 쉽다면 곧바로 변경
- 그렇지 않다면 마이그레이션 절차대로 진행

```java
public class Book {  
    private final List<String> reservations;  
  
    public Book(List<String> reservations) {  
        this.reservations = reservations;  
    }  
  
    public void addReservation(String customer) {  
	    this.reservations.add(customer);  
	}  
}

// 기존 함수와 이름이 같은 상태로 둘 수 없으니 나중에 찾기 쉬운 임시이름을 붙인다.
public class Book {  
    private final List<String> reservations;  
  
    public Book(List<String> reservations) {  
        this.reservations = reservations;  
    }  
  
    public void addReservation(String customer) {  
        zz_addReservation(customer, false);  
    }  

    public void zz_addReservation(String customer, boolean isPriority) {  
        this.reservations.add(customer);  
    }  
}
```

#### 예시: 매개변수를 속성으로 바꾸기
```java
public boolean inNewEngland(Customer customer) {  
    return List.of("MA", "CT", "ME", "VT", "NH", "RI")  
            .contains(customer.getAddress().state());  
}

@Test  
public void inNewEnglandTest() {  
	...
	
    // when  
    List<Customer> result = someCustomers.stream()  
            .filter(book::inNewEngland)  
            .toList();
    
    ...    
}
```

```java
public boolean inNewEngland(Customer customer) {  
    String state = customer.getAddress().state();  
    return xxNEWinNewEngland(state);  
}  
  
public boolean xxNEWinNewEngland(String state) {  
    return List.of("MA", "CT", "ME", "VT", "NH", "RI")  
            .contains(state);  
}

@Test  
public void inNewEnglandTest() {  
	...
	
    // when  
    List<Customer> result = someCustomers.stream()  
        .filter(c -> book.xxNEWinNewEngland(c.getAddress().state()))  
        .toList();
    
    ...    
}
```

```java
public boolean inNewEngland(String state) {  
    return List.of("MA", "CT", "ME", "VT", "NH", "RI")  
            .contains(state);  
}

@Test  
public void inNewEnglandTest() {  
	...
	
    // when  
    List<Customer> result = someCustomers.stream()  
        .filter(c -> book.inNewEngland(c.getAddress().state()))  
        .toList();
    
    ...    
}
```

## 변수 캡슐화하기
#### 배경
- 데이터는 참조하는 모든 부분을 한 번에 바꿔야 코드가 제대로 작동한다.
- 유효범위가 넓어질수록 다루기 어렵다.
- 그래서 유효범위가 넓은 데이터를 옮길 때는 먼저 그 데이터 접근을 독점하는 함수를 만들어 캡슐화를 한다.
- 그래야 자주 사용하는 데이터에 대한 결합도를 낮출 수 있다.
#### 절차
1. 변수의 접근과 갱신을 전담하는 캡슐화 함수를 만든다.
2. 정적 검사를 수행한다.
3. 직접 변수를 호출 하는 부분을 모드 캡슐화 함수 호출로 변경한다.
4. 변수의 접근 범위를 제한한다.
    - 변수 직접 접근을 막을 수 없을 때는 변수 이름을 바꿔서 테스트하여 변수를 참조하는 곳을 찾는다.
5. 테스트
6. 레코드라면 **레코드 캡슐화하기**를 적용할지 고려한다.
#### 예시
```java
@Getter  
@Setter  
public class Person {  
    private String firstName;  
    private String lastName;  
  
    public Person(String firstName, String lastName) {  
        this.firstName = firstName;  
        this.lastName = lastName;  
    }  
}

// 
기본적인 캡슐화를 위해 가정 먼저 데이터를 읽고 쓰는 함수부터 정의
public class Owner {  
    private Person person;  
  
    public Owner(Person person) {  
        this.person = person;  
    }  
  
    public Person getDefaultOwner() {  
        return person;  
    }  
      
    public void setDefaultOwner(Person person) {  
        this.person = person;  
    }  
}
```

```java
@Test
@DisplayName("owner2의 lastName을 수정했는데 owner1의 lastName도 수정되었다.")
public void setterTest() {  
    Person person = new Person("마틴", "파울러");  
    Owner owner = new Owner(person);  
  
    Person owner1 = owner.getDefaultOwner();  
    assertThat(owner1.getLastName()).isEqualTo("파울러");  

	// owner2의 값을 변경했는데?
    Person owner2 = owner.getDefaultOwner();  
    owner2.setLastName("파슨스");  

	// owner1의 값이 변경되었다.
    assertThat(owner1.getLastName()).isEqualTo("파슨스");  
}
```
![](https://github.com/user-attachments/assets/33dbba3e-6d47-48ee-9c02-d9dff64ac550)

```
public class Owner {  
    private Person person;  
  
    public Owner(Person person) {  
        this.person = person;  
    }  
  
    public Person getDefaultOwner() {  
	    // 원본 데이터의 영향이 없도록 새로운 객체를 생성한다.
        return new Person(person.getFirstName(), person.getLastName());  
    }  
  
    public void setDefaultOwner(Person person) {  
        this.person = person;  
    }  
}
```

```java
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
```
![](https://github.com/user-attachments/assets/b0352918-baf3-4abc-a8d7-55d87a82f243)

> ⚠️
> - 세터에서도 복제본을 만드는 편이 좋을 수도 있다.
> - 정확한 기준은 그 데이터가 어디서 오는지
> - 원본 데이터의 모든 변경을 그대로 반영할 수 있도록 링크를 유지해야 하는지에 따라 다르다.

## 변수 이름 바꾸기
#### 배경
프로그래밍의 핵심은 이름짓기
이름의 중요성은 그 사용 범위에 영향을 많이 받는다.
함수 호출 한 번으로 끝나지 않고 값이 영속되는 필드는 이름에 더 신경 써야 한다.

#### 절차
1. 폭넓게 쓰이는 변수라면 변수 캡슐화하기를 고려한다.
2. 이름을 바꿀 변수를 참조하는 곳을 모두 찾아서, 하나씩 변경한다.
    - 다른 코드베이스에서 참조하는 변수는 외부에 공개된 변수이므로 이 리팩터링을 적용할  수 없다.
    - 변수 값이 변하지 않는다면 다른 이름으로 복제본을 만들어서 하나씩 점진석으로 변경한다.
3. 테스트

#### 예시
```java
public class RenameVariable {  
    public void before() {  
        String tpHd = "대흥동 타이거 우직";  
        String result = "";  
          
        result += "<h1>" + tpHd + "</h1>";  
        tpHd = "test";  
    }  
      
    public void after() {  
        Title title = new Title("대흥동 타이거 우직");  
        String result = "";  
          
        result += "<h1>" + title.getTitle() + "</h1>";  
        title.setTitle("test");  
    }  
      
    public static class Title {
        private String tpHd = "";
  
        public Title(String tpHd) {  
            this.tpHd = tpHd;  
        }  
  
        public String getTitle() {  
            return tpHd;  
        }  
          
        public void setTitle(String tpHd) {  
            this.tpHd = tpHd;  
        }  
    }  
}
```

## 매개변수 객체 만들기
#### 배경
데이터 항목 여러 개가 함께 몰려다니는 경우를 자주 본다.
이런 데이터 무를 발견하면 데이터 구조 하나로 모아준다.

데이터 뭉치를 데이터 구조로  묶으면 데이터 사이의 관계가 명확해진다
같은 데이터 구조를 사용하는 모든 함수가 원소를 참조할 때 항상 같은 이름을 사용하기 때문에 일관성도 높다.

#### 절차
1. 적당한 데이터 구조를 만든다.
    - 클래스로 만드는 것ㄹ 선호한다. 주로 데이터 구조를 value object로 만든다.
2. 테스트한다.
3. **함수 선언 바꾸기**로 새 데이터 구조를 매개변수로 추가한다.
4. 테스트
5. 함수 호출 시 새로운 데이터 구조 인스턴스를 넘기도록 수정한다.
6. 기존 매개변수를 사용하던 코드를 새 데이터 구조의 원소를  사용하도록 바꾼다.
7. 다 바꿨다면 기존 매개변수를 제거하고 테스트한다.

#### 예시
```java
public record OperatingPlan(int temperatureFloor, int temperatureCeiling) {}

public record Reading(int temp, LocalDateTime time) {  
  
    public static List<Reading> readingsOutSideRange(Station station, int min, int max) {  
        return station.readings().stream()  
                .filter(r -> r.temp() < min || r.temp() > max)  
                .toList();  
    }  
}

@Test  
public void test() {  
  
    // given  
    Station station = new Station("ZB1", List.of(  
            new Reading(47, LocalDateTime.of(2016, 11, 10, 9, 10)),  
            new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 20)),  
            new Reading(58, LocalDateTime.of(2016, 11, 10, 9, 30)),  
            new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 40)),  
            new Reading(51, LocalDateTime.of(2016, 11, 10, 9, 50))  
    ));  
  
    OperatingPlan operatingPlan = new OperatingPlan(50, 53);  
  
    // when  
    List<Reading> readings = Reading.readingsOutSideRange(station,  
            operatingPlan.temperatureFloor(),  
            operatingPlan.temperatureCeiling()  
    );  
  
    // then  
    assertThat(readings)  
            .extracting("temp")  
            .containsExactly(47, 58);  
}
```

```java
public record OperatingPlan(int temperatureFloor, int temperatureCeiling) {  
  
    public record NumberRange(int min, int max) {}  
    public NumberRange getNumberRange() {  
        return new NumberRange(temperatureFloor, temperatureCeiling);  
    }  
}

public record Reading(int temp, LocalDateTime time) {  
  
    public static List<Reading> readingsOutSideRange(Station station, OperatingPlan.NumberRange range) {  
        return station.readings().stream()  
                .filter(r -> r.temp() < range.min() || r.temp() > range.max())  
                .toList();  
    }  
}

@Test  
public void test() {  
  
    // given  
    Station station = new Station("ZB1", List.of(  
            new Reading(47, LocalDateTime.of(2016, 11, 10, 9, 10)),  
            new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 20)),  
            new Reading(58, LocalDateTime.of(2016, 11, 10, 9, 30)),  
            new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 40)),  
            new Reading(51, LocalDateTime.of(2016, 11, 10, 9, 50))  
    ));  
  
    OperatingPlan operatingPlan = new OperatingPlan(50, 53);  
  
    // when  
    List<Reading> readings = Reading.readingsOutSideRange(station,  
            operatingPlan.getNumberRange()  
    );  
  
    // then  
    assertThat(readings)  
            .extracting("temp")  
            .containsExactly(47, 58);  
}
```

## 여러 함수를 클래스로 묶기
#### 배경
공통 데이터를 중심으로 긴밀하게 엮여 작동하는 함수 무리를 발견하면 클래스 하나로 묶고 싶어 싶다.
클래스로 묶으면 이 함수들이 공유하는 공통 환경을 더 명확하게 표현할 수 있고,
각 함수에 전달되는 인수를 줄여서 객체 안에서의 함수 호출을 간결하게 만들 수 있다.
클래스로 묶을 때 두드러진 장점은 클라이언트가 객체의 핵심 데이터를 변경할 수 있고,
파생 객체들을 일관되게 관리할 수 있다.
#### 절차
1. 함수들이 공유하는 공통 데이터 레코드를 캡슐화 한다.
    1. 레코드가 아닐경우 먼저 **매개변수 객체 만들기**로 데이터를 하나로 묶는 레코드로 만든다.
2. 공통  레코드를 사용하는 함수 각각을 새 클래스로 옮긴다.
    1. 공통 레코드의 멤버는 함수 호출문의 인수 목록에서 제거한다.
3. 데터를 조각하는 로직들은  **함수로 추출**해서 새 클래스로 옮긴다.

#### 예시
```java
public record Reading(String customer, int quantity, int month, int year) {}


@Test  
public void client1() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
  
    // when  
    int baseCharge = baseRate(aReading.month(), aReading.year()) * aReading.quantity();  
  
    assertThat(baseCharge).isEqualTo(100);  
}  
  
@Test  
public void client2() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
    int base = (baseRate(aReading.month(), aReading.year()) * aReading.quantity());  
  
    // when  
    int taxableCharge = Math.max(0, base - taxThreshold(aReading.year()));  
  
    // then  
    assertThat(taxableCharge).isEqualTo(60);  
}  
  
@Test  
public void client3() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
  
    // when  
    int basicChargeAmount = calculateBaseCharge(aReading);  
  
    // then  
    assertThat(basicChargeAmount).isEqualTo(100);  
}  
  
public int baseRate(int month, int year) {  
    if (month >= 6 && month <= 8) { // 여름철  
        return 12;  
    } else if (month == 12 || month == 1 || month == 2) { // 겨울철  
        return 15;  
    }  
    return 10; // 기본 요금  
}  
  
public int taxThreshold(int month) {  
    if (month >= 6 && month <= 8) {  
        return 30;  
    } else if (month == 12 || month == 1 || month == 2) {  
        return 50;  
    }  
    return 40;  
}  
  
public int calculateBaseCharge(Reading aReading) {  
    return baseRate(aReading.month(), aReading.year()) * aReading.quantity();  
}
```

```java
// 함수 이동 및 함수 명 바꾸기
public record Reading(String customer, int quantity, int month, int year) {  
  
    public int baseCharge(Reading aReading) {  
        return baseRate(aReading.month(), aReading.year()) * aReading.quantity();  
    }  
  
    public int baseRate(int month, int year) {  
        if (month >= 6 && month <= 8) { // 여름철  
            return 12;  
        } else if (month == 12 || month == 1 || month == 2) { // 겨울철  
            return 15;  
        }  
        return 10; // 기본 요금  
    }  
}

@Test  
public void client3() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
  
    // when  
    int basicChargeAmount = calculateBaseCharge(aReading);  
  
    // then  
    assertThat(basicChargeAmount).isEqualTo(100);  
}  
```

```java
// 함수 인라인

@Test  
public void client1() {  
	...
    // when  
    int baseCharge = aReading.baseCharge(aReading);  
	...
}  
  
@Test  
public void client2() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
  
    // when  
    int taxableCharge = Math.max(0, aReading.baseCharge(aReading) - taxThreshold(aReading.year()));
  
    // then  
    assertThat(taxableCharge).isEqualTo(60);  
}  
  
@Test  
public void client3() {  
	...
    // when  
    int basicChargeAmount = aReading.baseCharge(aReading);
    ...
}
```

```java
// 함수 추출 및 클래스로 이동
public record Reading(String customer, int quantity, int month, int year) {
	...
    public int taxableChargeFn(Reading aReading) {  
        return Math.max(0, aReading.baseCharge(aReading) - taxThreshold(aReading.year()));
    }  
}

@Test  
public void client2() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
  
    // when  
    int taxableCharge = aReading.taxableChargeFn(aReading);  
  
    // then  
    assertThat(taxableCharge).isEqualTo(60);  
}
```

## 여러 함수를 변환 함수로 묶기
#### 배경
#### 절차
1. 변환할 레코드를 입력받아서 값을 그래도 변환하는 변환 함수를 만든다.
    - 대체로 깊은 복사로 처리해야 한다. 변환 함수가 원본을 바꾸지 않는지 검사하는 테스트가 필요하다.
2. 묶을 함수 중 함수 하나를 골라서 본문 코드를 변환 함수로 옮기고, 처리 결과를 레코드에 새 필드로 기록한다. 그런 다음 클라이언트 코드가 이 필드를 사용하도록 수정한다.
    - 로직이 복잡하면 **함수 추출하기**부터 한다.
3. 테스트
4. 나머지 관련 함수도 위 과정에 따라 처리한다.
#### 예시
```
@Getter  
public class EnrichReading {  
    private final String customer;  
    private final int quantity;  
    private final int month;  
    private final int year;  
    private final int baseCharge;  
    private final int taxableCharge;  
  
    @Builder  
    private EnrichReading(String customer, int quantity, int month, int year, int baseCharge, int taxableCharge) {  
        this.customer = customer;  
        this.quantity = quantity;  
        this.month = month;  
        this.year = year;  
        this.baseCharge = baseCharge;  
        this.taxableCharge = taxableCharge;  
    }  
  
    public static EnrichReading create(Reading aReading) {  
        return EnrichReading.builder()  
                .customer(aReading.customer())  
                .quantity(aReading.quantity())  
                .month(aReading.month())  
                .year(aReading.year())  
                .baseCharge(baseCharge(aReading))  
                .taxableCharge(Math.max(0, baseCharge(aReading) - taxThreshold(aReading.year())))  
                .build();  
    }  
  
    private static int baseCharge(Reading aReading) {  
        return baseRate(aReading.month(), aReading.year()) * aReading.quantity();  
    }  
  
    private static int baseRate(int month, int year) {  
        if (month >= 6 && month <= 8) { // 여름철  
            return 12;  
        } else if (month == 12 || month == 1 || month == 2) { // 겨울철  
            return 15;  
        }  
        return 10; // 기본 요금  
    }  
  
    private static int taxThreshold(int month) {  
        if (month >= 6 && month <= 8) {  
            return 30;  
        } else if (month == 12 || month == 1 || month == 2) {  
            return 50;  
        }  
        return 40;  
    }  
}

@Test  
public void client1() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
    EnrichReading enrichReading = EnrichReading.create(aReading);  
  
    // when  
    int baseCharge = enrichReading.getBaseCharge();  
  
    assertThat(baseCharge).isEqualTo(100);  
}  
  
@Test  
public void client2() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
    EnrichReading enrichReading = EnrichReading.create(aReading);  
  
    // when  
    int taxableCharge = enrichReading.getTaxableCharge();  
  
    // then  
    assertThat(taxableCharge).isEqualTo(60);  
}  
  
@Test  
public void client3() {  
    // given  
    Reading aReading = new Reading("ivan", 10, 5, 2017);  
    EnrichReading enrichReading = EnrichReading.create(aReading);  
  
    // when  
    int basicChargeAmount = enrichReading.getBaseCharge();  
  
    // then  
    assertThat(basicChargeAmount).isEqualTo(100);  
  
  
}
```

> 6.9에서 Reading은 함수를 제공하는 형태이고, 6.10의 EnrichReading은 계산된 결과를 객체로 제공하는 형태가 가장 큰 차이인거 같다.

## 단계 쪼개기
#### 배경
- 서로 다른 두 대상을 한꺼번에 다루는 코드를 발견하면 각각 별개 모듈로 나눈다.
- 가장 대표적인 예로 컴파일러
    1. 텍스트를 토큰화
    2. 토큰을 파싱하여 구문 트리 구성
    3. 구문 트리를 변환하는 다양항 단계를 거침
    4. 목적 코드 생성
- 각 단계는 자신만의 문제에 집중하기 때문에 나머지 단계에 관해 잘 몰라도 된다.
#### 절차
1. 두 번째 단계에 해당하는 코드를 독립 함수로 추출
2. 테스트
3. 중간 데이터 구조를 만들어서 앞에서 추출한 함수의 인수로 추가
4. 테스트
5. 추출한 두 번째 단계 함수의 매개변수를 하나씩 검토. 그 중 첫 번째 단계에서 사용되는 것은 중간 데이터 구조로 옮김
    - 간혹 두 번째 단계에서 사용하면 안 되는 매개변수가 있다. 이럴 때는 각 매개변수를 사용한 결과를 중간 데이터 구조의 필드로 추출, 이 필듸의 값을 설정하는 **문장을 호출한 곳으로 옮긴다.**
6. 첫 번째 단계 코드를 **함수로 추출**하면서 중간 데이터 구조를 반환
    - 이때 첫 번째 단계를 변환기 객체로 추출 해도 됨
#### 예시
```java
public static double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {  
    double basePrice = product.basePrice() * quantity;  
    double discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
    double shippingPerCase = (basePrice > shippingMethod.discountThreshold())  
            ? shippingMethod.discountFee() : shippingMethod.feePerCase();  
    double shippingCost = quantity * shippingPerCase;  
    double price = basePrice - discount + shippingCost;  
  
    return price;  
}

public record Product(double basePrice, int discountThreshold, double discountRate) {}

public record ShippingMethod(double discountThreshold, double discountFee, double feePerCase) {}
```

```java
// 두 단계로 나누기
public static double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {  
    double basePrice = product.basePrice() * quantity;  
    double discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
    double price = applyShipping(basePrice, shippingMethod, quantity, discount);  
    return price;  
}  
  
public static double applyShipping(double basePrice, ShippingMethod shippingMethod, int quantity, double discount) {  
    double shippingPerCase = (basePrice > shippingMethod.discountThreshold())  
            ? shippingMethod.discountFee() : shippingMethod.feePerCase();  
    double shippingCost = quantity * shippingPerCase;  
  
    return basePrice - discount + shippingCost;  
}
```

```java
// 중간 데이터 구조 추가
public static double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {  
    double basePrice = product.basePrice() * quantity;  
    double discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
  
    PriceDate priceDate = new PriceDate(basePrice, quantity, discount);  
    double price = applyShipping(priceDate , shippingMethod);  
  
    return price;  
}  
  
public static double applyShipping(PriceDate priceDate, ShippingMethod shippingMethod) {  
    double shippingPerCase = (priceDate.basePrice() > shippingMethod.discountThreshold())  
            ? shippingMethod.discountFee() : shippingMethod.feePerCase();  
    double shippingCost = priceDate.quantity() * shippingPerCase;  
  
    return priceDate.basePrice() - priceDate.discount() + shippingCost;  
}
```

```java
// 첫 번째 함수 코드로 추출
public static double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {  
    PriceDate priceDate = calculatePricingData(product, quantity);  
    return applyShipping(priceDate , shippingMethod);  
}  
  
public static PriceDate calculatePricingData(Product product, int quantity) {  
    double basePrice = product.basePrice() * quantity;  
    double discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
  
    return new PriceDate(basePrice, quantity, discount);  
}  
  
public static double applyShipping(PriceDate priceDate, ShippingMethod shippingMethod) {  
    double shippingPerCase = (priceDate.basePrice() > shippingMethod.discountThreshold())  
            ? shippingMethod.discountFee() : shippingMethod.feePerCase();  
    double shippingCost = priceDate.quantity() * shippingPerCase;  
  
    return priceDate.basePrice() - priceDate.discount() + shippingCost;  
}
```
#### 예시: 명령줄 프로그램 쪼개기(자바)
```java
public static void main(String[] args) {  
    try {  
        if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
        String fileName = args[args.length - 1];  
        File input = Paths.get(fileName).toFile();  
        ObjectMapper mapper = new ObjectMapper();  
        Order[] orders = mapper.readValue(input, Order[].class);  
        if (Stream.of(args).anyMatch(arg -> "-r".equals(arg))) {  
            System.out.println(Stream.of(orders)  
                    .filter(o -> "ready".equals(o.status()))  
                    .count());  
        } else System.out.println(orders.length);  
    } catch(Exception ignore) {}  
}
```

```java
class JavaExampleCodeTest {  
    @TempDir  
    Path tempDir;  
  
    @Test  
    void testNoFileNameThrowsException() {  
        assertThatThrownBy(() -> JavaExampleCode.run(new String[]{}))  
                .isInstanceOf(RuntimeException.class)  
                .hasMessage("파일명을 입력하세요.");  
    }  
  
    @Test  
    void testFileNotFoundThrowsException() {  
        assertThatThrownBy(() -> JavaExampleCode.run(new String[]{"nonexistent.json"}))  
                .isInstanceOf(IOException.class);  
    }  
  
    @Test  
    void testInvalidJsonThrowsException() throws IOException {  
        File invalidJsonFile = tempDir.resolve("invalid.json").toFile();  
        try (FileWriter writer = new FileWriter(invalidJsonFile)) {  
            writer.write("Invalid JSON Content");  
        }  
  
        assertThatThrownBy(() -> JavaExampleCode.run(new String[]{invalidJsonFile.getAbsolutePath()}))  
                .isInstanceOf(IOException.class);  
    }  
  
    @Test  
    void testValidJsonWithoutReadyStatus() throws IOException {  
        File validJsonFile = tempDir.resolve("valid.json").toFile();  
        try (FileWriter writer = new FileWriter(validJsonFile)) {  
            writer.write("[{\"status\":\"delivered\"}, {\"status\":\"shipped\"}]");  
        }  
  
        long result = JavaExampleCode.run(new String[]{validJsonFile.getAbsolutePath()});  
        assertThat(result).isEqualTo(2);  
    }  
  
    @Test  
    void testValidJsonWithReadyStatus() throws IOException {  
        File validJsonFile = tempDir.resolve("valid.json").toFile();  
        try (FileWriter writer = new FileWriter(validJsonFile)) {  
            writer.write("[{\"status\":\"ready\"}, {\"status\":\"shipped\"}, {\"status\":\"ready\"}]");  
        }  
  
        long result = JavaExampleCode.run(new String[]{"-r", validJsonFile.getAbsolutePath()});  
        assertThat(result).isEqualTo(2);  
    }  
}
```

```java
// run 메서드 추출
public static void main(String[] args) {  
    try {  
        System.out.println(run(args));  
    } catch(Exception ignore) {}  
}  
  
public static long run(String[] args) throws IOException {  
    if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
    String fileName = args[args.length - 1];  
    File input = Paths.get(fileName).toFile();  
    ObjectMapper mapper = new ObjectMapper();  
    Order[] orders = mapper.readValue(input, Order[].class);  
    if (Stream.of(args).anyMatch(arg -> "-r".equals(arg))) {  
        return Stream.of(orders)  
                .filter(o -> "ready".equals(o.status()))  
                .count();  
    } else return orders.length;  
}
```
```java
// 단계 쪼개기 counterOrders
public static void main(String[] args) {  
    try {  
        System.out.println(run(args));  
    } catch(Exception ignore) {}  
}  
  
public static long run(String[] args) throws IOException {  
    if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
    String fileName = args[args.length - 1];  
    return countOrders(args, fileName);  
}  
  
private static long countOrders(String[] args, String fileName) throws IOException {  
    File input = Paths.get(fileName).toFile();  
    ObjectMapper mapper = new ObjectMapper();  
    Order[] orders = mapper.readValue(input, Order[].class);  
    if (Stream.of(args).anyMatch(arg -> "-r".equals(arg))) {  
        return Stream.of(orders)  
                .filter(o -> "ready".equals(o.status()))  
                .count();  
    } else return orders.length;  
}
```

```java
// 중간 클래스 추가
public static long run(String[] args) throws IOException {  
    if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
    CommandLine commandLine = new CommandLine();  
    commandLine.onlyCountReady = Stream.of(args).anyMatch(arg -> "-r".equals(arg));  
  
    String fileName = args[args.length - 1];  
  
  
    return countOrders(commandLine, args, fileName);  
}  
  
private static long countOrders(CommandLine commandLine, String[] args, String fileName) throws IOException {  
    File input = Paths.get(fileName).toFile();  
    ObjectMapper mapper = new ObjectMapper();  
    Order[] orders = mapper.readValue(input, Order[].class);  
  
    if (commandLine.onlyCountReady) {  
        return Stream.of(orders)  
                .filter(o -> "ready".equals(o.status()))  
                .count();  
    } else return orders.length;  
}  
  
private static  class CommandLine {  
    boolean onlyCountReady;  
}
```

```java
// fileName을 commandLine으로 이동
public static long run(String[] args) throws IOException {  
    if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
    CommandLine commandLine = new CommandLine();  
    commandLine.onlyCountReady = Stream.of(args).anyMatch(arg -> "-r".equals(arg));  
    commandLine.fileName = args[args.length - 1];  
  
  
    return countOrders(commandLine, args);  
}  
  
private static long countOrders(CommandLine commandLine, String[] args) throws IOException {  
    File input = Paths.get(commandLine.fileName).toFile();  
    ObjectMapper mapper = new ObjectMapper();  
    Order[] orders = mapper.readValue(input, Order[].class);  
  
    if (commandLine.onlyCountReady) {  
        return Stream.of(orders)  
                .filter(o -> "ready".equals(o.status()))  
                .count();  
    } else return orders.length;  
}  
  
private static  class CommandLine {  
    boolean onlyCountReady;  
    String fileName;  
}
```

```java
// parseCommandLine 함수 추출 및 인라인
public static long run(String[] args) throws IOException {  
    return countOrders(parseCommandLine(args));  
}  
  
private static CommandLine parseCommandLine(String[] args) {  
    if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
    CommandLine commandLine = new CommandLine();  
    commandLine.onlyCountReady = Stream.of(args).anyMatch(arg -> "-r".equals(arg));  
    commandLine.fileName = args[args.length - 1];  
    return commandLine;  
}
```

#### 예시: 첫 번째 단계에 변환기 사용하기(자바)

```java
@Getter  
public class CommandLine {  
    private final String[] args;  
  
    public CommandLine(String[] args) {  
        this.args = args;  
    }  
}
public static void main(String[] args) {  
    try {  
        System.out.println(run(args));  
    } catch(Exception ignore) {}  
}  
  
public static long run(String[] args) throws IOException {  
    if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
    String fileName = args[args.length - 1];  
    return countOrders(args, fileName);  
}  
  
private static long countOrders(String[] args, String fileName) throws IOException {  
    File input = Paths.get(fileName).toFile();  
    ObjectMapper mapper = new ObjectMapper();  
    Order[] orders = mapper.readValue(input, Order[].class);  
    if (Stream.of(args).anyMatch(arg -> "-r".equals(arg))) {  
        return Stream.of(orders)  
                .filter(o -> "ready".equals(o.status()))  
                .count();  
    } else return orders.length;  
}
```

```java
// filename 메서드 추출
@Getter  
public class CommandLine {  
    private final String[] args;  
  
    public CommandLine(String[] args) {  
        this.args = args;  
    }  
  
    public String filename() {  
        return this.args[this.args.length - 1];  
    }  
}

public static long run(String[] args) throws IOException {  
    if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
    CommandLine commandLine = new CommandLine(args);  
  
    return countOrders(commandLine, args, commandLine.filename());  
}

private static long countOrders(CommandLine commandLine, String[] args) throws IOException {  
    File input = Paths.get(commandLine.filename()).toFile();  
    ObjectMapper mapper = new ObjectMapper();  
    Order[] orders = mapper.readValue(input, Order[].class);  
    if (Stream.of(args).anyMatch(arg -> "-r".equals(arg))) {  
        return Stream.of(orders)  
                .filter(o -> "ready".equals(o.status()))  
                .count();  
    } else return orders.length;  
}
```

```java
// onlyCountReady 메서드 추출
@Getter  
public class CommandLine {  
    private final String[] args;  
  
    public CommandLine(String[] args) {  
        this.args = args;  
    }  
  
    public String filename() {  
        return this.args[this.args.length - 1];  
    }  
  
    public boolean onlyCountReady() {  
        return Stream.of(this.args).anyMatch(arg -> "-r".equals(arg));  
    }  
}

private static long countOrders(CommandLine commandLine) throws IOException {  
    File input = Paths.get(commandLine.filename()).toFile();  
    ObjectMapper mapper = new ObjectMapper();  
    Order[] orders = mapper.readValue(input, Order[].class);  
    if (commandLine.onlyCountReady()) {  
        return Stream.of(orders)  
                .filter(o -> "ready".equals(o.status()))  
                .count();  
    } else return orders.length;  
}
```

```java
// 문장을 함수로 옮기기
public CommandLine(String[] args) {  
    this.args = args;  
    if (args.length == 0) throw new RuntimeException("파일명을 입력하세요.");  
}

public static long run(String[] args) throws IOException {  
    CommandLine commandLine = new CommandLine(args);  
  
    return countOrders(commandLine);  
}

```