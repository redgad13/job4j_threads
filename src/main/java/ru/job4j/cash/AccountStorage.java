package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl;
        if (accounts.containsValue(account)) {
            rsl = false;
        } else {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = false;
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        if (accounts.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from != null && to != null) {
            from = new Account(fromId, from.amount() - amount);
            to = new Account(toId, to.amount() + amount);
            accounts.put(fromId, from);
            accounts.put(toId, to);
            rsl = true;
        }
        return rsl;
    }
}
