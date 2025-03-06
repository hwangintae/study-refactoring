## chapter07 캡슐화
## 느낀점
1. 레거시 코드의 거대한 클래스를 마주하였을 때 어떻게 하면 좋을지 감조차 잡히지 않을때가 있었다.
   그러다 리팩터링을 포기하곤 했는데 이번 클래스 추출하기를 통해 함수만 추출할게 아니라 클래스도 추출하여 의미와 의도록 명확하게 들어나게 하는게 중요하다는것을 알게 되었다.
   거대한 클래스를 리팩터링을 할때 어떻게 접근해야할지 감이 잡히게 되었다.
2. 컬렉션 캡슐화를 읽으면서 객체에서 제어할 수 없으면 캡슐화가 된게 아니라는 내용을 통해 내가 캡슐화를 잘 이해하고 있는것이 맞나? 라는 의문이 생겼다. 그러면서 일급 컬렉션에 대해 찾아보게 되었고 캡슐화란 객체가 의도한 방향대로 의미있는 함수와 데이터를 제공하는 것 이라고 이해했다.
#### 배경
대부분의 프로그래밍 언어는 데이터 레코드를 표현하는 구조를 제공
레코드는 계산해서 얻을 수 있는 값과 그렇지 않은 값을 명확히 구분해 저장해야하는 번거로움이 있다.

가변 데이터를 저장하는 용도로는 레코드보다 객체를 선호한다.

레코드 구조는 두 가지로 구분할 수 있다. 하나는 필드 이름을 노출하는 형태, 다른 하나는 내가 원하는 이름을 쓸 수 있는 형태

#### 절차
1. 레코드를 담은 별수를 캡슐화 한다.
2. 레코드를 감싼 단순한 클래스로 해당 변수의 내용을 교체한다. 이 클래스에 원본 레코드를 반환하는 접근자도 정의하고, 변수를 캡슐화하는 함수들이 이 접근자를 사용하도록 수정
3. 테스트
4. 원본 레코드 대신 새로 정의한 클래스 타입의 객체를 반환하는 함수를  새로 만든다.
5. 레코드를 반환하는 예전 함수를 사용하는 코드를 4에서 만든 새 함수를 사용하도록 바꾼다. 필드에 접근할 때는 객체 접근자를 사용한다.
    - 중첩된 구조처럼 복잡한 레코드라면, 먼저 데이터를 갱신하는 클라이언트들에 주의해서 살펴본다. 클라이언트가 데이터를 읽기만 한다면 데이터의 복제본이나 읽기전용 프락시를 반환할지 고려한다.
6. 클래스에서 원본 데이터를 반환하는 접근자와 원본 레코드를 반환하는 함수들을 제거한다.
7. 테스트
8. 레코드의 필드도 데이터 구조인 중첩 구조라면 **레코드 캡슐화하기**와 **컬렉션 캡슐화하기**를 재귀적으로 적용한다.

#### 예시: 간단한 레코드 캡슐화 하기
```java
@Getter  
@Setter  
public class Organization {  
    private final String name;  
    private final String country;  
  
    public Organization(String name, String country) {  
        this.name = name;  
        this.country = country;  
    }  
}
```

#### 예시: 중첩된 레코드 캡슐화하기
```java
public class CustomerData {  
    private Map<String, Customer> customers = new HashMap<>();  
  
    public Customer getCustomer(String customerID) {  
        return customers.get(customerID);  
    }  
  
    public void addCustomer(String id, String name) {  
        customers.put(id, new Customer(id, name));  
    }  
}

public class Customer {  
    private String id;  
    private String name;  
    private Map<Integer, Map<Integer, Integer>> usages = new HashMap<>();  
  
    public Customer(String id, String name) {  
        this.id = id;  
        this.name = name;  
    }  
  
    public void setUsage(int year, int month, int amount) {  
        if (!usages.containsKey(year)) {  
            usages.put(year, new HashMap<>());  
        }  
  
        Map<Integer, Integer> integerIntegerMap = usages.get(year);  
  
        integerIntegerMap.put(month, amount);  
    }  
  
    public int getUsage(int year, int month) {  
        return usages.getOrDefault(year, new HashMap<>())  
                .getOrDefault(month, 0);  
    }  
}
```

## 컬렉션 캡슐화하기
#### 배경
가변 데이터를 모두 캡슐화한다. 데이터 구조가 언제 어떻게 수정되는지 파악하기 쉬워서 데이터 구조를 변경하기도 쉽다.

컬렉션을 감싼 클래스에 흔히 add(), remove()라는 컬렉션 변경자 메서드를 만든다.

