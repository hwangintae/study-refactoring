# chapter04 테스트 구축하기

> - 리팩터링을 제대로 하려면 실수를 잡아주는 견고한 테스트가 필요하다.
> - 자동 리팩터링 도구를 활용하더라도 테스트로 재차 검증을 해야한다.
> - 리팩터링을 하지 않더라도 좋은 테스트를 작성하는 일은 개발 효율을 높혀준다.

## 자가 테스트 코드의 가치
1. 실제 코드를 작성하는 시간의 비중은 크지 않다.
2. 대부분의 시간은 디버깅에 쓴다.
3. **테스트 코드는 강력한 버그 검출 도구로, 버그를 찾는 데 걸리는 시간을 대폭 줄여준다.**
4. 테스트를 작성하다 보면 원하는 기능을 추가하기 위해 무엇이 필요한지 고민하게 된다.
5. 구현보다 인터페이스에 집중하게 된다.
## 테스트할 샘플 코드
1. 생산 계획 : 지역의 수요와 가격으로 구성
2. 지역에 위치한 생산자들은 제품을 특정 가격, 특정 수량만큼 생산할 수 있다.
3. 생산자별로 제품을 모두 판매했을 때 총 수익을 보여둔다.
4. 화면 맨 아래에는 생상 부족분과 현재 계획에서 거둘 수 있는 총 수익도 보여준다.

``` java
@Getter  
public class Province {  
    private final String name;  
    private final List<Producer> producers;  
    private int totalProduction;  
    private int demand;  
    private int price;  
  
    @Builder  
    protected Province(String name, List<Producer> producers, int totalProduction, int demand, int price) {  
        this.name = name;  
        this.producers = producers;  
        this.totalProduction = totalProduction;  
        this.demand = demand;  
        this.price = price;  
    }  
  
    public static Province create(ProvinceData doc) {  
        Province province = Province.builder()  
                .name(doc.getName())  
                .producers(new ArrayList<>())  
                .totalProduction(0)  
                .demand(doc.getDemand())  
                .price(doc.getPrice())  
                .build();  
  
        doc.getProducers().forEach(d -> province.addProducer(Producer.builder()  
                .province(province)  
                .cost(d.getCost())  
                .name(d.getName())  
                .production(d.getProduction())  
                .build()));  
  
        return province;  
    }  
  
  
    public void addProducer(Producer arg) {  
        this.producers.add(arg);  
        this.totalProduction += arg.getProduction();  
    }  
  
    public int getShortfall() {  
        return this.demand - this.totalProduction;  
    }  
  
    public int getProfit() {  
        return getDemandValue() - getDemandCost();  
    }  
  
    public int getDemandValue() {  
        return getSatisfiedDemand() * this.price;  
    }  
  
    public int getSatisfiedDemand() {  
        return Math.min(this.demand, this.totalProduction);  
    }  
  
    public int getDemandCost() {  
        int remainingDemand = this.demand;  
        int result = 0;  
  
        List<Producer> sortedProducers = this.producers.stream()  
                .sorted(Comparator.comparingInt(Producer::getCost))  
                .toList();  
  
        for (Producer p : sortedProducers) {  
            int contribution = Math.min(remainingDemand, p.getProduction());  
            remainingDemand -= contribution;  
            result += contribution * p.getCost();  
        }  
  
        return result;  
    }  
  
    @SuppressWarnings({})  
    public void setTotalProduction(int arg) {  
        this.totalProduction = arg;  
    }  
  
    public void setDemand(String arg) {  
        this.demand = Integer.parseInt(arg);  
    }  
  
    public void setPrice(String arg) {  
        this.price = Integer.parseInt(arg);  
    }  
  
}
```

```java
@Getter  
public class Producer {  
    private final Province province;  
    private int cost;  
    private final String name;  
    private int production;  
  
    @Builder  
    protected Producer(Province province, int cost, String name, int production) {  
        this.province = province;  
        this.cost = cost;  
        this.name = name;  
        this.production = production;  
    }  
  
    public void setCost(String arg) {  
        this.cost = Integer.parseInt(arg);  
    }  
  
    public void setProduction(String amountStr) {  
        int newProduction = 0;  
        try {  
            newProduction = Integer.parseInt(amountStr);  
        } catch (NumberFormatException ignore) {  
        }  
  
        this.province.setTotalProduction(province.getTotalProduction() + newProduction - this.production);  
        this.production = newProduction;  
    }  
}
```

```java
@Getter  
public class ProvinceData {  
    private final String name;  
    private final List<Producer> producers;  
    private final int demand;  
    private final int price;  
  
    protected ProvinceData(String name, List<Producer> producers, int demand, int price) {  
        this.name = name;  
        this.producers = producers;  
        this.demand = demand;  
        this.price = price;  
    }  
  
    public static ProvinceData getSample() {  
//    {  
//        name: "Asia",  
//        producers: [  
//            {name: "Byzantium", cost: 10, production: 9},  
//            {name: "Attalia", cost: 12, production: 10},  
//            {name: "Sinope", cost: 10, production: 6}  
//        ],  
//        demand: 30,  
//        price: 20  
//    }  
//    """;  
  
        List<Producer> producers = List.of(  
                Producer.builder().name("Byzantium").cost(10).production(9).build(),  
                Producer.builder().name("Attalia").cost(12).production(10).build(),  
                Producer.builder().name("Sinope").cost(10).production(6).build()  
        );  
  
        return new ProvinceData("Asia", producers, 30, 20);  
    }  
}
```


