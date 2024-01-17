import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FullTest {

    public static Collection<Path> getTestFiles() {
        try (Stream<Path> files = Files.list(Paths.get("input"))
                .filter(p -> p.getFileName().toString().startsWith("TEST") && !p.getFileName().toString().startsWith("TEST_ERROR"))) {
            return files.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<Path> getTestErrorFiles() {
        try (Stream<Path> files = Files.list(Paths.get("input"))
                .filter(p -> p.getFileName().toString().startsWith("TEST_ERROR"))) {
            return files.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("getTestFiles")
    public void test(Path p) {
        Path expectedOutputPath = p.getParent().resolveSibling("expected_output").resolve(p.getFileName().toString().replace(".txt", "_Expected_Output.txt"));
        Path outputPath = p.getParent().resolveSibling("output").resolve(p.getFileName().toString().replace(".txt", "Output.txt"));

        Main.main(new String[]{p.toAbsolutePath().toString(), outputPath.toAbsolutePath().toString()});

        Assertions.assertThat(outputPath).hasSameTextualContentAs(expectedOutputPath);

    }

    @ParameterizedTest
    @MethodSource("getTestErrorFiles")
    public void testError(Path p) {
        Path outputPath = p.getParent().resolveSibling("output").resolve(p.getFileName().toString().replace(".txt", "Output.txt"));

        Main.main(new String[]{p.toAbsolutePath().toString(), outputPath.toAbsolutePath().toString()});

        Assertions.assertThat(outputPath).hasContent("ERROR");

    }

}
