using PageBook.ViewModels;

namespace PageBook.Views;
public partial class ToDoEditorPage : ContentPage
{
    private ToDoEditorViewModel _viewModel;

    public ToDoEditorPage(ToDoEditorViewModel viewModel)
    {
        InitializeComponent();
        BindingContext = _viewModel = viewModel;
    }
}