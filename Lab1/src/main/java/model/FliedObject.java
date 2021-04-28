package model;

public class FliedObject extends Entity{
    private Direction direction;

    public FliedObject(String name) {
        super(name);
    }

    public void setDirection(Direction direction){
        this.direction=direction;
    }

    public Direction getDirection(){
        return direction;
    }

    public void addAction(Action action,Direction direction){
        if (action!=null){
            this.currentAction=action;
            this.direction=direction;
            if (action.getAction().equals("fly") && direction==null){
                setDirection(Direction.NORTH);
            }
        }
    }

}
