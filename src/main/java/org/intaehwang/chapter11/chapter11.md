좋은 API는 데이터를 갱신하는 함수와 조회하는 함수를 명확히 구분한다.

두 기능이 섞여 있으면? -> 질의 함수와 변경 함수 분리하기
값 하나 때문에 여러개로 나뉜 함수들은? -> 함수 매개변수화하기
매개변수가 함수의 동작 모드를 전환하는 용도로만 쓰이면? -> 플래그 인수 제거하기

객체를 불변으로 만들기 -> 세터 제거하기
생성자만으로 부족할 때 -> 생성자를 팩터리 함수로 바꾸기



# 질의 함수와 변경 함수 분리하기
#### 배경
부수효과가 전혀 없는 함수를 추구해야 한다.

질의 함수는 모두 부수효과가 없어야 한다.
#### 절차
1. 대상 함수를 복제하고 질의 목적에 충실한 이름을 짓는다
    1. 함수 내부를 살펴 무엇을 반환하는지 찾는다.
2. 새 질의 함수에서 부소효과를 모두 제거한다.
3. 정적 검사를 수행한다.
4. 원래 함수를 호출하는 곳을 모두 찾아낸다. 호출하는 곳에서 반환 값을 사용한다면 질의 함수를 호출하도록 바꾸고, 원래 함수를 호출하는 코드를 바로 아래 줄에 새로 추가한다. 하나 수정할 때마다 테스트
5. 원래 함수에서 질의 관련 코드를 제거한다.
6. 테스트
#### 예시
```java
public String alertForMiscreant(List<String> people) {  
    for (String p : people) {  
        if ("조커".equals(p)) {  
            setOfAlarms();  
            return "조커";  
        }  
        if ("사루만".equals(p)) {  
            setOfAlarms();  
            return "사루만";  
        }  
    }  
  
    return "";  
}
```

- 함수를 복제하고 질의 목적에 맞는 이름짓기
```java
public String findMiscreant(List<String> people) {  
    for (String p : people) {  
        if ("조커".equals(p)) {  
            setOfAlarms();  
            return "조커";  
        }  
        if ("사루만".equals(p)) {  
            setOfAlarms();  
            return "사루만";  
        }  
    }  
  
    return "";  
}
```

- 부수효과를 낳는 부분을 제거
```java
public String findMiscreant(List<String> people) {  
    for (String p : people) {  
        if ("조커".equals(p)) {    
            return "조커";  
        }  
        if ("사루만".equals(p)) {
            return "사루만";  
        }  
    }  
  
    return "";  
}
```

- 변경 함수에서 질의 관련 코드를 없앤다.
```java
public void alertForMiscreant(List<String> people) {  
    for (String p : people) {  
        if ("조커".equals(p)) {  
            setOfAlarms();  
            return;  
        }  
        if ("사루만".equals(p)) {  
            setOfAlarms();  
            return;  
        }  
    }
}
```
# 함수 매개변수화하기
#### 배경
두 함수의 로직이 비슷하고 리터럴 값만 다르다면,
다른 값만 매개변수로 받아 처리하는 함수 하나로 합쳐서 중복을 없앨 수 있다.
#### 절차
1. 비슷한 함수 중 하나를 선택
2. 함수 선언 바꾸기로 리터럴들을 매개변수로 추가
3. 함수를 호출하는 곳 모두에 적절한 리터럴 값을 추가
4. 테스트
5. 매개변수로 받은 값을 사용하도록 함수 본문을 수정
6. 비슷한 다른 함수를 호출하는 코드를 찾아 매개변수화된 함수를 호출하도록 하나씩 수정
    1. 매개변수화된 함수가 비슷한 함수와 다르게 동작한다면 그 비슷한 함수의 동작도 처리할 수 있도록 본문 코드를 적절히 수정한 후 진행
#### 예시
```java
public void tenPercentRaise(Person person) {  
    person.setSalary(person.getSalary().multiply(1.1));  
}  
  
public void fivePercentRaise(Person person) {  
    person.setSalary(person.getSalary().multiply(1.05));  
}
```

- raise함수로 대체
```java
public void raise(Person person, double factor) {  
    person.setSalary(person.getSalary().multiply(1 + factor));  
}
```

- 하지만 이렇게 간단히 끝나지 않는 경우도 있음
```java
public Usd baseCharge(int usage) {  
    if (usage < 0) return new Usd(0);  
  
    double amount = bottomBand(usage) * 0.03  
            + middleBand(usage) * 0.05  
            + topBand(usage) * 0.07;  
      
    return new Usd(amount);  
}  
  
public int bottomBand(int usage) {  
    return Math.min(usage, 100);  
}  
  
public int middleBand(int usage) {  
    return usage > 100 ? Math.min(usage, 200) - 100 : 0;  
}  
  
public int topBand(int usage) {  
    return usage > 200 ? usage - 200 : 0;  
}
```

