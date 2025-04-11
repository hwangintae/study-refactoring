# 메서드 올리기
#### 배경
중복 코드 제거는 중요하다.
중복되었다는 것은 한쪽의 변경이 다른쪽에는 반영되지 않을 수 있음을 뜻한다.
중복은 찾기도 힘들다.

메서드 올리기를 적용하기 가장 쉬운 상황은 메서드들의 본문 코드가 똑같을 때다.

메서드 올리기 리팩터링을 적용하려면 선행 단계를 거쳐야 할 때가 많다.
서로 다른 두 클래스의 두 메서드를 각각 매개변수화하면 궁극적으로 같은 메서드가 되기도 한다.

메서드 올리기를 적용하기에 가장 이상하고 복잡한 상황은 해당 메서드의 본문에서 참조하는 필드들이 서브클래스에만 있는 경우다. 이 경우라면 필드들 먼저 슈퍼클래스로 올린후에 메서드를 올려야 한다.

두 메서드의 전체 흐름은 비슷하니잠 세부 내용이 다르다면 템플릿 메서드 만들기를 고려하자.

#### 절차
1. 똑같이 동작하는 메서드인지 면밀히 살펴본다.
    1. 실질적으로 하는 일은 같지만 코드가 다르다면 본문 코드가 똑강아질 때까지 리팩터링한다.
2. 메서드 안에서 호출하는 다른 메서드와 참조하는 필드들을 슈퍼클래스에서도 호출하고 참조할  수 있는지 확인한다.
3. 메서드 시그니처가 다르다면 함수 선언 바꾸기로 슈퍼클래스에서 사용하고 싶은 형태로 통일 한다.
4. 슈퍼클래스에 새로운 메서드를 생성하고, 대상 메서드의 코드를 복사해넣는다.
5. 정적 검사를 수행한다.
6. 서브클래스 중 하나의 메서드를 제거한다.
7. 테스트
8. 모든 서브클래스의 메서드가 없어질 때까지 다른 서브클래스의 메서드를 하나씩 제거한다.

#### 예시
```java
public class Party {  
}

public class Employee extends Party {  
    private int monthlyCost;  
  
    public int annualCost() {  
        return this.monthlyCost * 12;  
    }  
}

public class Department extends Party {  
    private int monthlyCost;  
  
    public int totalAnnualCost() {  
        return this.monthlyCost * 12;  
    }  
}
```

- 상속
```java
public class Party {  
    private final int monthlyCost;  
  
    public Party(int monthlyCost) {  
        this.monthlyCost = monthlyCost;  
    }  
  
    public int annualCost() {  
        return this.monthlyCost * 12;  
    }  
}

public class Employee extends Party {  
    private final int monthlyCost;  
  
    public Employee(int monthlyCost) {  
        super(monthlyCost);  
        this.monthlyCost = monthlyCost;  
    }  
}

public class Department extends Party {  
    private final int monthlyCost;  
  
    public Department(int monthlyCost) {  
        super(monthlyCost);  
        this.monthlyCost = monthlyCost;  
    }  
}
```

# 필드 올리기
#### 배경
서브클래스들이 독립적으로 개발됐거나 뒤늦게 계층구조로 리팩터링된 경우라면 일부 기능이 중복되어 있을 때가 자주 있다. 특히 필드가 중복되기 쉽다.

중복된 필드는 슈퍼클래스로 끌어올리자

이렇게 하면 두 가지 중복을 줄일 수 있다.
1. 데이터 중복 선언을 없앨 수 있다.
2. 해당 필드를 사용하는 동작을 서브클래스에서 슈퍼클래스로 옮길 수 있다.
#### 절차
1. 후보 필드들을 사용하는 곳 모두가 그 필드들을 똑같은 방식으로 사용하는지 면밀히 살핀다.
2. 필드들이 이름이 각기 다르다면 똑같은 이름으로 바꾼다.
3. 슈퍼클래스에 새로운 필드를 생성한다.
    1. 서브클래스에서 이 필드에 접근할 수 있어야 한다(대부분 언어에서는 protected로 선언하면 된다.)
4. 서브클래스의 필드들을 제거한다.
5. 테스트

# 생성자 본문 올리기
#### 배경
서브클래스들에서 기능이 같은 메서드들을 반견하면 함수 추출하기와 메서드 올리기로 슈퍼클래스로 옮긴다.
그런데 그 메서드가 생성자라면 안된다.

생성자는 할 수 있는 일과 호출 순서에 제약이 있기 때문에 조금 다른 식으로 접근해야 한다.

#### 절차
1. 슈퍼클래스에 생성자가 없다면 하나 정의한다. 서브클래스의 생성자들에서 이 생성자가 호출되는지 확인한다.
2. 문장 슬라이드하기로 공통 문장 모두를 super() 호출 직후로 옮긴다.
3. 공통 코드를 슈퍼클래스에 추가하고 서브클래스들에서는 제거한다. 생성자 매개변수 중 공통 코드에서 참조하는 값들을 모두 super()로 건넨다.
4. 테스트
5. 생성자 시작 부분으로 옮길 수 없는 공통 코드에는 함수 추출하기와 메서드 올리기를 차례로 적용한다.

