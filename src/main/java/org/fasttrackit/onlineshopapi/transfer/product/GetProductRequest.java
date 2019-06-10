package org.fasttrackit.onlineshopapi.transfer.product;

public class GetProductRequest {

    private String partialName;
    private Integer minQuantity;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    @Override
    public String toString() {
        return "GetProductRequest{" +
                "partialName='" + partialName + '\'' +
                ", minQuantity=" + minQuantity +
                '}';
    }
}
