package ru.job4j.tdd;

import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

/**
 * \* User: zhv
 * \* Date: 31.10.2021
 * \* Time: 12:33
 * \* Description:
 * \
 */
public class Cinema3D implements Cinema {
    @Override
    public List<Session> find(Predicate<Session> filter) {
        return null;
    }

    @Override
    public Ticket buy(Account account, int row, int column, Calendar date) {
        return null;
    }

    @Override
    public void add(Session session) {

    }

    @Override
    public boolean checkPlace(Session session, Ticket ticket) throws Exception {
        return false;
    }

    @Override
    public Calendar checkDate(Ticket ticket) throws Exception {
        return null;
    }
}
