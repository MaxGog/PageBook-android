using PageBook.ViewModels;

namespace PageBook.Views;

public partial class ToDoListPage : ContentPage
{
    private ToDoListViewModel _viewModel;

    public ToDoListPage(ToDoListViewModel viewModel)
    {
        InitializeComponent();
        BindingContext = _viewModel = viewModel;
    }
}