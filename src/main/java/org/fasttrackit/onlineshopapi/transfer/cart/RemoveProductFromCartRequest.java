package org.fasttrackit.onlineshopapi.transfer.cart;

public class RemoveProductFromCartRequest {

    private Long customerId;
    private Long productId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "RemoveProductFromCartRequest{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                '}';
    }
}
