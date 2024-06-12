package com.compulynx.compas.Requests;

public class DelinkingRequest {
  private int customerId;
  private String initiatorId;
  private String delinkingReason;

  public DelinkingRequest(int customerId, String initiatorId, String delinkingReason) {
    this.customerId = customerId;
    this.initiatorId = initiatorId;
    this.delinkingReason = delinkingReason;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public String getInitiatorId() {
    return initiatorId;
  }

  public void setInitiatorId(String initiatorId) {
    this.initiatorId = initiatorId;
  }

  public String getDelinkingReason() {
    return delinkingReason;
  }

  public void setDelinkingReason(String delinkingReason) {
    this.delinkingReason = delinkingReason;
  }

  @Override
  public String toString() {
    return "DelinkingRequest{"
        + "customerId='"
        + customerId
        + '\''
        + ", initiatorId='"
        + initiatorId
        + '\''
        + ", delinkingReason='"
        + delinkingReason
        + '\''
        + '}';
  }
}
