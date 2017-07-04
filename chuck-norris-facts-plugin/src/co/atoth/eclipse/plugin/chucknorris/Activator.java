package co.atoth.eclipse.plugin.chucknorris;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import co.atoth.eclipse.plugin.chucknorris.preferences.PluginSettings;

public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "chuck.norris.facts.plugin"; //$NON-NLS-1$
    
    // The shared instance
    private static Activator plugin;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
    }
    
    public static PluginSettings getSettings(){
		IPreferenceStore prefs = getDefault().getPreferenceStore();
		PluginSettings settings = new PluginSettings();
		settings.setFactFirstName(prefs.getString("FIRST_NAME"));
		settings.setFactLastName(prefs.getString("LAST_NAME"));
		settings.setFactLoadCount(prefs.getInt("FACT_NO"));
		return settings;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     *
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

	public static AbstractUIPlugin getDefault() {
		return plugin;
	}
    
}