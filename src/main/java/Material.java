public class Material {
    int idMaterial;
    String description;
    String unit;
    int pricePerUnit;

    public Material(int idMaterial, String description, String unit, int pricePerUnit) {
        this.idMaterial = idMaterial;
        this.description = description;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    public Material(String description, String unit, int pricePerUnit) {
        this.description = description;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }

    //sets idMaterial, not sure about this one or if it's necessary.
    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPricePerUnit(int pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    // returns description not sure about this one.
    @Override
    public String toString() {
        return description;
    }
}
