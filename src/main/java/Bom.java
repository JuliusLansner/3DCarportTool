public class Bom {
    int id;
    int price;
    int orderId;

    public Bom(int id, int price, int orderId) {
        this.id = id;
        this.price = price;
        this.orderId = orderId;
    }

    public Bom(int price, int orderId) {
        this.price = price;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    @Override
    public String toString() {
        return "Bom{" +
                "id=" + id +
                ", price=" + price +
                ", orderId=" + orderId +
                '}';
    }
}
