using PageBook.Models;
using PageBook.ViewModels;

namespace PageBook.Views;
public partial class ToDoEditorPage : ContentPage
{
    private ToDoEditorViewModel viewModel;
    public ToDoEditorPage()
    {
        InitializeComponent();
        BindingContext = viewModel = new ToDoEditorViewModel(Navigation);
    }

    public ToDoEditorPage(ToDo item)
    {
        InitializeComponent();
        BindingContext = viewModel = new ToDoEditorViewModel(Navigation, item);
    }
}