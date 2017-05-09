package hr.tvz.bole.other.mapper;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.web.form.NotebookForm;

public class NotebookMapper {

	public static NotebookForm mapNotebookToForm(Notebook notebook) {
		NotebookForm notebookForm = new NotebookForm();
		notebookForm.setId(notebook.getId());
		notebookForm.setTitle(notebook.getTitle());
		notebookForm.setDescription(notebook.getDescription());
		
		return notebookForm;
	}
	
	public static Notebook mapFormToNotebook(NotebookForm notebookForm) {
		Notebook notebook = new Notebook();
		notebook.setId(notebookForm.getId());
		notebook.setTitle(notebookForm.getTitle());
		notebook.setDescription(notebookForm.getDescription());
		
		return notebook;
	}
}
