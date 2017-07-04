package co.atoth.eclipse.plugin.chucknorris.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import co.atoth.eclipse.plugin.chucknorris.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("FIRST_NAME", PluginSettings.DEFAULT_FIRST_NAME);
		store.setDefault("LAST_NAME", PluginSettings.DEFAULT_LAST_NAME);
		store.setDefault("FACT_NO", PluginSettings.DEFAULT_FACT_NO);
	}

}
