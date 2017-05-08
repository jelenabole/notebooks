package hr.tvz.bole.service;

import java.beans.PropertyEditorSupport;

import hr.tvz.bole.MockHelper;
import hr.tvz.bole.model.Notebook;

public class NotebookEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) {
		Notebook currentNotebook = null;
		for (Notebook notebook : MockHelper.mockNotebookList()) {
			if (notebook.getId().equals(Integer.parseInt(text))) {
				currentNotebook = notebook;
				break;
			}
		}
		this.setValue(currentNotebook);
	}

	@Override
	public String getAsText() {
		Notebook notebook = (Notebook) this.getValue();
		return (notebook != null ? notebook.getId().toString() : "");
	}
}
