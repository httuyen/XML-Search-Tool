package com.ui;

import java.io.IOException;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
	private Group group;
	private Text txtLevel;
	public static StyledText styledText;

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
		shell.setSize(659, 595);
		shell.setText("Search Tool");
		shell.setLayout(new GridLayout(1, false));
		;

		group = new Group(shell, SWT.NONE);
		GridData gd_group = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_group.heightHint = 137;
		gd_group.widthHint = 633;
		group.setLayoutData(gd_group);

		Button btnOpenFile = new Button(group, SWT.NONE);
		btnOpenFile.setBounds(10, 10, 62, 25);
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

		txtPath = new Text(group, SWT.BORDER);
		txtPath.setBounds(77, 12, 238, 21);
		txtPath.setEnabled(false);

		lblParentTag = new Label(group, SWT.NONE);
		lblParentTag.setBounds(10, 44, 63, 15);
		lblParentTag.setText("Parent Tag:");

		lblChildTag = new Label(group, SWT.NONE);
		lblChildTag.setBounds(20, 70, 53, 15);
		lblChildTag.setText("Child Tag:");

		txtTagName = new Text(group, SWT.BORDER);
		txtTagName.setBounds(78, 41, 237, 21);

		txtChildTag = new Text(group, SWT.BORDER);
		txtChildTag.setBounds(78, 67, 237, 21);

		btnFind = new Button(group, SWT.NONE);
		btnFind.setBounds(249, 120, 89, 25);
		btnFind.setText("Find");

		lblAttr = new Label(group, SWT.NONE);
		lblAttr.setBounds(323, 44, 53, 15);
		lblAttr.setText("Attribute:");

		txtAttri = new Text(group, SWT.BORDER);
		txtAttri.setBounds(382, 41, 195, 21);

		btnOnlyAttr = new Button(group, SWT.CHECK);
		btnOnlyAttr.setBounds(583, 43, 46, 16);
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

		btnOnlyChild = new Button(group, SWT.CHECK);
		btnOnlyChild.setBounds(77, 96, 57, 16);
		btnOnlyChild.setText("Only");
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

		Button btnLevel = new Button(group, SWT.CHECK);
		btnLevel.setBounds(187, 96, 93, 16);
		btnLevel.setText("with level tag");
		btnLevel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				HAVE_LEVEL = btn.getSelection();
				if(btn.getSelection()) {
					txtLevel.setEnabled(true);
				}else {
					txtLevel.setText("0");
					txtLevel.setEnabled(false);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		txtLevel = new Text(group, SWT.BORDER);
		txtLevel.setBounds(286, 94, 29, 21);
		txtLevel.setEnabled(false);
		txtLevel.setText("0");
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
						STR_BUILDER.setLength(0);
						parseXML(SELECTED, txtTagName.getText(), txtChildTag.getText(),
								validLvlInput(txtLevel.getText()), createMap(formatStr("")), false, ONLY_ATTR);
						styledText.setText(STR_BUILDER.toString());
					} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e1) {
						e1.printStackTrace();
					}

				} else {
					try {
						STR_BUILDER.setLength(0);
						parseXML(SELECTED, txtTagName.getText(), txtChildTag.getText(),
								validLvlInput(txtLevel.getText()), createMap(formatStr(txtAttri.getText())), true,
								ONLY_ATTR);
						styledText.setText(STR_BUILDER.toString());
					} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		styledText = new StyledText(shell, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

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
				//styledText.redraw();
//				for(int i = 0;i<=90;i++) {
//					styledText.append("\n");	
//				}
			}
		});
	}
}