- 비슷한 함수들을 매개변수화하여 통합할 때 먼저 대상 함수 중 하나를 골라 매개변수를 추가한다.
```java
public Usd baseCharge(int usage) {  
    if (usage < 0) return new Usd(0);  
  
    double amount = withinBand(usage, 0, 100) * 0.03  
            + withinBand(usage, 100, 200) * 0.05  
            + withinBand(usage, 200, ) * 0.07;  
  
    return new Usd(amount);  
}

public int withinBand(int usage, int bottom, int top) {  
    return usage > bottom ? Math.min(usage, top) - bottom : 0;  
}
```

- 새로 만든 매개변수화 함수를 호출하도록 바꾼다.
```java
public Usd baseCharge(int usage) {  
    if (usage < 0) return new Usd(0);  
  
    double amount = withinBand(usage, 0, 100) * 0.03  
            + withinBand(usage, 100, 200) * 0.05  
            + withinBand(usage, 200, Integer.MAX_VALUE) * 0.07;  
  
    return new Usd(amount);  
}  
  
public int withinBand(int usage, int bottom, int top) {  
    return usage > bottom ? Math.min(usage, top) - bottom : 0;  
}
```
# 플래그 인수 제거하기
#### 배경
플래그 인수란 호출되는 함수가 실행할 로직을 호출하는 쪽에서 선택하기 위해 전달하는 인수.

플래그 인수를 사용하면 호출할  수 있는 함수들이 무엇이고 어떻게 호출해야하는지 이해하기 어려워진다.

특정한 기능 하나만 수행하는 명시적인 함수를 제공하는 편이 훨씬 깔끔하다.

> [!note]
> bookConcert(customer, CustomerType.PREMIUM) 보단
> premiumBookConcert(customer)이 깔끔

함수 하나에서 플래그 인수를 두 개 이상사용하면 플래그 인수를 써야하는 합당한 근거가 될 수 있다.
플래그 인수 없이 구현하려면 플래그 인수들의 가능한 조합 수만큼 함수를 만들어야 하기 때문!

그런데 플래그 인수가 둘 이상이면 함수 하나가 너무 많은 일을 처리하고 있다는 신호!

그러므로 같은 로직을 조합해내는 더 간단한 함수를 만들 방법을 고민해야한다.

#### 절차
1. 매개변수로 주어질수 있는 값 각각에 대응하는 명시적 함수들을 생성
    1. 주가 되는 함수에 깔끔한 분배 조건문이 포함되어 있다면 **조건문 분해하기**로 명시적 함수들을 생성. 그렇지 않다면 래핑 함수 형태로 만든다.
2. 원래 함수를 호출하는 코드들을 모두 찾아서 각 리터럴 값에 대응되는 명시적 함수를 호출하도록 수정

#### 예시
```java
public static int deliveryDate(Order order, boolean isRush) {  
    if (isRush) {  
        int deliveryTime;  
  
        if (List.of("MA", "CT").contains(order.getDeliveryState())) deliveryTime = 1;  
        else if (List.of("NY", "NH").contains(order.getDeliveryState())) deliveryTime = 2;  
        else deliveryTime = 3;  
  
        return order.getPlaceOn().plusDays(1 + deliveryTime);  
    } else {  
        int deliveryTime;  
  
        if (List.of("MA", "CT", "NY").contains(order.getDeliveryState())) deliveryTime = 2;  
        else if (List.of("ME", "NH").contains(order.getDeliveryState())) deliveryTime = 3;  
        else deliveryTime = 4;  
  
        return order.getPlaceOn().plusDays(2 + deliveryTime);  
    }  
}
```

- 조건문 분해하기
```java
public static int deliveryDate(Order order, boolean isRush) {  
    if (isRush) return rushDeliveryDate(order);  
    else return regularDeliveryDate(order);  
}  
  
public static int rushDeliveryDate(Order order) {  
    int deliveryTime;  
  
    if (List.of("MA", "CT").contains(order.getDeliveryState())) deliveryTime = 1;  
    else if (List.of("NY", "NH").contains(order.getDeliveryState())) deliveryTime = 2;  
    else deliveryTime = 3;  
  
    return order.getPlaceOn().plusDays(1 + deliveryTime);  
}  
  
public static int regularDeliveryDate(Order order) {  
    int deliveryTime;  
  
    if (List.of("MA", "CT", "NY").contains(order.getDeliveryState())) deliveryTime = 2;  
    else if (List.of("ME", "NH").contains(order.getDeliveryState())) deliveryTime = 3;  
    else deliveryTime = 4;  
  
    return order.getPlaceOn().plusDays(2 + deliveryTime);  
}
```
# 객체 통째로 넘기기
#### 배경
하나의 레코드에서 몇개의 값을 인수로 넘기는 것 보다. 레코드 전체를 넘긴다. 레코드 전체를 넘기면 변화에 대응하기 쉽다.

