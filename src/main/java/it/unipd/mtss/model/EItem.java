////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

public class EItem {
    private EItemType type;
    private String name;
    private double price;

    public EItem(EItemType type, String name, double price) {

        if (type == null) {
            throw new IllegalArgumentException("Type non puo' essere nullo");
        }

        if (name == null) {
            throw new IllegalArgumentException("Name non puo' essere nullo");
        }

        if (price < 0.00) {
            throw new IllegalArgumentException
            ("Price non puo' essere negativo");
        }

        this.type = type;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public EItemType getEItemType() {
        return type;
    }
}
