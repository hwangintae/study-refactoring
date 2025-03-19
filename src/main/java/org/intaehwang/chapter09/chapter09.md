# 변수 쪼개기
#### 배경
루프 변수는 반복문을 한 번 돌 때마다 값이 바뀐다.

수집 변수(collection variable)는 메서드가 동작하는 중간중간에 값을 저장한다.

그 외 변수들은 나중에 쉽게 참조하려는 목적으로 흔히 쓰인다. 이런 변수는 값을 단 한 번만 대입해야 한다.
대입이 두 번 이상 이뤄진다면 여러 가지 역할을 수행한다는 신호다.
#### 절차
1. 변수를 선언한 곳과 값을 처음 대입하는 곳에서 변수 이름을 바꾼다.
    1. 이후의 대입이 항상 i = i + <무엇> 형태라면 수집 변수이므로 쪼개면 안 된다.
2. 가능하면 immutable으로 선언한다.
3. 이 변수에 두 번째로 값을 대입하는 곳 앞까지의 모든 참조(이 변수가 쓰인 곳)를 새로운 변수 이름으로 바꾼다.
4. 두 번째 대입 시 변수를 우너래 이름으로 다시 선언한다.
5. 테스트
6. 반복
#### 예시
- acc 변수에 값이 두 번 대입된다.
```java
@Getter  
public class Scenario {  
    private final int primaryForce;  
    private final int mass;  
    private final int delay;  
    private final int secondaryForce;  
  
    public Scenario(int primaryForce, int mass, int delay, int secondaryForce) {  
        this.primaryForce = primaryForce;  
        this.mass = mass;  
        this.delay = delay;  
        this.secondaryForce = secondaryForce;  
    }  
}

public double distanceTravelled(Scenario scenario, int time) {  
    double result;  
    double acc = 1.0 * scenario.getPrimaryForce() / scenario.getMass();  
    int primaryTime = Math.min(time, scenario.getDelay());  
  
    result = 0.5 * acc * primaryTime * primaryTime;  
  
    int secondaryTime = time - scenario.getDelay();  
    if (secondaryTime > 0) {  
        double primaryVelocity = acc * scenario.getDelay();  
  
        acc = 1.0 * (scenario.getPrimaryForce() + scenario.getSecondaryForce()) / scenario.getMass();  
  
        result += primaryVelocity * secondaryTime + 0.5 * acc * secondaryTime * secondaryTime;  
    }  
  
    return result;  
}
```

- acc를 primaryAcceleration, secondaryAcceleration로 쪼개기
```java
public double distanceTravelled(Scenario scenario, int time) {  
    double result;  
    double primaryAcceleration = 1.0 * scenario.getPrimaryForce() / scenario.getMass();  
    int primaryTime = Math.min(time, scenario.getDelay());  
  
    result = 0.5 * primaryAcceleration * primaryTime * primaryTime;  
  
    int secondaryTime = time - scenario.getDelay();  
    if (secondaryTime > 0) {  
        double primaryVelocity = primaryAcceleration * scenario.getDelay();  
  
        double secondaryAcceleration = 1.0 * (scenario.getPrimaryForce() + scenario.getSecondaryForce()) / scenario.getMass();  
  
        result += primaryVelocity * secondaryTime + 0.5 * secondaryAcceleration * secondaryTime * secondaryTime;  
    }  
  
    return result;  
}
```

#### 예시: 입력 매개변수의 값을 수정할 때
```java
public int discount(int inputValue, int quantity) {  
    if (inputValue > 50) inputValue = inputValue - 2;  
    if (quantity > 100) inputValue = inputValue - 1;  
      
    return inputValue;  
}

public int discount(int inputValue, int quantity) {  
    int result = inputValue;  
      
    if (inputValue > 50) result = result - 2;  
    if (quantity > 100) result = result - 1;  
  
    return result;  
}
```
# 필드 이름 바꾸기
#### 배경
데이터 구조가 중요한 만큼 반드시 깔끔하게 관리해야 한다.

다른 요소와 마찬가지로 개발을 진행할수록 데이터를 더 잘 이해하게 된다.
#### 절차
1. 레코드의 유효 범위가 제한적이라면 필드에 접근하는 모든 코드를 수정한 후 테스트
2. 레코드가 캡슐화되지 않았다면 우선 레코드를 캡슐화
3. 캡슐화된 객체 안의 private 필드명을 변경하고, 그에 맞게 내부 메서드들을 수정
4. 테스트
5. 생성자의 매개변수 중 필드와 이름이 겹치는 게 있다면 함수 선언 바꾸기로 변경
6. 접근자들의 이름도 바꿔준다.
#### 예시
```java
@Getter  
@Setter  
public class Organization {  
    private String name;  
    private String country;  
  
    public Organization(String name, String country) {  
        this.name = name;  
        this.country = country;  
    }  
}

@Getter  
@Setter  
public class Organization {  
    private String title;  
    private String country;  
  
    public Organization(String title, String country) {  
        this.title = title;  
        this.country = country;  
    }  
}
```

