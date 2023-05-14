public class TransactionDetails {
    
    private Document document;
    private String code;
    private int quantity;
    private float unitPrice;
    private Product product;

    public TransactionDetails(Document document, String code, int quantity, float unitPrice, Product product){
        this.document = document;
        this.code = code;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product = product;
    }

    public Document getDocument() {
        return document;
    }

    public String getCode() {
        return code;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder(code + ":\n");
        details.append("\n    Reference product: " + product.getProductSerialNumber());
        details.append("\n    Name product: " + product.getTag());
        details.append("\n    Quantity: " + quantity);
        details.append("\n    Unit price: " + unitPrice);
        details.append("\n    From " + document.getType() + " n°" + document.getDocumentNumber() + "\n");
        return details.toString();
    }
}