#### 예시
```java
public class Party {  
    private String name;  
  
    public Party(String name) {  
        this.name = name;  
    }  
}

public class Employee extends Party {  
    private Long id;  
    private String name;  
    private int monthlyCost;  
  
    public Employee(String name, Long id, int monthlyCost) {  
        super(name);  
        this.id = id;  
        this.name = name;  
        this.monthlyCost = monthlyCost;  
    }  
}

public class Department extends Party {  
    private String name;  
    private String staff;  
  
    public Department(String name, String staff) {  
        super(name);  
        this.name = name;  
        this.staff = staff;  
    }  
}
```
#### 예시: 공통 코드가 나중에 올 때
```java
public class Employee extends Party {  
    private Long id;  
    private String name;  
    private int monthlyCost;  
  
    public Employee(String name) {  
        super(name);  
        this.name = name;  
    }  
      
    public boolean isPrivileged() {  
        return false;  
    }  
  
    public void assignCar() {}  
}

public class Manager extends Employee {  
    private int grade;  
  
    public Manager(String name, int grade) {  
        super(name);  
        this.grade = grade;  
        if (this.isPrivileged()) this.assignCar();  
    }  
  
    public boolean isPrivileged() {  
        return this.grade > 4;  
    }  
    public void assignCar() {  
  
    }  
}
```

- 함수로 추출 후 슈퍼클래스로 옮기기
```
public class Employee extends Party {  
    private Long id;  
    private String name;  
    private int monthlyCost;  
  
    public Employee(String name) {  
        super(name);  
        this.name = name;  
    }  
  
    public boolean isPrivileged() {  
        return false;  
    }  
  
    public void assignCar() {}  
  
    public void finishConstruction() {  
        if (this.isPrivileged()) this.assignCar();  
    }  
}

public class Manager extends Employee {  
    private int grade;  
  
    public Manager(String name, int grade) {  
        super(name);  
        this.grade = grade;  
        finishConstruction();  
    }  
}
```

# 메서드 내리기
#### 배경
특정 서브클래스 하나와 관련된 메서드는 슈퍼클래스에서 제거하고 해당 서브클래스들에 추가하는 편이 깔끔하다. 다만, 이 리팩터링은 해당 기능을 제공하는 서브클래스가정확히 무엇인지 호출자가 알고 있을 대만 적용할 수 있다. 그렇지 못한상황이라면 서브클래스에 따라 다르게 동작하는 슈퍼클래스의 기만적인 조건부 로직을 다형성으로 바꿔야한다.

#### 절차
1. 대상 메서드를 모든 서브클래스에 복사한다.
2. 슈퍼클래스에서 그 메서드를 제거한다.
3. 테스트
4. 이 메서드를 사용하지 않는 모든 서브클래스에서 제거한다.
5. 테스트

# 필드 내리기
#### 배경
서브클래스 하나에서만 사용하는 필드는 해당 서브클래스로 옮긴다.

#### 절차
1. 대상 필드를 모든 서브클래스에 정의한다.
2. 슈퍼클래스에서 그 필드를 제거한다.
3. 테스트
4. 이 필드를 사용하지 않는 모든 서브클래스에서 제거한다.
5. 테스트

# 타입 코드를 서브클래스로 바꾸기
#### 배경
소프트웨어 시스템에서는 비슷한 대상들을 특성에 따라 구분해야 할 때가 자주 있다.

타입코드는 프로그래밍 언어에 따라 열거형이나 심볼, 문자열, 숫자 등으로 표현하며, 외부 서비스가 제공하는 데이터를 다루려 할 때 딸려오는 일이 흔하다.

서브클래스는 두 가지 면에서 특히 매력적이다.
1. 조건에 따라 다르게 동작하도록 해주는 다형성을 제공한다.
2. 특정 타입에서만 의미가 있는 값을 사용하는 필드나 메서드가 있을 때 발현된다.

이번 리팩터링은 대상 클래스에 직접 적용할지, 아니면 타입 코드 자체에 적용할지를 고민해야한다.
서브클래싱 대상을 직원 유형 속성에 적용하고자 한다면 먼저 타입 코드에 기본형을 객체로 바구기를 적용하여 직원 유형 클래스를 만든 다음, 이 클래스에 이번 리팩터링을 적용하면 된다.

#### 절차
1. 타입 코드 필드를 자가 캡슐화한다.
2. 타입 코드 값 하나를 선택하여 그 값에 해당하는 서브클래스를 만든다. 타입 코드 게터 메서드를 오버라이드하여 해당 타입 코드의 리터럴 값을 반환하게 한다.
3. 매개변수로 받은 타입 코드와 방금 만든 서브클래스를 매핑하는 선택 로직을 만든다.
    1. 직접 상속일 때는 생성자를 팩토리 함수로 바꾸기를 적용하고 선택 로직을 팩토리에 넣는다. 간접 상속일 때는 선택 로직을 생서자에 두면 될 것이다.
4. 테스트
5. 타입 코드 값 각가에 대해 서브클래스 생성과 선택 로직 추가를 반복한다. 클래스 하나가 완성될 때마다 테스트
6. 타입 코드 필드를 제거한다.
7. 테스트
8. 타입 코드 접근자를 이용하는 메서드 모두에 메서드 내리기와 조건부 로직을 다형성으로 바꾸기를 적용한다.

