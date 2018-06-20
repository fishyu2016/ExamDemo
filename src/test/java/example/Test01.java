package example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Test01 {

    public static void main(String[] args)
    {
        List list= new ArrayList();
        list.add(1);
        list.add(2);

        if(list.contains(1))
        {
            System.out.println("list contains 1");

            System.out.println("1 in list the index is " +list.indexOf(2));

            list.clear();
            System.out.println("clear list ");
            list.add(3);

            for (int i = 0; i < list.size(); i++)
            {
                System.out.println(list.size());
                System.out.println(list.get(i));

            }
        }
        else
        {
            System.out.println("list not contains 1");
        }

    }



//    @Test
//    public void getResult()
//    {
//        One one = new One();
//        assertEquals("foo",one.foo());
//    }

}
