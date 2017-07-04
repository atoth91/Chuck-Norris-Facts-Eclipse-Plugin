package co.atoth.eclipse.plugin.chucknorris.service;
import java.util.List;

import co.atoth.eclipse.plugin.chucknorris.preferences.PluginSettings;

public interface FactService {
    public List<Fact> getRandomFacts(PluginSettings settings);
}
