package com.ui;

import java.util.List;

import javax.xml.bind.JAXBException;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.constant.Constant;
import com.process.FilterSNMP;
import com.process.NodeType;

public class FormMain1 extends NodeType {

	protected Shell shell;
	private Text txtPath;
	private Text txtTagName;
	private Label lblParentTag;
	private Label lblAttr;
	private Text txtAttri;
	private Button btnFind;
	private Button btnOnlyAttr;
	private Label lblChildTag;
	private Text txtChildTag;
	private Button btnOnlyChild;
	private Group grS;
	public static StyledText styledText;
	private Label lblFileName;
	private Text txtFileName;
	private Button btnURLOut;
	private Text txtURLOut;
	private Group grF;
	private Group grOK;
	private Button btnFolder;
	private Text txtFolder;
	private Text txtNE;
	private TabItem tabS;
	private TabItem tabF;
	private Button btnExportFile;
	private Button btnOpenA;
	private Label lblFMS;
	private Text txtNesting;
	private Text txtVB;
	private Text txtES;
	private Label lblNE;
	private Label lblVB;
	private Label lblES;
	private Button cbKeepVB;
	private Spinner spinner;
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
		shell.setSize(808, 595);
		shell.setText("Search Tool");
		shell.setLayout(new GridLayout(3, false));
		// shell.setImage(ICON);
		final TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLocation(-4, -22);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_tabFolder.heightHint = 205;
		gd_tabFolder.widthHint = 418;
		tabFolder.setLayoutData(gd_tabFolder);
		tabFolder.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				TAB_INDEX = tabFolder.getSelectionIndex();
				if (TAB_INDEX == 1) {
					if (btnExportFile == null)
						System.out.println("null");
					btnExportFile.setSelection(true);
					btnExportFile.setEnabled(false);
					styledText.setText("");
					setEnable(true);

				} else {
					try {
						btnExportFile.setSelection(false);
						btnExportFile.setEnabled(true);
						setEnable(false);
						btnOpenA.setEnabled(false);
						btnOpenA.setSelection(false);
						lblFileName.setEnabled(false);
						txtFileName.setEnabled(false);
					} catch (Exception e2) {

					}

				}

			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		tabS = new TabItem(tabFolder, SWT.NONE);
		tabS.setText("Search");

		grS = new Group(tabFolder, SWT.NONE);
		tabS.setControl(grS);
		grS.setLocation(5, -104);

