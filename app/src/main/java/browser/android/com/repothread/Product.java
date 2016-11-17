package browser.android.com.repothread;

/**
 * @author lizejun
 * @version 1.0 2016/11/17
 */
public class Product {

    private int productId = 0;

    public Product() { }

    public Product(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                '}';
    }
}
