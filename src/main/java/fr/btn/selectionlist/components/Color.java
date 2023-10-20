package fr.btn.selectionlist.components;

public class Color extends Component{
    public Color(int id, String label) {
        super(id, label);
    }

    @Override
    public String toString() {
        return this.label;
    }
}