#### 예시: 직접 상속할 때

```java
public abstract class Employee {  
    private final String name;  
  
    public Employee(String name) {  
        this.name = name;  
    }

	public abstract String getType();
  
    public static Employee createEmployee(String type, String name) {  
        return switch (type) {  
            case "engineer" -> new Engineer(name);  
            case "salesperson" -> new Salesperson(name);  
            case "manager" -> new Manager(name);  
            default -> throw new IllegalArgumentException("Invalid type: " + type);  
        };  
    }  
}

public class Salesperson extends Employee {  
    public Salesperson(String name) {  
        super(name);  
    }  
  
    public String getType() {  
        return "salesperson";  
    }  
}

public class Manager extends Employee {  
    public Manager(String name) {  
        super(name);  
    }  
  
    public String getType() {  
        return "manager";  
    }  
}

public class Engineer extends Employee {  
    public Engineer(String name) {  
        super(name);  
    }  
  
    public String getType() {  
        return "engineer";  
    }  
}
```

#### 예시: 간접 상속할 때
조건: 직원의 서브클래스로 '아르바이트'와 '정직원'이라는 클래스가 이미 있어서 Employee를 직접 상속하는 방식으로 타입 코드 문제에 대처할 수 없을 때

```java
public class Employee {  
    private final String name;  
    private String type;  
  
    public Employee(String name, String type) {  
        this.name = name;  
        this.type = type;  
    }  
  
    private void validateType(String arg) {  
        if (!List.of("engineer", "manager", "salesperson").contains(arg)) {  
            throw new IllegalArgumentException("Invalid type " + arg);  
        }  
    }  
  
    public String getType() {  
        return this.type;  
    }  
  
    public void setType(String type) {  
        this.type = type;  
    }  
  
    public String capitalizedType() {  
        return Character.toUpperCase(this.type.charAt(0)) + this.type.substring(1).toLowerCase();  
    }  
  
    @Override  
    public String toString() {  
        return this.name + "(" + this.capitalizedType() + ")";  
    }  
}
```

- EmployeeType 만들기
```java
public class EmployeeType {  
    private String value;  
  
    public EmployeeType(String value) {  
        this.value = value;  
    }  
  
    @Override  
    public String toString() {  
        return this.value;  
    }  
  
    public String capitalizedName() {  
        return Character.toUpperCase(this.toString().charAt(0))  
                + this.toString().substring(1).toLowerCase();  
    }  
}

public class Employee {  
    private final String name;  
    private EmployeeType type;  
  
    public Employee(String name, EmployeeType type) {  
        this.name = name;  
        this.type = type;  
    }  
      
    public static EmployeeType createEmployeeType(String aString) {  
        return switch (aString) {  
            case "engineer" -> new Engineer(aString);  
            case "Manager" -> new Manager(aString);  
            case "salesperson" -> new Salesperson(aString);  
            default -> throw new IllegalArgumentException("Unknown employee type: " + aString);  
        };  
    }  
  
    private void validateType(String arg) {  
        if (!List.of("engineer", "manager", "salesperson").contains(arg)) {  
            throw new IllegalArgumentException("Invalid type " + arg);  
        }  
    }  
  
    public String typeString() {  
        return this.type.toString();  
    }  
  
    public EmployeeType getType() {  
        return this.type;  
    }  
  
    public void setType(String type) {  
        this.type = Employee.createEmployeeType(type);  
    }  
  
    public String capitalizedType() {  
        return Character.toUpperCase(this.type.toString().charAt(0))  
                + this.type.toString().substring(1).toLowerCase();  
    }  
  
    @Override  
    public String toString() {  
        return this.name + "(" + this.type.capitalizedName() + ")";  
    }  
}
```

# 서브클래스 제거하기
#### 배경
서브클래스는 원래 데이터 구조와는 다른 변종을 만들거나 종류에 따라 동작이 달라지게 할 수 있는 유요한 메커니즘이다. 다름을 프로그래밍하는멋진 수단이다. 하지만 스프트웨어 시스템이 성장함에 따라 서브클래스로 만든 변종이 다른 모듈로 이동하거나 완전히 사라지기도 하면서 가치가 바래기도 한다. 결국 한 번도 활용되지 않기도 하며, 때론 서브클래스를 필요로 하지 않는 방식으로 만들어진 기능에서만 쓰이기도 한다.

더 이상 쓰이지 않는 서브클래스와 마주하는 프로그래머는 가치 없는 것을 이해하느라 에너지를 낭비할 것이다. 이런 정도까지 되면 서브클래스를 슈퍼클래스의 필드로 대체해 제거한다.

#### 절차
1. 서브클래스의 생성자를 팩토리 함수로 바꾼다.
    1. 생성자를 사용하는 측에서 데이터 필드를 이용해 어떤 서브클래스를 생성할지 결정한다면 그 결정 로직을 슈퍼클래스의 팩토리 메서드에 넣는다.
2. 서브클래스의 타입을 검사하는 코드가 있다면 그 검사 코드에 함수 추출하기와 함수 옮기기를 차로 적용하여 슈퍼클래스로 옮긴다.
3. 서브클래스의 타입을 나타내는 필드를 슈퍼클래스에 만든다.
4. 서브클래스를 참조하는 메서드가 방금 만든 타입 필드를 이용하도록 수정한다.
5. 서브클래스를 지운다.
6. 테스트

