using PageBook.Models;
using PageBook.ViewModels;

namespace PageBook.Views;

public partial class EditorPage : ContentPage
{
    private readonly EditorViewModel viewModel;
	public EditorPage()
    {
        InitializeComponent();
        BindingContext = viewModel = new EditorViewModel(Navigation, noteEditor);
    }
    public EditorPage(Note note) : this()
    {
        viewModel.Content = note.Content;
        //viewModel.Title = note.Title;
        viewModel.id = note.Id;
    }
}

