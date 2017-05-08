package hr.tvz.bole.service;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.repository.NotebookRepository;

@Service
public class NotebookEditor extends PropertyEditorSupport {

	@Autowired
	NotebookRepository notebookRepository;
	
	@Override
	public void setAsText(String text) {
		Notebook currentNotebook = null;
		for (Notebook notebook : notebookRepository.findAll()) {
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