레코드와 함수가 서로 다른 모듈에 있을 경우에는 레코드 자체에 의존하기 때문에 리팩터링을 하지 않는다.

한 객체가 제공하는 기능 중 항상 똑같은 일부만을 사용한느 코드가 많다면, 그 기능만 따로 묶어서 **클래스로 추출**하라는 신호일 수 있다.

#### 절차
1. 매개변수들을 원하는 형태로 받는 빈 함수를 만든다.
    1. 마지막 단계에서 함수의 이름을 변경해야 하니 검색하기 쉬운 이름으로 지어준다.
2. 새 함수의 본문에서는 원래 함수를 호출하도록 하며, 새 매개변수와 원래 함수의 매개변수를 매핑한다.
3. 정적 검사를 수행
4. 모든 호출자가 새 함수를 사용하게 수정한다. 하나씩 수정하며 테스트
    1. 수정 후에는 원래의 매개변수를 만들어내는 코드 일부가 필요 없어질 수 잇따. 따라서 죽은 코드 제거하기로 없앨 수 있다.
5. 호출자를 모두 수정했다면 원래 함수를 인라인한다.
6. 새 함수의 이름을 적절히 수정하고 모든 호출자에 반영한다.

#### 예시
```java
public static String checkWithinRange(Room room) {  
    int low = room.getDayTempRange().getLow();  
    int high = room.getDayTempRange().getHigh();  
  
    HeatingPlan aPlan = new HeatingPlan(new TemperatureRange(10, 20));  
    if (!aPlan.withinRange(low, high))  
        return "방 온도가 지정 범위를 벗어났습니다.";  
  
    return "";  
}

@Getter  
public class HeatingPlan {  
    private final TemperatureRange temperatureRange;  
  
    public HeatingPlan(TemperatureRange temperatureRange) {  
        this.temperatureRange = temperatureRange;  
    }  
  
    public boolean withinRange(int bottom, int top) {  
        return (bottom >= this.temperatureRange.getLow())  
                && (top <= this.temperatureRange.getHigh());  
    }   
}
```

- 범위 객체를 통째로 건내기
```java
public static String checkWithinRange(Room room) {  
    HeatingPlan aPlan = new HeatingPlan(new TemperatureRange(10, 20));  
    
    if (!aPlan.withinRange(room.getDayTempRange()))  
        return "방 온도가 지정 범위를 벗어났습니다.";  
  
    return "";  
}

@Getter  
public class HeatingPlan {  
    private final TemperatureRange temperatureRange;  
  
    public HeatingPlan(TemperatureRange temperatureRange) {  
        this.temperatureRange = temperatureRange;  
    }  
  
    public boolean withinRange(DayTempRange dayTempRange) {  
  
        return (dayTempRange.getLow() >= this.temperatureRange.getLow())  
                && (dayTempRange.getHigh() <= this.temperatureRange.getHigh());  
    }  
}
```

#### 예시: 새 함수를 다른 방식으로 만들기
```java
public static String checkWithinRange(Room room) {  
    int low = room.getDayTempRange().getLow();  
    int high = room.getDayTempRange().getHigh();  
  
    HeatingPlan aPlan = new HeatingPlan(new TemperatureRange(10, 20));  
    if (!aPlan.withinRange(low, high))  
        return "방 온도가 지정 범위를 벗어났습니다.";  
  
    return "";  
}
```

- 함수 추출
```java
public static String checkWithinRange2(Room room) {  
    DayTempRange tempRange = room.getDayTempRange();  
    HeatingPlan aPlan = new HeatingPlan(new TemperatureRange(10, 20));  
    boolean isWithinRange = xxNEWwithinRange(aPlan, tempRange);  
  
    if (!isWithinRange)  
        return "방 온도가 지정 범위를 벗어났습니다.";  
  
    return "";  
}  
  
private static boolean xxNEWwithinRange(HeatingPlan aPlan, DayTempRange tempRange) {  
    int low = tempRange.getLow();  
    int high = tempRange.getHigh();  
      
    boolean isWithinRange = aPlan.withinRange(low, high);  
    return isWithinRange;  
}
```

