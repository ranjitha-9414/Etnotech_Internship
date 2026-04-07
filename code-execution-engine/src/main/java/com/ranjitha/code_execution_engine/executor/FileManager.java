package com.ranjitha.code_execution_engine.executor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    public static Path createJavaFile(String code, String className) throws IOException {
        Path tempDir = Files.createTempDirectory("code_exec_");
        Path javaFile = tempDir.resolve(className + ".java");
        Files.writeString(javaFile, code);
        return javaFile;
    }
}