package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) {
        Base rsl = memory.computeIfPresent(model.id(), (k, v) -> {
            if (memory.get(model.id()).version() != model.version()) {
                try {
                    throw new OptimisticException("Версии не одинаковы");
                } catch (OptimisticException e) {
                    e.printStackTrace();
                }
            }
            return new Base(model.id(), model.name(), model.version() + 1);
        });
        return rsl != null;
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}