## 첫 번째 테스트
```java
class Chapter04Test {  
  
    @Test  
    public void shortfailTest() {  
        // given  
        Province asia = Province.create(ProvinceData.getSample());  
  
        // when  
        int shortfall = asia.getShortfall();  
  
        // then  
        // junit의 Assertions 사용
        assertEquals(5, shortfall);
        
        // assertj의 Assertions 사용
        assertThat(shortfall).isEqualTo(5);
    }  
}
```


> - 실패해야 할 상황에서는 반드시 실패하게 만들자.
> - 자주 테스트하라. 작성 중인 코드는 최소한 몇 분 간격을 ㅗ테스트하고, 적어도 하루에 한 번은 전체 테스트를 돌려보자.

```java
public int getShortfall() {  
    return this.demand - this.totalProduction * 2;  
}
```
![오류화면](https://github.com/user-attachments/assets/4e838573-8199-4c59-974b-f1e25f99f56a)

## 테스트 추가하기
> 완벽하게 만드느라 테스트를 수행하지 못하느니, 불안전한 테스트라도 작성해 실행하는 게 낫다.

- 테스트는 위험 요인을 중심으로 작성한다.
- 테스트의 목적은 향후에 발생하는 버그를 찾는데 있다.

```java

class Chapter04Test {  
  
    private Province asia;  
  
    @BeforeEach  
    void setUp() {  
        asia = Province.create(ProvinceData.getSample());  
    }  
  
    @Test  
    public void shortfailTest() {  
        // when  
        int shortfall = asia.getShortfall();  
  
        // then  
        assertThat(shortfall).isEqualTo(5);  
    }  
  
    @Test  
    public void profitTest() {  
        // when  
        int profit = asia.getProfit();  
  
        // then  
        assertThat(profit).isEqualTo(230);  
    }  
}
```

- `@BeforeEach` 구문은 각가의 테스트 바로 전에 실행되어 asia를 초기화하기 때문에 모든 테스트가 자신만의 새로운 asia를 사용하게 된다.
- 모든 테스트를 독립적으로 구성한다.
- (📌 `@BeforeEach`를 이용해 새로운 객체를 만들기 위해 사용했으나 실제로는 각 test 내부에서 직접 생성하는 코드를 작성한다.)

## 픽스처 수정하기
- 실전에서는 사용자가 값을 변경하면서 픽스처의 내용도 수정되는 경우가 흔하다.
- 테스트는 setup-exercise-verify, given-when-then, arrange-act-assert 등 패턴이 있다.
- 하나의 `@Test`에서는 하나만 검증하는게 좋다.
- 앞쪽 검증이 통과하지 못하면 나머지 검증은 실행도 못하기 때문에 실패 원인을 파악하는데 어렵다.
- 하지만, 여기서는 한 테스트에 묶어도 두 속성이 밀접하다고 판단되어 이렇게 했다.
```java
@Test  
public void changeProductionTest() {  
    // given  
    asia.getProducers().get(0).setProduction("20");  
  
    // when  
    int shortfall = asia.getShortfall();  
    int profit = asia.getProfit();  
  
    // then  
    assertThat(shortfall).isEqualTo(-6);  
    assertThat(profit).isEqualTo(292);  
}
```

## 경계 조건 검사하기
> - 의도대로 사용하는 상황을 벗어나는 경계 지점 테스트를 작성하면 좋다. 
> - 문제가 생길 가능성이 있는 경계 조건을 생각해보고 그 부분을 집중적으로 테스트하자.

```java
@Test  
public void noProducersTest() {  
    // given  
    ProvinceData data = ProvinceData.builder()  
            .name("No producers")  
            .producers(new ArrayList<>())  
            .demand(30)  
            .price(20)  
            .build();  
  
    Province asia = Province.create(data);  
  
    // when  
    int shortfall = asia.getShortfall();  
    int profit = asia.getProfit();  
  
    // then  
    assertThat(shortfall).isEqualTo(30);  
    assertThat(profit).isEqualTo(0);  
}  
  
@Test  
public void zeroDemand() {  
    // given  
    Province asia = Province.create(ProvinceData.getSample());  
    asia.setDemand("0");  
  
    // when  
    int shortfall = asia.getShortfall();  
    int profit = asia.getProfit();  
  
    // then  
    assertThat(shortfall).isEqualTo(-25);  
    assertThat(profit).isEqualTo(0);  
}  
  
@Test  
public void negativeDemand() {  
    // given  
    Province asia = Province.create(ProvinceData.getSample());  
    asia.setDemand("-1");  
  
    // when  
    int shortfall = asia.getShortfall();  
    int profit = asia.getProfit();  
  
    // then  
    assertThat(shortfall).isEqualTo(-26);  
    assertThat(profit).isEqualTo(-10);  
}  
  
@Test  
public void emptyStringDemand() {  
    // given  
    Province asia = Province.create(ProvinceData.getSample());  
  
    // expected  
    assertThatThrownBy(() -> asia.setDemand(""))  
            .isInstanceOf(NumberFormatException.class);  
}
```

## 끝나지 않은 여정
> 버그 리포트를 받으면 가장 먼저 그 버그를 드러내는 단위 테스트부터 작성하자.

