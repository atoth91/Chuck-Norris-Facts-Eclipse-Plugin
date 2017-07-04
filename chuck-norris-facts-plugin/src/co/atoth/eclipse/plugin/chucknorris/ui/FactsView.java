package co.atoth.eclipse.plugin.chucknorris.ui;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.part.ViewPart;

import co.atoth.eclipse.plugin.chucknorris.Activator;
import co.atoth.eclipse.plugin.chucknorris.preferences.PreferencePage;
import co.atoth.eclipse.plugin.chucknorris.service.Fact;
import co.atoth.eclipse.plugin.chucknorris.service.FactService;
import co.atoth.eclipse.plugin.chucknorris.service.IcndbFactService;

public class FactsView extends ViewPart {

	public static final String ID = "co.atoth.eclipse.plugin.chucknorris.FactsView";
	
	private Browser browser;
	private FactService service;

	public FactsView() {
	}

	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		
		service = new IcndbFactService();
		
        IActionBars viewToolbar = getViewSite().getActionBars();
        
        Action settingsAction = new Action() {
        	@Override
            public void run() {
        		 PreferencesUtil.createPreferenceDialogOn(parent.getShell(),
        				 PreferencePage.ID,
                         null,
                         null).open();
            }
		};
		settingsAction.setImageDescriptor(Activator.getImageDescriptor("icons/configs.gif"));
        ActionContributionItem settingsActionItem = new ActionContributionItem(settingsAction);        
        viewToolbar.getToolBarManager().add(settingsActionItem);
        
        Action refreshAction = new Action() {
        	@Override
            public void run() {
        		Job refreshJob = new Job("Loading facts...") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
		                monitor.beginTask("Loading facts...", IProgressMonitor.UNKNOWN);
						
						List<Fact> facts = service.getRandomFacts(Activator.getSettings());
						Display.getDefault().syncExec(()->{
							addFacts(facts);
						});
						
						 monitor.done();
						return Status.OK_STATUS;
					}
				};
				refreshJob.schedule();
            }
		};
        refreshAction.setText("Refresh");
        refreshAction.setToolTipText("Fetch new facts");
        refreshAction.setImageDescriptor(Activator.getImageDescriptor("icons/refresh-16x16.png"));
        ActionContributionItem refreshActionItem = new ActionContributionItem(refreshAction);        
        viewToolbar.getToolBarManager().add(refreshActionItem);
        
        //Init load
        refreshAction.run();
	}
	
	private void addFacts(List<Fact> newFacts){
		StringBuilder sb = new StringBuilder();

		for(Fact fact : newFacts){
			sb.append("<p>" + fact.getText() + "</p>");
		}
		sb.append("<hr>");
		sb.append(browser.getText());
		browser.setText(sb.toString());
	}

	public void setFocus() {
		browser.setFocus();
	}
	
}