> [!note]
> - 데이터 구조를 불변으로 만들 수 있는 프로그래밍 언어의 경우 캡슐화 대신 데이터 구조의 값을 복제해 새로운 이름으로 선언한다.
> - 그런 다음 사용하는 곳을 찾아 하나씩 새 데이터를 사용하도록 수정하고, 마지막으로 원래의 데이터 구조를 제거한다.
# 파생 변수를 질의 함수로 바꾸기
#### 배경
- 가변 데이터는 소프트웨어에 문제를 일으키는 원인 중 하나다.
- 가변 데이터는 서로 다른 두 코드를 이상한 방식으로 결합 하기도 한다
- 가변데이터를 완전히 배제할수는 없지만 가변 데이터의 유효 범위를 가능한 작게 해야한다.

> [!note]
> 예외적으로 피연산자 데이터가 불변이면 계산 결과도 일정하므로 역시 불변으로 만들 수 있다.
> 새로운 데이터 구조를 생성하는 변형 연산이라면 계산 코드로 대체할 수 있더라도 그래도 두는 것도 좋다.

변형 연산
1. 데이터 구조를 감싸며 그 데이터에 기초한 계산 결과를 속성으로 제공하는 객체
2. 데이터 구조를 받아 다른 데이터 구조로 변환해 반환하는 함수

#### 절차
1. 변수 값이 갱신ㅚ는 지점을 모두 찾는다. 필요하면 **변수 쪼개기**를 활용해 각 갱신 지점에서 변수를 분리한다.
2. 해당 변수의 값을 계산해주는 함수를 만든다
3. 해당 변수가 사용되는 모든 곳에 **어서션을 추가**하여 함수의 계산 결과가 변수의 값과 같은지 확인한다.
    1. 필요하면 **변수 캡슐화하기**를 적용하여 어서션이 들어갈 장소를 마련해준다.
4. 테스트
5. 변수를 읽는 코드를 모두 함수 호출로 대체한다.
6. 테스트
7. 변수를 선언하고 갱신하는 코드를 **죽은 코드 제거하기**로 없앤다.
#### 예시
```java
public class ProductionPlan {  
    private int production;  
    private final List<Adjustment> adjustments;  
      
    public ProductionPlan(int production, List<Adjustment> adjustments) {  
        this.production = production;  
        this.adjustments = adjustments;  
    }  
      
    public int getProduction() {  
        return this.production;  
    }  
      
    public void applyAdjustment(Adjustment anAdjustment) {  
        this.adjustments.add(anAdjustment);  
        this.production += anAdjustment.getAmount();  
    }  
}
```

- 어서션을 추가해 테스트 해본다.
```java
public class ProductionPlan {  
    // ...
  
    public int getProduction() {  
        assert this.production == calculatedProduction();  
        return this.production;  
    }  
  
    // ...
  
    public int calculatedProduction() {  
        return adjustments.stream()  
                .map(Adjustment::getAmount)  
                .reduce(0, Integer::sum);  
    }  
}

@Test  
public void test() {  
    // given  
    int production = 10;  
    List<Adjustment> adjustments = List.of(new Adjustment(10));  
  
    ProductionPlan productionPlan = new ProductionPlan(production, adjustments);  
  
    // when  
    int result = productionPlan.getProduction();  
  
    // then  
    assertThat(production).isEqualTo(result);  
}

@Test  
public void emptyList() {  
    // given  
    int production = 0;  
    List<Adjustment> adjustments = List.of();  
  
    ProductionPlan productionPlan = new ProductionPlan(adjustments);  
  
    // when  
    int result = productionPlan.getProduction();  
  
    // then  
    assertThat(production).isEqualTo(result);  
}
```

- 어서션이 실패하지 않으면 계산 결과를 직접 반환하도록 수정
```java
public class ProductionPlan {  
    // ...
  
    public int getProduction() {  
        return adjustments.stream()  
                .map(Adjustment::getAmount)  
                .reduce(0, Integer::sum);  
    }  
  
    public void applyAdjustment(Adjustment anAdjustment) {  
        this.adjustments.add(anAdjustment);  
        this.production += anAdjustment.getAmount();  
    }
}
```

- 죽은 코드 제거
```java
public class ProductionPlan {  
    private final List<Adjustment> adjustments;  
  
    public ProductionPlan(List<Adjustment> adjustments) {  
        this.adjustments = adjustments;  
    }  
  
    public int getProduction() {  
        return adjustments.stream()  
                .map(Adjustment::getAmount)  
                .reduce(0, Integer::sum);  
    }  
  
    public void applyAdjustment(Adjustment anAdjustment) {  
        this.adjustments.add(anAdjustment);  
    }  
}
```

