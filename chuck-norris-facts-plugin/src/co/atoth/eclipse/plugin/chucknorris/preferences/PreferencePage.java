package co.atoth.eclipse.plugin.chucknorris.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import co.atoth.eclipse.plugin.chucknorris.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public static final String ID = "co.atoth.eclipse.plugin.chucknorris.preferences.PreferencePage";

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Chuck Norris Facts Settings");
	}
	
	public void createFieldEditors() {
		
		StringFieldEditor firstNameEditor = new StringFieldEditor("FIRST_NAME", "First name:", getFieldEditorParent());
		firstNameEditor.setEmptyStringAllowed(false);
		firstNameEditor.setTextLimit(100);
		addField(firstNameEditor);
		
		StringFieldEditor lastNameEditor = new StringFieldEditor("LAST_NAME", "Last name:", getFieldEditorParent());
		lastNameEditor.setEmptyStringAllowed(false);
		lastNameEditor.setTextLimit(100);
		addField(lastNameEditor);
		
		IntegerFieldEditor factNoEditor = new IntegerFieldEditor("FACT_NO", "Facts to load on refresh:", getFieldEditorParent());
		factNoEditor.setValidRange(1, 100);
		addField(factNoEditor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}