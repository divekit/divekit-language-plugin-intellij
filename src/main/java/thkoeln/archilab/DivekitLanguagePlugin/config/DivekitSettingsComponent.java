package thkoeln.archilab.DivekitLanguagePlugin.config;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DivekitSettingsComponent {

    private final JPanel myMainPanel;
    private final JBTextField myJarPath = new JBTextField();

    public DivekitSettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter Path to the Divekit Language Server JAR: "), myJarPath, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return myJarPath;
    }

    @NotNull
    public String getJarPathText() {
        return myJarPath.getText();
    }

    public void setJarPathText(@NotNull String newText) {
        myJarPath.setText(newText);
    }
}
