package com.cgds.postecontrol.ihm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.cgds.postecontrol.PosteDeControleController;
import com.cgds.postecontrol.rmi.PosteControleConnection;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class Accueil {

	protected Shell shell;
	protected Display display;

	PosteDeControleController controller;
	
	Combo combo;

	public Accueil(PosteDeControleController controller){
		this.controller = controller;
		display = Display.getDefault();
		shell = new Shell(display);
		createContents();
		shell.addListener(SWT.Close, new Listener()
	     {
	        @Override
	        public void handleEvent(Event arg0)
	        {
	           System.out.println("Main Shell handling Close event, about to dipose the main Display");
	           display.close();
	        }
	     }); 
		
		shell.open();
		while (!display.isDisposed()) {
			try{
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			}catch(Exception e){
				System.out.println("Acceuil display.readAndDispatch Exception");
			}
		}
	    System.exit(0);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell.setSize(408, 158);
		shell.setText("Poste de Controle");
		
		Label lblSelectionnerUnDrone = new Label(shell, SWT.NONE);
		lblSelectionnerUnDrone.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblSelectionnerUnDrone.setBounds(10, 23, 172, 23);
		lblSelectionnerUnDrone.setText("Selectionner un Drone :");
		
		combo = new Combo(shell, SWT.READ_ONLY);
		combo.setItems(controller.listeDrones());
		combo.setBounds(202, 23, 77, 23);
		combo.select(0);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0)  {
				System.out.println("Supervision " + combo.getText());
				controller.creerConnexionSupervision(combo.getText());
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnNewButton.setBounds(10, 72, 105, 31);
		btnNewButton.setText("Supervision");
		
		Button btnVisualisation = new Button(shell, SWT.NONE);
		btnVisualisation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				System.out.println("ComboText:" + combo.getText());
				Visualisation visualisation = new Visualisation(shell.getDisplay(), combo.getText());
				controller.creerConnexionVisualisation(combo.getText(),visualisation);
			}
		});
		btnVisualisation.setText("Visualisation");
		btnVisualisation.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnVisualisation.setBounds(142, 72, 105, 31);
		
		Button btnContrle = new Button(shell, SWT.NONE);
		btnContrle.setText("Contrôle");
		btnContrle.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnContrle.setBounds(276, 72, 94, 31);
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				combo.setItems(controller.listeDrones());
			}
		});
		btnNewButton_1.setBounds(305, 23, 65, 25);
		btnNewButton_1.setText("Rafraichir");

	}
}
