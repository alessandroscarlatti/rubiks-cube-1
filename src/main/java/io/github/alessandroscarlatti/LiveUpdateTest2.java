package io.github.alessandroscarlatti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LiveUpdateTest2 {

    public static void main(String[] args) throws Exception {


        // 1/21/2023 confirmed that this does NOT work with intellij live reload, while in debug mode
        // the page will not update while in debug mode, even if hosted from another project.
        // the page will update after the job is completed.
        // however, the page can be manually refreshed successfully, while in debug mode, and the page content is updated.
        System.out.println("Asdf");
        setText("v2");
    }

    private static void setText(String value) {
        try {
//            Path file = Paths.get("sandbox/test2.html");
            Path file = Paths.get("C:\\workspace\\2023.01.02_rubiks_cube_v1\\test2.html");

            String text = Files.readString(file);

            String regex = "(<td class=\"target\">)(.+?)(</td>)";

            text = text.replaceAll(regex, "$1" + value + "$3");

            Files.writeString(file, text);
        } catch (Exception e) {
            throw new IllegalStateException("Error setting text", e);
        }
    }
}
