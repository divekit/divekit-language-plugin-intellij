package thkoeln.archilab.DivekitLanguagePlugin.config;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DivekitSettingsConfigurable implements Configurable {

    private DivekitSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Divekit: Settings";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new DivekitSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        DivekitSettingsState settingsState = DivekitSettingsState.getInstance();
        boolean modified = !settingsComponent.getJarPathText().equals(settingsState.pathToJar);
        return modified;
    }

    @Override
    public void apply() {
        DivekitSettingsState settings = DivekitSettingsState.getInstance();
        settings.pathToJar = settingsComponent.getJarPathText();
    }

    @Override
    public void reset() {
        DivekitSettingsState settings = DivekitSettingsState.getInstance();
        settingsComponent.setJarPathText(settings.pathToJar);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
