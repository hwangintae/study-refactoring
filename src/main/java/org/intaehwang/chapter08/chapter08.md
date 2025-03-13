## 함수 옮기기
#### 배경
좋은 소프트웨어 설계의 핵심은 모듈성

모듈성을 높이려면 요소 사이의 연결 관계를 쉽게 찾고 이해할 수 있어야 한다.

객체지향의 핵심은 모듈화 컨텍스트 클래스다.

어떤 함수가 자신이 속한 모듈보다 다른 모듈의 요소들을 더 많이 참조한다면 옮겨야한다. 그래야 캡슐화가 좋아져 세부사항에 덜 의존하게 된다.
#### 절차
1. 컨텍스트를 확인해서 함수와 함께 옮겨야 할 요소가 있는지 고민한다.
    1. 호출되는 함수 중 함께 옮길게 있다면 먼저 옮긴다. 여러개면 영향이 적은 함수부터 옮긴다.
    2. 하위 함수 호출자가 고수준 함수 하나뿐이면 먼저 고수준 함수에 인라인한 다음, 고수준 함수로 옮기고, 옮긴 위치에서 개별 함수들로 다시 추출
2. 선택한 함수가 다형 메서드인지 확인한다.
    1. 객체 지향 언어에서는 같은 메서드가 슈퍼클래스나 서브클래스에도 선언되어 잇는지 고려
3. 선택한 함수를 타깃 컨텍스트로 복사한다.
    1. 함수 본문에서 소스 컨텍스트의 요소를 사용한다면 해당 요소들을 매개변수로 넘기거나 소스 컨텍스 자체를 참조로 넘긴다.
    2. 함수를 옮기게 되면 새로운 컨텍스트에 어울리는 새로운 이름으로 바꿔줘야 할 경우가 많다. 필요하면 바꾼다.
4. 정적 분석을 수행한다.
5. 소스 컨텍스트에서 타깃 함수를 참조할 방법ㅇ르 찾아 반영한다.
6. 소스 함수를 타깃 함수의 위임 함수가 되도록 수정한다.
7. 테스트
8. 소스 함수를 인라인할지 고민
    1. 소스 함수는 언제든 위임 함수로 남겨둘 수 있다. 하지만 소스 함수를 호출하는 곳에서 타깃 함수를 직접 호출하는 데 무리가 없다면 중간 단계(소스 함수)는 제거하는 편이 낫다.

#### 예시: 중첩 함수를 최상위로 옮기기
> [!note] java에는 중첩 함수가 없으므로 그냥 클래스로 대체하였다.

```java
@Getter  
public class TrackSummary {  
    private double totalTime;  
    private double totalDistance;  
    private double pace;  
    private Point[] points;  

	public TrackSummary() {} 
  
    @Builder  
    private TrackSummary(double totalTime, double totalDistance, double pace, Point[] points) {  
        this.totalTime = totalTime;  
        this.totalDistance = totalDistance;  
        this.pace = pace;  
        this.points = points;  
    }   
  
    public TrackSummary create(Point[] points) {  
        double totalTime = calculateTime();  
        double totalDistance = calculateDistance();  
  
        return TrackSummary.builder()  
                .totalTime(totalTime)  
                .totalDistance(totalDistance)  
                .pace(totalTime / 60 / totalDistance)  
                .points(points)  
                .build();  
    }  
  
    public double calculateTime() {  
        return 0;  
    }  
  
    public double calculateDistance() {  
        new Distance();
    }   
  
}
```

- calculateDistance 함수 DIstance class로 옮기기
```java
public class Distance {  
  
    public Distance() {}  
  
    public static double calculate(Point[] points) {  
        double result = 0;  
  
        for (int i = 1; i < points.length; i++) {  
            result += distance(points[i - 1], points[i]);  
        }  
  
        return result;  
    }  
  
    public static double distance(Point p1, Point p2) {  
        int EARTH_RADIUS = 3959;  
  
        double dLat = radians(p2.getY()) - radians(p1.getY());  
        double dLon = radians(p2.getX()) - radians(p1.getX());  
  
        double a = Math.pow(Math.sin(dLat / 2), 2)  
                + Math.cos(radians(p2.getY()))  
                * Math.cos(radians(p1.getY()))  
                * Math.pow(Math.sin(dLon / 2), 2);  
  
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));  
  
        return EARTH_RADIUS * c;  
    }  
  
    public static double radians(double degrees) {  
        return degrees * Math.PI / 180;  
    }  
}
```