#### 예시
```java
@Getter  
public class Person {  
    private final String name;  
  
    public Person(String name) {  
        this.name = name;  
    }  
      
    public String genderCode() {  
        return "X";  
    }  
}

public class Male extends Person {  
    public Male(String name) {  
        super(name);  
    }  
  
    @Override  
    public String genderCode() {  
        return "M";  
    }  
}

public class Female extends Person {  
    public Female(String name) {  
        super(name);  
    }  
  
    @Override  
    public String genderCode() {  
        return "F";  
    }  
}

public List<Person> loadFromInput(List<People> people) {  
    List<Person> result = new ArrayList<>();  
  
    people.forEach(aRecord -> {  
                Person p = switch (aRecord.gender()) {  
                    case "M" -> new Male(aRecord.name());  
                    case "F" -> new Female(aRecord.name());  
                    default -> new Person(aRecord.name());  
                };  
  
                result.add(p);  
            });  
  
    return result;  
}
```

- 팩토리 메소드 생성 및 파이프라인으로 변경
```java
@Getter  
public class Person {  
    // ...
  
    public static Person createPerson(People aRecord) {  
	    return switch (aRecord.gender()) {  
	        case "M" -> new Male(aRecord.name());  
	        case "F" -> new Female(aRecord.name());  
	        default -> new Person(aRecord.name());  
	    };  
}
  
    // ...
}

public List<Person> loadFromInput(List<People> people) {  
    return people.stream()  
            .map(Person::createPerson)  
            .toList();  
}
```

- 서브클래스 제거하기
```java
@Getter  
public class Person {  
    private final String name;  
    private final String genderCode;  
  
    public Person(String name, String genderCode) {  
        this.name = name;  
        this.genderCode = genderCode;  
    }  
  
    public static Person createPerson(People aRecord) {  
        return switch (aRecord.gender()) {  
            case "M" -> new Person(aRecord.name(), "M");  
            case "F" -> new Person(aRecord.name(), "F");  
            default -> new Person(aRecord.name(), "X");  
        };  
    }  
  
    public String genderCode() {  
        return this.genderCode;  
    }  
  
    public boolean isMale(Person aPerson) {  
        return "M".equals(aPerson.genderCode());  
    }  
}
```


# 슈퍼클래스 추출하기
#### 배경
비슷한 일을 수행하는 두 클래스가 보이면 상속 메커니즘을 이용해서 비슷한 부분으로 공통의 슈퍼클래스로 옮겨 담을 수 있다. 공통된 부분이 데이터라면 필드 올리기를 활용하고, 동작이라면 메서드 올리기를 활용하면 된다.

상속은 프로그램이 성장하면서 깨우쳐가게 되며, 슈퍼클래스로 끌어올리고 싶은 공통 요소를 찾았을 때 수행하는 사례가 많다.

슈퍼클래스 추출하기의 대안으로는 클래스 추출하기가 있다. 어느 것을 선택하느냐는 중복 동작을 상속으로 해결하느냐 위임으로 해결하느냐에 달렸다. 슈퍼클래스 추출하기 방법이 더 간단할 경우가 많으니 이 리팩터링을 먼저 시도해보길 권한다,

#### 절차
1. 빈 슈퍼클래스를 만든다. 원래의 클래스들이 새 클래스를 상속하도록 한다.
    1. 필요하다면 생성자에 함수 선언 바꾸기를 적용한다.
2. 테스트
3. 생성자 본문 올리기, 메서드 올리기, 필드 올리기를 차례로 적용하여 공통 원소를 슈퍼클래스로 옮긴다.
4. 서브클래스에 남은 메서드들을 검토한다. 공통되는 부분이 있다면 함수로 추출한 다음 메서드 올리기를 적용한다.
5. 원래 클래스들을 사용하는 코드를 검토하여 슈퍼클래스의 인터페이스를 사용하게 할지 고민해본다.

#### 예시
```java
@Getter  
public class Employee {  
    private final Long id;  
    private final String name;  
    private final int monthlyCost;  
  
    public Employee(Long id, String name, int monthlyCost) {  
        this.id = id;  
        this.name = name;  
        this.monthlyCost = monthlyCost;  
    }  
      
    public int annualCost() {  
        return this.monthlyCost * 12;  
    }  
}

@Getter  
public class Department {  
    private final String name;  
    private final List<Staff> staff;  
  
    public Department(String name, List<Staff> staff) {  
        this.name = name;  
        this.staff = staff;  
    }  
  
    public int totalMonthlyCost() {  
        return this.staff.stream()  
                .map(Staff::getMonthlyCost)  
                .reduce(0, Integer::sum);  
    }  
  
    public int headCount() {  
        return this.staff.size();  
    }  
  
    public int totalAnnualCost() {  
        return this.totalMonthlyCost() * 12;  
    }  
}
```

