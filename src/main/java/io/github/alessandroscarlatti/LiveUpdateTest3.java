package io.github.alessandroscarlatti;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LiveUpdateTest3 {

    public static void main(String[] args) throws Exception {
        System.out.println("Asdf");
        setText("""
                f:c/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                b:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                l:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                r:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                u:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                d:e/270,s/90,c/90,c,s/90,c/180,c/270,s/90,e/90
                """.stripIndent());
    }

    private static void setText(String value) {
        try {
//            Path file = Paths.get("sandbox/test2.html");

            Path file = Paths.get("sandbox/test3.html");

            String text = Files.readString(file);

            String regex = "(?s)(<template.*?id=\"templateCubeCsv\".*?>)(.+?)(</template>)";

            text = text.replaceAll(regex, "$1" + value + "$3");

            Files.writeString(file, text);
        } catch (Exception e) {
            throw new IllegalStateException("Error setting text", e);
        }
    }
}