- 새 보금자리에 잘 정착했는지 확인
```java
public double calculateDistance() {  
    return Distance.calculate(points);
}
```

- 변수 인라인하기
```java
public TrackSummary create(Point[] points) {  
    double totalTime = calculateTime();  
  
    double totalDistance = Distance.calculate(points);  
  
    return TrackSummary.builder()  
            .totalTime(totalTime)  
            .totalDistance(totalDistance)  
            .pace(totalTime / 60 / totalDistance)  
            .points(points)  
            .build();  
}
```

#### 예시: 다른 클래스로 옮기기

```java
public class Account {  
  
    private int daysOverdrawn;  
    private AccountType type;  
  
    public double bankCharge() {  
        double result = 4.5;  
  
        if (daysOverdrawn > 0) result += overdraftCharge();  
  
        return result;  
    }  
  
    public double overdraftCharge() {  
        if (type.isPremium()) {  
            double baseCharge = 10;  
  
            if (daysOverdrawn <= 7) return baseCharge;  
            else return baseCharge + (daysOverdrawn - 7) * 0.85;  
        } else return daysOverdrawn * 1.75;  
    }  
}

@Getter  
public class AccountType {  
    private final boolean premium;  
  
    public AccountType(boolean premium) {  
        this.premium = premium;  
    }
}
```

- overdraftCharge() AccountType class로 옮기기
```java
@Getter  
public class AccountType {  
    private final boolean premium;  
  
    public AccountType(boolean premium) {  
        this.premium = premium;  
    }  
  
    public double overdraftCharge(int daysOverdrawn) {  
        if (this.premium) {  
            double baseCharge = 10;  
  
            if (daysOverdrawn <= 7) return baseCharge;  
            else return baseCharge + (daysOverdrawn - 7) * 0.85;  
        } else return daysOverdrawn * 1.75;  
    }  
}
```

- 새 보금자리에 잘 정착했는지 확인
```java
public double overdraftCharge() {  
    return type.overdraftCharge(this.daysOverdrawn);  
}
```

- 함수 인라인
```java
public class Account {  
  
    private int daysOverdrawn;  
    private AccountType type;  
  
    public double bankCharge() {  
        double result = 4.5;  
  
        if (daysOverdrawn > 0) result += type.overdraftCharge(this.daysOverdrawn);  
  
        return result;  
    }  
}
```

- 소스 컨텍스트에서 가져와야 할 데이터가 많다면?
```java
public double overdraftCharge(Account account) {  
    if (this.premium) {  
        double baseCharge = 10;  
  
        if (account.getDaysOverdrawn() <= 7) return baseCharge;  
        else return baseCharge + (account.getDaysOverdrawn() - 7) * 0.85;  
    } else return account.getDaysOverdrawn() * 1.75;  
}
```
## 필드 옮기기
#### 배경
프로그램의 진짜 힘은 데이터 구조에서 나온다. 데이터 구조를 활용하면 자연슬버게 단순하고 직관적인 코드가 짜여진다.

현재 데이터 구조가 적절치 않으면 곧바로 수정해야 한다.

함수에 어떤 레코드를 넘길 때마다 또 다른 레코드의 필드도 함께 넘기고 있다면 데이터 위치를 옮겨야 한다.

**데이터 조각**들은 **상호 관계가 명확하게 드러나도록** 한 레코드에 담는 게 가장 좋다.

구조체 여러 개에 정의된 똑같은 필드들을 갱신해야 한다면 한 번만 갱신해도 되는 다른 위치로 옮기라는 신호
#### 절차
1. 캡슐화되어 있지 않다면 캡슐화한다
2. 테스트
3. 타깃 객체에 필드와 접근자 메서드들을 생성한다.
4. 정적 검사를 수행
5. 소스 객체에서 타깃 객체를 참조할 수 있는지 확인
    1. 기존 필드나 메서드 중 타깃 객체를 넘겨주는 게 있을지 모른다. 간단치 않다면 객체를 저장할 새 필드를 소스 객체에 생성
