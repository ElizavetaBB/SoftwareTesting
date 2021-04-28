package model;

import java.util.HashSet;

public class Person extends Entity{

    private Condition condition;
    private double probabilityOfDying;
    private HashSet<FliedObject> visibleObjects;
    private FliedObject location;
    private HashSet<Person> friends;

    public Person(String name, FliedObject location){
        super(name);
        if (location==null) throw new NullPointerException();
        this.location=location;
        this.condition=Condition.CALM;
        this.probabilityOfDying=0;
        this.friends=new HashSet<>();
        visibleObjects=new HashSet<>();
    }

    public void addAction(Action action){
        if (action!=null) {
            this.currentAction = action;
            if (action.getAction().equals("see")){
                seeObject(action);
            }else if (action.getAction().equals("zoom")){
                if (action.getNewObjectName()!=null) action.getActionEntity().setName(action.getNewObjectName());
                seeObject(action);
            }else{
                if (visibleObjects!=null) {
                    for (FliedObject fliedObject:visibleObjects)
                        if (fliedObject.getName().equals("Rocket") && Math.abs(fliedObject.getDirection().count-location.getDirection().count)==1 && condition.name().equals("CALM")){
                            probabilityOfDying=1;
                            break;
                        }
                }
            }
            if (friends!=null) {
                for (Person person : friends) {
                    person.setCondition(this.condition);
                    person.setProbabilityOfDying(this.probabilityOfDying);
                }
            }
        }
    }

    private void seeObject(Action action){
        if (!visibleObjects.contains(action.getActionEntity())) visibleObjects.add(action.getActionEntity());
        if (Math.abs(action.getActionEntity().getDirection().count-location.getDirection().count)==1){
            probabilityOfDying=0.2;
            if (action.getActionEntity().getName().equals("Arrow")) {
                probabilityOfDying=0.3;
                this.condition=Condition.CALM;
            }
            if (action.getActionEntity().getName().equals("Rocket")) {
                probabilityOfDying=0.7;
                this.condition=Condition.NERVOUS;
            }
        }else{
            probabilityOfDying=0;
            this.condition=Condition.CALM;
        }
    }

    public double getProbabilityOfDying(){
        return probabilityOfDying;
    }

    public void setCondition(Condition condition){
        this.condition=condition;
    }

    public void addFriend(Person person){
        if (person.location.equals(this.location)) friends.add(person);
    }

    public Condition getCondition(){
        return condition;
    }

    public void setProbabilityOfDying(double probabilityOfDying){
        this.probabilityOfDying=probabilityOfDying;
    }
}

