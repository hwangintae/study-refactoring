package org.intaehwang.chapter06.splitPhase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JavaExampleCodeChapter10Test {
    @TempDir
    Path tempDir;

    @Test
    void testNoFileNameThrowsException() {
        assertThatThrownBy(() -> JavaExampleCode.run(new String[]{}))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("파일명을 입력하세요.");
    }

    @Test
    void testFileNotFoundThrowsException() {
        assertThatThrownBy(() -> JavaExampleCode.run(new String[]{"nonexistent.json"}))
                .isInstanceOf(IOException.class);
    }

    @Test
    void testInvalidJsonThrowsException() throws IOException {
        File invalidJsonFile = tempDir.resolve("invalid.json").toFile();
        try (FileWriter writer = new FileWriter(invalidJsonFile)) {
            writer.write("Invalid JSON Content");
        }

        assertThatThrownBy(() -> JavaExampleCode.run(new String[]{invalidJsonFile.getAbsolutePath()}))
                .isInstanceOf(IOException.class);
    }

    @Test
    void testValidJsonWithoutReadyStatus() throws IOException {
        File validJsonFile = tempDir.resolve("valid.json").toFile();
        try (FileWriter writer = new FileWriter(validJsonFile)) {
            writer.write("[{\"status\":\"delivered\"}, {\"status\":\"shipped\"}]");
        }

        long result = JavaExampleCode.run(new String[]{validJsonFile.getAbsolutePath()});
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testValidJsonWithReadyStatus() throws IOException {
        File validJsonFile = tempDir.resolve("valid.json").toFile();
        try (FileWriter writer = new FileWriter(validJsonFile)) {
            writer.write("[{\"status\":\"ready\"}, {\"status\":\"shipped\"}, {\"status\":\"ready\"}]");
        }

        long result = JavaExampleCode.run(new String[]{"-r", validJsonFile.getAbsolutePath()});
        assertThat(result).isEqualTo(2);
    }
}