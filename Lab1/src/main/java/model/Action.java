package model;

public class Action {
    private final String action;
    private FliedObject actionEntity;
    private String newObjectName;

    public Action(String action){
        this.action=action;
    }

    public Action(String action, FliedObject actionEntity){
        this(action);
        this.actionEntity=actionEntity;
    }

    public Action(String action, FliedObject actionEntity,String newObjectName){
        this(action,actionEntity);
        this.newObjectName=newObjectName;
    }

    public String getAction(){
        return action;
    }

    public String getNewObjectName(){
        return newObjectName;
    }

    public FliedObject getActionEntity(){
        return actionEntity;
    }

}
