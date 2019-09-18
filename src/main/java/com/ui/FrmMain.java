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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.xml.sax.SAXException;

import com.constant.Constant;
import com.process.NodeType;
import com.process.ReadWriteDataFromFile;

public class FrmMain extends Constant {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FrmMain window = new FrmMain();
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
		shell.setLayout(new GridLayout(1, false));

		final ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		// item file
		final ToolItem itemFile = new ToolItem(toolBar, SWT.DROP_DOWN);
		itemFile.setText("File");

		final Menu fileMenu = new Menu(toolBar);
		MenuItem openFile = new MenuItem(fileMenu, SWT.PUSH);
		openFile.setText("&Open\tCTRL+O");
		openFile.setAccelerator(SWT.CTRL + 'O');

		itemFile.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Rectangle rect = itemFile.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					pt = toolBar.toDisplay(pt);
					fileMenu.setLocation(pt.x, pt.y);
					fileMenu.setVisible(true);
				}
			}
		});

		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.DROP_DOWN);
		tltmNewItem_1.setText("Search");

		final StyledText styledText = new StyledText(shell, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		styledText.addLineStyleListener(new LineStyleListener() {
			@Override
			public void lineGetStyle(LineStyleEvent event) {
				// Using ST.BULLET_NUMBER sometimes results in weird alignment.
				// event.bulletIndex = styledText.getLineAtOffset(event.lineOffset);
				StyleRange styleRange = new StyleRange();
				styleRange.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
				int maxLine = styledText.getLineCount();
				int bulletLength = Integer.toString(maxLine).length();
				// Width of number character is half the height in monospaced font, add 1
				// character width for right padding.
				int bulletWidth = (bulletLength + 1) * styledText.getLineHeight() / 2;
				styleRange.metrics = new GlyphMetrics(0, 0, bulletWidth);
				event.bullet = new Bullet(ST.BULLET_TEXT, styleRange);
				// getLineAtOffset() returns a zero-based line index.
				int bulletLine = styledText.getLineAtOffset(event.lineOffset) + 1;
				event.bullet.text = String.format("%" + bulletLength + "s", bulletLine);
			}
		});
		styledText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				styledText.redraw();
			}
		});

		class Open implements SelectionListener {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Open");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.xml", "*.txt", "*.doc", ".rtf", "*.*" };
				fd.setFilterExtensions(filterExt);
				SELECTED = fd.open();
				try {
					//NodeType.parseXML(SELECTED);
					ReadWriteDataFromFile.readData(SELECTED);
					styledText.setText(TEXT);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

		}

		openFile.addSelectionListener(new Open());

	}

}
