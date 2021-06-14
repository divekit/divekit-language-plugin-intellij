package thkoeln.archilab.DivekitLanguagePlugin.config;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DivekitSettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField jarPath = new JBTextField();
    private final JBTextField variationsConfigPath = new JBTextField();
    private final JBTextField variableExtensionsConfigPath = new JBTextField();

    public DivekitSettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter Path to the Divekit Language Server JAR: "), jarPath, 1, false)
                .addLabeledComponent(new JBLabel("Enter Path to the variationsConfig.json File: "), variationsConfigPath, 1, false)
                .addLabeledComponent(new JBLabel("Enter Path to the variableExtensionsConfig.json File: "), variableExtensionsConfigPath, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return jarPath;
    }

    @NotNull
    public String getJarPathText() {
        return jarPath.getText();
    }

    public void setJarPathText(@NotNull String newText) {
        jarPath.setText(newText);
    }

    @NotNull
    public String getVariationsConfigPathText() {
        return variationsConfigPath.getText();
    }

    public void setVariationsConfigPathText(@NotNull String newText) {
        variationsConfigPath.setText(newText);
    }

    @NotNull
    public String getVariableExtensionsConfigPathText() {
        return variableExtensionsConfigPath.getText();
    }

    public void setVariableExtensionsConfigPath(@NotNull String newText) {
        variableExtensionsConfigPath.setText(newText);
    }
 }