#### 예시: 소스가 둘 이상일 때
> [!note]
> 예제코드인 JS에서는 production의 초기값이 0이 아니면 어서션 코드에서 실패한다고 나와있는데...
> java에서는 production의 초기값이 없을 경우 0으로 자동 주입되어 성공한다.
> type이 Integer일 경우 null 이므로 예제에 나온 0을 더해도 안된다.
>
> 굳이 Integer를 사용할 경우 null 인 경우 0으로 초기화 하는 과정을 추가하면 된다.

- 테스트 디버깅 결과
  ![img|700](https://github.com/user-attachments/assets/dd068440-cd15-4e7b-a154-d5608e62828b)

```java
public class ProductionPlan {  
    private int production;  
    private List<Adjustment> adjustments = new ArrayList<>();  
  
    public ProductionPlan() {}
  
    public int getProduction() {  
        assert this.production == calculatedProduction();  
        return this.production;  
    }  
  
    public void applyAdjustment(Adjustment anAdjustment) {  
        this.adjustments.add(anAdjustment);  
        this.production += anAdjustment.getAmount();  
    }  
  
    public int calculatedProduction() {  
        return adjustments.stream()  
                .map(Adjustment::getAmount)  
                .reduce(0, Integer::sum);  
    }  
}

@Test  
public void noArgsConstructor() {  
    // given  
    ProductionPlan productionPlan = new ProductionPlan();  
  
    // when  
    int result = productionPlan.getProduction();  
  
    // then  
    assertThat(result).isEqualTo(0);  
}

public class ProductionPlan {  
    private Integer production;  
    private List<Adjustment> adjustments = new ArrayList<>();  
  
    public ProductionPlan() {}  
  
    public int getProduction() {  
        if (this.production == null) this.production = 0;  
        assert this.production == calculatedProduction();  
        return this.production;  
    }  

	// ...
}
```

# 참조를 값으로 바꾸기
#### 배경
객체(데이터 구조)를 다른 객체(데이터 구조)에 중첩하면 내부 객체를 참조 혹은 값으로 취급할 수 있다.

**참조냐 값이냐의 차이는 내부 객체의 속성을 갱신하는 방식에서 가장 극명하게 드러난다.**

참조로 다루는 경우에는 내부 객체는 그대로 둔 채 그 객체의 속성만 갱신하며, 값으로 다루는 경우에는 새로운 속을 담은 객체로 기존 내부 객체를 통째로 대체한다.

필드를 값으로 다룬다면 내부 객체의 클래스를 수정하여 값 객체(Value Object)로 만들 수 있다.

VO는 대체로 자유롭게 활용하기 좋은데, **특히 불변이기 때문이다.**

값을 복제해 이곳저곳에서 사용하더라도 **서로 간의 참조를 관리하지 않아도 된다.**

VO는 분산 시스템과 동시성 시스템에서 특히 유용하다.
#### 절차
1. 후보 클래스가 불변인지 혹은 불변이 될 수 있는지 확인
2. 각각의 **세터를 하나씩 제거**한다.
3. VO의 필드들을 사용하는 동치성 비교 메서드를 만든다.
    1. 대부분의 언어는 이런 상황에 사용할 수있도록 오버라이딩 가능한 동치성 비교 메서드를 제공한다. 동치성 비교 메서드를 오버라이드할 때는 보통 해시코드 생성 메서드로 함께 오버라이드 해야한다.
#### 예시
```java
public class Person {  
    private final TelephoneNumber telephoneNumber = new TelephoneNumber();  
      
    public String getOfficeAreaCode() {  
        return this.telephoneNumber.getAreaCode();  
    }  
      
    public void setOfficeAreaCode(String areaCode) {  
        this.telephoneNumber.setAreaCode(areaCode);  
    }  
      
    public String getOfficeNumber() {  
        return this.telephoneNumber.getNumber();  
    }  
      
    public void setOfficeNumber(String number) {  
        this.telephoneNumber.setNumber(number);  
    }  
}

@Getter  
@Setter  
public class TelephoneNumber {  
    private String areaCode;  
    private String number;  
  
    public TelephoneNumber() {}  
}
```

- TelephoneNumber 세터 제거 및 생성자를 이용한 필드 초기화
> [!note]
> 큰 이유 없이 record를 연습겸 사용해봤다.

```java
public record TelephoneNumber(String areaCode, String number) {  
}

public class Person {  
    private TelephoneNumber telephoneNumber;  
  
    public Person(TelephoneNumber telephoneNumber) {  
        this.telephoneNumber = telephoneNumber;  
    }  
  
    public String getOfficeAreaCode() {  
        return this.telephoneNumber.areaCode();  
    }  
  
    public void setOfficeAreaCode(String areaCode) {  
        this.telephoneNumber = new TelephoneNumber(
	        areaCode, this.telephoneNumber.number()
        );  
    }  
  
    public String getOfficeNumber() {  
        return this.telephoneNumber.number();  
    }  
  
    public void setOfficeNumber(String number) {  
        this.telephoneNumber = new TelephoneNumber(
	        this.telephoneNumber.areaCode(), number
		);  
    }  
}
```

- 참조 기반 동치성을 값 기반 동치성으로 대체
```java
public record TelephoneNumber(String areaCode, String number) {  
  
    @Override  
    public boolean equals(Object o) {  
        if (this == o) return true;  
        if (o == null || getClass() != o.getClass()) return false;  
        TelephoneNumber that = (TelephoneNumber) o;  
        return Objects.equals(number, that.number) && Objects.equals(areaCode, that.areaCode);  
    }  
  
    @Override  
    public int hashCode() {  
        return Objects.hash(areaCode, number);  
    }  
}

@Test  
public void isEquals() {  
    TelephoneNumber t1 = new TelephoneNumber("312", "555-0142");  
    TelephoneNumber t2 = new TelephoneNumber("312", "555-0142");  
  
    assertThat(t1.equals(t2)).isTrue();  
}
```
# 값을 참조로 바꾸기
#### 배경
하나의 데이터 구조 안에 논리적으로 똑같은 제3의 데이터 구조를 참조하는 레코드가 여러 개 있을 때가 있다.
예) 같은 고객이 요청한 주문이 여러 개 섞여 있을 때, 고객을 값으로 참조로 다룰 수 있다.

