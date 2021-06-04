import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.*;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class DivekitCodeCompletionTest extends BasePlatformTestCase {

    private Path variationsPath;
    private Path extensionsPath;
    private Path readmePath;

    public void setUp() throws Exception{
        super.setUp();

        /*
        Helper methods like myFixture.copyFileToProject() are sadly not working with the language server, because they
        create temp:// files which can not be worked with further.
        So we need to handle the file creation for ourselves.
         */

        String absolutePathToProject = "";
        String[] pathArray = myFixture.getProject().getProjectFilePath().split("/");

        for(int i = 0; i < pathArray.length - 1; i++) {
            absolutePathToProject = absolutePathToProject.concat(pathArray[i]).concat("/");
        }

        absolutePathToProject = absolutePathToProject.substring(0, absolutePathToProject.length() - 1);

        String pathToVariationsConfig = absolutePathToProject.concat("/src/variationsConfig.json");
        String pathToExtensionsConfig = absolutePathToProject.concat("/src/variableExtensionsConfig.json");

        Path srcPath = Paths.get(absolutePathToProject + "/src/");

        variationsPath = Paths.get(pathToVariationsConfig);
        extensionsPath = Paths.get(pathToExtensionsConfig);
        readmePath = Paths.get(absolutePathToProject + "/src/Readme.md");

        Files.createDirectory(srcPath);

        Files.createFile(variationsPath);
        Files.createFile(extensionsPath);
        Files.createFile(readmePath);

        Files.writeString(variationsPath, "{\n" +
                "  \"objects\": [\n" +
                "    {\n" +
                "      \"ids\": \"Vehicle\",\n" +
                "      \"objectVariations\": [\n" +
                "        {\n" +
                "          \"id\": \"RaceCar\",\n" +
                "          \"Attr\": {\n" +
                "            \"Power\": {\n" +
                "              \"\": \"horsePower\",\n" +
                "              \"Type\": \"Integer\",\n" +
                "              \"Value\": 203\n" +
                "            },\n" +
                "            \"Built\": {\n" +
                "              \"\": \"yearBuilt\",\n" +
                "              \"Type\": \"Integer\",\n" +
                "              \"Value\": 2017\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"variableExtensions\": [\"Basic\", \"Getter\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}");
        Files.writeString(extensionsPath, "[\n" +
                "  {\n" +
                "    \"id\": \"Basic\",\n" +
                "    \"variableExtensions\":\n" +
                "    {\n" +
                "      \"\": {\n" +
                "        \"preValue\": \"\",\n" +
                "        \"value\": \"id\",\n" +
                "        \"postValue\": \"\",\n" +
                "        \"modifier\": \"NONE\"\n" +
                "      },\n" +
                "      \"Class\":\n" +
                "      {\n" +
                "        \"preValue\": \"\",\n" +
                "        \"value\": \"id\",\n" +
                "        \"postValue\": \"\",\n" +
                "        \"modifier\": \"NONE\"\n" +
                "      },\n" +
                "      \"ClassPath\":\n" +
                "      {\n" +
                "        \"preValue\": \"thkoeln.praktikum.\",\n" +
                "        \"value\": \"Class\",\n" +
                "        \"postValue\": \"\",\n" +
                "        \"modifier\": \"NONE\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"Getter\",\n" +
                "    \"variableExtensions\":\n" +
                "    {\n" +
                "      \"GetToOne\":\n" +
                "      {\n" +
                "        \"preValue\": \"get\",\n" +
                "        \"value\": \"Class\",\n" +
                "        \"postValue\": \"\",\n" +
                "        \"modifier\": \"NONE\"\n" +
                "      },\n" +
                "      \"GetToMany\":\n" +
                "      {\n" +
                "        \"preValue\": \"get\",\n" +
                "        \"value\": \"s\",\n" +
                "        \"postValue\": \"\",\n" +
                "        \"modifier\": \"NONE\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]");
        Files.writeString(readmePath, "This is a $");
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/resources/";
    }

    public void testShouldStartLanguageServerAndProvideCompletionItems() {

        /*
        Load the created Readme.md as a VirtualFile.
         */
        VirtualFile readme = LocalFileSystem.getInstance().findFileByPath(readmePath.toString());

        /*
        Open the readme file in the in-memory editor.
        This is a working method to get a file with prefix file:// into
        the editor.
         */
        myFixture.configureFromExistingVirtualFile(readme);

        /*
        The expected completion Items that will be generated out of the variationsConfig.json and variableExtensionsConfig.json, which
        will be provided by the Divekit Language Server if everything works as supposed
         */
        ArrayList<String> expectedCompletionItems = new ArrayList<>(Arrays.asList("$Vehicle$", "$Vehicle_Attr_Built$", "$Vehicle_Attr_BuiltType$", "$Vehicle_Attr_BuiltValue$",
                "$Vehicle_Attr_Power$", "$Vehicle_Attr_PowerType$", "$Vehicle_Attr_PowerValue$", "$VehicleClass$",
                "$VehicleClassPath$", "$VehicleGetToMany$", "$VehicleGetToOne$"));

        /*
        Path to jar must be hard coded for test, if not provided by build path...
         */
//        String serverJarPath = DivekitSettingsState.getInstance().pathToJar;

        String[] command = new String[]{"java", "-jar",
                "C:/Users/Marco/IdeaProjects/automated-repo-setup/DivekitLanguageServer-0.95-jar-with-dependencies.jar",
                variationsPath.toString(), extensionsPath.toString()};

        RawCommandServerDefinition definition = new RawCommandServerDefinition("md, java", command);

        //Start the Divekit Language Server
        IntellijLanguageClient.addServerDefinition(definition);

        // Move the caret to the end of the line
        myFixture.getEditor().getCaretModel().moveCaretRelatively(11, 0, false, false, true);

        //Type a V in the opened editor, so we have the text "This is a $V". Caret is now behind the V and this is the place
        //where we want to invoke the completion
        myFixture.type("V");

        ArrayList<String> providedCompletionItems = new ArrayList<>();

        /*
        The myFixture.complete() fires the event which motivates the Divekit Language Server to provide the
        completionItems to the editor.
         */
        for(LookupElement lookupElement : myFixture.complete(CompletionType.BASIC)) {
            providedCompletionItems.add(lookupElement.getLookupString());
        }

        // The final assertion
        assertEquals(expectedCompletionItems, providedCompletionItems);
    }
}
