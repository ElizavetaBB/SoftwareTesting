package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {
    private Person person;
    private static FliedObject fliedObject;

    @BeforeAll
    public static void initShip(){
        fliedObject=new FliedObject("Ship");
    }

    @BeforeEach
    public void init(){
        person=new Person("Форд",fliedObject);
    }

    @Test
    public void testAddAction(){
        Person art=new Person("Форд",fliedObject);
        art.addFriend(person);
        FliedObject arrow=new FliedObject("Arrow");
        Action fly=new Action("fly");
        fliedObject.addAction(fly, Direction.SOUTH);
        arrow.addAction(fly,null);
        art.addAction(new Action("see",arrow));
        art.addAction(new Action("zoom",arrow,"Rocket"));
        Assertions.assertEquals(Condition.NERVOUS,art.getCondition());
        Assertions.assertEquals(0.7,person.getProbabilityOfDying());
    }

    @Test
    public void testDoNothing(){
        FliedObject arrow=new FliedObject("Arrow");
        Action fly=new Action("fly");
        fliedObject.addAction(fly, Direction.SOUTH);
        arrow.addAction(fly,null);
        person.addAction(new Action("see",arrow));
        arrow.setName("Rocket");
        person.addAction(new Action("Do nothing"));
        Assertions.assertEquals(Condition.CALM,person.getCondition());
        Assertions.assertEquals(1,person.getProbabilityOfDying());
    }

    @Test
    public void testSeeInDifferentWay(){
        FliedObject arrow=new FliedObject("Ship");
        Action fly=new Action("fly");
        fliedObject.addAction(fly, Direction.WEST);
        arrow.addAction(fly,null);
        person.addAction(new Action("see",arrow));
        Assertions.assertEquals(Condition.CALM,person.getCondition());
        Assertions.assertEquals(0,person.getProbabilityOfDying());
    }

}
