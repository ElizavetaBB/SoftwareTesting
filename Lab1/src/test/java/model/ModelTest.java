package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelTest {

    @Test
    public void testZoom(){
        FliedObject ship=new FliedObject("Ship");
        Person zafod=new Person("Зафод",ship);
        Person art=new Person("Форд",ship);
        art.addFriend(zafod);
        FliedObject arrow=new FliedObject("Arrow");
        Action fly=new Action("fly");
        ship.addAction(fly, Direction.SOUTH);
        arrow.addAction(fly,null);
        art.addAction(new Action("see",arrow));
        art.addAction(new Action("zoom",arrow,"Rocket"));
        Assertions.assertEquals(Condition.NERVOUS,art.getCondition());
        Assertions.assertEquals(0.7,zafod.getProbabilityOfDying());
    }

    @Test
    public void testNotZoom(){
        FliedObject ship=new FliedObject("Ship");
        Person zafod=new Person("Зафод",ship);
        FliedObject arrow=new FliedObject("Arrow");
        Action fly=new Action("fly");
        ship.addAction(fly, Direction.EAST);
        arrow.addAction(fly,Direction.WEST);
        zafod.addAction(new Action("see",arrow));
        Assertions.assertEquals(Condition.CALM,zafod.getCondition());
        arrow.setName("Rocket");
        zafod.addAction(new Action("walk"));
        Assertions.assertEquals(Condition.CALM,zafod.getCondition());
        Assertions.assertEquals(1.0,zafod.getProbabilityOfDying());
    }

    @Test
    public void testDifferentWays(){
        FliedObject ship=new FliedObject("Ship");
        Person zafod=new Person("Зафод",ship);
        FliedObject arrow=new FliedObject("Arrow");
        Action fly=new Action("fly");
        ship.addAction(fly, Direction.NORTH);
        arrow.addAction(fly,Direction.WEST);
        zafod.addAction(new Action("see",arrow));
        Assertions.assertEquals(Condition.CALM,zafod.getCondition());
        zafod.addAction(new Action("zoom",arrow,"Rocket"));
        Assertions.assertEquals(Condition.CALM,zafod.getCondition());
        Assertions.assertEquals(0,zafod.getProbabilityOfDying());
    }

    @Test
    public void testNotAtSameShips(){
        FliedObject ship=new FliedObject("Ship1");
        FliedObject ship2=new FliedObject("Ship2");
        Person zafod=new Person("Зафод",ship);
        Person art=new Person("Форд",ship2);
        zafod.addFriend(art);
        FliedObject arrow=new FliedObject("Arrow");
        Action fly=new Action("fly");
        ship.addAction(fly, Direction.EAST);
        ship2.addAction(fly,Direction.NORTH);
        arrow.addAction(fly,Direction.WEST);
        zafod.addAction(new Action("see",arrow));
        zafod.addAction(new Action("zoom",arrow,"Rocket"));
        Assertions.assertEquals(Condition.NERVOUS,zafod.getCondition());
        Assertions.assertEquals(Condition.CALM,art.getCondition());
        Assertions.assertEquals(0.0,art.getProbabilityOfDying());
    }

    @Test
    public void testZoomNotRocket(){
        FliedObject ship=new FliedObject("Ship");
        Person zafod=new Person("Зафод",ship);
        FliedObject arrow=new FliedObject("Arrow");
        Action fly=new Action("fly");
        ship.addAction(fly, Direction.NORTH);
        arrow.addAction(fly,Direction.SOUTH);
        zafod.addAction(new Action("see",arrow));
        Assertions.assertEquals(Condition.CALM,zafod.getCondition());
        zafod.addAction(new Action("zoom",arrow,"Ship"));
        Assertions.assertEquals(Condition.CALM,zafod.getCondition());
        Assertions.assertEquals(0.2,zafod.getProbabilityOfDying());
    }

}