- 함수 옮기기 및 인라인
```java
public static String checkWithinRange2(Room room) {  
    DayTempRange tempRange = room.getDayTempRange();  
    HeatingPlan aPlan = new HeatingPlan(new TemperatureRange(10, 20));  
  
    if (aPlan.xxNEWwithinRange(aPlan, tempRange))  
        return "방 온도가 지정 범위를 벗어났습니다.";  
  
    return "";  
}
```

# 매개변수를 질의 함수로 바꾸기
#### 배경
매개변수 목록은 함수의 변동 요인을 모아 놓는곳. 즉, 함수의 동작에 변화를 줄 수 있는 일차적 수단

매개변수 목록에서도 중복은 피하는게 좋다. 짧을수록 좋다.

피호출 함수가 스스로 쉽게 결정할 수 있는 값을 매개변수로 건네는 것도 일종의 중복

> [!note]
> 매개변수가 있다면 결정 주체가 호출자가 되고, 매개변수가 없다면 피호출 함수가 된다.
> 아 뭔말이야..

매개변수를 제거하면 피호출 함수에 원치 않는 의존성이 생길때는 매개변수를 질의 함수로 바꾸지 않는다.

#### 절차
1. 필요하다면 대상 매개변수의 값을 계산하는 코드를 별도 함수로 추출 해놓는다.
2. 함수 본문에서 대상 매개변수로의 참조를 모두 찾아서 그 매개변수의 값을 만들어주는 표현식을 참조하도록 바꾼다. 하나 수정할 때마다 테스트한다.
3. 함수 선언 바꾸기로 대상 매개변수를 없앤다.
#### 예시
```java
public class Order {  
    private final int quantity;  
    private final int itemPrice;  
  
    public Order(int quantity, int itemPrice) {  
        this.quantity = quantity;  
        this.itemPrice = itemPrice;  
    }  
  
    public double finalPrice() {  
        int basePrice = quantity * itemPrice;  
  
        int discountLevel;  
  
        if (this.quantity > 100) discountLevel = 2;  
        else discountLevel = 1;  
  
        return this.discountedPrice(basePrice, discountLevel);  
    }  
  
    private double discountedPrice(int basePrice, int discountLevel) {  
        return switch (discountLevel) {  
            case 1 -> basePrice * 0.95;  
            case 2 -> basePrice * 0.8;  
            default -> throw new IllegalStateException("Unexpected value: " + discountLevel);  
        };  
    }  
}
```

- 임시 변수를 질의 함수로 바꾸기
```java
public class Order {  
    private final int quantity;  
    private final int itemPrice;  
  
    public Order(int quantity, int itemPrice) {  
        this.quantity = quantity;  
        this.itemPrice = itemPrice;  
    }  
  
    public double finalPrice() {  
        int basePrice = quantity * itemPrice;  
  
        return this.discountedPrice(basePrice);  
    }  
  
    private double discountedPrice(int basePrice) {  
        return switch (this.discountLevel()) {  
            case 1 -> basePrice * 0.95;  
            case 2 -> basePrice * 0.8;  
            default -> throw new IllegalStateException("Unexpected value: " + this.discountLevel());  
        };  
    }  
      
    private int discountLevel() {  
        return (this.quantity > 100) ? 2 : 1;  
    }  
}
```
# 질의 함수를 매개변수로 바꾸기
#### 배경
대상 함수가 더 이상 매개변수화하려는 특정 원소에 의존하길 원치 않을 때, 두 극단 사이에서 적절한 균형을 찾아야 한다.

1. 모든 것을 매개변수로 바꿔 아주 길고 반복적인 매개변수 목록을 만드는 것
2. 함수들끼리 많은 것을 공유하여 수많은 결합을 만들어내는 것

모듈을 개발할 때 순수 함수들을 따로 구분하고, 프로그램의 입출력과 기타 가변 원소들을 다루는 로직으로 순수한 함수들의 겉을 감싸는 패턴을 많이 활용

단점은 질의 함수를 매개변수로 바꾸면 어떤값을 제공할지 호출자가 알아야 함
결국 호출자가 복잡해진다.

#### 절차
1. 변수 추출하기로 질의 코드를 함수 본문의 나머지 코드와 분리
2. 함수 본문 중해당 질의를 호출하지 않는 코드들을 별도 함수로 추출
    1. 검색하기 쉬운 이름으로 짓는다.
3. 방금 만든 변수를 인하인하여 제거
4. 원래 함수도 인라인
5. 새 함수의 이름을 원래 함수의 이름으로 고쳐준다.

