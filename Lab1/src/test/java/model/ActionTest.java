package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionTest {
    private Action action;

   @Test
   public void testConstructor(){
       action=new Action("fly");
       Assertions.assertEquals("fly",action.getAction());
       Assertions.assertNull(action.getActionEntity());
       Assertions.assertNull(action.getNewObjectName());
       FliedObject fliedObject=new FliedObject("Rocket");
       action=new Action("see",fliedObject);
       Assertions.assertEquals(fliedObject,action.getActionEntity());
       action=new Action("see",fliedObject,"Arrow");
       Assertions.assertEquals("Arrow",action.getNewObjectName());
   }

   @Test
   public void testGetters(){
       FliedObject fliedObject=new FliedObject("Rocket");
       action=new Action("see",fliedObject,"Arrow");
       Assertions.assertEquals(fliedObject,action.getActionEntity());
       Assertions.assertEquals("Arrow",action.getNewObjectName());
       Assertions.assertEquals("see",action.getAction());
   }
}
