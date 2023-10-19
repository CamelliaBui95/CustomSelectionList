package fr.btn.selectionlist.components;

public abstract class Component {
    protected int id;
    protected String label;

    public Component(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
