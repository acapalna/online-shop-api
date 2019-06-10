package org.fasttrackit.onlineshopapi.transfer.customer;



public class GetCustomerRequest {

    private Long partialId;
    private String partialFirstName;
    private String partialLastName;
    private String partialPhoneNumber;

    public Long getPartialId() {
        return partialId;
    }

    public void setPartialId(Long partialId) {
        this.partialId = partialId;
    }

    public String getPartialFirstName() {
        return partialFirstName;
    }

    public void setPartialFirstName(String partialFirstName) {
        this.partialFirstName = partialFirstName;
    }

    public String getPartialLastName() {
        return partialLastName;
    }

    public void setPartialLastName(String partialLastName) {
        this.partialLastName = partialLastName;
    }

    public String getPartialPhoneNumber() {
        return partialPhoneNumber;
    }

    public void setPartialPhoneNumber(String partialPhoneNumber) {
        this.partialPhoneNumber = partialPhoneNumber;
    }

    @Override
    public String toString() {
        return "GetCustomerRequest{" +
                "partialId='" + partialId + '\'' +
                ", partialFirstName='" + partialFirstName + '\'' +
                ", partialLastName='" + partialLastName + '\'' +
                ", partialPhoneNumber='" + partialPhoneNumber + '\'' +
                '}';
    }
}