6. 접근자들이 타깃 필드를 사용하도록 수정
    1. 여러 소스에서 같은 타깃을 공유한다면, 먼저 세터를 수정하여 타깃 필드와 소스 필드 모두를 갱신, 일관성을 깨뜨리는 갱신을 검출할 수 있도록 어서션을 추가. 모든게 잘  마무리되었다면 접근자들이 타깃 필드를 사용하도록 수정
7. 테스트
8. 소스 필드를 제거
9. 테스트
#### 예시
> [!note]
> 테스트의 편의를 위해 Clock class를 추가하였다.
```java
@Getter  
public class Customer {  
    private final String name;  
    private double discountRate;  
    private final CustomerContract customerContract;  
  
    public Customer(String name, double discountRate) {  
        this.name = name;  
        this.discountRate = discountRate;  
        this.customerContract = new CustomerContract(Clock.dateToday());  
    }  
  
    public void becomePreferred() {  
        this.discountRate += 0.03;  
    }  
  
    public double applyDiscount(Amount amount) {  
        return amount.subtract(amount.multiply(this.discountRate));  
    }  
}

@Getter  
public class CustomerContract {  
    private final LocalDate startDate;  
  
    public CustomerContract(LocalDate startDate) {  
        this.startDate = startDate;  
    }  
}

public class Amount {  
    private double amount;  
  
    public Amount(double amount) {  
        this.amount = amount;  
    }  
  
    public double subtract(double val) {  
        return this.amount -= val;  
    }  
  
    public double multiply(double val) {  
        return this.amount *= val;  
    }  
}

public class Clock {  
    public static LocalDate dateToday() {  
        return LocalDate.now();  
    }  
}
```

- CustomerContract class로 discountRate 옮기기
```java
@Getter  
@Setter  
public class CustomerContract {  
    private final LocalDate startDate;  
    private double discountRate;  
  
    public CustomerContract(LocalDate startDate) {  
        this.startDate = startDate;  
    }  
  
    public CustomerContract(LocalDate startDate, double discountRate) {  
        this.startDate = startDate;  
        this.discountRate = discountRate;  
    }  
}

@Getter  
public class Customer {  
    private final String name;  
    private double discountRate;  
    private final CustomerContract customerContract;  
  
    public Customer(String name, double discountRate) {  
        this.name = name;  
        setDiscountRate(discountRate);  
        this.customerContract = new CustomerContract(Clock.dateToday());  
    }  
  
    private void setDiscountRate(double aNumber) {  
        this.customerContract.setDiscountRate(aNumber);  
    }  
  
    public void becomePreferred() {  
        setDiscountRate(this.discountRate + 0.03);  
    }  
  
    public double applyDiscount(Amount amount) {  
        return amount.subtract(amount.multiply(this.discountRate));  
    }  
}
```
#### 예시: 공유 객체로 이동하기
```java
@Getter  
public class Account {  
    private final int number;  
    private final AccountType type;  
    private final double interestRate;  
  
    public Account(int number, AccountType type, double interestRate) {  
        this.number = number;  
        this.type = type;  
        this.interestRate = interestRate;  
    }  
}

@Getter  
public class AccountType {  
    private final String name;  
  
    public AccountType(String name) {  
        this.name = name;  
    }  
}
```

> [!warning]
> Account가 AccountType의 이자율을 가져오도록 수정하면 문제가 생길수 있다.
> 수정 전에도 이자율이 계좌 종류별로 같게 설저오디어 있다면 그래도 리팩터링하면 되고 이자율이 다른 계좌가 하나라도 있다면 리팩터링이 아니다.
>
> 먼저 데이터베이스를 확인해서 모든 계좌의 이자율이 계좌 종류에 부합하게설정되어 있는지 확인해야 한다.

```java
public Account(int number, AccountType type, double interestRate) {  
    this.number = number;  
    this.type = type;  
      
    if (interestRate != type.getInterestRate()) {  
        throw new IllegalArgumentException("Interest rate must be equal to " + type.getInterestRate());  
    }  
    this.interestRate = interestRate;  
}
```

## 문장을 함수로 옮기기
#### 배경
예컨대 특정 함수를 호출하는 코드가 나올 때마다 그 앞이나 뒤에서 똑같은 코드가 추가로 실행될 때 그 반복되는 부분을 피호출 함수로 합치는 방법을 궁리

추후 반복되는 부분에서 무언가 수정할 일이 생겼을 때 단 한 곳만 수정하면 된다.

