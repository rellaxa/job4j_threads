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
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (amount < 0 && fromId != toId) {
            return false;
        }
        var optFromAcc = getById(fromId);
        var optToAcc = getById(toId);
        if (optFromAcc.isPresent() && optToAcc.isPresent()) {
            Account fromAcc = optFromAcc.get();
            Account toAcc = optToAcc.get();
            if (fromAcc.amount() >= amount) {
                update(new Account(fromId, fromAcc.amount() - amount));
                update(new Account(toId, toAcc.amount() + amount));
                return true;
            }
        }
        return false;
    }
}
