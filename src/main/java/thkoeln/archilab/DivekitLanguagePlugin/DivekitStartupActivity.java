package thkoeln.archilab.DivekitLanguagePlugin;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.lsp4intellij.IntellijLanguageClient;
import org.wso2.lsp4intellij.client.languageserver.serverdefinition.RawCommandServerDefinition;
import thkoeln.archilab.DivekitLanguagePlugin.config.DivekitSettingsState;

import java.io.File;

public class DivekitStartupActivity implements StartupActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupActivity.class);

    private static final NotificationGroup NOTIFICATION_GROUP =
            new NotificationGroup("Divekit Notification Group", NotificationDisplayType.STICKY_BALLOON, true);

    @Override
    public void runActivity(@NotNull Project project) {

        String pathToVariationsConfig = "";
        String pathToExtensionsConfig = "";

        File variationsConfig;
        File extensionsConfig;

        if(!DivekitSettingsState.getInstance().pathToVariationsConfig.isEmpty() &&
                !DivekitSettingsState.getInstance().pathToVariableExtensionsConfig.isEmpty()) {
            pathToVariationsConfig = DivekitSettingsState.getInstance().pathToVariationsConfig;
            pathToExtensionsConfig = DivekitSettingsState.getInstance().pathToVariableExtensionsConfig;

            variationsConfig = new File(pathToVariationsConfig);
            extensionsConfig = new File(pathToExtensionsConfig);
        } else {
            //default case
            pathToVariationsConfig = "/config/variationsConfig.json";
            pathToExtensionsConfig = "/config/variableExtensionsConfig.json";

            String basePath = project.getBasePath();

            String projectPathToVariationsConfig = basePath.concat(pathToVariationsConfig);
            String projectPathToExtensionsConfig = basePath.concat(pathToExtensionsConfig);

            variationsConfig = new File(projectPathToVariationsConfig);
            extensionsConfig = new File(projectPathToExtensionsConfig);
        }

        if (variationsConfig.exists() && extensionsConfig.exists() && !DivekitSettingsState.getInstance().pathToJar.isEmpty()) {

            Notification notification = NOTIFICATION_GROUP.createNotification("Der Divekit Language-Server steht in diesem Projekt bereit.", NotificationType.INFORMATION);

            Notifications.Bus.notify(notification);

            String serverJarLocation = DivekitSettingsState.getInstance().pathToJar;

            String[] command = new String[]{"java", "-jar",
                    serverJarLocation,
                    variationsConfig.getAbsolutePath(), extensionsConfig.getAbsolutePath()};

            IntellijLanguageClient.addServerDefinition(new RawCommandServerDefinition("md,java", command), project);
        }
    }
}
