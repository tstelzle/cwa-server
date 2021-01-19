package app.coronawarn.server.common.federation.client.hostname;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class NoopHostnameVerifierProviderTest {

  NoopHostnameVerifierProvider provider = new NoopHostnameVerifierProvider();

  @Test
  void isNotNull() {
    assertThat(provider.createHostnameVerifier()).isNotNull();
  }
}
