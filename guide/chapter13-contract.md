# Contract

```java
public enum UnitKind {
  MARINE(10), FLAME_THROWER(8)
  ;
  private final int maxPower;
```
  
```java
  private UnitKind(int maxPower) {
    this.maxPower = maxPower;
  }
}
```

```java
public class MilitaryUnit {
  private final UnitKind kind;
  private final int power;
  private IntUnaryOperator bonus;
```
  
```java
  public MilitaryUnit(UnitKind kind, int power) {
    this.kind = Objects.requireNonNull(kind);
    if (power < 0 || power >= kind.maxPower) {
      throw new IllegalArgumentException("invalid power " + power);
    }
    this.power = power;
    this.bonus = x -> x;
  }
```
  
```java
  public void bonus(IntUnaryOperator bonus) {
    this.bonus = Objects.requireNonNull(bonus);
  }
```
  
```java
  public int fightingPower() {
    return Math.max(0, Math.min(unit.maxPower, bonus.applyAsInt(power)));
  }
}
```
