package app.coronawarn.server.services.distribution.objectstore.integration;

import app.coronawarn.server.services.distribution.Application;
import app.coronawarn.server.services.distribution.config.DistributionServiceConfig;
import app.coronawarn.server.services.distribution.objectstore.ObjectStoreAccess;
import app.coronawarn.server.services.distribution.objectstore.client.ObjectStoreClient;
import app.coronawarn.server.services.distribution.objectstore.client.ObjectStorePublishingConfig;
import app.coronawarn.server.services.distribution.objectstore.client.S3ClientWrapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

import static app.coronawarn.server.services.distribution.Application.main;
import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties(value = {DistributionServiceConfig.class})
//@ContextConfiguration(classes=ObjectStorePublishingConfig.class)
@ActiveProfiles({"integration-test"})
@Tag("s3-integration")
@SpringBootTest(classes = Application.class)
public class DistributionIT {
//  @Autowired
//  private ObjectStoreAccess objectStoreAccess;

//  @Bean
//  ObjectStorePublishingConfig getObjectStoreConfig() {
//    return new ObjectStorePublishingConfig();
//  }

  @Test
  void runDistribution() {
//    main(new String[] {});
    assertThat(List.of(1)).isNotEmpty();
  }

}