#### 예시
```java
public class HeatingPlan {  
    private final int max;  
    private final int min;  
    private final Thermostat thermostat;  
  
    public HeatingPlan(int max, int min, Thermostat thermostat) {  
        this.max = max;  
        this.min = min;  
        this.thermostat = thermostat;  
    }  
  
    public int targetTemperature() {  
        if (thermostat.selectedTemperature() > max) return this.max;  
        else if (thermostat.selectedTemperature() < min) return this.min;  
        else return thermostat.selectedTemperature();  
    }  
}
```

```java
public class HeatingPlan {  
    private final int max;  
    private final int min;  
    private final Thermostat thermostat;  
  
    public HeatingPlan(int max, int min, Thermostat thermostat) {  
        this.max = max;  
        this.min = min;  
        this.thermostat = thermostat;  
    }  
  
    public int targetTemperature() {  
        return targetTemperature(thermostat.selectedTemperature());  
    }  
  
    private int targetTemperature(int selectedTemperature) {  
        if (selectedTemperature > max) return this.max;  
        else if (selectedTemperature < min) return this.min;  
        else return selectedTemperature;  
    }  
}
```
# 세터 제거하기
#### 배경
세터가 없고 생성자에서만 설정되면 수정하지 않겠다는 의도가 확실해지고, 변경될 가능성이 봉쇄된다.
세터 제거가 필요한 상황
1. 무조건 접근자 메서드를 통해서만 필드를 다루려 할 때
2. 생성 스크립트를 사용해 객체를 생성할 때
#### 절차
1. 설정해야 할 값을 생성자에서 받지 않는다면 그 값을 받ㅇ르 매개변수를 생성자에 추가한다. 그런다음 생성자 안에서 적절한 세터를 호출한다.
    1. 세터 여러 개를 제거하려면 해당값 모두를 한꺼번에 생성자에 추가한다. 그러면 이후 과정이 간소해진다.
2. 생성자 밖에서 세터를 호출하는 곳을 찾아서 제거하고, 대신 새로운 생성잘르 사용하도록 한다. 하나 수정할 때마다 테스트한다.
    1. 새로운 객체를 생성하는 방식으로는 세터 호출을 대체할 수 없다면 이 리팩터링을 취소한다.
3. 세터 메서드를 인라인한다. 가능하다면 해당 필드를 불변으로 만든다.
4. 테스트
#### 예시
```java
@Getter  
@NoArgsConstructor  
public class Person {  
    private String name;  
    private String id;  
  
    public void setName(String name){  
        this.name = name;  
    }  
  
    public void setId(String id) {  
        this.id = id;  
    }  
}
```

- id 세터 제거
```java
@Getter  
public class Person {  
    private String name;  
    private String id;  
  
    public Person(String id) {  
        this.id = id;  
    }  
  
    public void setName(String name){  
        this.name = name;  
    }  
}
```

# 생성자를 팩터리 함수로 바꾸기
#### 배경
자바의 생성자는 반드시 그 생성자를 정의한 클래스의 인스턴스를 반환해야한다. 서브클래스의 인스턴스나 프락시를 반환할 수는 없다.

생성자의 이름도 고정되어, 기본 이름보다 더 적절한 이름이 잇어도 사용할 수 없다.

생성자를 호출하려면 특별한 연산자를 사용해야 해서 일반 함수가 오길 기대하는 자리에는 쓰기 어렵다.

팩토리 함수는 이런 제약이 없다.

#### 절차
1. 팩토리 함수를 만든다. 팩토리 함수의 본문에서는 원래의 생성자를 호출한다.
2. 생성자를 호출하던 코드를 팩토리 함수 호출로 바꾼다.
3. 하나씩 수정할 때마다 테스트
4. 생성자의 가시 범위가 최소가 되도록 제한한다.
#### 예시
```java
public class Employee {  
    private final String name;  
    private final String typeCode;  
  
    public Employee(String name, String typeCode) {  
        this.name = name;  
        this.typeCode = typeCode;  
    }  
      
    public static Map<String, String> legalTypeCodes() {  
        return Map.of(  
                "E", "Engineer",  
                "M", "Manager",  
                "S", "Salesperson"  
        );  
    }
}
```

- 팩토리 매소드 만들기
```java
public class Employee {  
    private final String name;  
    private final String typeCode;  
  
    private Employee(String name, String typeCode) {  
        this.name = name;  
        this.typeCode = typeCode;  
    }  
  
    public static Map<String, String> legalTypeCodes() {  
        return Map.of(  
                "E", "Engineer",  
                "M", "Manager",  
                "S", "Salesperson"  
        );  
    }  
  
    public static Employee createEnginner(String name) {  
        return new Employee(name, "E");  
    }  
  
    public static Employee createManager(String name) {  
        return new Employee(name, "M");  
    }  
  
    public static Employee createSalesperson(String name) {  
        return new Employee(name, "S");  
    }  
  
}
```