가장 흔히 사용하는 방식은 컬렉션 게터를 제공하되 내부 컬렉션의 복제본을 반환하는 것

#### 절차
1. 아직 컬렉션을 캡슐화하지 않았다면 **변수 캡슐화하기** 부터 한다.
2. 컬렉션에 원소를 추가/제거하는 함수를 추가한다.
    1. 컬렉션 자체를 통째로 바꾸는 **세터는 제거**한다. 세터를 제거할 수 없다면 인수로 받은 컬렉션을 복제해 저장하도록 한다.
3. 정적 검사를 수행
4. 컬렉션을 참조하는 부분을 모두 찾는다. 컬렉션의 변경자를 호출하는 코드를 추가한 함수를 호출하도록 수정한다.
5. 컬렉션 게터를 수정해서 원본 내용을 수정할 수 없는 읽기전용 프락시나 복제본을 반환하게 한다.
6. 테스트

#### 예시
```java
@Getter  
public class Person {  
    private final String name;  
    private final List<Course> courses;  
  
    public Person(String name, List<Course> courses) {  
        this.name = name;  
        this.courses = courses;  
    }  
}

@Getter  
public class Course {  
    private final String name;  
    private final boolean advanced;  
  
    public Course(String name, boolean advanced) {  
        this.name = name;  
        this.advanced = advanced;  
    }  
}
```

> 만약에 클라이언트에서 course의 값을 직접 변경했을 경우 Person class가 courses 컬렉션을 더 이상 제어할 수 없다. 즉, 캡슐화가 깨진다.

```java
@Test  
public void test() {  
    Person person = new Person("우직이", new ArrayList<>());  
  
    List<Course> courses = person.getCourses();  
  
    List<Course> list = courses.stream()  
            .map(course -> new Course(course.getName(), false))  
            .toList();  
}
```

제대로 캡슐화하기 위해 클라이언트가 수업을 하나씩 추가하고 제거하는 메서드를 Person에 추가
```java
// @Getter를 지우자
public class Person {  
    ... 
      
    public void addCourse(Course course) {  
        courses.add(course);  
    }  
      
    public void removeCourse(Course course) {  
        courses.remove(course);  
    }  
}
```

## 기본형을 객체로 바꾸기
#### 배경
개발 초기에는 단순한 정보를 숫자나 문자열 같은 간단한 데이터 항목으로 표현할 때가 많다.
그러다가 정보들이 더 이상 간단하지 않게 변한다.
단순한 출력 이상의 기능이 필요해지는 순간 그 데이터를 표현하는 전용 클래스를 정의한다.

#### 절차
1. 변수를 캡슐화 한다.
2. 단순간 value class를 만든다. 생성자는 기존 값을 인수로 받아서 저장하고 이 값을 반환하는 게터를 추가한다.
3. 정적 검사를 수행한다.
4. 값 클래스의 인스턴스를 새로 만들어서 필드에 저장하도록 세터를 수정한다. 이미 있다면 필드의 타입을 적절히 변경한다.
5. 새로 만든 클래스의 게터를 호출한 결과를 반환하도록 게터를 수정한다.
6. 테스트
7. **함수 이름을 바꾸면** 원본 접근자의 동작을 더 잘 드러낼 수 있는지 검토한다.
    1. **참조를 값으로 바꾸기** 나 **값을 참조로** 바꾸면 새로 만든 객체의 역할이 더 잘 드러나는지 검토한다.

#### 예시
```java
@Getter  
@Setter  
public class Order {  
    private final Priority priority;  
  
    public Order(Priority priority) {  
        this.priority = priority;  
    }  
}

@Getter  
public class Priority {  
    private final String value;  
  
    public Priority(String value) {  
        this.value = value;  
    }  
}
```

```java
public class Order {  
    private Priority priority;  
  
    public Order(Priority priority) {  
        this.priority = priority;  
    }  
      
    public String getPriorityString() {  
        return priority.toString();  
    }  
      
    public void setPriority(String aString) {  
        this.priority = new Priority(aString);  
    }  
}
```

## 임시 변수를 질의 함수로 바꾸기
#### 배경
함수 안에서 어떤 코드의 결과값을 두에서 다시 참조할 목적으로 임시 변수를 쓰기도 한다. 코드가 반복되는 걸 줄이고 값의 의미를 설명할 수도 있어서 유용하다.

한 걸음 더 나아가 함수로 만들어서 사용하는 편이 나을 때가 많다.