문장들을 함수로 옮기려면 그 문장들이 피호출함수의 일부라는 확신이 있어야 한다.
#### 절차
1. 반복 코드가 함수 호출 부분과 멀리 떨어져 있다면 **문장 슬라이드하기**를 적용해 근처로 옮긴다.
2. 타깃 함수를 호출하는 곳이 한 곳뿐이면, 단순히 소스 위치에서 해당 코드를 잘라내어 피호출 함수로 복사하고 테스트
3. 호출자가 둘 이상이면 호출자 중 하나에서 '타깃 함수 호출 부분과 그 함수로 옮기려는 문장들을 함께' **다른 함수로 추출** 한다. 추출한 함수에 기억하기 쉬운 임시 이름을 지어준다.
4. 다른 호출자 모두가 방금 추출한 함수를 사용하도록 수정
5. 모든 호출자가 새로운 함수를 사용하게 되면 원래 함수를 **새로운 함수 안으로 인라인** 한 후 원래 함수를 제거
6. 새로운 함수의 이름을 원래 함수의 이름으로 바꾼다
    1. 더 나은 이름이 있다면 그 이름을 쓴다.
#### 예시
```java
@Getter  
public class Person {  
    private final String name;  
    private final Photo photo;  
  
    public Person(String name, Photo photo) {  
        this.name = name;  
        this.photo = photo;  
    }  
}

@Getter  
public class Photo {  
    private final String title;  
    private final String location;  
    private final LocalDate date;  
  
    public Photo(String title, String location, LocalDate date) {  
        this.title = title;  
        this.location = location;  
        this.date = date;  
    }  
}

public class Render {  
  
    public String renderPerson(Person person) {  
        List<String> result = List.of(  
                "<p>" + person.getName() + "</p>",  
                "<p>제목: " + person.getPhoto().getTitle() + "</p>"  
        );  
  
        return String.join("\n", result);  
    }  
  
    public String renderPhoto(Photo p) {  
        return String.join("\n", List.of(  
                "<div>",  
                "<p>" + p.getTitle() + "</p>",  
                emitPhotoData(p),  
                "</div>"  
        ))  
    }  
  
    public String emitPhotoData(Photo aPhoto) {  
        List<String> result = List.of(  
                "<p>위치" + aPhoto.getLocation() + "</p>",  
                "<p>날짜" + aPhoto.getDate().toString() + "</p>"  
        );  
  
        return String.join("\n", result);  
    }  
}
```

```java
public String renderPhoto(Photo p) {  
    return String.join("\n", List.of(  
            "<div>",  
            emitPhotoData(p),  
            "</div>"  
    ));
}  
  
public String emitPhotoData(Photo aPhoto) {  
    List<String> result = List.of(  
            "<p>" + aPhoto.getTitle() + "</p>",  
            "<p>위치" + aPhoto.getLocation() + "</p>",  
            "<p>날짜" + aPhoto.getDate().toString() + "</p>"  
    );  
  
    return String.join("\n", result);  
}
```
## 문장을 호출한 곳으로 옮기기
#### 배경
추상화의 경계는 항상 올바르게 긋기가 만만치 않다. 그래서 코드베이스의 기능 범위가 달라지면 추상화의 경계도 움직이게 된다.

함수 관점에서 생각해보면 초기에는 응집도 높고 한 가지 일만 수행하던 함수가 어느새 둘 이상의 다른 일을 수행하게 바뀔 수 있다.

여러 곳에서 사용하던 기능이 일부 호출자에게는 다르게 동작하도록 바뀌어야 한다면 이런 일들이 발생한다.
#### 절차
1. 호출자와 피호출자가 단순한 상황이면 피호출 함수의 처음줄을 잘라내어 호출자로 복사해 넣는다
2. 더 복작합 상황에서는 이동하지 '않길' 원하는 모든 문장을 함수로 추출한 다음 검색하기 쉬운 임시 이름을 지어준다.
    1. 대상 함수가 서브클래스에서 오버라이드 됐다면 오버라이드한 서브클래스들의 메서드 모두에서 동일하게 남길 부분을 메서드로 추출. 이때 남겨질 메서드의 본문은 모든 클래스에서 똑같아야 한다. 그런 다음 슈퍼클래스의 메서드만 남기고 서브클래스들의 메서드를 제거한다.
