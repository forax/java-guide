// To starts, run jshell --enable-preview which is a program able to interpret Java syntax
// then cut and paste the following lines to see how it works
// To exit jshell type /exit

// # Contract

public enum UnitKind {
  MARINE(10), FLAME_THROWER(8)
  ;
  private final int maxPower;
  
  private UnitKind(int maxPower) {
    this.maxPower = maxPower;
  }
}

public class MilitaryUnit {
  private final UnitKind kind;
  private final int power;
  private IntUnaryOperator bonus;
  
  public MilitaryUnit(UnitKind kind, int power) {
    this.kind = Objects.requireNonNull(kind);
    if (power < 0 || power >= kind.maxPower) {
      throw new IllegalArgumentException("invalid power " + power);
    }
    this.power = power;
    this.bonus = x -> x;
  }
  
  public void bonus(IntUnaryOperator bonus) {
    this.bonus = Objects.requireNonNull(bonus);
  }
  
  public int fightingPower() {
    return Math.max(0, Math.min(unit.maxPower, bonus.applyAsInt(power)));
  }
}