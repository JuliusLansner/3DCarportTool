public class MaterialVariant {

    private int materialeVariantID;
    private int materialeID;
    private int length;
    private int partslistID;
    private int price;
    private String description;

    public MaterialVariant(int materialeVariantID, int materialeID, int length, int partslistID, String description, int price) {
        this.materialeVariantID = materialeVariantID;
        this.description = description;
        this.materialeID = materialeID;
        this.length = length;
        this.partslistID = partslistID;
        this.price = price;
    }

    public MaterialVariant(int materialeID, int length, int price) {
        this.materialeID = materialeID;
        this.length = length;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMaterialeVariantID() {
        return materialeVariantID;
    }

    public int getMaterialeID() {
        return materialeID;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPartslistID() {
        return partslistID;
    }

    @Override
    public String toString() {
        return "MaterialVariant{" +
                "materialeVariantID=" + materialeVariantID +
                ", materialeID=" + materialeID +
                ", length=" + length +
                ", partslistID=" + partslistID +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
