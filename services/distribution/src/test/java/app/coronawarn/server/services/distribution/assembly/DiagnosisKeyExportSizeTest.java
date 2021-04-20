package app.coronawarn.server.services.distribution.assembly;

import static app.coronawarn.server.services.distribution.common.Helpers.buildDiagnosisKeyForSubmissionTimestamp;
import static app.coronawarn.server.services.distribution.common.Helpers.buildDiagnosisKeysWithFlexibleRollingPeriod;
import static app.coronawarn.server.services.distribution.common.Helpers.buildTraceTimeIntervalWarning;

import app.coronawarn.server.services.distribution.assembly.component.CryptoProvider;
import app.coronawarn.server.services.distribution.assembly.diagnosiskeys.structure.file.TemporaryExposureKeyExportFile;
import app.coronawarn.server.services.distribution.assembly.structure.WritableOnDisk;
import app.coronawarn.server.services.distribution.assembly.structure.archive.Archive;
import app.coronawarn.server.services.distribution.assembly.structure.archive.ArchiveOnDisk;
import app.coronawarn.server.services.distribution.assembly.structure.archive.decorator.signing.DistributionArchiveSigningDecorator;
import app.coronawarn.server.services.distribution.assembly.structure.directory.Directory;
import app.coronawarn.server.services.distribution.assembly.structure.directory.DirectoryOnDisk;
import app.coronawarn.server.services.distribution.assembly.structure.file.File;
import app.coronawarn.server.services.distribution.assembly.structure.file.FileOnDisk;
import app.coronawarn.server.services.distribution.assembly.structure.util.ImmutableStack;
import app.coronawarn.server.services.distribution.assembly.tracewarnings.structure.file.TraceTimeIntervalWarningExportFile;
import app.coronawarn.server.services.distribution.config.DistributionServiceConfig;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@EnableConfigurationProperties(value = DistributionServiceConfig.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CryptoProvider.class, DistributionServiceConfig.class},
    initializers = ConfigDataApplicationContextInitializer.class)
class DiagnosisKeyExportSizeTest {

  @Autowired
  CryptoProvider cryptoProvider;

  @Autowired
  DistributionServiceConfig distributionServiceConfig;

  @Rule
  private final TemporaryFolder outputFolder = new TemporaryFolder();

  private java.io.File outputFile;
  private Directory<WritableOnDisk> parentDirectory;

  @BeforeEach
  void setupAll() throws IOException {
    outputFolder.create();
    outputFile = outputFolder.newFolder();
    parentDirectory = new DirectoryOnDisk(outputFile);
  }

  @Test
  void measureDiagnosisKeyExportSize() {

    File<WritableOnDisk> archive;

    System.out.println("TEMPORARY EXPOSURE KEY EXPORT");
    System.out.println("=============================");

    archive = createSignedArchive(createTemporaryExposureKeyExportFile(0));
    System.out.println("Size for 0 keys:        " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTemporaryExposureKeyExportFile(1));
    System.out.println("Size for 1 key:         " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTemporaryExposureKeyExportFile(10));
    System.out.println("Size for 10 keys:       " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTemporaryExposureKeyExportFile(100));
    System.out.println("Size for 100 keys:      " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTemporaryExposureKeyExportFile(1000));
    System.out.println("Size for 1000 keys:     " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTemporaryExposureKeyExportFile(10000));
    System.out.println("Size for 10000 keys:    " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTemporaryExposureKeyExportFile(100000));
    System.out.println("Size for 100000 keys:   " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTemporaryExposureKeyExportFile(1000000));
    System.out.println("Size for 1000000 keys:  " + archive.getBytes().length + " Bytes");

    System.out.println("\n");

    System.out.println("TRACE TIME WARNING EXPORT");
    System.out.println("=========================");

    archive = createSignedArchive(createTraceTimeIntervalWarningExportFile(0));
    System.out.println("Size for 0 keys:        " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTraceTimeIntervalWarningExportFile(1));
    System.out.println("Size for 1 key:         " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTraceTimeIntervalWarningExportFile(10));
    System.out.println("Size for 10 keys:       " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTraceTimeIntervalWarningExportFile(100));
    System.out.println("Size for 100 keys:      " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTraceTimeIntervalWarningExportFile(1000));
    System.out.println("Size for 1000 keys:     " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTraceTimeIntervalWarningExportFile(10000));
    System.out.println("Size for 10000 keys:    " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTraceTimeIntervalWarningExportFile(100000));
    System.out.println("Size for 100000 keys:   " + archive.getBytes().length + " Bytes");

    archive = createSignedArchive(createTraceTimeIntervalWarningExportFile(1000000));
    System.out.println("Size for 1000000 keys:  " + archive.getBytes().length + " Bytes");
  }

  private TemporaryExposureKeyExportFile createTemporaryExposureKeyExportFile(int numKeys) {
    return TemporaryExposureKeyExportFile.fromDiagnosisKeys(
        buildDiagnosisKeysWithFlexibleRollingPeriod(123456, 123456, numKeys, 144),
        "DE", 0, 10, distributionServiceConfig
    );
  }

  private TraceTimeIntervalWarningExportFile createTraceTimeIntervalWarningExportFile(int numKeys) {
    return TraceTimeIntervalWarningExportFile.fromTraceTimeIntervalWarnings(
        buildTraceTimeIntervalWarning(123, 125, 123456, numKeys),
        "DE", 10, distributionServiceConfig
    );
  }

  private File<WritableOnDisk> createSignedArchive(FileOnDisk exportFile) {

    Archive<WritableOnDisk> archive = new ArchiveOnDisk("test.zip");
    archive.addWritable(exportFile);

    var decorated = new DistributionArchiveSigningDecorator(archive, cryptoProvider, distributionServiceConfig);
    decorated.prepare(new ImmutableStack<>());

    return decorated;
  }
}
