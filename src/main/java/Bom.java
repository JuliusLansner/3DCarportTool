public class Bom {
    int id;
    int price;
    int orderId;

    public Bom(int id, int price, int orderId) {
        this.id = id;
        this.price = price;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
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
