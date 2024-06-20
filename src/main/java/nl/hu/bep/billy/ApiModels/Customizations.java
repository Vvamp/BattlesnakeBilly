package nl.hu.bep.billy.ApiModels;

public class Customizations {
    public String color;
    public String head;
    public String tail;

    public Customizations() {
    }

    public Customizations(Customizations other) {
        this.color = other.color;
        this.head = other.head;
        this.tail = other.tail;
    }
}