3. 원래 **함수를 인라인** 한다.
4. 추출된 함수의 이름을 원래 함수의 이름으로 변경한다.
    1. 더 나은 이름이 떠오르면 그 이름을 사용하자.
#### 예시
```java
public class Render {  
    public void renderPerson(OutputStreamWriter outStream, Person person) throws IOException {  
        outStream.write("<p>" + person.getName() + "</p>\n");  
        renderPhoto(outStream, person.getPhoto());  
        emitPhotoData(outStream, person.getPhoto());  
    }  
  
    public void listRecentPhotos(OutputStreamWriter outStream, List<Photo> photos) throws IOException {  
        photos.stream()  
                .filter(p -> p.getDate().isAfter(recentDateCutoff()))  
                .forEach(p -> {  
                    try {  
                        outStream.write("<div>\n");  
                        emitPhotoData(outStream, p);  
                        outStream.write("</div>\n");  
                    } catch (Exception ignored) {  
                    }  
                });  
    }  
  
    public void emitPhotoData(OutputStreamWriter outStream, Photo aPhoto) throws IOException {  
        outStream.write("<p>제목" + aPhoto.getTitle() + "</p>");  
        outStream.write("<p>날짜" + aPhoto.getDate().toString() + "</p>");  
        outStream.write("<p>위치" + aPhoto.getLocation() + "</p>");  
    }  
  
    public LocalDate recentDateCutoff() {  
        return Clock.cutOff();  
    }  
  
    public void renderPhoto(OutputStreamWriter outStream, Photo p) throws IOException {  
        emitPhotoData(outStream, p);  
    }  
}
```

```java
public class Render {  
    public void renderPerson(OutputStreamWriter outStream, Person person) throws IOException {  
        outStream.write("<p>" + person.getName() + "</p>\n");  
        renderPhoto(outStream, person.getPhoto());  
        emitPhotoData(outStream, person.getPhoto());  
        outStream.write("<p>위치" + person.getPhoto().getLocation() + "</p>");  
    }  
  
    public void listRecentPhotos(OutputStreamWriter outStream, List<Photo> photos) throws IOException {  
        photos.stream()  
                .filter(p -> p.getDate().isAfter(recentDateCutoff()))  
                .forEach(p -> {  
                    try {  
                        outStream.write("<div>\n");  
                        emitPhotoData(outStream, p);  
                        outStream.write("<p>위치" + p.getLocation() + "</p>");  
                        outStream.write("</div>\n");  
                    } catch (Exception ignored) {  
                    }  
                });  
    }  
  
    public LocalDate recentDateCutoff() {  
        return Clock.cutOff();  
    }  
  
    public void renderPhoto(OutputStreamWriter outStream, Photo p) throws IOException {  
    }  
  
    public void emitPhotoData(OutputStreamWriter outStream, Photo photo) throws IOException {  
        outStream.write("<p>제목" + photo.getTitle() + "</p>");  
        outStream.write("<p>날짜" + photo.getDate().toString() + "</p>");  
    }  
}
```

## 인라인 코드를 함수 호출로 바꾸기
#### 배경
함수는 여러 동작을 하나로 묶어준다. 함수의 이름이 목적을 말해주기 때문에 함수를 활용하면 코드를 이해하기 쉬워진다.

이미 존재하는 함수와 똑같은 일을 하는 인라인 코드를 발견하면 해당 코드를 함수 호출로 대체하길 원한다.

함수의 이름이 명확하다면 인라인 코드 대신 함수 이름을 넣어도 말이된다.
#### 절차
1. 인라인 코드를 함수 호출로 대체한다.
2. 테스트
## 문장 슬라이드하기
#### 배경
관련된 코드들이 가까이 모여 있다면 이해하기가 더 쉽다.

모든 변수 선언을 함수 첫머리에 모아두는 사람도 있는데, 마틴 파울러는 변수를 처음 사용할 때 선언하는 스타일을 선호

코드가 모여있지 않다면 함수 추출은 애초에 수행할 수 조차 없다.
#### 절차
1. 코드 조각을 이동할 목표 위치를 찾는다. 코드 조각의 원래 위치와 목표 위치 사이의 코드를 본다. 조각을 모으고 나면 동작이 달라지는 코드가 있는지 살핀다. 다음과 같은 간섭이 있다면 이 리팩터링을 포기한다.
    1. 참조하는 요소를 선언하는 문장 앞으로 이동할 수 없다.
    2. 참조하는 요소의 뒤로는 이동할 수 없다.
    3. 참조하는 요소를 수정하는 문장을 건너뛰어 이동할 수 없다.
    4. 수정하는 요소를 참조하는 요소를 건너뛰어 이동 할 수 없다.
