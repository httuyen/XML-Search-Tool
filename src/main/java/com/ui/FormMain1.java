package com.ui;

import java.io.IOException;

import javax.naming.SizeLimitExceededException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.SAXException;

import com.process.NodeType;

public class FormMain1 extends NodeType {

	protected Shell shell;
	private Text txtPath;
	private Text txtTagName;
	private Label lblParentTag;
	private Label lblAttr;
	private Text txtAttri;
	private Text txtResult;
	private Button btnFind;
	private Button btnOnlyAttr;
	private Label lblChildTag;
	private Text txtChildTag;
	private Button btnOnlyChild;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FormMain1 window = new FormMain1();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
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
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(933, 595);
		shell.setText("Search Tool");
		shell.setLayout(new GridLayout(10, false));
		;
						new Label(shell, SWT.NONE);
				
						Button btnOpenFile = new Button(shell, SWT.NONE);
						btnOpenFile.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
						btnOpenFile.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								FileDialog fd = new FileDialog(shell, SWT.OPEN);
								fd.setText("Open");
								fd.setFilterPath(SELECTED);
								String[] filterExt = { "*.xml", "*.txt", "*.doc", ".rtf", "*.*" };
								fd.setFilterExtensions(filterExt);
								// fd.open();
								if ((SELECTED = fd.open()) == null)
									return;
								else
									txtPath.setText(SELECTED);
							}
						});
						btnOpenFile.setText("Open File");
		
				GridData gd_txtPath = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
				gd_txtPath.widthHint = 361;
				
						txtPath = new Text(shell, SWT.BORDER);
						txtPath.setLayoutData(gd_txtPath);
						txtPath.setEnabled(false);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
										new Label(shell, SWT.NONE);
								
										lblParentTag = new Label(shell, SWT.NONE);
										GridData gd_lblParentTag = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
										gd_lblParentTag.widthHint = 63;
										lblParentTag.setLayoutData(gd_lblParentTag);
										lblParentTag.setText("Parent Tag:");
						
								txtTagName = new Text(shell, SWT.BORDER);
								GridData gd_txtTagName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
								gd_txtTagName.widthHint = 225;
								txtTagName.setLayoutData(gd_txtTagName);
						
								lblAttr = new Label(shell, SWT.NONE);
								GridData gd_lblAttr = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
								gd_lblAttr.widthHint = 57;
								lblAttr.setLayoutData(gd_lblAttr);
								lblAttr.setText("Attribute:");
						
								txtAttri = new Text(shell, SWT.BORDER);
								GridData gd_txtAttri = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
								gd_txtAttri.widthHint = 183;
								txtAttri.setLayoutData(gd_txtAttri);
				
						btnOnlyAttr = new Button(shell, SWT.CHECK);
						btnOnlyAttr.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
						btnOnlyAttr.setText("Only");
						btnOnlyAttr.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								if(btnOnlyAttr.getSelection())
									SELECTION=true;				
							}
						});
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		lblChildTag = new Label(shell, SWT.NONE);
		lblChildTag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblChildTag.setText("Child Tag:");
		
		txtChildTag = new Text(shell, SWT.BORDER);
		GridData gd_txtChildTag = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtChildTag.widthHint = 226;
		txtChildTag.setLayoutData(gd_txtChildTag);
		
		btnOnlyChild = new Button(shell, SWT.CHECK);
		GridData gd_btnOnlyChild = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnOnlyChild.widthHint = 57;
		btnOnlyChild.setLayoutData(gd_btnOnlyChild);
		btnOnlyChild.setText("Only");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		btnFind = new Button(shell, SWT.NONE);
		GridData gd_btnFind = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnFind.widthHint = 89;
		btnFind.setLayoutData(gd_btnFind);
		btnFind.setText("Find");
		btnFind.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (txtPath.getText().isEmpty()) {
					createMes(shell, "ERROR", "The file hasn't been opened !!!");
					return;
				}
				if (txtTagName.getText().isEmpty()) {
					createMes(shell, "ERROR", "Tag name can't empty !!!");
					return;
				}
				if (txtAttri.getText().isEmpty()) {
					try {
						parseXML(SELECTED, txtTagName.getText(), createMap(formatStr(txtAttri.getText())), false);
					} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						System.out.println(formatStr(txtAttri.getText()));
						parseXML(SELECTED, txtTagName.getText(), createMap(formatStr(txtAttri.getText())), true);
					} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		txtResult = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_txtResult = new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1);
		gd_txtResult.heightHint = 464;
		txtResult.setLayoutData(gd_txtResult);
	}

}
