package com.darkbrook.library.gui;

public abstract class DefaultGuiInterface implements GuiInterface {

	private Gui gui;
	private String title;
	
	@Override
	public void setGui(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public Gui getGui() {
		return gui;
	}
	
	@Override
	public String getTitle() {
		return title;
	}

}