		Button btnOpenFile = new Button(grS, SWT.NONE);
		btnOpenFile.setBounds(10, 11, 62, 25);
		btnOpenFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Open");
				fd.setFilterPath(SELECTED);
				String[] filterExt = { "*.xml", "*.txt", "*.doc", ".rtf", "*.*" };
				fd.setFilterExtensions(filterExt);
				if ((SELECTED = fd.open()) == null)
					return;
				else
					txtPath.setText(SELECTED);
			}
		});
		btnOpenFile.setText("Open File");

		txtPath = new Text(grS, SWT.BORDER | SWT.READ_ONLY);
		txtPath.setBounds(78, 13, 296, 21);

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
		lblAttr.setBounds(180, 58, 53, 15);
		lblAttr.setText("Attribute:");

		txtAttri = new Text(grS, SWT.BORDER);
		txtAttri.setBounds(236, 55, 138, 21);

		btnOnlyAttr = new Button(grS, SWT.CHECK);
		btnOnlyAttr.setBounds(380, 59, 46, 16);
		btnOnlyAttr.setText("Only");
		btnOnlyAttr.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				Button btn = (Button) e.getSource();
				ONLY_ATTR = btn.getSelection();
			}

		});

		btnOnlyChild = new Button(grS, SWT.CHECK);
		btnOnlyChild.setBounds(50, 112, 138, 16);
		btnOnlyChild.setText("Only (comming soon)");
		btnOnlyChild.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				Button btn = (Button) e.getSource();
				ONLY_CHILD = btn.getSelection();
			}
		});

		btnOnlyChild.setVisible(false);
		Button btnLevel = new Button(grS, SWT.CHECK);
		btnLevel.setBounds(50, 136, 93, 16);
		btnLevel.setText("with level tag");
		btnLevel.setVisible(false);
		btnLevel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				Button btn = (Button) e.getSource();
				HAVE_LEVEL = btn.getSelection();
				if (btn.getSelection()) {
					spinner.setEnabled(true);

				} else {
					// txtLevel.setText("0");
					spinner.setEnabled(false);

				}
			}

		});
		
		spinner = new Spinner(grS, SWT.BORDER | SWT.READ_ONLY);
		spinner.setBounds(144, 134, 47, 22);
		spinner.setVisible(false);
		spinner.setEnabled(false);
		// txtLevel.setText("0");

		tabF = new TabItem(tabFolder, SWT.NONE);
		tabF.setText("Filter");

		grF = new Group(tabFolder, SWT.NONE);
		tabF.setControl(grF);
		grF.layout(false);

		btnFolder = new Button(grF, SWT.NONE);
		btnFolder.setBounds(10, 10, 88, 25);
		btnFolder.setText("Choose Folder");
		btnFolder.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				super.widgetSelected(e);
				DirectoryDialog dd = new DirectoryDialog(shell, SWT.OPEN);
				dd.setText("Open");
				dd.setFilterPath(SELECTED);
				dd.setFilterPath("d:\\");
				if ((URL_FILTER = dd.open()) == null)
					return;
				else {
					txtFolder.setText(URL_FILTER);
					txtURLOut.setText(URL_FILTER);
				}
			}

		});

		txtFolder = new Text(grF, SWT.BORDER | SWT.READ_ONLY);
		txtFolder.setBounds(122, 12, 204, 21);
		lblNE = new Label(grF, SWT.NONE);
		lblNE.setBounds(51, 103, 32, 15);
		lblNE.setText("NE IP:");

		txtNE = new Text(grF, SWT.BORDER);
		txtNE.setBounds(123, 100, 141, 21);

		lblFMS = new Label(grF, SWT.NONE);
		lblFMS.setBounds(51, 46, 62, 15);
		lblFMS.setText("Format File:");

		Label lblNesting = new Label(grF, SWT.NONE);
		lblNesting.setBounds(51, 79, 55, 15);
		lblNesting.setText("Nesting:");

		txtNesting = new Text(grF, SWT.BORDER);
		txtNesting.setBounds(123, 75, 141, 21);

		Button cbKeepNesting = new Button(grF, SWT.CHECK);
		cbKeepNesting.setBounds(270, 78, 55, 16);
		cbKeepNesting.setText("Keep");

		lblVB = new Label(grF, SWT.NONE);
		lblVB.setBounds(51, 129, 62, 15);
		lblVB.setText("Variable BD:");

		txtVB = new Text(grF, SWT.BORDER);
		txtVB.setBounds(123, 126, 141, 21);

		cbKeepVB = new Button(grF, SWT.CHECK);
		cbKeepVB.setBounds(270, 128, 88, 16);
		cbKeepVB.setText("Keep (Only)");
		cbKeepVB.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				super.widgetSelected(e);
				Button btn = (Button) e.getSource();
				IS_KEEP_VB = btn.getSelection();
			}

		});

		lblES = new Label(grF, SWT.NONE);
		lblES.setBounds(52, 156, 67, 15);
		lblES.setText("Error Status:");

		txtES = new Text(grF, SWT.BORDER);
		txtES.setBounds(123, 153, 141, 21);

		final Combo combFormat = new Combo(grF, SWT.NONE | SWT.READ_ONLY);
		combFormat.setBounds(123, 40, 203, 23);

		combFormat.setItems(ITEMS);
		combFormat.setText(ITEMS[0]);
		combFormat.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				super.widgetSelected(e);
				if (combFormat.getText().equals("snmptracing.log")) {
					setLayoutSNMP(true);

				} else {
					setLayoutSNMP(false);
				}
			}

		});

		cbKeepNesting.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				super.widgetSelected(e);
				Button btn = (Button) e.getSource();
				IS_KEEP_N = btn.getSelection();
			}
		});

		grOK = new Group(shell, SWT.NONE);
		GridData gd_grOK = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_grOK.heightHint = 188;
		grOK.setLayoutData(gd_grOK);
		btnExportFile = new Button(grOK, SWT.CHECK);
		btnExportFile.setBounds(58, 131, 93, 16);
		btnExportFile.setText("Export File");
		btnOpenA = new Button(grOK, SWT.CHECK);
		btnOpenA.setBounds(58, 153, 195, 16);
		btnOpenA.setText("Open when export complete");
		btnOpenA.setEnabled(false);

		lblFileName = new Label(grOK, SWT.NONE);
		lblFileName.setBounds(47, 69, 55, 15);
		lblFileName.setText("File name:");
		lblFileName.setEnabled(false);

		txtFileName = new Text(grOK, SWT.BORDER);
		txtFileName.setBounds(109, 66, 98, 21);
		txtFileName.setEnabled(false);

		btnURLOut = new Button(grOK, SWT.NONE);
		btnURLOut.setBounds(27, 35, 75, 25);
		btnURLOut.setText("URL Output");
		btnURLOut.setEnabled(false);

		txtURLOut = new Text(grOK, SWT.BORDER | SWT.READ_ONLY);
		txtURLOut.setBounds(108, 37, 201, 21);

		btnFind = new Button(grOK, SWT.NONE);
		btnFind.setBounds(120, 187, 108, 25);
		btnFind.setText("OKE");

		btnFind.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FOUNDED = false;
				switch (TAB_INDEX) {
				case 0:
					FOUNDED = false;
					if (!HAVE_LEVEL)
						LVNULL = true;
					else
						LVNULL = false;
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
									validLvlInput(spinner.getText()), createMap(formatStr("")), false, ONLY_ATTR);
							if (!FOUNDED) {
								createMes(shell, "ERROR", "Not Found");
								styledText.setText("");
								return;
							} else {
								if (!HAVE_EXPORT) {
									styledText.setText(STR_BUILDER.toString());
									createMes(shell, "NOTIFICATION", "Search complete!!!");
								}
							}
							// toPrettyString(STR_BUILDER.toString(), 4);
							if (HAVE_EXPORT) {
								exportXML(createURLOut(txtFileName.getText()), STR_BUILDER, HAVE_OPEN);
								if (!HAVE_OPEN) {
									createMes(shell, "NOTIFICATION", "Export complete!!!");
								}
							}
						} catch (JAXBException e1) {
							// TODO Auto-generated catch block
							createMes(shell, "Error", "Input invalid");
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							createMes(shell, "Error", "Input invalid");
							e1.printStackTrace();
						}

					} else {
						try {
							STR_BUILDER.setLength(0);
							parseXML(SELECTED, txtTagName.getText(), txtChildTag.getText(),
									validLvlInput(spinner.getText()), createMap(formatStr(txtAttri.getText())), true,
									ONLY_ATTR);
							if (!FOUNDED) {
								createMes(shell, "ERROR", "Not Found");
								styledText.setText("");
								return;
							} else {
								if (!HAVE_EXPORT) {
									styledText.setText(STR_BUILDER.toString());
									createMes(shell, "NOTIFICATION", "Search complete!!!");
								}
							}
							if (HAVE_EXPORT) {
								exportXML(createURLOut(txtFileName.getText()), STR_BUILDER, HAVE_OPEN);
								if (!HAVE_OPEN) {
									createMes(shell, "NOTIFICATION", "Export complete!!!");
								}
							}

						} catch (JAXBException e1) {
							// TODO Auto-generated catch block
							createMes(shell, "Error", "Input invalid");
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							createMes(shell, "Error", "Input invalid");
							e1.printStackTrace();
						}
					}
					break;
				case 1:
					if (txtFolder.getText().isEmpty()) {
						createMes(shell, "ERROR", "Please choose folder input!!!");
						return;
					} else if (combFormat.getText().isEmpty()) {
						createMes(shell, "ERROR", "Format file can be not empty!!!");
						return;
					} else if (txtNE.getText().isEmpty() && txtNesting.getText().isEmpty()) {
						createMes(shell, "ERROR", "Only one NE IP or Nesting  can be empty!!!");
						return;
					}

					if (HAVE_EXPORT) {
						List<String> lss = Constant.listFile(URL_FILTER, combFormat.getText());
						for (String ls : lss) {
							try {
								exportXML(txtURLOut.getText() + "\\" + createPathFilter(ls),
										FilterSNMP.readFileP(URL_FILTER + "\\" + ls, txtNE.getText(),
												txtNesting.getText(), txtVB.getText(), txtES.getText(), IS_KEEP_N,
												IS_KEEP_VB),
										!HAVE_OPEN);
								// System.out.println(ls);
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								createMes(shell, "Error", "Input invalid");
								e1.printStackTrace();
							}
						}
						
						createMes(shell, "NOTIFICATION", "Export complete!!!");
					}
					break;
				default:
					break;
				}

			}
		});

		btnURLOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				super.widgetSelected(e);
				DirectoryDialog dd = new DirectoryDialog(shell, SWT.OPEN);
				dd.setText("Open");
				dd.setFilterPath(SELECTED);
				dd.setFilterPath("d:\\");
				if ((URL_OUT = dd.open()) == null)
					return;
				else
					txtURLOut.setText(URL_OUT);
			}
		});

		btnOpenA.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				super.widgetSelected(e);
				Button btn = (Button) e.getSource();
				HAVE_OPEN = btn.getSelection();
			}

		});

		btnExportFile.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				super.widgetSelected(e);
				Button btn = (Button) e.getSource();
				if (!btn.getSelection()) {
					txtFileName.setEnabled(false);
					lblFileName.setEnabled(false);
					btnURLOut.setEnabled(false);
					btnOpenA.setEnabled(false);
				} else {
					txtFileName.setEnabled(true);
					lblFileName.setEnabled(true);
					btnURLOut.setEnabled(true);
					btnOpenA.setEnabled(true);
				}
				HAVE_EXPORT = btn.getSelection();
			}

		});

		styledText = new StyledText(shell, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		styledText.addLineStyleListener(new LineStyleListener() {

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
			public void modifyText(ModifyEvent e) {
			}
		});
		// test();
	}

	public void setLayoutSNMP(boolean bl) {
		lblNE.setVisible(bl);
		txtNE.setVisible(bl);
		lblVB.setVisible(bl);
		txtVB.setVisible(bl);
		lblES.setVisible(bl);
		txtES.setVisible(bl);
		cbKeepVB.setVisible(bl);
	}

	public void setEnable(boolean bl) {
		HAVE_EXPORT = bl;
		lblFileName.setEnabled(!bl);
		txtFileName.setEnabled(!bl);
		btnURLOut.setEnabled(bl);
		btnOpenA.setEnabled(!bl);
		btnOpenA.setSelection(!bl);
	}
}