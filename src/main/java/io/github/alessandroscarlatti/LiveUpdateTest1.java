package io.github.alessandroscarlatti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LiveUpdateTest1 {

    public static void main(String[] args) throws Exception {
        Path file = Paths.get("sandbox/test2.html");

        String text = Files.readString(file);

        text = text.replace("{REPLACE_ME}", "NEW VALUE");

        Files.writeString(file, text);

        // 1/21/2023 confirmed that this works with intellij live reload
    }
}
