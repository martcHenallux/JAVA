package Business;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DBAccess.ProductDBAccess;
import Model.Product;

public class ProductManager {
    
    ProductDBAccess dao;

    public ProductManager(){
        dao = new ProductDBAccess();
    }

    private void createProduct(String tag, float price){
        dao.createProduct(tag, price);
    }

    public void addNewMerchandiseToStock(int quantity, int productSerialNumber){
        dao.addNewMerchandiseToStock(quantity, productSerialNumber);
    }

    public void takeMerchandiseOutOfStock(int quantity, int productSerialNumber){
        dao.takeMerchandiseOutOfStock(quantity, productSerialNumber);
    }

    public ArrayList<Product> readAllProducts(){
        return dao.readAllProducts();
    }

    public Product readOneProduct(int productSerialNumber){
        return dao.readOneProduct(productSerialNumber);
    }

    public Product bestProductSoldForEntity(int serialNumberEntity){
        return dao.bestProductSoldForEntity(serialNumberEntity);
    }

    public void ProductIsIn(String tag, float price){
        ArrayList<Product> products = new ArrayList<>();
        products = readAllProducts();
        Boolean isIn = false;
        for (Product product : products) {
            if(product.getTag().toLowerCase().equals(tag.toLowerCase())){
                isIn = true;
            }
        }
        if(!isIn){
            createProduct(tag, price);
        }
    }

    public Object[][] compraringProducts(Product product1, Product product2){
        Object[][] products = new Object[2][2];
        Float quantityP1 = takingQuantityFromString(product1);
        Float quantityP2 = takingQuantityFromString(product2);
        Float priceP1 = (product1.getActualUnitPrice()/quantityP1);
        Float priceP2 = (product2.getActualUnitPrice()/quantityP2);
        if(priceP1 < priceP2){
            products[0][0] = product1;
            products[1][0] = product2;
            products[0][1] = priceP1;
            products[1][1] = priceP2;
        }
        else{
            products[0][0] = product2;
            products[1][0] = product1;
            products[0][1] = priceP2;
            products[1][1] = priceP1;
        }
        return products;
    }

    private Float takingQuantityFromString(Product product){
        String[] partsProduct = product.getTag().toLowerCase().split(" ");
        String quantityS = null;
        Float quantityF = null;
        Boolean isCl = false;
        String pattern = "\\b\\d+c?l\\b";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher;
        for (String part : partsProduct) {
            matcher = regex.matcher(part);
            if(matcher.find()){
                quantityS = part;
            }
        }
        pattern = "c";
        regex = Pattern.compile(pattern);
        matcher = regex.matcher(quantityS);
        if(matcher.find()){
            isCl = true;
        }
        pattern = "\\d+\\.?\\d+";
        regex = Pattern.compile(pattern);
        matcher = regex.matcher(quantityS);
        if(matcher.find()){
            quantityS = matcher.group();
            quantityF = Float.parseFloat(quantityS);
            if(isCl){
                quantityF = quantityF / 100.0f;
            }
        }
        return quantityF;
    }
}
