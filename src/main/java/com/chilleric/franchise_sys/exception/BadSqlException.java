package com.chilleric.franchise_sys.exception;

public class BadSqlException extends RuntimeException {
  public BadSqlException(String message) {
    super(message);
  }
}
