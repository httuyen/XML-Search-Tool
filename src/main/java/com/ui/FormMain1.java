package com.ui;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
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
	private Group grS;
	private Text txtLevel;
	public static StyledText styledText;
	private Label label;
	private Text txtFileName;
	private Button btnURLOut;
	private Text txtURLOut;
	private Group grF;
	private Group grOK;
	private Button btnFolder;
	private Text txtFolder;
	private Text text;
	private Text text_1;

	// private Image small;
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
		// small = new Image(display,"..\\Search-Tool\\images\\search.ico");
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
		shell.setSize(843, 595);
		shell.setText("Search Tool");
		shell.setLayout(new GridLayout(2, false));
		// shell.setImage(small);

		grS = new Group(shell, SWT.NONE);
		grS.setText("Search Option");
		grS.setLocation(5, -104);
		GridData gd_grS = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_grS.heightHint = 158;
		gd_grS.widthHint = 440;
		grS.setLayoutData(gd_grS);

		Button btnOpenFile = new Button(grS, SWT.NONE);
		btnOpenFile.setBounds(10, 26, 62, 25);
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

		txtPath = new Text(grS, SWT.BORDER);
		txtPath.setBounds(77, 28, 297, 21);
		txtPath.setEnabled(false);

		lblParentTag = new Label(grS, SWT.NONE);
		lblParentTag.setBounds(10, 60, 63, 15);
		lblParentTag.setText("Parent Tag:");

		lblChildTag = new Label(grS, SWT.NONE);
		lblChildTag.setBounds(20, 86, 53, 15);
		lblChildTag.setText("Child Tag:");

		txtTagName = new Text(grS, SWT.BORDER);
		txtTagName.setBounds(78, 57, 93, 21);

		txtChildTag = new Text(grS, SWT.BORDER);
		txtChildTag.setBounds(78, 83, 93, 21);

		lblAttr = new Label(grS, SWT.NONE);
		lblAttr.setBounds(177, 58, 53, 15);
		lblAttr.setText("Attribute:");

		txtAttri = new Text(grS, SWT.BORDER);
		txtAttri.setBounds(236, 55, 138, 21);

		btnOnlyAttr = new Button(grS, SWT.CHECK);
		btnOnlyAttr.setBounds(380, 59, 46, 16);
		btnOnlyAttr.setText("Only");
		btnOnlyAttr.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				ONLY_ATTR = btn.getSelection();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		btnOnlyChild = new Button(grS, SWT.CHECK);
		btnOnlyChild.setBounds(50, 112, 138, 16);
		btnOnlyChild.setText("Only (comming soon)");
		btnOnlyChild.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				ONLY_CHILD = btn.getSelection();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		btnOnlyChild.setEnabled(false);
		Button btnLevel = new Button(grS, SWT.CHECK);
		btnLevel.setBounds(50, 136, 93, 16);
		btnLevel.setText("with level tag");
		btnLevel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				HAVE_LEVEL = btn.getSelection();
				if (btn.getSelection()) {
					txtLevel.setEnabled(true);
				} else {
					txtLevel.setText("0");
					txtLevel.setEnabled(false);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		txtLevel = new Text(grS, SWT.BORDER);
		txtLevel.setBounds(142, 134, 29, 21);
		txtLevel.setEnabled(false);
		txtLevel.setText("0");

		grF = new Group(shell, SWT.NONE);
		grF.setText("Filter Option");
		grF.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		//grF.setVisible(false);
		
		btnFolder = new Button(grF, SWT.NONE);
		btnFolder.setBounds(10, 28, 88, 25);
		btnFolder.setText("Choose Folder");

		txtFolder = new Text(grF, SWT.BORDER);
		txtFolder.setBounds(105, 32, 251, 21);
		txtFolder.setEnabled(false);
		Label lblNE = new Label(grF, SWT.NONE);
		lblNE.setBounds(67, 66, 32, 15);
		lblNE.setText("NE IP:");

		Label lblAttriNE = new Label(grF, SWT.NONE);
		lblAttriNE.setBounds(44, 91, 55, 15);
		lblAttriNE.setText("Attribute:");

		text = new Text(grF, SWT.BORDER);
		text.setBounds(105, 63, 165, 21);

		text_1 = new Text(grF, SWT.BORDER);
		text_1.setBounds(105, 88, 165, 21);

		grOK = new Group(shell, SWT.NONE);
		GridData gd_grOK = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_grOK.heightHint = 125;
		grOK.setLayoutData(gd_grOK);
		Button btnExportFile = new Button(grOK, SWT.CHECK);
		btnExportFile.setBounds(69, 53, 93, 16);
		btnExportFile.setText("Export File");
		final Button btnOpenA = new Button(grOK, SWT.CHECK);
		btnOpenA.setBounds(69, 75, 195, 16);
		btnOpenA.setText("Open when export complete");
		btnOpenA.setEnabled(false);

		label = new Label(grOK, SWT.NONE);
		label.setBounds(30, 27, 55, 15);
		label.setText("File name:");
		label.setEnabled(false);

		txtFileName = new Text(grOK, SWT.BORDER);
		txtFileName.setBounds(85, 24, 98, 21);
		txtFileName.setEnabled(false);

		btnURLOut = new Button(grOK, SWT.NONE);
		btnURLOut.setBounds(189, 22, 75, 25);
		btnURLOut.setText("URL Output");
		btnURLOut.setEnabled(false);

		txtURLOut = new Text(grOK, SWT.BORDER);
		txtURLOut.setBounds(270, 24, 262, 21);
		txtURLOut.setEnabled(false);

		btnFind = new Button(grOK, SWT.NONE);
		btnFind.setBounds(156, 108, 108, 25);
		btnFind.setText("OKE");

		btnFind.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FOUNDED = false;
				if (txtPath.getText().isEmpty()) {
					createMes(shell, "ERROR", "The file hasn't been opened !!!");
					return;
				}
				if (txtTagName.getText().isEmpty()) {
					createMes(shell, "ERROR", "Tag name can't empty !!!");
					return;
				}
				if (HAVE_EXPORT) {
					if (txtURLOut.getText().isEmpty()) {
						createMes(shell, "ERROR", "Please choose the path of output");
						return;
					} else if (txtFileName.getText().isEmpty()) {
						createMes(shell, "ERROR", "Output file can't be not empty!!!");
						return;
					}
				}

				if (txtAttri.getText().isEmpty()) {
					try {
						STR_BUILDER.setLength(0);
						parseXML(SELECTED, txtTagName.getText(), txtChildTag.getText(),
								validLvlInput(txtLevel.getText()), createMap(formatStr("")), false, ONLY_ATTR);
						if (!FOUNDED) {
							createMes(shell, "ERROR", "Not Found");
							styledText.setText("");
							return;
						}
						// toPrettyString(STR_BUILDER.toString(), 4);
						styledText.setText(STR_BUILDER.toString());
						if (HAVE_EXPORT) {
							exportXML(createURLOut(txtFileName.getText()), HAVE_OPEN);
							if (!HAVE_OPEN) {
								createMes(shell, "NOTIFICATION", "Export complete!!!");
							}
						}
					} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e1) {
						e1.printStackTrace();
					} catch (JAXBException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					try {
						STR_BUILDER.setLength(0);
						parseXML(SELECTED, txtTagName.getText(), txtChildTag.getText(),
								validLvlInput(txtLevel.getText()), createMap(formatStr(txtAttri.getText())), true,
								ONLY_ATTR);
						if (!FOUNDED) {
							createMes(shell, "ERROR", "Not Found");
							styledText.setText("");
							return;
						}
						// toPrettyString(STR_BUILDER.toString(), 4);
						styledText.setText(STR_BUILDER.toString());
						if (HAVE_EXPORT) {
							exportXML(createURLOut(txtFileName.getText()), HAVE_OPEN);
							if (!HAVE_OPEN) {
								createMes(shell, "NOTIFICATION", "Export complete!!!");
							}
						}
					} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e1) {
						e1.printStackTrace();
					} catch (JAXBException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btnURLOut.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				DirectoryDialog dd = new DirectoryDialog(shell, SWT.OPEN);
				dd.setText("Open");
				dd.setFilterPath(SELECTED);
				dd.setFilterPath("d:\\");
				if ((URL_OUT = dd.open()) == null)
					return;
				else
					txtURLOut.setText(URL_OUT);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		btnOpenA.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				HAVE_OPEN = btn.getSelection();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		btnExportFile.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				if (!btn.getSelection()) {
					txtFileName.setEnabled(false);
					label.setEnabled(false);
					btnURLOut.setEnabled(false);
					btnOpenA.setEnabled(false);
				} else {
					txtFileName.setEnabled(true);
					label.setEnabled(true);
					btnURLOut.setEnabled(true);
					btnOpenA.setEnabled(true);
				}
				HAVE_EXPORT = btn.getSelection();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		styledText = new StyledText(shell, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		styledText.addLineStyleListener(new LineStyleListener() {

			@Override
			public void lineGetStyle(LineStyleEvent event) {
				StyleRange styleRange = new StyleRange();
				styleRange.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
				int maxLine = styledText.getLineCount();
				int bulletLength = Integer.toString(maxLine).length();
				int bulletWidth = (bulletLength + 1) * styledText.getLineHeight() / 2;
				styleRange.metrics = new GlyphMetrics(0, 0, bulletWidth);
				event.bullet = new Bullet(ST.BULLET_TEXT, styleRange);
				int bulletLine = styledText.getLineAtOffset(event.lineOffset) + 1;
				event.bullet.text = String.format("%" + bulletLength + "s", bulletLine);
			}
		});
		styledText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				// styledText.redraw();
//				for(int i = 0;i<=90;i++) {
//					styledText.append("\n");	
//				}
			}
		});
	}
}