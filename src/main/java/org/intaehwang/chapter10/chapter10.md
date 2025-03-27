# 조건문 분해하기
#### 배경
복잡한 조건부 로직은 프로그램을 복잡하게 만든다.
다양한 조건과 동작 코드를 작성하면 순식간에 꽤 긴 함수가 탄생한다.
긴 함수 자체로도 읽기가 어렵지만, 조건문은 그 어려움을 한충 가중시킨다.

조건문은 무슨 일이 일어나는지 이야기는 해주지만 왜 일어나는지는 제대로 설명하지 않는다.

#### 절차
1. 조건식과 그 조건식에 딸린 조건절 각각을 함수로 호출
#### 예시
```java
public void test1(Plan plan, int quantity) {  
    double charge;  
      
    LocalDate aDate = LocalDate.now();  
      
    if (!aDate.isBefore(plan.summerStart()) && !aDate.isAfter(plan.summerEnd())) {  
        charge = quantity * plan.summerRate();  
    } else {  
        charge = quantity * plan.summerRate() + plan.regularServiceCharge();  
    }  
}
```

- 조건과 동작코드를 함수로 추출
```java
public void test1(Plan plan, int quantity) {  
    double charge;  
  
    LocalDate aDate = LocalDate.now();  
  
    if (summer(plan, aDate)) {  
        charge = summerCharge(plan, quantity);  
    } else {  
        charge = regularCharge(plan, quantity);  
    }  
}  
  
public double regularCharge(Plan plan, int quantity) {  
    return quantity * plan.summerRate() + plan.regularServiceCharge();  
}  
  
public double summerCharge(Plan plan, int quantity) {  
    return quantity * plan.summerRate();  
}  
  
public boolean summer(Plan plan, LocalDate aDate) {  
    return !aDate.isBefore(plan.summerStart()) && !aDate.isAfter(plan.summerEnd());  
}
```

- 선호에 따라 삼항연산으로 변경가능
```java
public void test1(Plan plan, int quantity) {  
    double charge;  
  
    LocalDate aDate = LocalDate.now();  
  
    charge = summer(plan, aDate) ? summerCharge(plan, quantity) : regularCharge(plan, quantity);
}
```
# 조건식 통합하기
#### 배경
비교하는 조건은 다르지만 그 결과로 수행하는 동작은 똑같은 코드들이 더러 있다.

이럴땐 and 연산자와 or 연산자를 사용하면 여러 개의 비교 로직을 하나로 합칠 수 있다.

조건부 코드를 통합하는 중요한 이유
1. 내가 하려는 일이 더 명확해진다.
2. **함수 추출하기** 까지 이어질 가능성이 높다.

조건식을 통합해야 하는 이유는 이 리팩터링을 하지 말아야 하는 이유도 설명해준다. 하나의 검사라고 생각할 수 없는, 다시 말해 진짜 독립된 검사들이라고 판단되면 이 리팩터링을 해서는 안된다.
#### 절차
1. 해당 조건식들 모에 부수효과가 없는지 확인
    1. 부수효과가 있는 조건식들에는 질의 함수와 **변경 함수 분리하기**를 먼저 적용
2. 조건문 두 개를 선택하여 두 조건문의 조건식들을 논리 연산자로 결합
    1. 순차적으로 이뤄지는(레벨이 같은) 조건문은 or로 결합, 중첩된 조건문은 and로 결합
3. 테스트
4. 조건이 하나만 남을 때까지 반복
5. 하나로 합쳐진 조건식을 **함수로 추출**할지 고려
#### 예시: or 사용하기
```java
public record Employee(  
        int seniority,  
        int monthsDisabled,  
        boolean isPartTime  
) {  
}

public int disabilityAmount(Employee anEmployee) {  
    if (anEmployee.seniority() < 2) return 0;  
    if (anEmployee.monthsDisabled() > 12) return 0;  
    if (anEmployee.isPartTime()) return 0;  
  
    return 1;  
}
```

- 순차적인 경우 or 연산자로 묶기
```java
public int disabilityAmount(Employee anEmployee) {  
    if ((anEmployee.seniority() < 2)  
            || (anEmployee.monthsDisabled() > 12)  
            || (anEmployee.isPartTime())) return 0;  
  
    return 1;  
}
```

- 함수로 추출
```java
public int disabilityAmount(Employee anEmployee) {  
    if (isNotEligibleForDisability(anEmployee)) return 0;  
  
    return 1;  
}  
  
public boolean isNotEligibleForDisability(Employee anEmployee) {  
    return (anEmployee.seniority() < 2)  
            || (anEmployee.monthsDisabled() > 12)  
            || (anEmployee.isPartTime());  
}
```

#### 예시: and 사용하기
```java
public record Employee(  
        int seniority,  
        int monthsDisabled,  
        boolean isPartTime,  
        boolean onVacation  
) {  
}

public int disabilityAmount(Employee anEmployee) {  
    if (isNotEligibleForDisability(anEmployee)) return 0;  
    if (anEmployee.onVacation()) {  
        if (anEmployee.seniority() > 10)  
            return 1;  
    }  
  
    return 2;  
}
```

