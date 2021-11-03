package ru.job4j.template;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

@Ignore
public class GeneratorStrTest {
    @Test
    public void whenTrue() {
        Generator generatorStr = new GeneratorStr();
        String res = generatorStr.produce("I am a ${name}, Who are ${subject}? ",
                Map.of("name", "Petr Arsentev", "subject ", "you"));
        assertEquals("I am a Petr Arsentev, Who are you? ", res);
    }

    @Test
    public void whenFalse() {
        Generator generatorStr = new GeneratorStr();
        String res = generatorStr.produce("I am a ${name}, Who are ${subject}? ",
                Map.of("name", "Petr Ivanov", "subject ", "you"));
        assertEquals("I am a Petr Arsentev, Who are you? ", res);
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenMapHasMoreMembersException() {
        new GeneratorStr()
                .produce("I am a ${name}, Who are ${subject}? ",
                Map.of("name", "Petr Arsentev", "subject ", "you", "secondName", "Sergeevich"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenTemplateHasMoreMembersException() {
        new GeneratorStr()
                .produce("I am a ${name}, Who are ${subject}? What are ${subject} ${action} here?",
                Map.of("name", "Petr Arsentev", "subject ", "you"));
    }
}