package com.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class FrmMain {

	protected Shell shell;
	/**
	 * Launch the application.
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
		
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.setText("File");
		
		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_1.setText("Search");
		
		final StyledText styledText = new StyledText(shell, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		
		styledText.addLineStyleListener(new LineStyleListener() {
		    @Override
		    public void lineGetStyle(LineStyleEvent event) {
		        // Using ST.BULLET_NUMBER sometimes results in weird alignment.
		        //event.bulletIndex = styledText.getLineAtOffset(event.lineOffset);
		        StyleRange styleRange = new StyleRange();
		        styleRange.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
		        int maxLine = styledText.getLineCount();
		        int bulletLength = Integer.toString(maxLine).length();
		        // Width of number character is half the height in monospaced font, add 1 character width for right padding.
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
				// TODO Auto-generated method stub
				styledText.redraw();
			}
		});
		
	}

}
