package ArguablyGoodDesign;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveToFile extends PersistInDb{

    @Override
    public void save(String result) {
        // Resolve relative to current working dir (user.dir) so GUI and CLI agree,
        // and log the absolute path so user knows which file to open.
        Path filePath = Paths.get(System.getProperty("user.dir")).resolve("example.txt").toAbsolutePath();
        // Fallback: if user.dir is DocumentEditor02, also mirror to project root for discoverability
        Path projectRootAlt = Paths.get(System.getProperty("user.dir")).getParent() != null
                ? Paths.get(System.getProperty("user.dir")).getParent().resolve("example.txt").toAbsolutePath()
                : null;
        String content = result;

        try{
            Files.writeString(filePath, content);
            System.out.println("File created and written successfully → " + filePath + " (" + content.length() + " bytes)");
            // Mirror to project root if running from DocumentEditor02 submodule (helps when user looks at repo root)
            if (projectRootAlt != null && !projectRootAlt.equals(filePath)) {
                try { Files.writeString(projectRootAlt, content); } catch (Exception ignore) {}
            }
            // Also keep src/ArguablyGoodDesign/example.txt in sync if it exists (IDE often shows this file)
            try {
                Path srcFile = Paths.get("DocumentEditor02/src/ArguablyGoodDesign/example.txt").toAbsolutePath();
                if (Files.exists(srcFile.getParent())) Files.writeString(srcFile, content);
            } catch (Exception ignore) {}
            try {
                Path altSrc = Paths.get(System.getProperty("user.dir")).resolve("src/ArguablyGoodDesign/example.txt").toAbsolutePath();
                if (Files.exists(altSrc.getParent())) Files.writeString(altSrc, content);
            } catch (Exception ignore) {}
        }catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
