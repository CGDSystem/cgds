package com.cgds.postecontrol.ihm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.cgds.postecontrol.rmi.PosteControleConnection;

public class Supervision {

	protected Object result;
	protected Shell shell;
	private Label valBatterie;
	private Label valAltitude;
	private Label valDirection;
	private Label valCoordonneesGps;
	private Label valIntensitDuSignal;
	private Label valMissionEnCours;
	private String nomDrone;

	public Supervision (String nomDrone){
		this.nomDrone = nomDrone;
	}
	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell();
		shell.setSize(450, 196);
		shell.setText(nomDrone + " - Supervision");
		shell.setLayout(new GridLayout(2, false));

		Label lblBatterie = new Label(shell, SWT.NONE);
		lblBatterie.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblBatterie.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));
		lblBatterie.setText("Batterie");

		valBatterie = new Label(shell, SWT.BORDER);
		valBatterie.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblAltitude = new Label(shell, SWT.NONE);
		lblAltitude.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblAltitude.setText("Altitude");
		lblAltitude.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));

		valAltitude = new Label(shell, SWT.BORDER);
		valAltitude.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblDirection = new Label(shell, SWT.NONE);
		lblDirection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblDirection.setText("Direction");
		lblDirection.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));

		valDirection = new Label(shell, SWT.BORDER);
		valDirection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblCoordonneesGps = new Label(shell, SWT.NONE);
		lblCoordonneesGps.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblCoordonneesGps.setText("Coordonnées GPS");
		lblCoordonneesGps.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));

		valCoordonneesGps = new Label(shell, SWT.BORDER);
		valCoordonneesGps.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		Label lblIntensitDuSignal = new Label(shell, SWT.NONE);
		lblIntensitDuSignal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblIntensitDuSignal.setText("Intensité du signal");
		lblIntensitDuSignal.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));

		valIntensitDuSignal = new Label(shell, SWT.BORDER);
		valIntensitDuSignal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		Label lblMissionEnCours = new Label(shell, SWT.NONE);
		lblMissionEnCours.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblMissionEnCours.setText("Mission en cours");
		lblMissionEnCours.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));

		valMissionEnCours = new Label(shell, SWT.BORDER);
		valMissionEnCours.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

	}

	public void update(List<String> message){
		UpdateAsyncrone update =  new UpdateAsyncrone(message);
		Display.getDefault().asyncExec(update);
	}
	
	public class UpdateAsyncrone implements Runnable {

		List<String> args = new ArrayList<String>();
		
		public UpdateAsyncrone(List<String> args) {
			this.args = args;
		}
		
		@Override
		public void run() {
			valBatterie.setText(args.get(1));
			valAltitude.setText(args.get(0));
			 valDirection.setText(args.get(2));
			 valCoordonneesGps.setText(args.get(3));
			 valIntensitDuSignal.setText(args.get(4));
			 valMissionEnCours.setText(args.get(5));

		}

	}
}
