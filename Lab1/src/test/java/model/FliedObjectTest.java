package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FliedObjectTest {
    private FliedObject fliedObject;

    @BeforeEach
    public void initObject(){
        fliedObject=new FliedObject("Ship");
    }

    @Test
    public void testGetAndSet(){
        Assertions.assertNull(fliedObject.getDirection());
        fliedObject.setDirection(Direction.SOUTH);
        Assertions.assertEquals(Direction.SOUTH,fliedObject.getDirection());
    }

    @Test
    public void testAddAction(){
        fliedObject.addAction(new Action("fly"),null);
        Assertions.assertEquals(Direction.NORTH,fliedObject.getDirection());
        fliedObject.addAction(new Action("fly"),Direction.SOUTH);
        Assertions.assertEquals(Direction.SOUTH,fliedObject.getDirection());
    }
}