# 함수를 명령으로 바꾸기
#### 배경
함수를 위한 객체를 만들면 더 유용한 상황이 있다. 이런 객체를 명령 객체 또는 명령이라 한다.

명령은 함수보다 훨씬 유연하게 함수를 제어하고 표현할 수 있다.

명령은 되돌리기 같은 보조 연산을 제공할 수 있으며, 수명주기를 더 정밀하게 제어하는 데 필요한 매개변수를 만들어주는 메서드도 제공할 수 있다.

객체는 지원하지만 일급 함수를 지원하지 않는 프로그래밍 언어를 사용할 때는 명령을 이용해 일급 함수의 기능 대부분을 흉내 낼 수 있다.

중첩 함수를 지원하지 않는 언어에서도 메서드와 필드를 이용해 복잡한 함수를 잘게 쪼갤 수 있고, 이렇게 쪼갠 메서드들을 테스트와 디버깅에 직접 이용할 수 있다.

유연성은 복잡성을 키운다. 그렇기 때문에 명령과 일급 함수 중 선택해야 한다면 일급 함수를 선택한다.
#### 절차
1. 대상 함수의 기능을 옮길 빈 클래스를 만든다. 클래스 이름은 함수 이름에 기초해 짓는다.
2. 방금 생성한 빈 클래스로 함수를 옮긴다.
    1. 리팩터링이 끝날 때까지는 원래 함수를 전달 함수 역할로 남겨둔다.
    2. 명령 관련 이름은 사용하는 프로그래밍 언어의 명명규칙을 따른다. 규칙이 없다면 execute나 call 같이 명령의 실행 함수에 흔히 쓰이는 이름을 택하자
3. 함수의 인수들 각각은 명령의 필드로 만들어 생성자를 통해 설정할지 고민해본다.

#### 예시
```java
public static int score(Candidate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide) {  
    int result = 0;  
    int healthLevel = 0;  
    boolean highMedicalRiskFlag = false;  
  
    if (medicalExam.isSmoker()) {  
        healthLevel += 10;  
        highMedicalRiskFlag = true;  
    }  
  
    String certificationGrade = "regular";  
    if (scoringGuide.stateWithLowCertification(candidate.isOriginState())) {  
        certificationGrade = "low";  
        result -= 5;  
    }  
  
    result -= Math.max(healthLevel - 5, 0);  
    return result;  
}
```

- 함수를 명령으로 바꾸기
```java
public class Scorer {  
    private final Candidate candidate;  
    private final MedicalExam medicalExam;  
    private final ScoringGuide scoringGuide;  
      
    public Scorer(Candidate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide) {  
        this.candidate = candidate;  
        this.medicalExam = medicalExam;  
        this.scoringGuide = scoringGuide;  
    }  
  
    public int execute() {  
        int result = 0;  
        int healthLevel = 0;  
        boolean highMedicalRiskFlag = false;  
  
        if (medicalExam.isSmoker()) {  
            healthLevel += 10;  
            highMedicalRiskFlag = true;  
        }  
  
        String certificationGrade = "regular";  
        if (scoringGuide.stateWithLowCertification(candidate.isOriginState())) {  
            certificationGrade = "low";  
            result -= 5;  
        }  
  
        result -= Math.max(healthLevel - 5, 0);  
        return result;  
    }  
}

public static int score(Candidate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide) {  
    return new Scorer(candidate, medicalExam, scoringGuide).execute();  
}
```
# 명령을 함수로 바꾸기
#### 배경
명령 객체는 복잡한 연산을다룰 수 있는 강력한 메커니즘을 제공

큰 연산 하나를 여러 개의 작은 메서드로 쪼개고 필드를 이용해 쪼개진 메서드 끼리 정보를 공유할 수 있다.

로직이 크게 복잡하지 않다면 명령 객체는 장점보다 단점이 크니 함수로 바꿔주는 게 낫다.
#### 절차
1. 명령을 생성하는 코드와 명령의 실행 메서드를 호출하는 코드를 함께 함수로 추출한다.
    1. 이 함수가 바로 명령을 대체할 함수다.
2. 명령의 실행 함수가 호출하는 보조 메서드를 각각을 인라인 한다.
    1. 보조 메서드가 값을 반환한다면 함수 인라인에 앞서 변수 추출하기를 적용한다.