임시 변수를 질의 함수로 바꾼다고 다 좋아지는 건 아니다.
#### 절차
1. 변수가 사용되기 전에 값이 확실히 결저오디는지, 변수를 사용할 때마다 계산 로직이 매번 다른 결과를 내지는 않는지 확인하다.
2. 읽기전용으로 만들 수 있는 변수는 읽기전용으로 만든다.
3. 테스트
4. 변수 대입문을 함수로 추출한다.
    1. 같은 이름을 가질 수 없다면 함수 이름을 임시로 짓는다. 또한 추출한 함수가 부수 효과를 일으키지는 않는지 확인한다. 부수효과가 있다면 **질의 함수와 변경 함수 분리하기**로 대처한다.
5. 테스트
6. **변수 인라인하기**로 임시 변수를 제거한다.

#### 예시
```java
@Getter  
public class Order {  
    private final int quantity;  
    private final Item item;  
  
    public Order(int quantity, Item item) {  
        this.quantity = quantity;  
        this.item = item;  
    }  
      
    public double getPrice() {  
        double basePrice = this.quantity * this.item.getPrice();  
        double discountFactor = 0.98;  
          
        if (basePrice > 1_000) discountFactor -= 0.03;  
        return basePrice * discountFactor;  
    }  
}
```

```java
@Getter  
public class Order {  
    ...
  
    public double getPrice() {  
        return getBasePrice() * getDiscountFactor();  
    }  
      
    private double getDiscountFactor() {  
        double discountFactor = 0.98;  
  
        if (getBasePrice() > 1_000) discountFactor -= 0.03;  
        return discountFactor;  
    }  
      
    private double getBasePrice() {  
        return this.quantity * this.item.getPrice();  
    }  
}
```

## 클래스 추출하기
#### 배경
메서드와 데이터가 너무 많은 클래스는 이해하기가 쉽지 않다. 특히 일부 데이터와 메서드를 따로 묶을 수 있다면 분리하라는 신호다.

함께 변경되는 일이 많거나 서로 의존하는 데이터들도 분리한다.

작은 일부의 기능만을 위해 서브클래스를 만들거나, 확장해야 할 기능이 무엇이냐에 따라 서브클래스를 만드는 방식도 달라진다면 클래스를 나눠야 한다는 신호다.
#### 절차
1. 클래스의 역할을 분리할 방법을 정한다.
2. 분리될 역할을 담당할 클래스를 새로 만든다.
    1. 원래 클래스에 남은 역할과 클래스 이름이 어울리지 않는다면 적절히 바꾼다.
3. 원래 클래스의 생성자에서 새로운 클래스의 인스턴스를 생성하여 필드에 저장해둔다.
4. 분리될 역할에 필요한 **필드들을 새 클래스로 옮긴다.**
5. 메서드들도 새 클래스에 옮긴다. 이때 저수준 메서드, 즉 호출을 당하는 일이 많은 메서드부터 옮긴다.
6. 양쪽 클래스의 인터페이스를 살펴보면서 불필요한 메서드를 제거하고, 이름도 새로운 환경에 맞게 바꾼다.
7. 새 클래스를 외부로 노출할지 정한다. 노출하려거든 새 클래스에 **참조를 값으로 바꾸기**를 적용할지 고민해본다.
#### 예시
```java
public class Person {  
    private String name;  
    private String officeAreaCode;  
    private String officeNumber;  
  
    public Person(String name, String officeAreaCode, String officeNumber) {  
        this.name = name;  
        this.officeAreaCode = officeAreaCode;  
        this.officeNumber = officeNumber;  
    }  
      
    public String telephoneNumber() {  
        return "(" + officeAreaCode + ")" + officeNumber;  
    }  

	public String getName() {  
	    return this.name;  
	}  
	  
	public String getOfficeAreaCode() {  
	    return this.officeAreaCode;  
	}  
	  
	public String getOfficeNumber() {  
	    return this.officeNumber;  
	}  
	  
	public void setName(String name) {  
	    this.name = name;  
	}  
	  
	public void setOfficeAreaCode(String officeAreaCode) {  
	    this.officeAreaCode = officeAreaCode;  
	}  
	  
	public void setOfficeNumber(String officeNumber) {  
	    this.officeNumber = officeNumber;  
	}
}

class PersonTest {  
  
    private Person person;  
  
    @BeforeEach  
    void setUp() {  
        person = new Person("대흥동 타이거 우직", "123", "4567890");  
    }  
  
    @Test  
    void testConstructorAndGetters() {  
        assertEquals("대흥동 타이거 우직", person.getName());  
        assertEquals("123", person.getOfficeAreaCode());  
        assertEquals("4567890", person.getOfficeNumber());  
    }  
  
    @Test  
    void testTelephoneNumber() {  
        assertEquals("(123)4567890", person.telephoneNumber());  
    }  
  
    @Test  
    void testSetters() {  
        person.setName("용현동 늑대 행복이");  
        person.setOfficeAreaCode("987");  
        person.setOfficeNumber("6543210");  
  
        assertEquals("용현동 늑대 행복이", person.getName());  
        assertEquals("987", person.getOfficeAreaCode());  
        assertEquals("6543210", person.getOfficeNumber());  
        assertEquals("(987)6543210", person.telephoneNumber());  
    }  
}
```

