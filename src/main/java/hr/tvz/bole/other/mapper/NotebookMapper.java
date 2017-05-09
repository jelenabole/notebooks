package hr.tvz.bole.other.mapper;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.web.form.NotebookForm;

public class NotebookMapper {

	// TODO - mapNotebookToNotebookForm
	// public static Notebook mapNotebooktoForm(Notebook notebook)

	public static Notebook mapFormToNotebook(NotebookForm notebookForm) {
		return new Notebook(notebookForm.getId(), notebookForm.getTitle(), notebookForm.getDescription());
	}
}
