package code.challenge.provider;

import java.util.function.Supplier;

public interface Provider<T> extends Supplier<T> {

    T now();

}