- if 중첩문을 and로 결합
```java
public int disabilityAmount(Employee anEmployee) {  
    if (isNotEligibleForDisability(anEmployee)) return 0;  
    if (anEmployee.onVacation()  
            && (anEmployee.seniority() > 10)) {  
        return 1;  
    }  
  
    return 2;  
}
```
# 중첩 조건문을 보호 구문으로 바꾸기
#### 배경
조건문은 주로 두 가지 형태로 쓰인다.
1. 모두 정상 동작으로 이어지는 형태
2. 한쪽만 정상인 형태

두 형태는 의도하는 바가 서로 다르므로 그 의도가 코드에 드러나야 한다.

정상 동작이라면 if else 절을 사용

한쪽만 정상이라면 비정상 조건을 if에서 검사, 참이면 함수에서 빠져나온다. (보호구문, guard clause)

중첩 조건문을 보호 구문으로 바꾸기 리팩터링의 핵심은 **의도를 부각**하는 데 있다.
#### 절차
1. 교체해야 할 조건 중 가장 바깥 것을 선택하여 보호 구문으로 바꾼다.
2. 테스트
3. 필요한 만큼 반복
4. 모든 보호 구문이 같은 결과를 반환한다면 **보호 구문들의 조건식을 통합**한다.
#### 예시
```java
public PayAmount payAmount(Employee anEmployee) {  
    PayAmount result;  
    if (anEmployee.isSeparated()) {  
        result = new PayAmount(0, "SEP");  
    } else {  
        if (anEmployee.isRetired()) {  
            result = new PayAmount(0, "RET");  
        } else {  
            lorem.ipsum(dolor.sitAmet);  
            consectetur(adipiscing).elit();  
            sed.do.eiusmod = tempor.indicicunt.ut(labore) && dolore(magna.aliqua);  
            ut.enim.ad(minim.weniam);  
            result = someFinalComputation();  
        }  
    }  
  
    return result;  
}
```

- 최상위 조건부터 보호 구문으로 바꾸기
```java
    public PayAmount payAmount(Employee anEmployee) {  
        if (anEmployee.isSeparated()) return new PayAmount(0, "SEP");  
        if (anEmployee.isRetired()) return new PayAmount(0, "RET");  
//        lorem.ipsum(dolor.sitAmet);  
//        consectetur(adipiscing).elit();  
//        sed.do.eiusmod = tempor.indicicunt.ut(labore) && dolore(magna.aliqua);  
//        ut.enim.ad(minim.weniam);  
        return someFinalComputation();  
    }
```

> [!TMI]
> 급여 계산 코드가 이해가 안되어서 GPT한테 물어봤는데...

