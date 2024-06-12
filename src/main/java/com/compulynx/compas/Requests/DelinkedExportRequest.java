package com.compulynx.compas.Requests;

public class DelinkedExportRequest {
  String name;
  String idNumber;
  String afisid;
  String branchId;
  String delinkStatus;
  String delinkInitiator;
  String delinkApprover;
  String delinkInitiatedAt;
  String authTimeline;

  public DelinkedExportRequest() {}

  public DelinkedExportRequest(
      String name,
      String idNumber,
      String afisid,
      String branchId,
      String delinkStatus,
      String delinkInitiator,
      String delinkApprover,
      String delinkInitiatedAt,
      String authTimeline) {
    this.name = name;
    this.idNumber = idNumber;
    this.afisid = afisid;
    this.branchId = branchId;
    this.delinkStatus = delinkStatus;
    this.delinkInitiator = delinkInitiator;
    this.delinkApprover = delinkApprover;
    this.delinkInitiatedAt = delinkInitiatedAt;
    this.authTimeline = authTimeline;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public String getAfisid() {
    return afisid;
  }

  public void setAfisid(String afisid) {
    this.afisid = afisid;
  }

  public String getBranchId() {
    return branchId;
  }

  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }

  public String getDelinkStatus() {
    return delinkStatus;
  }

  public void setDelinkStatus(String delinkStatus) {
    this.delinkStatus = delinkStatus;
  }

  public String getDelinkInitiator() {
    return delinkInitiator;
  }

  public void setDelinkInitiator(String delinkInitiator) {
    this.delinkInitiator = delinkInitiator;
  }

  public String getDelinkApprover() {
    return delinkApprover;
  }

  public void setDelinkApprover(String delinkApprover) {
    this.delinkApprover = delinkApprover;
  }

  public String getDelinkInitiatedAt() {
    return delinkInitiatedAt;
  }

  public void setDelinkInitiatedAt(String delinkInitiatedAt) {
    this.delinkInitiatedAt = delinkInitiatedAt;
  }

  public String getAuthTimeline() {
    return authTimeline;
  }

  public void setAuthTimeline(String authTimeline) {
    this.authTimeline = authTimeline;
  }

  @Override
  public String toString() {
    return "DelinkedExportRequest{"
        + "name='"
        + name
        + '\''
        + ", idNumber='"
        + idNumber
        + '\''
        + ", afisid='"
        + afisid
        + '\''
        + ", branchId='"
        + branchId
        + '\''
        + ", delinkStatus='"
        + delinkStatus
        + '\''
        + ", delinkInitiator='"
        + delinkInitiator
        + '\''
        + ", delinkApprover='"
        + delinkApprover
        + '\''
        + ", delinkInitiatedAt='"
        + delinkInitiatedAt
        + '\''
        + ", authTimeline='"
        + authTimeline
        + '\''
        + '}';
  }
}