- 추상클래스로 변경하고 슈퍼클래스로 메서드 올리기
```java
public abstract class Party {  
    private final String name;  
  
    public Party(String name) {  
        this.name = name;  
    }  
  
    public abstract int monthlyCost();  
  
    public int annualCost() {  
        return this.monthlyCost() * 12;  
    }  
}

@Getter  
public class Employee extends Party {  
    private final Long id;  
    private final int monthlyCost;  
  
    public Employee(Long id, String name, int monthlyCost) {  
        super(name);  
        this.id = id;  
        this.monthlyCost = monthlyCost;  
    }  
  
    @Override  
    public int monthlyCost() {  
        return monthlyCost;  
    }  
}

@Getter  
public class Department extends Party {  
    private final List<Staff> staff;  
  
    public Department(String name, List<Staff> staff) {  
        super(name);  
        this.staff = staff;  
    }  
  
    @Override  
    public int monthlyCost() {  
        return this.staff.stream()  
                .map(Staff::getMonthlyCost)  
                .reduce(0, Integer::sum);  
    }  
  
    public int headCount() {  
        return this.staff.size();  
    }  
}
```

# 계층 합치기
#### 배경
클래스 계층구조를 리팩터링하다 보면기능들을 위로 올리거나 아래로 내리는 일은 다반사로 벌어진다.
예를들어 계층구조도 진화하면서 어떤 클래스와 그 부모가 너무 비슷해져서 더는 독립적으로 존재해야 할 이유가 사라지는 경우가 생기기도 한다. 바로 그 둘을 하나로 합쳐야 할 시점이다.

#### 절차
1. 두 클래스 중 제거할 것을 고른다.
    1. 미래를 생각하여 더 적합한 이름의 클래스를 남기자. 둘 다 적절치 않다면 임의로 하나를 고른다.
2. 필드 올리기와 메서드 올리기 혹은 필드 내기리와 메서드 내리기를 적용하여 모든 요소를 하나의 클래스로 옮긴다.
3. 제거할 클래스를 참조하던 모든 코드가 남겨질 클래스를 참조하도록 고친다.
4. 빈 클래스를 제거한다.
5. 테스트

# 서브클래스를 위임으로 바꾸기
#### 배경
속한 갈래에 따라 동작이 달라지는 객체들은 상속으로 표현하는게 자연스럽다. 공통 데이터와 동작은 모두 슈퍼클래스에 두고 서브클래스는 자신에 맞게 기능을 추가하거나 오버라이드하면된다. 객체 지향 언어로는 이런 형태로 구현하기가 쉽기 대문에 흔히 활용된다.

하지만 상속에는 단점이 있다. 가장 명확한 단점은 한 번만 쓸 수 있는 카드라는 것.
무언가가 달라져야 하는 이유가 여러 개여도 상속에서는 그중 단 하나의 이유만 선택해 기준으로 삼을 수밖에 없다. 예를들어 사람 객체의 동작을 '나이대'와 '소득 수준'에 따라 달리 하고 싶다면 서브클래스는 젊은이와 어르신이 되거나, 혹은 부자와 서민이 되어야 한다. 둘다는 안 된다.

상속은 클래스들의 관계를 아주 긴미랗게 결합한다. 부모를 수정하면 이미 존재하는 자식들의 기능을 해치기가 쉽기 때문에 각별히 주의해야 한다. 부모를 수정하면 이미 존재하는 자식들의 기능을 해치기가 쉽기 때문에 각별히 주의해야 한다. 그래서 자식들이 슈퍼클래스를 어떻게 상속해 쓴느지를 이해해야 한다. 부모와 자식이 서로 다른 모듈에 속하거나 다른 팀에서 구현한다면 문제가 더 커진다.

위임은 두 문제를 모두 해결해준다. 다양한 클래스에 서로 다른 이유로 위임할 수 있다. 위임은 객체 사이의 일반적인 관계이므로 상호작용에 필요한 인터페이스를 명확히 정의할 수 있다. 즉, 상속보다 결합도가 훨씬 약하다. 그래서 서브클래싱 관련 문제에 직면하게 되면 흔히들 서브클래스를 위임으로 바꾸곤 한다.

유명한 원칙이 하나 있다. "상속보다는 객체를 사용하라!" 여기서 객체는 사실상 위임과 같은 말이다.
상속은 언제든 위임으로 바꿀 수 있기 때문에 처음에는 상속으로 접근한 후 문제가 생기기 시작하면 위임으로 갈아탄다. 상속을 쓰지 말라는게 아니라, 과용하는데 따른 반작용으로 나온 것이다.

디자인 패턴에 익숙한 사람이라면 서브클래스를 state pattern이나 strategy pattern으로 대체 한다고 생각하면 된다. 구조적으로 보면 이 두 패턴은 똑같이 호스트 위임 방식으로 계층구조를 분리해준다.

#### 절차
1. 생성자를 호출하는 곳이 많다면 생성자를 팩토리 함수로 바꾼다.
2. 위임으로 활용할 빈 클래스를 만든다. 이클래스의 생성자는 서브클래스에 특화된 데이터를 전부 받아야 하며, 보통은 슈퍼클래스를 가리키는 역참조도 필요하다.
3. 위임을 저장할 필드를 슈퍼클래스에 추가한다.
4. 서브클래스 생성 코드를 수정하여 위임 인스턴스를 생성하고 위임 필드에 대입해 초기화한다.
    1. 이 작업은 팩토리 함수가 수행한다. 혹은 생성자가 정확한 위임 인스턴스를 생성할 수 있는게 확실하다면 생성자에서 수행할 수도 있다.