```java
public class Person {  
    private String name;  
  
    private final TelephoneNumber telephoneNumber;  
  
    public Person(String name, String officeAreaCode, String officeNumber) {  
        this.name = name;  
        this.telephoneNumber = new TelephoneNumber(officeAreaCode, officeNumber);  
    }  
  
    public String telephoneNumber() {  
        return this.telephoneNumber.toString();  
    }  
  
    public String getName() {  
        return this.name;  
    }  
  
    public String getOfficeAreaCode() {  
        return this.telephoneNumber.getAreaCode();  
    }  
  
    public String getOfficeNumber() {  
        return this.telephoneNumber.getNumber();  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public void setOfficeAreaCode(String officeAreaCode) {  
        this.telephoneNumber.setAreaCode(officeAreaCode);  
    }  
  
    public void setOfficeNumber(String officeNumber) {  
        this.telephoneNumber.setNumber(officeNumber);  
    }  
}

@Getter  
@Setter  
public class TelephoneNumber {  
    private String areaCode;  
    private String number;  
  
    public TelephoneNumber(String areaCode, String number) {  
        this.areaCode = areaCode;  
        this.number = number;  
    }  
  
    public String toString() {  
        return "(" + this.areaCode + ")" + this.number;  
    }  
}
```

## 클래스 인라인하기
#### 배경
**클래스 추출하기**를 거꾸로 돌리는 리팩터링. 더 이상 제 역할을 못해서 그대로 두면 안되는 클래스는 인라인한다.

두 클래스의 기능을 지금과 다르게 배분하고 싶을 때도 클래스를 인라인한다. 클래스를 인라인해서 하나로 합친 다음 새로운 **클래스를 추출**하는 게 쉬울 수도 있다. 코드를 재구성할 때 흔히 사용하는 방식
#### 절차
1. 소스 클래스의 각 public 메서드에 대응하는 메서드들을 타깃 클래스에 생성한다. 이 메서드들은 단순히 작업을 소스 클래스로 위임해야한다.
2. 소스 클래스의 메서드를 사용하는 코드를 모두 타깃 클래스의 위임 메서드를 사용하도록 바꾼다. 하나씩 바꿀 때마다 테스트
3. 소스 클래스의 메서드와 필드를 모두 타깃 클래스로 옮긴다. 하나씩 바꿀 때마다 테스트
4. 소스 클래스를 삭제하고 조의를 표한다.
#### 예시
```java
public class Shipment {  
    private TrackingInformation trackingInformation;  
  
    public Shipment(TrackingInformation trackingInformation) {  
        this.trackingInformation = trackingInformation;  
    }  
  
    public TrackingInformation getTrackingInformation() {  
        return trackingInformation;  
    }  
  
    public void setTrackingInformation(TrackingInformation trackingInformation) {  
        this.trackingInformation = trackingInformation;  
    }  
  
    public String getTrackingInfo() {  
        return this.trackingInformation.display();  
    }  

	public String getShippingCompany() {  
	    return this.trackingInformation.getShippingCompany();  
	}  
	  
	public String getTrackingNumber() {  
	    return this.trackingInformation.getTrackingNumber();  
	}  
	  
	public void setShippingCompany(String shippingCompany) {  
	    this.trackingInformation.setShippingCompany(shippingCompany);  
	}  
	  
	public void setTrackingNumber(String trackingNumber) {  
	    this.trackingInformation.setTrackingNumber(trackingNumber);  
	}
}

public class TrackingInformation {  
    private String shippingCompany;  
    private String trackingNumber;  
  
    public TrackingInformation(String shippingCompany) {  
        this.shippingCompany = shippingCompany;  
    }  
  
    public String getShippingCompany() {  
        return shippingCompany;  
    }  
  
    public String getTrackingNumber() {  
        return trackingNumber;  
    }  
  
    public void setShippingCompany(String shippingCompany) {  
        this.shippingCompany = shippingCompany;  
    }  
  
    public void setTrackingNumber(String trackingNumber) {  
        this.trackingNumber = trackingNumber;  
    }  
  
    public String display() {  
        return shippingCompany + ":" + trackingNumber;  
    }  
}
```

