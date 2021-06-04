package thkoeln.archilab.DivekitLanguagePlugin.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//TODO - add configurable path to config json's
@State(
        name = "thkoeln.archilab.DivekitLanguagePlugin.config.DivekitSettingsState",
        storages = {@Storage("DivekitSettings.xml")}
)
public class DivekitSettingsState implements PersistentStateComponent<DivekitSettingsState> {

    public String pathToJar = "";

    public static DivekitSettingsState getInstance() {
        return ServiceManager.getService(DivekitSettingsState.class);
    }


    @Nullable
    @Override
    public DivekitSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull DivekitSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
