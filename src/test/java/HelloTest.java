import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloTest {

    class Hello {
        public String hi() {
            return "hello";
        }
    }

    @Test
    public void hiReturnsHello() {
        Hello hello = new Hello();
        assertEquals("should return \"hello\"", "hello", hello.hi());
    }
}


