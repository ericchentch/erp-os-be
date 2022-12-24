package com.chilleric.franchise_sys.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.chilleric.franchise_sys.log.AppLogger;
import com.chilleric.franchise_sys.log.LoggerFactory;
import com.chilleric.franchise_sys.log.LoggerType;

public abstract class AbstractSecondaryMongoRepo extends AbstractMongoRepo {

  @Autowired
  @Qualifier("mongo_secondary_template")
  protected MongoTemplate authenticationTemplate;

  protected AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);
}