5. 서브클래스의 메서드 중 위임 클래스로 이동할 것을 고른다.
6. 함수 옮기기를 적용해 위임 클래스로 옮긴다. 원래 메서드에서 위임하는 코드는 지우지 않는다.
    1. 이 메서드가 사용하는 원소 중 위임으로 옮겨야 하는게 있다면 함께 옮긴다. 슈퍼클래스에 유지해야 할 원소를 참조한다면 슈퍼클래스를 참조하는 필드를 위임에 추가한다.
7. 서브클래스 외부에도 원래 메서드를 호출하는 코드가 있다면 서브클래스의 위임 코드를 슈퍼클래스로 옮긴다. 이때 위임이 존재하는지를 검사하는 보호 코드로 감싸야 한다. 호출하는 외부 코드가 없다면 원래 메서드는 죽은 코드가 되므로 제거한다.
    1. 서브클래스가 둘 이상이고 서브클래스들에서 중복이 생겨나기 시작했다면 슈퍼클래스를 추출한다. 이렇게 하여 기본동작이 위임 슈퍼클래스로 옮겨졌다면 슈퍼클래의 위임 메서드들에는 보호 코드가 필요 없다.
8. 테스트
9. 서브클래스의 모든 메서드가 옮겨질 대까지 반복
10. 서브클래스의 생성자를 호출하는 코드를 찾아서 슈퍼클래스의 생성자를 사용하도록 수정
11. 테스트
12. 서브클래스를 삭제한다.

#### 예시: 서클래스가 하나일 때
```java
@Getter  
public class Booking {  
    private final Show show;  
    private final Instant date;  
  
    public Booking(Show show, Instant date) {  
        this.show = show;  
        this.date = date;  
    }  
  
    public boolean hasTalkback() {  
        return this.show.hasOwnProperty("talkback") && !this.isPeakDay();  
    }  
  
    public boolean isPeakDay() {  
        return true;  
    }  
  
    public double basePrice() {  
        double result = this.show.getPrice();  
  
        if (this.isPeakDay()) result += Math.round(result * 0.15);  
  
        return result;  
    }  
}

public class PremiumBooking extends Booking {  
    private final Extras extras;  
  
    public PremiumBooking(Show show, Instant date, Extras extras) {  
        super(show, date);  
        this.extras = extras;  
    }  
  
    @Override  
    public boolean hasTalkback() {  
        return this.getShow().hasOwnProperty("talkback");  
    }  
  
    @Override  
    public double basePrice() {  
        return Math.round(super.basePrice() + this.extras.premiumFee());  
    }  
  
    @Override  
    public boolean isPeakDay() {  
        return false;  
    }  
  
    public boolean hasDinner() {  
        return this.extras.hasOwnProperty("dinner") && !this.isPeakDay();  
    }  
}
```

상속은 한 번만 사용할 수 있는 도구다. 따라서 상속을 사용해야 할 다른 이유가 생긴다면, 그리고 그 이유가 프리미엄 예약 서브크래스보다 가치가 크다고 생각된다면 프리미엄 예약을 다른 방식으로 표현해야 한다.

- 팩토리 함수 만들고 서브클래스를 위임으로 옮기기
```java
@Getter  
public class Booking {  
    private final Show show;  
    private final Instant date;  
    private PremiumBookingDelegate premiumDelegate;  
  
    public Booking(Show show, Instant date) {  
        this.show = show;  
        this.date = date;  
    }  
  
    public static Booking createBooking(Show show, Instant date) {  
        return new Booking(show, date);  
    }  
  
    public boolean hasTalkback() {  
        return (this.premiumDelegate != null)  
                ? this.premiumDelegate.hasTalkback()  
                : this.show.hasOwnProperty("talkback") && !this.isPeakDay();  
    }  
  
    public boolean isPeakDay() {  
        return true;  
    }  
  
    public double basePrice() {  
        double result = this.show.getPrice();  
  
        if (this.isPeakDay()) result += Math.round(result * 0.15);  
  
        return result;  
    }  
      
    private void bePremium(Extras extras) {  
        this.premiumDelegate = new PremiumBookingDelegate(this.show, this.date, extras);  
    }  
}

public class PremiumBooking extends Booking {  
    private final Extras extras;  
  
    public PremiumBooking(Show show, Instant date, Extras extras) {  
        super(show, date);  
        this.extras = extras;  
    }  
  
    public static PremiumBooking createPremiumBooking(Show show, Instant date, Extras extras) {  
        return new PremiumBooking(show, date, extras);  
    }  
  
    @Override  
    public double basePrice() {  
        return Math.round(super.basePrice() + this.extras.premiumFee());  
    }  
  
    @Override  
    public boolean isPeakDay() {  
        return false;  
    }  
  
    public boolean hasDinner() {  
        return this.extras.hasOwnProperty("dinner") && !this.isPeakDay();  
    }  
}

public class PremiumBookingDelegate {  
    private final Show show;  
  
    public PremiumBookingDelegate(Show show, Instant date, Extras extras) {  
        this.show = show;  
    }  
  
    public boolean hasTalkback() {  
        return this.show.hasOwnProperty("talkback");  
    }  
}
```