2. 코드 조각을 원래 위치에서 잘라내어 목표 위치에 붙여 넣는다.
3. 테스트
#### 예시 : 조건문이 있을 때의 슬라이드
부수 효과가 없을 때 문장을 이동할 수 있다. retrieveOrder()의 내부를 살펴 보지 않고 부수 효과가 없을것으로 판단한 이유는 Command-query Separation 원칙을 지켜가며 코딩하기 때문이다.
```java
public class test {  
    private final Resources allocateResources;  
    private final Resources availableResources;  
  
    public test(Resources allocateResources, Resources availableResources) {  
        this.allocateResources = allocateResources;  
        this.availableResources = availableResources;  
    }  
      
    public Resource testFunction() {  
        Resource result;  
          
        if (availableResources.length() == 0) {  
            result = Resource.create();  
            allocateResources.push(result);  
        } else {  
            result = availableResources.pop();  
            allocateResources.push(result);  
        }  
          
        return result;  
    }  
}

public class Resources {  
    private List<Resource> list;  
  
    public Resources() {}  
  
    public static Resources create() {  
        return new Resources();  
    }  
  
    public int length() {  
        return list.size();  
    }  
  
    public void push(Resource resource) {  
        list.add(resource);  
    }  
  
    public Resource pop() {  
        if (list.isEmpty()) return Resource.create();  
  
        Resource resource = list.get(list.size() - 1);  
        list.remove(list.size() - 1);  
  
        return resource;  
    }  
}

public class Resource {  
    private int val;  
  
    public Resource() {}  
  
    public static Resource create() {  
        return new Resource();  
    }  
}
```

- 중복된 문장들을 조건문 밖으로 슬라이드할 수 있는데, 조건문 블록 밖으로 꺼내는 순간 한 문장으로 합쳐진다.
```java
public Resource testFunction() {  
    Resource result;  
  
    if (availableResources.length() == 0) {  
        result = Resource.create();  
    } else {  
        result = availableResources.pop();  
    }  
  
    allocateResources.push(result);  
  
    return result;  
}
```
## 반복문 쪼개기
#### 배경
종종 반복문 하나에서 두 가지 일을 수행하는 모습을 보게 된다. 그러면 반복문을 수정해야 할 때마다 두 가지 일 모두를 잘 이해하고 진행해야 한다. 반대로 각각의 반복문으로 분리해두면 수정할 동작 하나만 이해하면 된다.

반복문 쪼개기는 서로 다른 일들이 한 함수에서 이뤄지고 있다는 신호일 수 있다.

반복문을 두 번 실행하는걸 불편해하는 프로그래머도 많다. 중요한건 리팩터링과 최적화를 구분해야 한다는 것이다.

두 번 실행하는게 병목이라 밝혀지면 그때 다시 하나로 합치면 된다.
#### 절차
1. 반복문을복제해 두 개로 만든다.
2. 반복문이 중복되어 생기는 부수효과를 파악해서 제거한다.
3. 테스트
4. 완료됐으면, 각 반복문을 **함수로 추출**할지 고민한다.
#### 예시
```java
@Getter  
public class People {  
    private final int age;  
    private final int salary;  
  
    public People(int age, int salary) {  
        this.age = age;  
        this.salary = salary;  
    }  
}

@Test  
public void test() {  
    // given  
    List<People> peoples = List.of(  
            new People(1, 1),  
            new People(6, 6),  
            new People(3, 3),  
            new People(7, 7),  
            new People(4, 4),  
            new People(8, 8),  
            new People(5, 5),  
            new People(2, 2)  
    );  
  
    int infinity = Integer.MAX_VALUE;  
  
    // when  
    int youngest = peoples.get(0) != null ? peoples.get(0).getAge() : infinity;  
    int totalSalary = 0;  
  
    for (People p : peoples) {  
        if (p.getAge() < youngest) youngest = p.getAge();  
        totalSalary += p.getSalary();  
    }  
  
    // then  
    assertThat(youngest).isEqualTo(1);  
    assertThat(totalSalary).isEqualTo(36);  
}
```

