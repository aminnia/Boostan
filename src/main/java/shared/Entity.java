package shared;

public interface Entity<T> {
    boolean sameIdentityAs(T other);
}
