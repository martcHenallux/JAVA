package Model;
public class Product {
    
    private int productSerialNumber;
    private String tag;
    private float actualUnitPrice;
    private int stockQuantity;

    public Product(int productSerialNumber, String tag, float actualUnitPrice, int stockQuantity){
        this.productSerialNumber = productSerialNumber;
        this.tag = tag;
        this.actualUnitPrice = actualUnitPrice;
        this.stockQuantity = stockQuantity;
    }

    public int getProductSerialNumber() {
        return productSerialNumber;
    }

    public String getTag() {
        return tag;
    }

    public float getActualUnitPrice() {
        return actualUnitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    @Override
    public String toString() {
        StringBuilder productInformations = new StringBuilder(tag);
        productInformations.append(": \n    Serial number: " + productSerialNumber);
        productInformations.append("\n    Price per unit: " + actualUnitPrice);
        productInformations.append("\n    Quantity in stock: " + stockQuantity + "\n");
        return productInformations.toString();
    }
}