3. 함수 선언 바꾸기를 적용하여 생성자의 매개변수 모두를 명령의 실행 메서드로 옮긴다.
4. 명령의 실행 메서드에서 참조하는 필드들 대신 대응하는 매개변수를 사용하게끔 바꾼다. 하나씩 수정할 때마다 테스트한다.
5. 생성자 호출과 명령의 실행 메서드 호출을 호출자 안으로 인라인 한다.
6. 테스트
7. 죽은 코드 제거하기로 명령 클래스를 없앤다.

#### 예시
```java
@Getter  
public class Customer {  
    private final int baseRate;  
  
    public Customer(int baseRate) {  
        this.baseRate = baseRate;  
    }  
}

@Getter  
public class Provider {  
    private final int connectionCharge;  
  
    public Provider(int connectionCharge) {  
        this.connectionCharge = connectionCharge;  
    }  
}

public class ChargeCalculator {  
    private final Customer customer;  
    private final int usage;  
    private final Provider provider;  
  
    public ChargeCalculator(Customer customer, int usage, Provider provider) {  
        this.customer = customer;  
        this.usage = usage;  
        this.provider = provider;  
    }  
  
    public int baseCharge() {  
        return this.customer.getBaseRate() * this.usage;  
    }  
  
    public int charge() {  
        return this.baseCharge() + this.provider.getConnectionCharge();  
    }  
}

public static int calculateCharge(Customer customer, int usage, Provider provider) {  
    return new ChargeCalculator(customer, usage, provider).charge();  
}
```

- 명령을 함수로 바꾸기
```java
@NoArgsConstructor  
public class ChargeCalculator {  
  
    public static int charge(Customer customer, int usage, Provider provider) {  
        int baseCharge = customer.getBaseRate() * usage;  
        return baseCharge + provider.getConnectionCharge();  
    }  
}

private static int charge(Customer customer, int usage, Provider provider) {  
    return ChargeCalculator.charge(customer, usage, provider);  
}
```


# 수정된 값 반환하기
#### 배경
데이터가 어떻게 수정되는지 추적하는 일은 어렵다.

같은 데이터 블록을 읽고 수정하는 코드가 여러 곳이라면 데이터가 수정되는 흐름과 코드의 흐름을 일치시키기 상당히 어렵다.

어느 함수가 무슨 일을 하는지 쉽게 알 수 있게 하는 일이 대단히 중요하다.

데이터가 수정됐음을 알려주는 좋은 방법이 있다. 그렇다면 호출자 코드를 읽을 때 변수가 갱신됨을 분명히 인지하게 된다.

#### 절차
1. 함수가 수정된 값을 반환하게 하여 호출자가 그 값을 자신의 변수에 저장하게 한다.
2. 테스트
3. 피호출 함수 안에 반환할 값으 가리키는 새로운 변수를 선언
    1. 이 작업이 의도대로 이뤄졌는지 검사하고 싶으면 호출자에서 초깃값을 수정하자. 제대로 처리햇다면 수정된 값이 무시된다.
4. 테스트
5. 계산이 선언과 동시에 이뤄지도록 통합한다.
    1. 프로그래밍 언어에서 지원한다면 이 변수를 불변으로 지정
6. 테스트
7. 피호출 함수의 변수 이름을 새 역할에 어울리도록 바꿔준다.
8. 테스트

#### 예시
- java는 void 함수로 갱신할 방법이 없어 해당 예제는 결과만 작성했다.
```java
public static double gpsList(Points points) {  
    double totalAscent = calculateAscent(points);  
    double totalTime = calculateTime();  
    double totalDistance = calculateDistance();  
      
    double pace = totalTime / 60 / totalDistance;  
  
    return pace;  
}
```

# 오류 코드를 예외로 바꾸기
#### 배경
예외는 프로그래밍 언어에서 제공하는 독립적인 오류 처리 메커니즘이다. 오류가 발견되면 예외를 던진다.

예외를 사용하면 오류 코드를 일일이 검사하거나 오류를 식별해 콜스택 위로 던지는 일을 신경 쓰지 않아도 된다.

예외는 정확히 예상 밖의 동작일 때만 쓰여야 한다.

#### 절차
1. 콜스택 상위에 해당 예외를 처리할 예외 핸들러를 작성한다.
    1. 이 핸들러는 처음에는 모든 예외를 다시 던지게 해둔다.
    2. 적절한 처리를 해주는 핸들러가 이미 있다면 지금의 콜스택도 처리할 수 있도록 확장한다.
2. 테스트
3. 해당 오류 코드를 대체할 예외와 그 밖의 예외를 구분할 식별 방법을 찾는다.
    1. 사용하는 프로그램이 언어에 맞게 선택. 대부분 언어에서는 서브클래스를 사용하면 된다.