![Image](https://github.com/user-attachments/assets/c65ea2ac-afa2-4967-bfdc-58fc2117f8de)

#### 예시: 조건 반대로 만들기
```java
public int adjustedCapital(Instrument anInstrument) {  
    int result = 0;  
      
    if (anInstrument.capital() > 0) {  
        if (anInstrument.interestRate() > 0 && anInstrument.duration() > 0) {  
            result = (anInstrument.income() / anInstrument.duration()) * anInstrument.adjustmentFactor();  
        }  
    }  
      
    return result;  
}
```

- 중간에 부정으로 바꾼다음 부정문을 드모르간 법칙을 이용해 수정
```java
public int adjustedCapital(Instrument anInstrument) {  
    if (anInstrument.capital() <= 0  
            || (anInstrument.interestRate() <= 0 || anInstrument.duration() <= 0)) return 0;  
  
    return (anInstrument.income() / anInstrument.duration()) * anInstrument.adjustmentFactor();  
}
```
# 조건부 로직을 다형성으로 바꾸기
#### 배경
복잡한 조건부 로직은 프로그래밍에서 해석하기 가장 난해한 대상이다.

직관적으로 구조화할 방법을 항상 고민한다.

종종 더 높은 수준의 개념을 도입해 이 조건들을 분리할 수 있다.

클래스와 다형성을 이용하면 더 확실하게 분리할 수도 있다.

타입을 여러 개 만들고 각 타입이 조건부 로직을 처리하도록 구성하는 방법이 있다. switch문이 포함된 함수가 여러 개 보인다면 분명 이러한 상황

case별로 클래스를 하나씩 만들어 공통 switch 로직의 중복을 없앨 수 있다.
#### 절차
1. 다형적 동작을 표현하는 클래스들이 아직 없다면 만든다. 적합한 인스턴스를 알아서 만들어 반환하는 팩토리 함수도 함께 만든다.
2. 호출하는 코드에서 팩토리 함수를 사용하게 한다.
3. 조건부 로직 함수를 슈퍼클래스로 옮긴다.
    1. 조건부 로직이 온전한 함수로 분리되어 있지 않다면 먼저 **함수로 추출**한다.
4. 서브클래스 중 하나를 선택한다. 서브클래스에서 슈퍼클래스의 조건부 로직 메서드를 오버라이드한다. 조건부 문장 중 선택된 서브클래스에 해당하는 조건절을 서브클래스 메서드로 복사한 다음 적절히 수정
5. 같은 방식으로 각 조건절을 해당 서브클래스에서 메서드로 구현
6. 슈퍼클래스 메서드에는 기본 동작 부분만 남긴다. 슈퍼클래스가 추상 클래스여야 한다면. 이 메서드를 추상으로 선언하거나 서브클래스에서 처리해야 함을 알리는 에러를 던진다.
#### 예시
```java
public record Bird(  
        String name,  
        String type,  
        int numberOfCoconuts,  
        int voltage,  
        boolean isNailed  
) {
}

public Map<String, String> plumages(List<Bird> birds) {  
    return birds.stream()  
            .collect(Collectors.toMap(Bird::name, this::plumage));  
}  
  
public Map<String, Integer> speeds(List<Bird> birds) {  
    return birds.stream()  
            .collect(Collectors.toMap(Bird::name, this::airSpeedVelocity));  
}  
  
public String plumage(Bird bird) {  
    return switch (bird.type()) {  
        case "유럽 제비" -> "보통이다.";  
        case "아프리카 제비" -> (bird.numberOfCoconuts() > 2) ? "지쳤다" : "보통이다";  
        case "노르웨이 파랑 앵무" -> (bird.voltage() > 100) ? "그을렸다" : "예쁘다";  
        default -> "알 수 없다";  
    };  
}  
  
public int airSpeedVelocity(Bird bird) {  
    return switch (bird.type()) {  
        case "유럽 제비" -> 35;  
        case "아프리카 제비" -> 40 - 2 * bird.numberOfCoconuts();  
        case "노르웨이 파랑 앵무" -> (bird.isNailed()) ? 0 : 10 + bird.voltage() / 10;  
        default -> 0;  
    };  
}
```

- airSpeedVelocity()와 plumage()를 Bird class로 옮기기
```java
public record Bird(  
        String name,  
        String type,  
        int numberOfCoconuts,  
        int voltage,  
        boolean isNailed  
) {  
    public String plumage() {  
        return switch (this.type()) {  
            case "유럽 제비" -> "보통이다";  
            case "아프리카 제비" -> (this.numberOfCoconuts() > 2) ? "지쳤다" : "보통이다";  
            case "노르웨이 파랑 앵무" -> (this.voltage() > 100) ? "그을렸다" : "예쁘다";  
            default -> "알 수 없다";  
        };  
    }  
  
    public int airSpeedVelocity() {  
        return switch (this.type()) {  
            case "유럽 제비" -> 35;  
            case "아프리카 제비" -> 40 - 2 * this.numberOfCoconuts();  
            case "노르웨이 파랑 앵무" -> (this.isNailed()) ? 0 : 10 + this.voltage() / 10;  
            default -> 0;  
        };  
    }  
}

public Map<String, String> plumages(List<Bird> birds) {  
    return birds.stream()  
            .collect(Collectors.toMap(Bird::name, Bird::plumage));  
}  
  
public Map<String, Integer> speeds(List<Bird> birds) {  
    return birds.stream()  
            .collect(Collectors.toMap(Bird::name, Bird::airSpeedVelocity));  
}
```

- 팩토리 함수를 사용하도록 수정
```java
@Getter  
public class Bird {  
    protected final String name;  
    protected final String type;  
    protected final int numberOfCoconuts;  
    protected final int voltage;  
    protected final boolean isNailed;  
  
    protected Bird(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {  
        this.name = name;  
        this.type = type;  
        this.numberOfCoconuts = numberOfCoconuts;  
        this.voltage = voltage;  
        this.isNailed = isNailed;  
    }  
  
    public static Bird create(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {  
        return switch (type) {  
            case "유럽 제비" -> new EuropeanSwallow(name, type, numberOfCoconuts, voltage, isNailed);  
            case "아프리카 제비" -> new AfricanSwallow(name, type, numberOfCoconuts, voltage, isNailed);  
            case "노르웨이 파랑 앵무" -> new NorwegianBlueParrot(name, type, numberOfCoconuts, voltage, isNailed);  
            default -> new Bird(name, type, numberOfCoconuts, voltage, isNailed);  
        };  
    }  
  
    public String plumage() {  
        return "알 수 없다";  
    }  
  
    public int airSpeedVelocity() {  
        return 0;  
    }  
}

public class EuropeanSwallow extends Bird {  
    public EuropeanSwallow(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {  
        super(name, type, numberOfCoconuts, voltage, isNailed);  
    }  
  
    @Override  
    public String plumage() {  
        return "보통이다";  
    }  
  
    @Override  
    public int airSpeedVelocity() {  
        return 35;  
    }  
}

public class NorwegianBlueParrot extends Bird {  
    protected NorwegianBlueParrot(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {  
        super(name, type, numberOfCoconuts, voltage, isNailed);  
    }  
  
    @Override  
    public String plumage() {  
        return (this.voltage > 100) ? "그을렸다" : "예쁘다";  
    }  
  
    @Override  
    public int airSpeedVelocity() {  
        return (this.isNailed) ? 0 : 10 + this.voltage / 10;  
    }  
}

public class AfricanSwallow extends Bird {  
    protected AfricanSwallow(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {  
        super(name, type, numberOfCoconuts, voltage, isNailed);  
    }  
  
    @Override  
    public String plumage() {  
        return (this.numberOfCoconuts > 2) ? "지쳤다" : "보통이다";  
    }  
  
    @Override  
    public int airSpeedVelocity() {  
        return 40 - 2 * this.numberOfCoconuts;  
    }  
}

@Test  
public void bird() {  
    // given  
    Bird bird = Bird.create("", "한국 제비", 0, 0, false);  
  
    // when  
    String plumage = bird.plumage();  
    int airSpeedVelocity = bird.airSpeedVelocity();  
  
    // then  
    assertThat(plumage).isEqualTo("알 수 없다");  
    assertThat(airSpeedVelocity).isEqualTo(0);  
}  
  
@Test  
public void europeanSwallow() {  
    // given  
    Bird bird = Bird.create("", "유럽 제비", 0, 0, false);  
  
    // when  
    String plumage = bird.plumage();  
    int airSpeedVelocity = bird.airSpeedVelocity();  
  
    // then  
    assertThat(plumage).isEqualTo("보통이다");  
    assertThat(airSpeedVelocity).isEqualTo(35);  
    assertThat(bird).isInstanceOf(EuropeanSwallow.class);  
}  
  
@Test  
public void africanSwallow() {  
    // given  
    Bird bird = Bird.create("", "아프리카 제비", 2, 0, false);  
  
    // when  
    String plumage = bird.plumage();  
    int airSpeedVelocity = bird.airSpeedVelocity();  
  
    // then  
    assertThat(plumage).isEqualTo("보통이다");  
    assertThat(airSpeedVelocity).isEqualTo(36);  
    assertThat(bird).isInstanceOf(AfricanSwallow.class);  
}  
  
@Test  
public void africanSwallowIsOver2() {  
    // given  
    Bird bird = Bird.create("", "아프리카 제비", 3, 0, false);  
  
    // when  
    String plumage = bird.plumage();  
    int airSpeedVelocity = bird.airSpeedVelocity();  
  
    // then  
    assertThat(plumage).isEqualTo("지쳤다");  
}  
  
@Test  
public void norwegianBlueParrot() {  
    // given  
    Bird bird = Bird.create("", "노르웨이 파랑 앵무", 0, 100, false);  
  
    // when  
    String plumage = bird.plumage();  
    int airSpeedVelocity = bird.airSpeedVelocity();  
  
    // then  
    assertThat(plumage).isEqualTo("예쁘다");  
    assertThat(airSpeedVelocity).isEqualTo(20);  
}  
  
@Test  
public void norwegianBlueParrotIsOver100() {  
    // given  
    Bird bird = Bird.create("", "노르웨이 파랑 앵무", 0, 110, false);  
  
    // when  
    String plumage = bird.plumage();  
    int airSpeedVelocity = bird.airSpeedVelocity();  
  
    // then  
    assertThat(plumage).isEqualTo("그을렸다");  
    assertThat(airSpeedVelocity).isEqualTo(21);  
}  
  
@Test  
public void norwegianBlueParrotNaliedIsTure() {  
    // given  
    Bird bird = Bird.create("", "노르웨이 파랑 앵무", 0, 110, true);  
  
    // when  
    String plumage = bird.plumage();  
    int airSpeedVelocity = bird.airSpeedVelocity();  
  
    // then  
    assertThat(airSpeedVelocity).isEqualTo(0);  
}
```

#### 예시: 변형 동작을 다형성으로 표현하기
```java
@Getter  
public class Voyage {  
    private final String zone;  
    private final int length;  
  
    public Voyage(String zone, int length) {  
        this.zone = zone;  
        this.length = length;  
    }  
}

@Getter  
public class History {  
    private final String zone;  
    private final int profit;  
  
    public History(String zone, int profit) {  
        this.zone = zone;  
        this.profit = profit;  
    }  
}

public String rating(Voyage voyage, List<History> histories) {  
    int vpf = voyageProfitFactor(voyage, histories);  
    int vr = voyageRisk(voyage);  
    int chr = captainHistoryRisk(voyage, histories);  
  
    if (vpf * 3 > (vr + chr * 2)) return "A";  
    else return "B";  
}  
  
public int voyageRisk(Voyage voyage) {  
    int result = 1;  
  
    if (voyage.getLength() > 4) result += 2;  
    if (voyage.getLength() > 8) result += voyage.getLength() - 8;  
    if (List.of("중국", "동인도").contains(voyage.getZone())) result += 4;  
  
    return Math.max(result, 0);  
}  
  
public int captainHistoryRisk(Voyage voyage, List<History> histories) {  
    int result = 1;  
  
    if (histories.size() < 5) result += 4;  
    result += histories.stream()  
            .filter(v -> v.getProfit() < 0)  
            .toList()  
            .size();  
  
    if ("중국".equals(voyage.getZone()) && hasChina(histories)) result -= 2;  
  
    return Math.max(result, 0);  
  
}  
  
public boolean hasChina(List<History> histories) {  
    return histories.stream()  
            .anyMatch(v -> "중국".equals(v.getZone()));  
}  
  
public int voyageProfitFactor(Voyage voyage, List<History> histories) {  
    int result = 2;  
  
    if ("중국".equals(voyage.getZone())) result += 1;  
    if ("동인도".equals(voyage.getZone())) result += 1;  
    if ("중국".equals(voyage.getZone()) && hasChina(histories)) {  
        result += 3;  
  
        if (histories.size() > 10) result += 1;  
        if (voyage.getLength() > 12) result += 1;  
        if (voyage.getLength() > 18) result -= 1;  
    } else {  
        if (histories.size() > 8) result += 1;  
        if (voyage.getLength() > 14) result -= 1;  
    }  
  
    return result;  
}

@Test  
public void voyageRatingTest() {  
    // given  
    Chapter10Test chapter10Test = new Chapter10Test();  
    Voyage voyage = new Voyage("서인도", 10);  
    List<History> histories = List.of(  
            new History("동인도", 5),  
            new History("서인도", 15),  
            new History("중국", -2),  
            new History("서아프리카", 7)  
    );  
  
    // when  
    String rating = chapter10Test.rating(voyage, histories);  
  
    // then  
    assertThat(rating).isEqualTo("B");  
}
```

- Rating class 만들기
```java
public String rating(Voyage voyage, List<History> histories) {  
    return new Rating(voyage, histories).value();  
}

public class Rating {  
    private final Voyage voyage;  
    private final List<History> histories;  
  
    public Rating(Voyage voyage, List<History> histories) {  
        this.voyage = voyage;  
        this.histories = histories;  
    }  
  
    public String value() {  
        int vpf = voyageProfitFactor();  
        int vr = voyageRisk();  
        int chr = captainHistoryRisk();  
  
        if (vpf * 3 > (vr + chr * 2)) return "A";  
        else return "B";  
    }  
  
    public int voyageRisk() {  
        int result = 1;  
  
        if (voyage.getLength() > 4) result += 2;  
        if (voyage.getLength() > 8) result += voyage.getLength() - 8;  
        if (List.of("중국", "동인도").contains(voyage.getZone())) result += 4;  
  
        return Math.max(result, 0);  
    }  
  
    public int captainHistoryRisk() {  
        int result = 1;  
  
        if (histories.size() < 5) result += 4;  
        result += histories.stream()  
                .filter(v -> v.getProfit() < 0)  
                .toList()  
                .size();  
  
        if ("중국".equals(voyage.getZone()) && hasChinaHistory()) result -= 2;  
  
        return Math.max(result, 0);  
  
    }  
  
    public boolean hasChinaHistory() {  
        return histories.stream()  
                .anyMatch(v -> "중국".equals(v.getZone()));  
    }  
  
    public int voyageProfitFactor() {  
        int result = 2;  
  
        if ("중국".equals(voyage.getZone())) result += 1;  
        if ("동인도".equals(voyage.getZone())) result += 1;  
        if ("중국".equals(voyage.getZone()) && hasChinaHistory()) {  
            result += 3;  
  
            if (histories.size() > 10) result += 1;  
            if (voyage.getLength() > 12) result += 1;  
            if (voyage.getLength() > 18) result -= 1;  
        } else {  
            if (histories.size() > 8) result += 1;  
            if (voyage.getLength() > 14) result -= 1;  
        }  
  
        return result;  
    }  
}
```

- 변형 동작을 담을 빈 서브클래스 및 팩토리 함수 만들기
```java
public String rating(Voyage voyage, List<History> histories) {  
    return createRating(voyage, histories).value();  
}  
  
public Rating createRating(Voyage voyage, List<History> histories) {  
    if ("중국".equals(voyage.getZone()) && histories.stream()  
            .anyMatch(v -> "중국".equals(v.getZone()))) {  
        return new ExperiencedChinaRating(voyage, histories);              
    } else return new Rating(voyage, histories);  
}

public class ExperiencedChinaRating extends Rating{  
    public ExperiencedChinaRating(Voyage voyage, List<History> histories) {  
        super(voyage, histories);  
    }  
}
```

- 메서드 이름의 "AND"는 이 메서드가 두 가지 독립된 일을 수행함을 알 수 있다.
```java
public class Rating {  
    private final Voyage voyage;  
    private final List<History> histories;  
  
    public Rating(Voyage voyage, List<History> histories) {  
        this.voyage = voyage;  
        this.histories = histories;  
    }  
  
    public String value() {  
        int vpf = voyageProfitFactor();  
        int vr = voyageRisk();  
        int chr = captainHistoryRisk();  
  
        if (vpf * 3 > (vr + chr * 2)) return "A";  
        else return "B";  
    }  
  
    public int voyageRisk() {  
        int result = 1;  
  
        if (voyage.getLength() > 4) result += 2;  
        if (voyage.getLength() > 8) result += voyage.getLength() - 8;  
        if (List.of("중국", "동인도").contains(voyage.getZone())) result += 4;  
  
        return Math.max(result, 0);  
    }  
  
    public int captainHistoryRisk() {  
        int result = 1;  
  
        if (histories.size() < 5) result += 4;  
        result += histories.stream()  
                .filter(v -> v.getProfit() < 0)  
                .toList()  
                .size();  
  
        return Math.max(result, 0);  
  
    }  
  
    public boolean hasChinaHistory() {  
        return histories.stream()  
                .anyMatch(v -> "중국".equals(v.getZone()));  
    }  
  
    public int voyageProfitFactor() {  
        int result = 2;  
  
        if ("중국".equals(voyage.getZone())) result += 1;  
        if ("동인도".equals(voyage.getZone())) result += 1;  
        result += historyLengthFactor();  
        result += voyageLengthFactor();  
  
        return result;  
    }  
  
    public int voyageLengthFactor() {  
        int result = 0;  
  
        if (voyage.getLength() > 14) result -= 1;  
  
        return result;  
    }  
  
    public int historyLengthFactor() {  
        int result = 0;  
  
        if (histories.size() > 8) result += 1;  
  
        return result;  
    }  
}

public class ExperiencedChinaRating extends Rating{  
    private final Voyage voyage;  
    private final List<History> histories;  
  
    public ExperiencedChinaRating(Voyage voyage, List<History> histories) {  
        super(voyage, histories);  
        this.voyage = voyage;  
        this.histories = histories;  
    }  
  
    @Override  
    public int captainHistoryRisk() {  
        int result = super.captainHistoryRisk() - 2;  
  
        return Math.max(result, 0);  
    }  
  
    @Override  
    public int voyageProfitFactor() {  
        return super.voyageProfitFactor() + 3;  
    }  
  
    @Override  
    public int voyageLengthFactor() {  
        int result = 0;  
  
        if (voyage.getLength() > 12) result += 1;  
        if (voyage.getLength() > 18) result -= 1;  
  
        return result;  
    }  
  
    @Override  
    public int historyLengthFactor() {  
        int result = 0;  
        if (histories.size() > 10) result += 1;  
        return result;  
    }  
}
```

# 특이 케이스 추가하기
#### 배경
데이터 구조의 특정 값을 확인한 후 똑같은 동작을 수행하는 코드가 곳곳에 등장하는 경우가 더러 있다.
코드베이스에서 특정 값에 대해 똑같은 반응하는 코드가 여러 곳이라면 그 반응들을 한 데로 모으는 게 효율적이다.

특이 케이스 패턴이라는 것이 있는데, 바로 이럴 때 적용하면 좋은 메커니즘이다. 이 패턴을 활용하면 특이 케이스를 확인하는 코드 대부분을 단순한 함수 호출로 바꿀 수 있다.

특이 케이스는 여러 형태로 표현할 수 있다. 특이 케이스 객체에서 단순히 데이터를 읽기만 한다면 반환할 값들을 담은 리터럴 객체 형태로 준비하면 된다. 그 이상의 어떤 동작을 수행해야 한다면 필요한 메서드를 담은 객체를 생성하면 된다.
#### 절차
1. 컨테이너에 특이 케이스인지를 검사하는 속성을 추가하고, false를 반환한다
2. 특이 케이스 객체를 만든다. 이 객체는 특이 케이스인지를 검사하는 속성만 포함하며, 이 속성은 true를 반환하게 한다.
3. 클라이언트에서 특이 케이스인지를 검사하는 코드를 함수로 추출한다. 모든 클라이언트가 값을 직접 비교하는 대신 방금 추출한 함수를 사용하도록 고친다.
4. 코드에 새로운 특이 케이스 대상을 추가한다. 함수의 반환 값으로 받거나 변환 함수를 적용하면 된다.
5. 특이 케이스를 검사하는 함수 본문을 수정하여 특이 케이스 객체의 속성을 사용하도록 한다.
6. 테스트
7. 여러 함수를 클래스로 묶기나 여러 함수를 변환 함수로 묶기를 적용하여 특이 케이스를 처리하는 공통 동작을 새로운 요소로 옮긴다.
    1. 특이 케이스 클래스는 간단한 요청에는 항상 같은 값을 반환하는 게 보통이므로, 해당 특이 케이슬의 리터럴 레코드를 만들어 활용할 수 있을 것이다.
8. 아직도 특이 케이스 검사 함수를 이용하는 곳이 남아 있다면 검사 함수를 인라인 한다.
#### 예시
```java
@Getter  
public class Customer {  
    private final String name;  
    private int billingPlan;  
    private PaymentHistory paymentHistory;  
  
    public Customer(String name, int billingPlan, PaymentHistory paymentHistory) {  
        this.name = name;  
        this.billingPlan = billingPlan;  
        this.paymentHistory = paymentHistory;  
    }  
  
    public void setBillingPlan(int billingPlan) {  
        this.billingPlan = billingPlan;  
    }  
  
    public void setPaymentHistory(PaymentHistory paymentHistory) {  
        this.paymentHistory = paymentHistory;  
    }  
}

@Getter  
public class PaymentHistory {  
    private final int weekDelinquentInLastYear;  
  
    public PaymentHistory(int weekDelinquentInLastYear) {  
        this.weekDelinquentInLastYear = weekDelinquentInLastYear;  
    }  
}

public void client1(Customer customer) {  
    String customerName;  
  
    if (customer == null) customerName = "거주자";  
    else customerName = customer.getName();  
}  
  
public void client2(Customer customer) {  
    int plan = (customer == null) ?  
            REGISTRY_BILLING_PLANS_BASIC  
            : customer.getBillingPlan();  
}  
  
public void client3(Customer customer) {  
    if (customer != null) customer.setBillingPlan(NEW_PLAN);  
}  
  
public void client4(Customer customer) {  
    int weeksDelinquent = (customer == null) ?  
            0  
            : customer.getPaymentHistory().getWeekDelinquentInLastYear();  
}
```

- 미확인 고객 확인 메서드 추가
```java
@Getter  
public class Customer {  
    // ...  
      
    public boolean isUnknown() {  
        return false;  
    }
}

public class UnknownCustomer {  
  
    public boolean isUnknown() {  
        return true;  
    }  
}
```

- 미확인 고객 검사 함수추출
```java
public void client1(Customer customer) {  
    String customerName;  
  
    if (isUnknown(customer)) customerName = "거주자";  
    else customerName = customer.getName();  
}  
  
public void client2(Customer customer) {  
    int plan = (isUnknown(customer)) ?  
            REGISTRY_BILLING_PLANS_BASIC  
            : customer.getBillingPlan();  
}  
  
public void client3(Customer customer) {  
    if (!isUnknown(customer)) customer.setBillingPlan(NEW_PLAN);  
}  
  
public void client4(Customer customer) {  
    int weeksDelinquent = (isUnknown(customer)) ?  
            0  
            : customer.getPaymentHistory().getWeekDelinquentInLastYear();  
}  
  
public boolean isUnknown(Object arg) {  
    if (!(arg instanceof Customer) || (arg == null)) {  
        throw new IllegalArgumentException("잘못된 값과 비교");  
    }  
  
    return (arg == null);  
}
```

- Site class가 UnknownCustomer class를 반환하도록 수정
```java
public boolean isUnknown(Object arg) {  
    if (!(arg instanceof Customer) || arg instanceof UnknownCustomer) {  
        throw new IllegalArgumentException("잘못된 값과 비교");  
    }  
  
    return ((Customer) arg).isUnknown();  
}

public class UnknownCustomer implements MyCustomer {  
  
    public boolean isUnknown() {  
        return true;  
    }  
}
```

- UnknownCustomer class에 getName, getBillingPlan, setBillingPlan, getPaymentHistory 추가
```java
public class UnknownCustomer implements MyCustomer {  
  
    private static final int REGISTRY_BILLING_PLANS_BASIC = 110;  
  
    public boolean isUnknown() {  
        return true;  
    }  
  
    public String getName() {  
        return "거주자";  
    }  
  
    public int getBillingPlan() {  
        return REGISTRY_BILLING_PLANS_BASIC;  
    }  
  
    public void setBillingPlan(int billingPlan) {  
    }  
  
    public NullPaymentHistory getPaymentHistory() {  
        return new NullPaymentHistory(0);  
    }  
}

public void client1(Customer customer) {  
    String customerName = customer.getName();  
}  
  
public void client2(Customer customer) {  
    int plan = customer.getBillingPlan();  
}  
  
public void client3(Customer customer) {  
    customer.setBillingPlan(NEW_PLAN);  
}  
  
public void client4(Customer customer) {  
    int weeksDelinquent = customer.getPaymentHistory().getWeekDelinquentInLastYear();  
}  
  
public boolean isUnknown(Object arg) {  
    if (!(arg instanceof Customer) || arg instanceof UnknownCustomer) {  
        throw new IllegalArgumentException("잘못된 값과 비교");  
    }  
  
    return ((Customer) arg).isUnknown();  
}
```

#### 예시: 객체 리터럴 이용하기
```java
@Getter  
@Setter  
public class Customer {  
    private String name;  
    private int billingPlan;  
    private PaymentHistory paymentHistory;  
    private boolean unknown;  
  
    public Customer(String name, int billingPlan, PaymentHistory paymentHistory, boolean unknown) {  
        this.name = name;  
        this.billingPlan = billingPlan;  
        this.paymentHistory = paymentHistory;  
        this.unknown = unknown;  
    }  
}

public class Site {  
    private static final int REGISTRY_BILLING_PLANS_BASIC = 110;  
  
    private final Customer customer;  
  
    public Site(Customer customer) {  
        this.customer = customer;  
    }  
  
    public Customer getCustomer() {  
        return (customer == null) ? createUnknownCustomer() : this.customer;  
    }  
  
    public Customer createUnknownCustomer() {  
        return new Customer("거주자", REGISTRY_BILLING_PLANS_BASIC, new PaymentHistory(0), true);  
    }  
}
```


# 어서션 추가하기
#### 배경
특정 조건이 참일 때만 제대로 동작하는 코드가 있을 수 있다.
객체에서도 여러 필드 중 최소 하나에는 값이 있어야 동작하는 경우와 같다.

이런 가정이 코드에 항상 명시적으로 기술되어 있지 않아서 알고리즘을 보고 알아내야할 경우도 있다.

이럴 때 어서션을 이옹해서 코드 자체에 삽입하는 것이 좋다.

어서션은 항상 참이라고 가정하는 조건부 문장으로 어서션이 실패했다는건 프로그래머가 잘 못했다는 뜻
어션은 프로그램이 어떤 상태임을 가정한 채 실행되는지를 다른 개발자에게 알려주는 훌륭한 소통 도구

단위 테스트를 꾸준히 추가하여 사각을 좁히면 어서션보다 나을 때가 많지만 소통 측면에서는 어서션이 매력적
#### 절차
1. 참이라고 가정하는 조건이 보이면 그 조건을 명시하는 어서션을 추가한다.

#### 예시
```java
@Getter  
public class Customer {  
    private final int discountRate;  
  
    public Customer(int discountRate) {  
        this.discountRate = discountRate;  
    }  
      
    public int applyDiscount(int aNumber) {  
        return (this.discountRate > 0) ? aNumber - (this.discountRate * aNumber) : aNumber;  
    }  
}
```

- if-then 문장으로 재구성
```java
public int applyDiscount(int aNumber) {  
    if (this.discountRate <= 0) return aNumber;  
    else return aNumber - (this.discountRate * aNumber);  
}
```

- 어서션 추가
```java
public int applyDiscount(int aNumber) {  
    if (this.discountRate < 0) return aNumber;  
    else {  
        assert this.discountRate >= 0;  
        return aNumber - (this.discountRate * aNumber);  
    }  
}
```

- 어서션 세터로 이동
```java
public void setDiscountRate(int discountRate) {  
    assert (discountRate >= 0);  
    this.discountRate = discountRate;  
}
```
# 제어 플래그를 탈출문으로 바꾸기
#### 배경
제어 플래그는 코드의 동작을 변경하는데 사용하는 변수다

어딘가에서 값을 계산해 제어 플래그에 설정한 후 다은 어딘가의 조건문에서 검사하는 형태로 쓰인다.

제어 플래그의 주 서직지는 반복문 안이다.

break문이나 continue문 활용에 익숙하지 않은 사람이 심어놓기도 하고, 함수의 return문을 하나로 유지하고자 노력하는 사람이 심기도 한다.

#### 절차
1. 제어 플래그를 사용하는 코드를 함수로 추출할지 고려
2. 제어 플래그를 갱신하는 코드 각강르 적절한 제어문으로 바꾼다. 하나 바꿀 때마다 테스트
    1. 제어문으로 주로 return, break, continue가 쓰임
3. 모두 수정했다면 제어 플래그를 제거

#### 예시
```java
public void ttteeesssttt(List<String> people) {  
    boolean found = false;  
      
    for (String p : people) {  
        if (!found) {  
            if ("조커".equals(p)) {  
                sendAlert();  
                found = true;  
            }  
            if ("사루만".equals(p)) {  
                sendAlert();  
                found = true;  
            }  
        }  
    }  
}
```

- 갱신 코드 제거 후 제어 플래그를 참조하는 다른 코드도 제거
```java
public void ttteeesssttt(List<String> people) {  
    checkForMiscreants(people);  
}  
  
public void checkForMiscreants(List<String> people) {  
    for (String p : people) {  
        if ("조커".equals(p)) {  
            sendAlert();  
            return;  
        }  
        if ("사루만".equals(p)) {  
            sendAlert();  
            return;  
        }  
    }  
}
```