- 슈퍼클래스의 계산 로직을 함수로 추출하여 가격 계산과 분배 로직을 분리
```java
@Getter  
public class Booking {  
    private final Show show;  
    private final Instant date;  
    private PremiumBookingDelegate premiumDelegate;  
  
    public Booking(Show show, Instant date) {  
        this.show = show;  
        this.date = date;  
    }  
  
    public static Booking createBooking(Show show, Instant date) {  
        return new Booking(show, date);  
    }  
  
    public boolean hasTalkback() {  
        return (this.premiumDelegate != null)  
                ? this.premiumDelegate.hasTalkback()  
                : this.show.hasOwnProperty("talkback") && !this.isPeakDay();  
    }  
  
    public boolean isPeakDay() {  
        return true;  
    }  
  
    public double privateBasePrice() {  
        double result = this.show.getPrice();  
  
        if (this.isPeakDay()) result += Math.round(result * 0.15);  
        return result;  
    }  
  
    public double basePrice() {  
        return (this.premiumDelegate != null)  
                ? this.premiumDelegate.basePrice()  
                : this.privateBasePrice();  
    }  
  
    private void bePremium(Extras extras) {  
        this.premiumDelegate = new PremiumBookingDelegate(this, extras);  
    }  
}

public class PremiumBookingDelegate {  
    private final Booking host;  
    private final Extras extras;  
  
    public PremiumBookingDelegate(Booking host, Extras extras) {  
        this.host = host;  
        this.extras = extras;  
    }  
  
    public boolean hasTalkback() {  
        return this.host.getShow().hasOwnProperty("talkback");  
    }  
  
    public boolean isPeakDay() {  
        return true;  
    }  
      
    public double basePrice() {  
        return Math.round(this.host.privateBasePrice() + this.extras.premiumFee());  
    }  
}
```

- 위임의 메서드를 기반 메서드의 확장 형태로 재호출
```java
@Getter  
public class Booking {  
    // ...  
  
    public double basePrice() {  
        double result = this.show.getPrice();  
          
        if (this.isPeakDay()) result += Math.round(result * 0.15);  
        return (this.premiumDelegate != null)  
                ? this.premiumDelegate.extendBasePrice(result)  
                : result;  
    }  

	// ...
}

public class PremiumBookingDelegate {  
    // ...  
  
    public double extendBasePrice(double base) {  
        return Math.round(base + this.extras.premiumFee());  
    }  
}
```

- 팩토리 메서드가 슈펔르래스를 반환하도록 수정
```java
public class Booking {  
    // ...
  
    public static Booking createPremiumBooking(Show show, Instant date, Extras extras) {  
        Booking result = new Booking (show, date);  
        result.bePremium(extras);  
          
        return result;  
    }  
  
    // ...
}
```

#### 예시: 서브클래스가 여러 개일 때
```java
@Getter  
public class Bird {  
    private String name;  
    private String plumage;  
  
    public Bird(String name, String plumage) {  
        this.name = name;  
        this.plumage = plumage;  
    }  
  
    public String getPlumage() {  
  
        return (this.plumage != null) ? this.plumage : "보통이다";  
    }  
  
    public int airSpeedVelocity() {  
        return 0;  
    }  
}

public class EuropeanSwallow extends Bird {  
    public EuropeanSwallow(String name, String plumage) {  
        super(name, plumage);  
    }  
  
    @Override  
    public int airSpeedVelocity() {  
        return 35;  
    }  
}

public class AfricanSwallow extends Bird {  
    private int numberOfCoCount;  
  
    public AfricanSwallow(String name, String plumage, int numberOfCoCount) {  
        super(name, plumage);  
        this.numberOfCoCount = numberOfCoCount;  
    }  
  
    @Override  
    public int airSpeedVelocity() {  
        return 40 - 2 * this.numberOfCoCount;  
    }  
}

public class NorwegianBlueParrot extends Bird {  
    private String plumage;  
    private int voltage;  
    private boolean isNailed;  
  
    public NorwegianBlueParrot(String name, String plumage, int voltage, boolean isNailed) {  
        super(name, plumage);  
        this.plumage = plumage;  
        this.voltage = voltage;  
        this.isNailed = isNailed;  
    }  
  
    public String plumage() {  
        if (this.voltage > 100) return "그을렸다";  
        else return (this.plumage != null) ? this.plumage : "예쁘다";  
    }  
}
```