갱신할 일이 없다면 어느 방식이든 상관없다. 그러나 같은 데이터를 물리적으로 복제해 사용할 때 가장 크게 문제되는 상황은 그 데이터를 갱신해야 할 때다.

이런 상황이면 복제된 데이터들을 모두 참조로 바꾼다.

값을 참조로 바꾸면 엔티티 하나당 객체도 단 하나만 존재하게 된다. 그러면 보통 이런 객체들을 한데 모아놓고 클라이언트들의 접근을 관리해주는 일종의 저장소가 필요해진다.
#### 절차
1. 같은 부류에 속하는 객체들을 보관할 저장소를 만든다.
2. 생성자에서 이 부류의 객체들 중 특정 객체를 정확히 찾아내는 방법이 있는지 확인한다.
3. 호스트 객체의 생성자들을 수정하여 필요한 객체를 이 저장소에서 찾도록 한다. 하나 수정할 때마다 테스트한다.
#### 예시
```java
@Getter  
public class Customer {  
    private final String id;  
  
    public Customer(String id) {  
        this.id = id;  
    }  
}

@Getter  
public class Order {  
    private final int number;  
    private final Customer customer;  
  
    public Order(int number, String id) {  
        this.number = number;  
        this.customer = new Customer(id);  
    }  
}
```

- order가 5개 생성되면 만약 고객 정보가 추가된다면 5개를 다 바꾸어야한다.
```java
new Order(1, "hwang");  
new Order(2, "hwang");  
new Order(3, "hwang");  
new Order(4, "hwang");  
new Order(5, "hwang");

// 만약 Customer class에 나이가 있고, 그 나이가 변경된다면 5개 다 변경해야함
```

- 다른 외부 저장소를 만들어서 값을 관리할 수 있도록 한다.
```java
Map<String, Customer> repository = new HashMap<>();  
repository.put("hwang", new Customer("hwang"));  
  
new Order(1, repository.get("hwang"));  
new Order(2, repository.get("hwang"));  
new Order(3, repository.get("hwang"));  
new Order(4, repository.get("hwang"));  
new Order(5, repository.get("hwang"));
```
# 매직 리터럴 바꾸기
#### 배경
예를들어 9.80665라는 숫자가 산재해 있다. 표준중력을 뜻한다는 것을 코드를 읽는 사람이 모른다면 숫자 자체로 의미를 명확히 알려주지 못하므로 매직 리터럴이라 할 수 있다.

의미를 알고 있다고 해도 결국 각자의 머리에서 해석해낸 것일 뿐이라서, 이보다는 코드 자체가뜻을 분명하게 드러내는 게 좋다.
#### 절차
1. 상수를 선언하고 매직 리터럴을 대입한다.
2. 해당 리터럴이 사용되는 곳을 모두 찾는다.
3. 찾은 곳 각각에서 리터럴이 새 상수와 똑같은 의미로 쓰였는지 확인하여, 같은 의미라면 상수로 대체한 후 테스트 한다.