```java
@Test  
public void test() {  
    // given  
	...
  
    // when  
    int totalSalary = getTotalSalary(peoples);  
  
    int youngest = getYoungest(peoples);  
  
    // then  
    assertThat(youngest).isEqualTo(1);  
    assertThat(totalSalary).isEqualTo(36);  
}  
  
int getTotalSalary(List<People> peoples) {  
    return peoples.stream()  
            .map(People::getSalary)  
            .reduce(0, Integer::sum);  
}  
  
int getYoungest(List<People> peoples) {  
    return peoples.stream()  
            .map(People::getAge)  
            .min(Integer::compareTo)  
            .orElseThrow(() -> new IllegalStateException("empty people"));  
}
```
## 반복문을 파이프라인으로 바꾸기
#### 배경
대부분이 그렇듯 객체 컬렉션을 순회할 때 반복문을 사용한다.

하지만 언어는 계속해서 더 나은 구조를 제공하는 쪽으로 발전

대표적인 연산은 map과 filter다

논리를 파이프라인으로 표현하면 이해하기 훨씬 쉬워진다.

객체가 파이프라인을 따라 흐르며 어떻게 처리 되는지 읽을 수 있기 때문이다.
#### 절차
1. 반복문을 사용해서 컬렉션으 가리키는 변수를 하나 만든다.
    1. 기존 변수를 단순히 복사한 것일 수 도 있다.
2. 반복문의 첫 줄부터 시작해서, 각 단위 행위를 적절한 컬렉션 파이프라인 연산으로 대체한다. 이때 컬렉션 파이프라인 연산은 1에서 만든 반복문 컬렉션 변수에서 시작하여, 이전 연산의 결과를 기초로 연쇄적으로 수행된다.
3. 반복문의 모든 동작을 대체햇다면 반복문 자체를지운다.
    1.  반복문이 결과를 누적 변수에 대입했다면 파이프라인의 결과를  그 누적 변수에 대입한다.
#### 예시
```java
@Test  
public void replaceLoopWithPipeline() {  
    // given  
    String input = """  
            office, country, telephone
	        Chicago, USA, +1 312 373 1000
			Beijing, China, +86 4008 900 505
			Bangalore, India, +91 80 4064 9570
			Porto Alegre, Brazil, +55 51 3079 3550
			Chennai, India, +91 44 660 44766
			""";  
  
    // when  
    List<Map<String, String>> result = acquireData(input);  
  
    // then  
    assertThat(result)  
            .containsExactly(  
                    Map.of("city", "Bangalore", "phone", "+91 80 4064 9570"),  
                    Map.of("city", "Chennai", "phone", "+91 44 660 44766")  
            );  
  
}  
  
List<Map<String, String>> acquireData(String input) {  
    String[] lines = input.split("\n");  
  
    boolean firstLine = true;  
  
    List<Map<String, String>> result = new ArrayList<>();  
  
    for (String line : lines) {  
        if (firstLine) {  
            firstLine = false;  
            continue;  
        }  
        if (line.trim().isEmpty()) continue;  
  
        String[] record = line.split(",");  
        if (record[1].trim().equals("India")) {  
  
            result.add(Map.of(  
                    "city", record[0].trim(),  
                    "phone", record[2].trim()  
            ));  
        }  
    }  
  
    return result;  
}
```

- 조건을 람다로 바꾸기
```java
List<Map<String, String>> acquireData(String input) {  
    String[] lines = input.split("\n");  
  
    return Arrays.stream(lines)  
            .skip(1)  
            .filter(line -> !line.isBlank())  
            .map(line -> line.split(","))  
            .filter(record -> record[1].trim().equals("India"))  
            .map(record -> Map.of("city", record[0].trim(),  
                    "phone", record[2].trim()))  
            .toList();  
}
```
## 죽은 코드 제거하기
#### 배경
쓰지 않는 코드가 몇 줄 있다고 시스템이 느려지는것도 아니고 메모리를 많이 잡아 먹지도 않는다.

사실 최신 컴파일러들은 이런 코드를 알아서 제거한다.

코드를 삭제할 때 커밋 메시지로 남겨 놓자
#### 절차
1. 죽은 코드를 외부에서 참조할 수 잇는 경우라면 혹시라도 호출하는 곳이 잇는지 확인
2. 없다면 제거
3. 테스트