4. 정적검사를 수행
5. catch절을 수정하여 직접 처리할 수 있는 예외는 적절히 대처학 ㅗ그렇지 않은 예외는 다시 던진다.
6. 테스트
7. 오류 코드를 반환하는 곳 모두에서 예외를 던지도록 수정
8. 모두 수정햇다면 그 오류 코드를 콜스택 위로 전달하는 코드를 모두 제거
    1. 먼저 오류 코드를 검사하는 부분을 함정으로 바꾼 다음, 함정에 걸려들지 않는지 테스트

#### 예시
```java

public static void azsdfasdfasdfasdf() {  
    org.intaehwang.chapter11.replcaeErrorCodeWithException.Order order = new org.intaehwang.chapter11.replcaeErrorCodeWithException.Order("ko");  
      
    int status = calculateShippingCosts(order);  
    if (status < 0) log.info("에러 추가");  
}

public static int calculateShippingCosts(org.intaehwang.chapter11.replcaeErrorCodeWithException.Order order) {  
    int shippingRules = localShippingRules(order.getCountry());  
  
    if (shippingRules < 0) return shippingRules;  
    return 0;  
}  
  
public static int localShippingRules(String country) {  
    int data = CountryData.shippingRules(country);  
    if (data > 0) return new ShippingRules(data).role();  
    else return -23;  
}

@Getter  
public class Order {  
    private final String country;  
  
    public Order(String country) {  
        this.country = country;  
    }  
}

public class CountryData {  
  
    public static int shippingRules(String country) {  
  
        if ("ko".equals(country)) return 9999;  
        return -9999;  
    }  
}

public class ShippingRules {  
  
    private final int role;  
  
    public ShippingRules(int role) {  
        this.role = role;  
    }  
  
    public int role() {  
        return this.role;  
    }  
}
```

- 예외 처리
```java
public static void azsdfasdfasdfasdf() {  
    org.intaehwang.chapter11.replcaeErrorCodeWithException.Order order = new org.intaehwang.chapter11.replcaeErrorCodeWithException.Order("ko");  
      
    try {  
        calculateShippingCosts(order);  
    } catch (Exception e) {  
        if (e instanceof IllegalArgumentException) {  
            log.info("에러 추가");  
        } else {  
            throw new RuntimeException("error");  
        }  
    }  
}  
  
public static int calculateShippingCosts(org.intaehwang.chapter11.replcaeErrorCodeWithException.Order order) {  
    int shippingRules = localShippingRules(order.getCountry());  
  
    if (shippingRules < 0) return shippingRules;  
    return 0;  
}  
  
public static int localShippingRules(String country) {  
    int data = CountryData.shippingRules(country);  
    if (data > 0) return new ShippingRules(data).role();  
    else throw new IllegalArgumentException("error");  
}
```
# 예외를 사전확인으로 바꾸기
#### 배경
예외도 과용되곤 한다. 말 그래도 예외적으로 동작할 때만 쓰여야 한다. 함수 수행 시 문제가 될 수 있는 조건을 함수 호출 전에 검사할 수 있다면  예외를 던지는 대신 호출하는 곳엣 ㅓ조건을 검사한다.

#### 절차
1. 예외를 유발하는 상황을 검사할 수 있는 조건문을 추가한다. catch 블록의 코드를 조건문의 조건절 중 하나로 옮기고 남은 try 블록의 코드를 다른 조건절로 옮긴다.
2. catch 블록에 어서션을 추가하고 테스트 한다.
3. try 문과 catch 블록을 제거한다.
4. 테스트한다.

#### 예시
```java
public class ResourcePool {  
    private Deque<Resource> available;  
    private List<Resource> allocated;  
  
    public ResourcePool(Deque<Resource> available, List<Resource> allocated) {  
        this.available = available;  
        this.allocated = allocated;  
    }  
  
    public Resource get() {  
        Resource result;  
  
        try {  
            result = available.pop();  
            allocated.add(result);  
        } catch (NoSuchElementException e) {  
            result = Resource.create();  
            allocated.add(result);  
        }  
  
        return result;  
    }  
}
```

- try catch 제거
```java
public class ResourcePool {  
    private Deque<Resource> available;  
    private List<Resource> allocated;  
  
    public ResourcePool(Deque<Resource> available, List<Resource> allocated) {  
        this.available = available;  
        this.allocated = allocated;  
    }  
  
    public Resource get() {  
        Resource result;  
          
        if (available.isEmpty()) {  
            result = Resource.create();  
            allocated.add(result);  
        } else {  
            result = available.pop();  
            allocated.add(result);  
        }  
  
  
        return result;  
    }  
}
```