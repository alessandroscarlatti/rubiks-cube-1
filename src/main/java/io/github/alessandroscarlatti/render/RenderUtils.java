package io.github.alessandroscarlatti.render;

import io.github.alessandroscarlatti.model.Cube;
import io.github.alessandroscarlatti.serialization.CubeCsvMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RenderUtils {

    public static void main(String[] args) throws Exception {
        System.out.println("Asdf");

        Path file = Paths.get("sandbox/test3.html");

        setCubeCsvText(file, """
            f:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
            b:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
            l:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
            r:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
            u:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
            d:e/270,s/90,c/180,c/270,s/90,c/90,c/0,s/90,e/90
                """.stripIndent());
    }

    public static void renderCube(Cube cube) {
        renderCube(cube, Paths.get("src/main/web/test2.html"));
    }

    public static void renderCube(Cube cube, Path file) {
        String cubeCsv = CubeCsvMapper.getCubeCsv2(cube);

        setCubeCsvText(file, cubeCsv);
    }

    private static void setCubeCsvText(Path file, String value) {
        try {
            String text = Files.readString(file);

            String regex = "(?s)(<template.*?id=\"templateCubeCsv\".*?>)(.+?)(</template>)";

            text = text.replaceAll(regex, "$1" + value + "$3");

            Files.writeString(file, text);
        } catch (Exception e) {
            throw new IllegalStateException("Error setting text", e);
        }
    }
}
