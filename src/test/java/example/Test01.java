package example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test01 {



    @Test
    public void getResult()
    {
        One one = new One();
        assertEquals("foo",one.foo());
    }
}
