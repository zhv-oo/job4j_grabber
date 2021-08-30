package ru.job4j.grabber;

import java.util.List;

public class BaseStore implements Store {
    @Override
    public void save(Post post) {

    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post findById(int id) {
        return null;
    }
}