```java
public class Shipment {  
    private String shippingCompany;  
    private String trackingNumber;  
  
    public Shipment(String shippingCompany, String trackingNumber) {  
        this.shippingCompany = shippingCompany;  
        this.trackingNumber = trackingNumber;  
    }  
  
    public String getTrackingInfo() {  
        return shippingCompany + ":" + trackingNumber;  
    }  
  
    public String getShippingCompany() {  
        return this.shippingCompany;  
    }  
  
    public String getTrackingNumber() {  
        return this.trackingNumber;  
    }  
  
    public void setShippingCompany(String shippingCompany) {  
        this.shippingCompany = shippingCompany;  
    }  
  
    public void setTrackingNumber(String trackingNumber) {  
        this.trackingNumber = trackingNumber;  
    }  
}
```

## 위임 숨기기
#### 배경
서버 객체의 필드가 가리키는 객체의 메서드를 호출하려면 클라이언트는 이 위임 객체를 알아야한다. 위임 객체의 인터페이스가 바뀌면 이 인터페이스를 사용하는 모든 클라이언트가 코드를 수정해야 한다. 이러한 의존성을 없애려면 서버 자체에 위임 메서드를 만들어서 위임 객체의 존재를 숨기면 된다.
#### 절차
1. 위임 객체의 각 메서드에 해당하는 위임 메서드를 서버에 생성한다.
2. 클라이언트가 위임 객체 대신 서버를 호출하도록 수정한다. 하나씩 바꿀 때마다 테스트한다.
3. 모두 수정했다면, 서버로부터 위임 객체를 얻는 접근자를 제거한다.
4. 테스트
#### 예시
```java
@Getter  
@Setter  
public class Department {  
    private String changeCode;  
    private String manager;  
}

@Getter  
@Setter  
public class Person {  
    private String name;  
    private Department department;  
}

String manager = aPerson.getDepartment().getManager();
```

```java
@Getter  
@Setter  
public class Person {  
    private String name;  
    private Department department;  
  
    public String getManager() {  
        return this.department.getManager();  
    }  
}

String manager = aPerson.getManager();
```

## 중개자 제거하기
#### 배경
위임 객체의 또 다른 기능을 사용하고 싶을 때마다 서버에 위임 메서드를 추가해야하는데,  이렇게 기능을 추가하다 보면 단순히 전달만 하는 위임 메서드들이 성가셔진다.

어느 정도까지 숨겨야 적절한지를 판단하기란 쉽지 않다.
#### 절차
1. 위임 객체르 얻는 게터를 만든다.
2. 위임 메서드를 호출하는 클라이언트가 모두 이 게터를 거치도록 수정한다. 하나씩 바꿀 때마다 테스트
3. 모두 수정했다면 위임 메서드를 삭제한다.
    1. 자동 리팩터링 도구를 사용할 때는 **위임 필드를 캡슐화**한 다름, 이를 사용하는 모든 **메서드를 인라인** 한다.
#### 예시
```java
@Getter  
@Setter  
public class Person {  
    private String name;  
    private Department department;  
  
    public String getManager() {  
        return this.department.getManager();  
    }  
}

@Getter  
@Setter  
public class Department {  
    private String changeCode;  
    private String manager;  
}

String manager = aPerson.getManager();
```

```java
@Getter  
@Setter  
public class Department {  
    private String changeCode;  
    private String manager;  
}

@Getter  
@Setter  
public class Person {  
    private String name;  
    private Department department;  
}

String manager = aPerson.getDepartment().getManager();
```

## 알고리즘 교체하기
#### 배경
리팩터링하면 복잡한 대상을 단순한 단위로 나눌 수 있지만, 때로는 알고리즘 전체를 걷어내고 훨씬 간결한 알고리즘으로 바꿔야 할 때가 있다.

반드시 메서드를 가능한 한 잘게 나눴는지 확인한다. 거대하고 복잡한 알고리즘을 교체하기란 상당히 어렵다.
#### 절차
1. 교체할 코드를 함수 하나에 모은다.
2. 이 함수만을 이용해 동작을 검증하는 테스트를 마련한다.
3. 대체할 알고리즘을 준비한다.
4. 정적 검사를 수행한다.
5. 기존 알고리즘과 새 알고리즘의 결과를 비교하는 테스트를 수행한다. 두 결과가 같다면 리팩터링이 끝난다. 그렇지 않다면 기존 알고리즘을 참고해서 새 알고리즘을 테스트하고 디버깅한다.