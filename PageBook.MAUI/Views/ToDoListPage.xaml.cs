using System.ComponentModel;
using PageBook.Models;
using PageBook.ViewModels;

namespace PageBook.Views;

public partial class ToDoListPage : ContentPage
{
    private ToDoListViewModel _viewModel;

    public ToDoListPage()
    {
        InitializeComponent();
        BindingContext = _viewModel = new ToDoListViewModel(Navigation);
    }

    [Obsolete]
    private async void ToDoSelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        if ((ToDo)e.CurrentSelection != null)
        {
            await ((ToDoListViewModel)BindingContext).EditToDoItemAsync((ToDo)e.CurrentSelection);
            ((CollectionView)sender).SelectedItem = null;
        }
    }
}