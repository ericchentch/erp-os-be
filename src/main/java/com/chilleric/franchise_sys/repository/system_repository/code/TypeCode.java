package com.chilleric.franchise_sys.repository.system_repository.code;

public enum TypeCode {
  REGISTER("register"),
  VERIFY2FA("verify2FA");

  private String responseType;

  TypeCode(String type) {
    this.responseType = type;
  }

  public String getResponseType() {
    return responseType;
  }
}