- 위임으로 바꾸기
```java
@Getter  
public class Bird {  
    private String name;  
    private String plumage;  
    private SpeciesDelegate speciesDelegate;  
  
    public Bird(Species data) {  
        this.name = data.name();  
        this.plumage = data.plumage();  
        this.speciesDelegate = this.selectSpeciesDelegate(data);  
    }  
  
    public SpeciesDelegate selectSpeciesDelegate(Species data) {  
        return switch (data.name()) {  
            case "유럽 제비" -> new EuropeanSwallowDelegate();  
            case "아프리카 제비" -> new AfricanSwallowDelegate(data);  
            case "노르웨이 파랑 앵무" -> new NorwegianBlueParrotDelegate(data);  
            default -> throw new IllegalStateException("Unexpected value: " + data.name());  
        };  
    }  
  
    public String getPlumage() {  
  
        return (this.plumage != null) ? this.plumage : "보통이다";  
    }  
  
    public int airSpeedVelocity() {  
        return speciesDelegate != null ? speciesDelegate.airSpeedVelocity() : 0;  
    }  
}

public class AfricanSwallowDelegate extends SpeciesDelegate {  
    private int numberOfCoCount;  
  
    public AfricanSwallowDelegate(Species data) {  
        this.numberOfCoCount = data.numberOfCoCount();  
    }  
  
    @Override  
    public int airSpeedVelocity() {  
        return 40 - 2 * this.numberOfCoCount;  
    }  
}

public class EuropeanSwallowDelegate extends SpeciesDelegate {  
  
    public int airSpeedVelocity() {  
        return 35;  
    }  
}

public class NorwegianBlueParrotDelegate extends SpeciesDelegate {  
    private String plumage;  
    private int voltage;  
    private boolean isNailed;  
  
    public NorwegianBlueParrotDelegate(Species data) {  
        this.plumage = data.plumage();  
        this.voltage = data.voltage();  
        this.isNailed = data.isNailed();  
    }  
  
    @Override  
    public int airSpeedVelocity() {  
        return (this.isNailed) ? 0 : 10 + this.voltage / 10;  
    }  
  
    public String plumage() {  
        if (this.voltage > 100) return "그을렸다";  
        else return (this.plumage != null) ? this.plumage : "예쁘다";  
    }  
}

public abstract class SpeciesDelegate {  
    public abstract int airSpeedVelocity();  
}
```

# 슈퍼클래스를 위임으로 바꾸기
#### 배경
객체 지향 프로그래밍에서 상속은 기존 기능 재활용하는 강력하고 손쉬운 수단.

그러나 상속이 혼란과 복잡도를 키우는 방식으로 이뤄지기도 한다.
자바의 스택은 리스트를 상속받아서 스택에 적용되지 않는 리스트의 연산이 스택 인터페이스에 그대로 노출

자바의 스택은 슈퍼클래스를 위임으로 바꾸는 리팩터링을 적용해야 하는 좋은 예

#### 절차
1. 슈퍼클래스 객체를 참조한느 필드를 서브클래스에 만든다. 위임 참조를 새로운 슈퍼클래스 인스턴스로 초기화한다.
2. 슈퍼클래스의 동작 각각에 대응하는 전달 함수를 서브클래스에 만든다. 서로 관련된 함수끼리 그룹으로 묶어 진행하며, 그룹을 하나씩 만들 때마다 테스트
    1. 대부분은 전달 함수 각각을 테스트할 수 있다.
3. 슈퍼클래스의 동작 모두가 전달 함수로 오버라이드되었다면 상속 관계를 끊는다.

#### 예시
```java
@Getter  
public class CatalogItem {  
    private Long id;  
    private String title;  
    private List<String> tags;  
  
    public CatalogItem(Long id, String title, List<String> tags) {  
        this.id = id;  
        this.title = title;  
        this.tags = tags;  
    }  
  
    public boolean hasTag(String arg) {  
        return tags.contains(arg);  
    }  
}

public class Scroll extends CatalogItem {  
    private Instant dateLastCleaned;  
  
    public Scroll(Long id, String title, List<String> tags, Instant dateLastCleaned) {  
        super(id, title, tags);  
        this.dateLastCleaned = dateLastCleaned;  
    }  
  
    public boolean needsCleaning(Instant targetDate) {  
        long threshold = this.hasTag("revered") ? 700 : 1500;  
        return this.daysSinceLastCleaning(targetDate) > threshold;  
    }  
  
    public long daysSinceLastCleaning(Instant targetDate) {  
        return this.dateLastCleaned.until(targetDate, ChronoUnit.DAYS);  
    }  
}
```

- 위임으로 바꾸기
```java
@Getter  
public class CatalogItem {  
    private Long id;  
    private String title;  
    private List<String> tags;  
  
    public CatalogItem(Long id, String title, List<String> tags) {  
        this.id = id;  
        this.title = title;  
        this.tags = tags;  
    }  
  
    public boolean hasTag(String arg) {  
        return tags.contains(arg);  
    }  
}

public class Scroll {  
    private CatalogItem catalogItem;  
    private Instant dateLastCleaned;  
  
    public Scroll(Long id, String title, List<String> tags, Instant dateLastCleaned) {  
        this.catalogItem = new CatalogItem(id, title, tags);  
        this.dateLastCleaned = dateLastCleaned;  
    }  
  
    public boolean needsCleaning(Instant targetDate) {  
        long threshold = this.hasTag("revered") ? 700 : 1500;  
        return this.daysSinceLastCleaning(targetDate) > threshold;  
    }  
  
    public long daysSinceLastCleaning(Instant targetDate) {  
        return this.dateLastCleaned.until(targetDate, ChronoUnit.DAYS);  
    }  
  
    public Long getId() {  
        return this.catalogItem.getId();  
    }  
  
    public String getTitle() {  
        return this.catalogItem.getTitle();  
    }  
  
    public boolean hasTag(String arg) {  
        return this.catalogItem.hasTag(arg);  
    }  
  
}
```